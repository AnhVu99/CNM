package com.example.cnm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DangKy extends AppCompatActivity {
    EditText etName,etPass,etPassConf;
    RadioButton rbM,rbF;
    DatePicker doB;
    Button btnDK;
    ImageView img;
    String phonenumber;
    static final String host = "192.168.100.171";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thông tin đăng kí"); //Thiết lập tiêu đề nếu muốn
        String title = actionBar.getTitle().toString(); //Lấy tiêu đề nếu muốn
        //ánh xạ
        img = (ImageView) findViewById(R.id.imgAvatar_dk);
        etName = findViewById(R.id.editTextName_DK);
        etPass = findViewById(R.id.editTextPassword_DK);
        etPassConf = findViewById(R.id.editTextPasswordConfrim_DK);
        rbF = findViewById(R.id.radioButtonNu_DK);
        rbM = findViewById(R.id.radioButtonNam_DK);
        doB = findViewById(R.id.datePickerNS_DK);
        btnDK = findViewById(R.id.buttonDK_DK);
        phonenumber = getIntent().getStringExtra("phonenumber");
        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                String confPass = etPassConf.getText().toString().trim();

                if (name.isEmpty()) {
                    etName.setError("Không được trống");
                    etName.requestFocus();
                    return;
                }
                if (pass.isEmpty()) {
                    etPass.setError("Không được trống");
                    etPass.requestFocus();
                    return;
                }
                if (!pass.equals(confPass)) {
                    etPassConf.setError("Không khớp");
                    etPassConf.requestFocus();
                    return;
                }
                if(!rbF.isChecked() && !rbM.isChecked()){
                    Toast.makeText(DangKy.this,"Vui lòng chọn giới tính",Toast.LENGTH_LONG).show();
                    return;
                }
                //Tạo user
                MyUser user = new MyUser();
                Date d = getDateFromDatePicker(doB);
                user.setDoB(d.toString());
                user.setId(phonenumber);
                user.setName(etName.getText().toString());
                user.setPass(etPass.getText().toString().trim());
                if(rbM.isChecked())
                    user.setGender(Boolean.TRUE);
                else
                    user.setGender(Boolean.FALSE);

                // Khởi tạo OkHttpClient để lấy dữ liệu.
                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("user", user.getId())
                        .add("pass", user.getPass())
                        .add("name",user.getName())
                        .add("gender",String.valueOf(user.isGender()))
                        .add("bday",String.valueOf(user.getDoB()))
                        .add("trangthai",String.valueOf(Boolean.FALSE))
                        .build();
                Request request = new Request.Builder()
                        .url("http://"+host+":3000/users/create")
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("Error", e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(DangKy.this,"Đăng kí thành công",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });
        img.setImageResource(R.drawable.r1);
    }
    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}