package com.example.cnm;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<itemDB> lst,dsdb,dsyc;
    Button btnDB,btnYC,btnSearch;
    EditText etSearch;
    RecyclerView recyclerView;
    AdapterRecDB adapterRecDB;
    AdapterRecYC adapterRecYC;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_danhba, container, false);
        lst = new ArrayList<>();
        lst.add(new itemDB(R.drawable.r1,1,"03452645763","Trương Hoàng Anh Vũ"));
        lst.add(new itemDB(R.drawable.r1,1,"03452645763","Trương Hoàng Anh Vũ"));
        lst.add(new itemDB(R.drawable.r1,1,"03452645763","Trương Hoàng Anh Vũ"));
        lst.add(new itemDB(R.drawable.r1,1,"03452645763","Trương Hoàng Anh Vũ"));
        lst.add(new itemDB(R.drawable.r1,1,"03452645763","Trương Hoàng Anh Vũ"));
        lst.add(new itemDB(R.drawable.r1,0,"03452645763","Trương Hoàng Anh Vũ"));
        lst.add(new itemDB(R.drawable.r1,0,"03452645763","Trương Hoàng Anh Vũ"));
        lst.add(new itemDB(R.drawable.r1,0,"03452645763","Trương Hoàng Anh Vũ"));
        lst.add(new itemDB(R.drawable.r1,0,"03452645763","Trương Hoàng Anh Vũ"));
        lst.add(new itemDB(R.drawable.r1,0,"03452645763","Trương Hoàng Anh Vũ"));
        dsdb = new ArrayList<>();
        dsyc = new ArrayList<>();
        for(int i = 0;i<lst.size();i++){
            if(lst.get(i).getStt()==0)
                dsyc.add(lst.get(i));
            else
                dsdb.add(lst.get(i));
        }
        etSearch = view.findViewById(R.id.etSearch_DB);
        btnSearch = view.findViewById(R.id.buttonSearch_DB);
        adapterRecDB = new AdapterRecDB(getContext(),dsdb);
        adapterRecYC = new AdapterRecYC(getContext(),dsyc);
        btnDB = view.findViewById(R.id.buttonDanhBa_DB);
        btnYC = view.findViewById(R.id.buttonYC_DB);
        recyclerView = view.findViewById(R.id.recyle_DB);

        btnDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1,GridLayoutManager.VERTICAL,false));
                //recyclerView.setLayoutManager(new );
                recyclerView.setAdapter(adapterRecDB);
            }
        });
        btnYC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1,GridLayoutManager.VERTICAL,false));
                //recyclerView.setLayoutManager(new );
                recyclerView.setAdapter(adapterRecYC);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(getActivity());
            }
        });
        return view;
    }
    public static void showAlertDialog(final Activity context)  {
        final Drawable positiveIcon = context.getResources().getDrawable(R.drawable.ic_baseline_close_24);
        Drawable p = context.getDrawable(R.drawable.ic_baseline_close_24);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Custom Title Area.
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_search_proflie, null);
        builder.setCustomTitle(view);

        // Message.
        //builder.setMessage("This is AlertDialog with Custom Title Area");
        //
        builder.setCancelable(true);
        builder.setIcon(R.drawable.ic_baseline_person_add_24);
        builder.setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setNegativeButtonIcon(context.getDrawable(R.drawable.ic_baseline_mark_email_read_24));
                
            }
        });
        builder.setNegativeButtonIcon(context.getDrawable(R.drawable.ic_baseline_person_add_24));
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

}