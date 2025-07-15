package com.example.billreaderapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 100;
    public static final int GALLERY_REQUEST = 101;
    private Uri imageUri;

    private ImageView imageViewPreview;
    private TextView textViewResult;
    private TextToSpeech tts;

    private String extractedText = "";

    private float currentFontSize = 16f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button cameraButton = findViewById(R.id.btnCamera);
        Button galleryButton = findViewById(R.id.btnGallery);
        Button showSavedButton = findViewById(R.id.btnShowSaved);
        imageViewPreview = findViewById(R.id.imageViewPreview);
        textViewResult = findViewById(R.id.textViewResult);

        // Access font buttons from custom action bar include
        LinearLayout customBar = findViewById(R.id.custom_action_bar);
        Button btnIncreaseFont = customBar.findViewById(R.id.btnIncreaseFont);
        Button btnDecreaseFont = customBar.findViewById(R.id.btnDecreaseFont);

        btnIncreaseFont.setOnClickListener(v -> {
            currentFontSize += 2f;
            textViewResult.setTextSize(currentFontSize);
        });

        btnDecreaseFont.setOnClickListener(v -> {
            if (currentFontSize > 10f) {
                currentFontSize -= 2f;
                textViewResult.setTextSize(currentFontSize);
            }
        });

        cameraButton.setOnClickListener(v -> openCamera());
        galleryButton.setOnClickListener(v -> openGallery());
        showSavedButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SavedBillsActivity.class);
            startActivity(intent);
        });

        tts = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(new Locale("te", "IN"));
            }
        });
    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_REQUEST);
        } else {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageViewPreview.setImageBitmap(photo);
                runTextRecognition(photo);
            } else if (requestCode == GALLERY_REQUEST) {
                imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    imageViewPreview.setImageBitmap(bitmap);
                    runTextRecognition(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void runTextRecognition(Bitmap bitmap) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        recognizer.process(image)
                .addOnSuccessListener(visionText -> {
                    StringBuilder extractedBuilder = new StringBuilder();
                    for (com.google.mlkit.vision.text.Text.TextBlock block : visionText.getTextBlocks()) {
                        for (com.google.mlkit.vision.text.Text.Line line : block.getLines()) {
                            extractedBuilder.append(line.getText()).append("\n");
                        }
                    }
                    extractedText = extractedBuilder.toString().trim();
                    if (!extractedText.isEmpty()) {
                        showExtractedText(extractedText);
                    } else {
                        Toast.makeText(this, "No text found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "OCR Failed", Toast.LENGTH_SHORT).show());
    }

    private void showExtractedText(String text) {
        runOnUiThread(() -> {
            textViewResult.setText("Text: " + text);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            saveTextToFirestore(text);
        });
    }

    private void saveTextToFirestore(String text) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> bill = new HashMap<>();
        bill.put("originalText", text);
        bill.put("timestamp", FieldValue.serverTimestamp());

        db.collection("bills").add(bill)
                .addOnSuccessListener(documentReference ->
                        Toast.makeText(MainActivity.this, "Text saved to Firestore", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(MainActivity.this, "Failed to save text", Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
