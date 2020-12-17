package com.example.cnm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button btnDK,btnDN;
    EditText etID,etPass;
    static final String host = "192.168.43.73";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getActionBar().hide();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("TIÊU ĐỀ ACTIVITY"); //Thiết lập tiêu đề nếu muốn
        String title = actionBar.getTitle().toString(); //Lấy tiêu đề nếu muốn
        actionBar.hide();

        //ánh xạ
        btnDK = findViewById(R.id.btnDangKi);
        btnDN = findViewById(R.id.btnDangNhap);
        etID = findViewById(R.id.editTextID);
        etPass = findViewById(R.id.editTextTextPassword);
        //xử lý button
        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,XacNhanID.class);
                startActivity(intent);
            }
        });
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String number = etID.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    etID.setError("Valid number is required");
                    etID.requestFocus();
                    return;
                }
                else if(number.equals("9999999999")){
                    Intent intent = new Intent(MainActivity.this,Admin.class);
                    intent.putExtra("phonenumber", number);
                    startActivity(intent);
                }
                else {
                    final OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("user", number)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://"+host+":3000/users/getuser")
                            .patch(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("Error",e.getMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Moshi moshi = new Moshi.Builder().build();
                            Type usersType = Types.newParameterizedType(List.class, MyUser.class);
                            final JsonAdapter<List<MyUser>> jsonAdapter = moshi.adapter(usersType);
                            String json = response.body().string();
                            String s = "["+json+"]";
                            final List<MyUser> list = jsonAdapter.fromJson(s);
                            if(list.size()!=0){
                                MyUser u = list.get(0);
                                if(u.getPass().equals(etPass.getText().toString().trim())){
                                    if(u.isStt()){
                                        Intent intent = new Intent(MainActivity.this,Home.class);
                                        intent.putExtra("phonenumber", number);
                                        startActivity(intent);
                                    }
                                    else{
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(MainActivity.this,"Tài khoản đã bị khóa hoặc chưa được kích hoạt",Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                }
                                else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            etPass.setError("Sai mật khẩu");
                                            etPass.requestFocus();
                                            return;
                                        }
                                    });
                                }
                            }
                            else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this,"Tài khoản không tồn tại",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
}