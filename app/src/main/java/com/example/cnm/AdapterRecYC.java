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

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AdapterRecYC extends RecyclerView.Adapter<ViewHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<MyUser> arrayList;
    String id;
    static final String host = "192.168.43.73";

    public AdapterRecYC(Context context, ArrayList<MyUser> arrayList,String ID) {
        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        id = ID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItem = layoutInflater.inflate(R.layout.layout_item_yeucau,parent,false);
        return new ViewHolder(mItem,this);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        MyUser db = arrayList.get(position);
        holder.img.setImageResource(db.getImg());
        holder.tvSDT.setText(db.getId());
        holder.tvName.setText(db.getName());
        int actualPosition = holder.getAdapterPosition();
        notifyItemRangeChanged(actualPosition, arrayList.size());
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody;
                Request request;
                requestBody = new FormBody.Builder()
                        .add("user",holder.tvSDT.getText().toString())
                        .add("userrc",id)
                        .build();
                request = new Request.Builder()
                        .url("http://"+host+":3000/friends/remove")
                        .delete(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });
                arrayList.remove(position);
            }
        });

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody;
                Request request;
                requestBody = new FormBody.Builder()
                        .add("user",holder.tvSDT.getText().toString())
                        .add("userrc",id)
                        .build();
                request = new Request.Builder()
                        .url("http://"+host+":3000/friends/remove")
                        .delete(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });
                //
                requestBody = new FormBody.Builder()
                        .add("user",id)
                        .add("userrc",holder.tvSDT.getText().toString())//add zo
                        .build();
                request = new Request.Builder()
                        .url("http://"+host+":3000/friends/add")
                        .put(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });
//                arrayList.remove(position);
////                arrayList.notify();
////                notifyDataSetChanged();
//                notifyItemRemoved(position);
//                //notifyItemRangeRemoved(position,arrayList.size());
//                notifyItemRangeChanged(position,arrayList.size());
//                AdapterRecYC.this.notifyAll();
////                recycler.removeViewAt(position);
////                mAdapter.notifyItemRemoved(position);
////                mAdapter.notifyItemRangeChanged(position, list.size());
//                arrayList.remove(position);
//                recycler.removeViewAt(position);
//                mAdapter.notifyItemRemoved(position);
//                mAdapter.notifyItemRangeChanged(position, list.size());
            }
        });
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
