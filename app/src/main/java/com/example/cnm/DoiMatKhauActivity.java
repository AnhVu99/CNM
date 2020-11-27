package com.example.cnm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

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

public class DoiMatKhauActivity extends AppCompatActivity {
    EditText etMKCu,etMKmoi,etXacNhan;
    Button btnXacNhan;
    FrameLayout frag;
    RequestBody requestBody;
    Request request;
    MyUser user;
    static final String host = "192.168.100.171";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        etMKCu = findViewById(R.id.editTextPassword_DK);
        etMKmoi = findViewById(R.id.editTextPassMoi_MK);
        etXacNhan = findViewById(R.id.editTextPasswordConfrim_DK);
        btnXacNhan = findViewById(R.id.buttonXacNhan_MK);
        frag = findViewById(R.id.fragverify_MK);
        // Khởi tạo OkHttpClient để lấy dữ liệu.
        final OkHttpClient client = new OkHttpClient();
        final String phonenumber = getIntent().getStringExtra("phonenumber");

        requestBody = new FormBody.Builder()
                .add("user", phonenumber)
                .build();
        request = new Request.Builder()
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
                user = list.get(0);
            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldP = etMKCu.getText().toString().trim();
                String newP = etMKmoi.getText().toString().trim();
                String conP = etXacNhan.getText().toString().trim();
                if(!oldP.equals(user.getPass())){
                    etMKCu.setError("Mật khẩu cũ không đúng");
                    etMKCu.requestFocus();
                    return;
                }
                if(newP.isEmpty()){
                    etMKmoi.setError("Mật khẩu không được trống");
                    etMKmoi.requestFocus();
                    return;
                }
                if(!conP.equals(newP)){
                    etXacNhan.setError("Xác nhậnn mật khẩu không khớp");
                    etXacNhan.requestFocus();
                    return;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragverify_MK,Fragment_XacNhan_MK.newInstance(phonenumber,newP)).commit();
            }
        });
    }
}