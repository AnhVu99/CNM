package com.example.cnm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecDB extends RecyclerView.Adapter<WordViewHolder>{
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<itemDB> arrayList;

    public AdapterRecDB(Context context, ArrayList<itemDB> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItem = layoutInflater.inflate(R.layout.layout_item_db,parent,false);
        return new WordViewHolder(mItem,this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        itemDB db = arrayList.get(position);
        holder.img.setImageResource(db.getAnh());
        holder.tvSDT.setText(db.getSDT());
        holder.tvName.setText(db.getTen());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
class WordViewHolder extends RecyclerView.ViewHolder {

    ImageView img;
    TextView tvName,tvSDT;
    Button btn;
    AdapterRecDB adap;
    CardView cardView;

    public WordViewHolder(@NonNull View itemView,AdapterRecDB adapterRecDB) {
        super(itemView);
        cardView = itemView.findViewById(R.id.cardView);
        img = cardView.findViewById(R.id.imageView_item_DB);
        tvName = itemView.findViewById(R.id.textViewName_item_DB);
        tvSDT = itemView.findViewById(R.id.textViewSDT_item_DB);
        btn = itemView.findViewById(R.id.buttonMes_item_DB);
        adap = adapterRecDB;
    }
}
