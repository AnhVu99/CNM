package com.example.cnm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AdapterRecAD extends RecyclerView.Adapter<ADViewHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<MyUser> arrayList;
    public AdapterRecAD(Context context, List<MyUser> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ADViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItem = layoutInflater.inflate(R.layout.layout_item_admin,parent,false);
        return new ADViewHolder(mItem,this);
    }

    @Override
    public void onBindViewHolder(@NonNull final ADViewHolder holder, int position) {
        final MyUser user = arrayList.get(position);
        //holder.img.setImageResource(user.getImg());
        holder.tvID.setText(user.getId());
        holder.tvName.setText(user.getName());
        if (user.isStt())
            holder.aSwitch.setChecked(true);
        else
            holder.aSwitch.setChecked(false);
        final  OkHttpClient client = new OkHttpClient();
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                RequestBody requestBody;
                if (user.isStt()) {
                    requestBody = new FormBody.Builder()
                            .add("user", user.getId())
                            .add("trangthai",String.valueOf(Boolean.FALSE))
                            .build();

                } else {

                    // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
                    requestBody = new FormBody.Builder()
                            .add("user", user.getId())
                            .add("trangthai",String.valueOf(Boolean.TRUE))
                            .build();

                }
                Request request = new Request.Builder()
                        .url("http://10.156.167.139:3000/users/updatett")
                        .put(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("Error", e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ((Admin)context).runOnUiThread(new Runnable() {
                                                           @Override
                                                           public void run() {
                                                               Toast.makeText(context,"Thanh Công",Toast.LENGTH_LONG).show();
                                                           }
                                                       }

                        );
                    }
                });



            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
class ADViewHolder extends RecyclerView.ViewHolder {
    TextView tvName,tvID;
    ImageView img;
    CardView cardView;
    Switch aSwitch;
    AdapterRecAD adap;
    public ADViewHolder(@NonNull View itemView,AdapterRecAD adapterRecAD) {
        super(itemView);
        tvName = itemView.findViewById(R.id.textViewName_item_AD);
        tvID = itemView.findViewById(R.id.textViewSDT_item_AD);
        cardView = itemView.findViewById(R.id.cardView2);
        img = cardView.findViewById(R.id.imageView_item_AD);
        aSwitch = itemView.findViewById(R.id.switch_AD);
        adap = adapterRecAD;
    }
}
