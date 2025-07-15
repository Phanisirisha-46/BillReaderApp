package com.example.billreaderapp;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.BillViewHolder> {

    private List<Map<String, Object>> billsList;
    private Context context;
    private TextToSpeech tts;
    private float fontSize;

    public BillsAdapter(List<Map<String, Object>> billsList, Context context, float fontSize) {
        this.billsList = billsList;
        this.context = context;
        this.fontSize = fontSize;

        tts = new TextToSpeech(context, status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(new Locale("te", "IN")); // Telugu; change if needed
            }
        });
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        Map<String, Object> bill = billsList.get(position);
        String text = (String) bill.get("originalText");
        String timestamp = (String) bill.get("timestamp");
        String docId = (String) bill.get("docId");

        holder.textViewBill.setText(text);
        holder.textViewTimestamp.setText(timestamp);

        // Apply font size
        holder.textViewBill.setTextSize(fontSize);
        holder.textViewTimestamp.setTextSize(fontSize - 2);

        holder.btnDelete.setOnClickListener(v -> {
            FirebaseFirestore.getInstance().collection("bills")
                    .document(docId)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        billsList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, billsList.size());
                        Toast.makeText(context, "Bill deleted", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show());
        });

        holder.btnRead.setOnClickListener(v -> {
            if (tts != null) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return billsList.size();
    }

    public void setFontSize(float newSize) {
        this.fontSize = newSize;
        notifyDataSetChanged();
    }

    public static class BillViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBill, textViewTimestamp;
        Button btnDelete, btnRead;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBill = itemView.findViewById(R.id.textViewBill);
            textViewTimestamp = itemView.findViewById(R.id.textViewTimestamp);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnRead = itemView.findViewById(R.id.btnReadAloud);
        }
    }
}
