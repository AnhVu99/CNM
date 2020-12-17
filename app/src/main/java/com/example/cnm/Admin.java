package com.example.cnm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Admin extends AppCompatActivity {
    EditText etSearch;
    Button btSearch;
    RecyclerView rec;
    //List<MyUser> list;
    AdapterRecAD adapterRecAD;
    static final String host = "192.168.43.73";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        etSearch = findViewById(R.id.editTextSearch_Ad);
        btSearch = findViewById(R.id.buttonSearch_ad);
        rec = findViewById(R.id.rec_ad);
        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();
        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Type usersType = Types.newParameterizedType(List.class, MyUser.class);
        final JsonAdapter<List<MyUser>> jsonAdapter = moshi.adapter(usersType);
        Request request = new Request.Builder()
                .url("http://"+host+":3000/users/getall")// link gủi request
                .build()
                ;
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Error", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();
                final List<MyUser> list = jsonAdapter.fromJson(json);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterRecAD = new AdapterRecAD(Admin.this,list);
                        rec.setLayoutManager(new GridLayoutManager(Admin.this, 1,GridLayoutManager.VERTICAL,false));
                        rec.setAdapter(adapterRecAD);
                    }
                });


            }
        });
    }
}