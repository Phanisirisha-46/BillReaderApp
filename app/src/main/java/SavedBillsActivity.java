package com.example.billreaderapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SavedBillsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BillsAdapter adapter;
    private List<Map<String, Object>> billsList = new ArrayList<>();

    private float currentFontSize = 16f; // Default font size

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_bills);

        recyclerView = findViewById(R.id.recyclerViewBills);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BillsAdapter(billsList, this, currentFontSize);
        recyclerView.setAdapter(adapter);

        // Get buttons
        Button btnIncreaseFont = findViewById(R.id.btnIncreaseFont);
        Button btnDecreaseFont = findViewById(R.id.btnDecreaseFont);

        btnIncreaseFont.setOnClickListener(view -> {
            currentFontSize += 2f;
            adapter.setFontSize(currentFontSize);
        });

        btnDecreaseFont.setOnClickListener(view -> {
            currentFontSize = Math.max(10f, currentFontSize - 2f); // Limit min
            adapter.setFontSize(currentFontSize);
        });

        fetchBills();
    }

    private void fetchBills() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("bills")
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    billsList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Map<String, Object> bill = new HashMap<>();
                        bill.put("originalText", document.getString("originalText"));

                        // Format timestamp
                        Date date = document.getTimestamp("timestamp") != null
                                ? document.getTimestamp("timestamp").toDate()
                                : new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
                        String formattedTime = sdf.format(date);
                        bill.put("timestamp", formattedTime);

                        // Add docId for delete
                        bill.put("docId", document.getId());

                        billsList.add(bill);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SavedBillsActivity.this, "Failed to fetch bills", Toast.LENGTH_SHORT).show();
                });
    }
}
