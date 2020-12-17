package com.example.cnm;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_XacNhan_MK#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_XacNhan_MK extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText etCode;
    Button btnConf;
    FirebaseAuth auth;
    PhoneAuthOptions options;
    private String verificationIds;
    static final String host = "192.168.43.73";
    public Fragment_XacNhan_MK() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Fragment_XacNhan_MK.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_XacNhan_MK newInstance(String param1) {
        Fragment_XacNhan_MK fragment = new Fragment_XacNhan_MK();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
    public static Fragment_XacNhan_MK newInstance(String param1,String param2) {
        Fragment_XacNhan_MK fragment = new Fragment_XacNhan_MK();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__xac_nhan__m_k, container, false);
        etCode = view.findViewById(R.id.etCode_fvc);
        btnConf = view.findViewById(R.id.button_xacnhan_fvc);
        String PhoneNum = "+84"+mParam1;
        sendVerificationCode(PhoneNum);
        btnConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = etCode.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {

                    etCode.setError("Enter code...");
                    etCode.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });
        return view;
    }
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationIds, code);
        signInWithPhoneAuthCredential(credential);
//     credential.getSmsCode();

    }
    void sendVerificationCode(String num){
        auth = FirebaseAuth.getInstance();
        options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(num)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(getActivity())
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        // Save the verification id somewhere
                        // ...

                        // The corresponding whitelisted code above should be used to complete sign-in.
                        super.onCodeSent(verificationId, forceResendingToken);
                        verificationIds = verificationId;
                        //DangKyActivity.this.enableUserManuallyInputCode();
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        // Sign in with the credential
                        // ...
                        Toast.makeText(getContext(),phoneAuthCredential.getSmsCode(), Toast.LENGTH_LONG).show();
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        //editText.setText(e.getMessage());
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(getActivity(),new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if(getActivity().getClass()!=XacNhanID.class){
                        OkHttpClient client = new OkHttpClient();

                        RequestBody requestBody = new FormBody.Builder()
                                .add("user", mParam1)
                                .add("pass", mParam2)
                                .build();
                        Request request = new Request.Builder()
                                .url("http://"+host+":3000/users/update")
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
                                        Toast.makeText(getContext(),"Đã lưu thông tin thành công",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                    }
                    else
                    {
                        Intent intent = new Intent(getContext(), DangKy.class);
                        intent.putExtra("phonenumber", mParam1);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getContext(), "Không thành công", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}