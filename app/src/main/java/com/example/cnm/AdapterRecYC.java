package com.example.cnm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecYC extends RecyclerView.Adapter<ViewHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<MyUser> arrayList;

    public AdapterRecYC(Context context, ArrayList<MyUser> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItem = layoutInflater.inflate(R.layout.layout_item_yeucau,parent,false);
        return new ViewHolder(mItem,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyUser db = arrayList.get(position);
        holder.img.setImageResource(db.getImg());
        holder.tvSDT.setText(db.getId());
        holder.tvName.setText(db.getName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
class ViewHolder extends RecyclerView.ViewHolder {

    ImageView img;
    TextView tvName,tvSDT;
    Button btnAccept,btnRemove;
    AdapterRecYC adap;

    public ViewHolder(@NonNull View itemView, AdapterRecYC adapterRecYC) {
        super(itemView);
        img = itemView.findViewById(R.id.imageView_item_YC);
        tvName = itemView.findViewById(R.id.textViewName_item_YC);
        tvSDT = itemView.findViewById(R.id.textViewSDT_item_YC);
        btnAccept = itemView.findViewById(R.id.buttonConf_YC);
        btnRemove = itemView.findViewById(R.id.buttonRemove_YC);
        adap = adapterRecYC;
    }
}
