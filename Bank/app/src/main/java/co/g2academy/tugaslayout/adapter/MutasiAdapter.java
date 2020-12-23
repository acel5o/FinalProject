package co.g2academy.tugaslayout.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import co.g2academy.tugaslayout.R;
import co.g2academy.tugaslayout.model.Mutasi;

public class MutasiAdapter extends RecyclerView.Adapter<MutasiAdapter.MutasiViewHolder> {
    Context context;
    ArrayList<Mutasi> mutasi;

    Locale localeID = new Locale("in", "ID");
    NumberFormat rupiah = NumberFormat.getCurrencyInstance(localeID);

    public MutasiAdapter(Context context, ArrayList<Mutasi> mutasi) {
        this.context = context;
        this.mutasi = mutasi;
    }

    public MutasiAdapter.MutasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mutasi, parent, false);
        return new  MutasiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MutasiAdapter.MutasiViewHolder holder, int position) {
        holder.tanggal.setText(mutasi.get(position).getTanggal());
        holder.tipe.setText(mutasi.get(position).getTipe());
        holder.dana.setText(rupiah.format(Long.valueOf(mutasi.get(position).getDana())));
    }

    @Override
    public int getItemCount() {
        return mutasi.size();
    }

    public class MutasiViewHolder extends RecyclerView.ViewHolder{
        TextView tanggal;
        TextView tipe;
        TextView dana;

        public MutasiViewHolder(@NonNull View itemView) {
            super(itemView);

            tanggal = itemView.findViewById(R.id.tanggalTextView);
            tipe = itemView.findViewById(R.id.tipeTextView);
            dana = itemView.findViewById(R.id.totalTextView);
        }
    }
}
