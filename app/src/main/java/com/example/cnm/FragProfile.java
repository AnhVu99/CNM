package com.example.cnm;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    TextView tvID;
    EditText etName;
    RadioButton rbNam,rbNu;
    DatePicker dpNS;
    Button btnSave,btnDoiMK;
    RequestBody requestBody;
    Request request;
    // TODO: Rename and change types of parameters
    private String mParam1;

    public FragProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment FragProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static FragProfile newInstance(String param1) {
        FragProfile fragment = new FragProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_profile, container, false);
        tvID = view.findViewById(R.id.textViewID_frag_PF);
        etName = view.findViewById(R.id.editTextName_Frag_PF);
        rbNam = view.findViewById(R.id.radioButtonNam_Frag_PF);
        rbNu = view.findViewById(R.id.radioButtonNu_frag_PF);
        dpNS = view.findViewById(R.id.datePickerNS_frag_PF);
        btnDoiMK = view.findViewById(R.id.buttonDoiMK_frag_PF);
        btnSave = view.findViewById(R.id.buttonSave_frag_PF);
        tvID.setText(mParam1);

        // Khởi tạo OkHttpClient để lấy dữ liệu.
        final OkHttpClient client = new OkHttpClient();
        requestBody = new FormBody.Builder()
                .add("user", mParam1)
                .build();
        request = new Request.Builder()
                .url("http://192.168.68.172:3000/users/getuser")
                .patch(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Error",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //OkHttpClient client = new OkHttpClient();
                // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
                Moshi moshi = new Moshi.Builder().build();
                Type usersType = Types.newParameterizedType(List.class, MyUser.class);
                final JsonAdapter<List<MyUser>> jsonAdapter = moshi.adapter(usersType);
                String json = response.body().string();
                String s = "["+json+"]";
                final List<MyUser> list = jsonAdapter.fromJson(s);
                MyUser u = list.get(0);
                char[] date = u.getDoB().toCharArray();
                int y = Integer.parseInt(String.copyValueOf(date,0,4));
                int m = Integer.parseInt(String.copyValueOf(date,5,2));
                int d = Integer.parseInt(String.copyValueOf(date,8,2));
                dpNS.updateDate(y,m,d);
                etName.setText(list.get(0).getName());
                if(u.isGender())
                    rbNam.setChecked(true);
                else
                    rbNu.setChecked(true);
                String ti = list.get(0).getDoB();
            }
        });
        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DoiMatKhauActivity.class);
                intent.putExtra("phonenumber", mParam1);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean gt = false;
                if(rbNam.isChecked())
                    gt = true;
                else
                    gt = false;
                requestBody = new FormBody.Builder()
                        .add("user", mParam1)
                        .add("name",etName.getText().toString())
                        .add("gender",String.valueOf(gt))
                        .add("bday",String.valueOf(getDateFromDatePicker(dpNS)))
                        .build();
                request = new Request.Builder()
                        .url("http://192.168.68.172:3000/users/update")
                        .put(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("Error",e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(),"Đã lưu thông tin",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });
        return view;
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