package com.example.cnm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.cactoos.func.Async;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.google.common.math.LongMath.factorial;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Danhba#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Danhba extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static final String host = "192.168.100.171";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<MyUser> dsF,dsR,dsS;
    Button btnDB,btnYC,btnSearch;
    EditText etSearch;
    RecyclerView recyclerView;
    AdapterRecDB adapterRecDB;
    AdapterRecYC adapterRecYC;
    RequestBody requestBody;
    Request request;
    MyFriend friend;
    ProgressBar progressBar2;
    final OkHttpClient client = new OkHttpClient();
    public Danhba() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Danhba.
     */
    // TODO: Rename and change types and number of parameters
    public static Danhba newInstance(String param1, String param2) {
        Danhba fragment = new Danhba();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static Danhba newInstance(String param1) {
        Danhba fragment = new Danhba();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
    public static Danhba newInstance() {
        Danhba fragment = new Danhba();
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
    public  View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_danhba, container, false);

        //progressBar2 = view.findViewById(R.id.progressBar_DB);
        etSearch = view.findViewById(R.id.etSearch_DB);
        btnSearch = view.findViewById(R.id.buttonSearch_DB);
        btnDB = view.findViewById(R.id.buttonDanhBa_DB);
        btnYC = view.findViewById(R.id.buttonYC_DB);
        recyclerView = view.findViewById(R.id.recyle_DB);

//        Timer timer = new Timer();
//        ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("loading...");
//        progressDialog.show();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                requestBody = new FormBody.Builder()
//                        .add("user", mParam1)
//                        .build();
//                request = new Request.Builder()
//                        .url("http://"+host+":3000/friends/getown")
//                        .patch(requestBody)
//                        .build();
//                client.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.e("Error",e.getMessage());
//                    }
//
//                    @Override
//                    public void onResponse(Call call, final Response response) throws IOException {
//                        if(response.isSuccessful()){
//                            Moshi moshi = new Moshi.Builder().build();
//                            Type usersType = Types.newParameterizedType(List.class, MyFriend.class);
//                            final String json = response.body().string();
//                            final JsonAdapter<List<MyFriend>> jsonAdapter = moshi.adapter(usersType);
//                            final List<MyFriend> list;
//                            list = jsonAdapter.fromJson(json);
//                            friend = list.get(0);
//                        }
//                    }
//                });
//            }
//        };
//        timer.schedule(timerTask,2000);
//        dsF=new ArrayList<>();
//        dsF = getDSUser(friend,1);
//        adapterRecDB = new AdapterRecDB(getContext(),dsF);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1,GridLayoutManager.VERTICAL,false));
//        recyclerView.setAdapter(adapterRecDB);

        requestBody = new FormBody.Builder()
                        .add("user", mParam1)
                        .build();
                request = new Request.Builder()
                        .url("http://"+host+":3000/friends/getown")
                        .patch(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("Error",e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if(response.isSuccessful()){
                            Moshi moshi = new Moshi.Builder().build();
                            Type usersType = Types.newParameterizedType(List.class, MyFriend.class);
                            final String json = response.body().string();
                            final JsonAdapter<List<MyFriend>> jsonAdapter = moshi.adapter(usersType);
                            final List<MyFriend> list;
                            list = jsonAdapter.fromJson(json);
                            friend = list.get(0);
                        }
                    }
                });

        btnDB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                requestBody = new FormBody.Builder()
                        .add("user", mParam1)
                        .build();
                request = new Request.Builder()
                        .url("http://"+host+":3000/friends/getown")
                        .patch(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("Error",e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if(response.isSuccessful()){
                            Moshi moshi = new Moshi.Builder().build();
                            Type usersType = Types.newParameterizedType(List.class, MyFriend.class);
                            final String json = response.body().string();
                            final JsonAdapter<List<MyFriend>> jsonAdapter = moshi.adapter(usersType);
                            final List<MyFriend> list;
                            list = jsonAdapter.fromJson(json);
                            friend = list.get(0);
                        }
                    }
                });
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1,GridLayoutManager.VERTICAL,false));
                //getData(dsF,dsR,dsS,friend);
                dsF = new ArrayList<>();
                dsF = getDSUser(friend,0);
                adapterRecDB = new AdapterRecDB(getContext(),dsF);
                //recyclerView.setLayoutManager(new );
                recyclerView.setAdapter(adapterRecDB);
            }
        });
        btnYC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestBody = new FormBody.Builder()
                        .add("user", mParam1)
                        .build();
                request = new Request.Builder()
                        .url("http://"+host+":3000/friends/getown")
                        .patch(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("Error",e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if(response.isSuccessful()){
                            Moshi moshi = new Moshi.Builder().build();
                            Type usersType = Types.newParameterizedType(List.class, MyFriend.class);
                            final String json = response.body().string();
                            final JsonAdapter<List<MyFriend>> jsonAdapter = moshi.adapter(usersType);
                            final List<MyFriend> list;
                            list = jsonAdapter.fromJson(json);
                            friend = list.get(0);
                        }
                    }
                });
                dsR = new ArrayList<>();
                dsR = getDSUser(friend,1);
                adapterRecYC = new AdapterRecYC(getContext(),dsR,mParam1);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1,GridLayoutManager.VERTICAL,false));
                //recyclerView.setLayoutManager(new );
                recyclerView.setAdapter(adapterRecYC);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestBody = new FormBody.Builder()
                        .add("user", mParam1)
                        .build();
                request = new Request.Builder()
                        .url("http://"+host+":3000/friends/getown")
                        .patch(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("Error",e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if(response.isSuccessful()){
                            Moshi moshi = new Moshi.Builder().build();
                            Type usersType = Types.newParameterizedType(List.class, MyFriend.class);
                            final String json = response.body().string();
                            final JsonAdapter<List<MyFriend>> jsonAdapter = moshi.adapter(usersType);
                            final List<MyFriend> list;
                            list = jsonAdapter.fromJson(json);
                            friend = list.get(0);
                        }
                    }
                });
                String number = etSearch.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    etSearch.setError("Valid number is required");
                    etSearch.requestFocus();
                    return;
                }
                if(number.equals(mParam1)){
                    Toast.makeText(getContext(),"Đây là số điện thoại của bạn!!!",Toast.LENGTH_LONG).show();
                    return;
                }
                requestBody = new FormBody.Builder()
                        .add("user", number)
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(list.size()==0){
                                    showAlertDialog(getActivity());
                                }
                                else {
                                    if(SearchInList(etSearch.getText().toString().trim(),friend.getFriend())){
                                        // trong danh sach ban be
                                        MyUser user = list.get(0);
                                        showAlertDialog(getActivity(),user,1,mParam1);
                                    }
                                    else if(SearchInList(etSearch.getText().toString().trim(),friend.getSend())){
                                        // trong danh sach gui ket ban
                                        MyUser user = list.get(0);
                                        showAlertDialog(getActivity(),user,2,mParam1);
                                    }
                                    else if(SearchInList(etSearch.getText().toString().trim(),friend.getReceive())){
                                        // trong danh sach nhan
                                        MyUser user = list.get(0);
                                        showAlertDialog(getActivity(),user,3,mParam1);
                                    }
                                    else{
                                        MyUser user = list.get(0);
                                        showAlertDialog(getActivity(),user,0,mParam1);
                                    }
                                }
                            }
                        });

                    }
                });

            }
        });
        return view;
    }
    public ArrayList<MyUser> getDSUser(MyFriend myFriend,int stt){
        final ArrayList<MyUser> listtmp = new ArrayList<>();
        if(stt==0){
            //get friend
            for (String str:myFriend.getFriend()) {
                requestBody = new FormBody.Builder()
                        .add("user", str)
                        .build();
                request = new Request.Builder()
                        .url("http://"+host+":3000/users/getuser")
                        .patch(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Moshi moshi = new Moshi.Builder().build();
                        Type usersType = Types.newParameterizedType(List.class, MyUser.class);
                        String json = response.body().string();
                        String tmp ="["+json+"]";
                        final JsonAdapter<List<MyUser>> jsonAdapter = moshi.adapter(usersType);
                        final List<MyUser> list = jsonAdapter.fromJson(tmp);
                        listtmp.add(list.get(0));
                    }
                });
            }
            return listtmp;
        }
        else if(stt==1){
            //get Rec
            for (String str:myFriend.getReceive()) {
                requestBody = new FormBody.Builder()
                        .add("user", str)
                        .build();
                request = new Request.Builder()
                        .url("http://"+host+":3000/users/getuser")
                        .patch(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Moshi moshi = new Moshi.Builder().build();
                        Type usersType = Types.newParameterizedType(List.class, MyUser.class);
                        String json = response.body().string();
                        String tmp ="["+json+"]";
                        final JsonAdapter<List<MyUser>> jsonAdapter = moshi.adapter(usersType);
                        final List<MyUser> list = jsonAdapter.fromJson(tmp);
                        listtmp.add(list.get(0));
                    }
                });
            }
        }
        else{
            //get Send
            for (String str:myFriend.getSend()) {
                requestBody = new FormBody.Builder()
                        .add("user", str)
                        .build();
                request = new Request.Builder()
                        .url("http://"+host+":3000/users/getuser")
                        .patch(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Moshi moshi = new Moshi.Builder().build();
                        Type usersType = Types.newParameterizedType(List.class, MyUser.class);
                        String json = response.body().string();
                        String tmp ="["+json+"]";
                        final JsonAdapter<List<MyUser>> jsonAdapter = moshi.adapter(usersType);
                        final List<MyUser> list = jsonAdapter.fromJson(tmp);
                        listtmp.add(list.get(0));
                    }
                });
            }
        }
        return listtmp;
    }

    public static void showAlertDialog(final Activity context)  {
        //final Drawable positiveIcon = context.getResources().getDrawable(R.drawable.ic_baseline_close_24);
        Drawable p = context.getDrawable(R.drawable.ic_baseline_close_24);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Custom Title Area.

        // Set Title and Message:
        builder.setTitle("Thông báo").setMessage("Không tìm thấy");

        //
        builder.setCancelable(true);
        builder.setIcon(R.drawable.ic_baseline_report_problem_24);

        // Message.
        //builder.setMessage("Không tìm thấy");
        //
        // Create "OK" button with OnClickListener.
        builder.setPositiveButton("", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setPositiveButtonIcon(p);

        // Create AlertDialog:
        AlertDialog alert = builder.create();
        alert.show();
    }
    public static void showAlertDialog(final Activity context, final MyUser u, int stt, final String s)  {
        Drawable p = context.getDrawable(R.drawable.ic_baseline_close_24);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Custom Title Area.
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_search_proflie, null);
        TextView tvName = view.findViewById(R.id.textViewName_dialogSearch);
        final TextView tvSDT = view.findViewById(R.id.textViewSDT_dialogSearch);
        TextView gt = view.findViewById(R.id.textViewGioTinh_dialogSearch);
        TextView ns = view.findViewById(R.id.textViewNS_dialogSearch);
        ImageView img = view.findViewById(R.id.imageView_dialogSearch);

        if(u.isGender())
            gt.setText("Giới tính: Nam");
        else
            gt.setText("Giới tính: Nữ");
        ns.setText(String.copyValueOf(u.getDoB().toCharArray(),0,10));
        tvName.setText(u.getName());
        tvSDT.setText(u.getId());
        builder.setCustomTitle(view);

        // Message.
        //builder.setMessage("This is AlertDialog with Custom Title Area");
        //
        builder.setCancelable(true);
       if(stt==0){
           //builder.setIcon(R.drawable.ic_baseline_person_add_24);
           builder.setNegativeButton("", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {


               }
           });
           builder.setNegativeButtonIcon(p);
           // Create "OK" button with OnClickListener.
           builder.setPositiveButton("", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                   OkHttpClient client = new OkHttpClient();
                   RequestBody requestBody;
                   Request request;
                   requestBody = new FormBody.Builder()
                           .add("userrc",u.getId().toString())//dc nha
                           .add("user",s)// s = idHome
                           .build();
                   request = new Request.Builder()
                           .url("http://"+host+":3000/friends/send")
                           .post(requestBody)
                           .build();
                   client.newCall(request).enqueue(new Callback() {
                       @Override
                       public void onFailure(Call call, IOException e) {
                            Log.e("Error",e.getMessage());
                       }

                       @Override
                       public void onResponse(Call call, Response response) throws IOException {
//                            context.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    builder.setPositiveButtonIcon(context.getDrawable(R.drawable.ic_baseline_mark_email_read_24));
//                                }
//                            });
                       }
                   });

               }
           });
           builder.setPositiveButtonIcon(context.getDrawable(R.drawable.ic_baseline_person_add_24));
       }
       if(stt==1){// la ban
           builder.setNegativeButton("", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {


               }
           });
           builder.setNegativeButtonIcon(p);
           // Create "OK" button with OnClickListener.
           builder.setPositiveButton("", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {

               }
           });
           builder.setPositiveButtonIcon(context.getDrawable(R.drawable.ic_baseline_message_24));
           builder.setNegativeButton("", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   OkHttpClient client = new OkHttpClient();
                   RequestBody requestBody;
                   Request request;
                   requestBody = new FormBody.Builder()
                           .add("user",u.getId())
                           .add("userrc",s)// s = idHome
                           .build();
                   request = new Request.Builder()
                           .url("http://"+host+":3000/friends/delete")
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
               }
           });
       }
       if(stt == 2){
           // da gui
           builder.setNegativeButton("", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   OkHttpClient client = new OkHttpClient();
                   RequestBody requestBody;
                   Request request;
                   requestBody = new FormBody.Builder()
                           .add("user",s)
                           .add("userrc",u.getId())
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
               }
           });
           builder.setPositiveButton("", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   OkHttpClient client = new OkHttpClient();
                   RequestBody requestBody;
                   Request request;
                   requestBody = new FormBody.Builder()
                           .add("userrc",u.getId())//dc nha
                           .add("user",s)// s = idHome
                           .build();
                   request = new Request.Builder()
                           .url("http://"+host+":3000/friends/removesend")
                           .delete(requestBody)
                           .build();
                   client.newCall(request).enqueue(new Callback() {
                       @Override
                       public void onFailure(Call call, IOException e) {
                           Log.e("Error",e.getMessage());
                       }

                       @Override
                       public void onResponse(Call call, Response response) throws IOException {

                       }
                   });
               }
           });
           builder.setNegativeButtonIcon(p);
           // Create "OK" button with OnClickListener.
           builder.setPositiveButtonIcon(context.getDrawable(R.drawable.ic_baseline_mark_email_read_24));
       }
       if(stt == 3){
           // da nhan
           builder.setNegativeButton("", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   OkHttpClient client = new OkHttpClient();
                   RequestBody requestBody;
                   Request request;
                   requestBody = new FormBody.Builder()
                           .add("user",u.getId())
                           .add("userrc",s)
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

               }
           });
           builder.setNegativeButtonIcon(p);
           // Create "OK" button with OnClickListener.
           builder.setPositiveButtonIcon(context.getDrawable(R.drawable.ic_baseline_check_24));
           builder.setPositiveButton("", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   OkHttpClient client = new OkHttpClient();
                   RequestBody requestBody;
                   Request request;
                   requestBody = new FormBody.Builder()
                           .add("user",u.getId())
                           .add("userrc",s)
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
                           .add("user",s)
                           .add("userrc",u.getId())//add zo
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

               }
           });
       }

        // Create AlertDialog:
        AlertDialog alert = builder.create();
        alert.show();
    }
    boolean SearchInList(String e,List<String> list){
        for(String tmp:list){
            if(tmp.equals(e))
                return true;
        }
        return false;
    }

}