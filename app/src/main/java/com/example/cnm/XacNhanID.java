package com.example.cnm;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.xml.sax.helpers.XMLReaderAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class XacNhanID extends AppCompatActivity {
    Button btnNext,btnGuiMa;
    RadioButton email,sdt;
    TextView chon;
    EditText etID,etCode;
    FirebaseAuth auth;
    PhoneAuthOptions options;
    String phoneNumber;
    private String verificationIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_i_d);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Xác nhận ID"); //Thiết lập tiêu đề nếu muốn
        String title = actionBar.getTitle().toString(); //Lấy tiêu đề nếu muốn
        // ánh xạ
        //btnNext = findViewById(R.id.buttonNext);
        email = findViewById(R.id.radioButtonEmail);
        sdt = findViewById(R.id.radioButtonSDT);
        chon = findViewById(R.id.textViewNhap);
        etID = findViewById(R.id.editTextID);
        //etCode = findViewById(R.id.editTextMaXacNhan);
        btnGuiMa = findViewById(R.id.buttonGuiMa);

        //Xử lý Button
        btnGuiMa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String number = etID.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    etID.setError("Valid number is required");
                    etID.requestFocus();
                    return;
                }
                final OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("user", number)
                        .build();
                Request request = new Request.Builder()
                        .url("http://192.168.1.12:3000/users/getuser")
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
                        if(list.size()==0){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    phoneNumber = number;
                                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_XN,Fragment_XacNhan_MK.newInstance(phoneNumber)).commit();
                                }
                            });
                        }
                        else
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(XacNhanID.this,"Tài khoản đã tồn tại",Toast.LENGTH_LONG).show();
                                }
                            });
                    }
                });

            }
        });
        // Xử lý RadioButton
        email.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(email.isChecked()){
                    chon.setText("Nhập email: ");
                    etID.setInputType(InputType.TYPE_CLASS_TEXT);
                }

            }
        });
        sdt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(sdt.isChecked()){
                    chon.setText("Nhập số điện thoại: ");
                    etID.setInputType(InputType.TYPE_CLASS_PHONE);
                }
            }
        });
    }
}