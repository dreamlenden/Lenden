package fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lenden.DataModels.CustomerModel;
import com.example.lenden.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Activity.ImportAndEnterCustomerActivity;
import adapter.customer_list_adapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class customer_list extends Fragment {

    ImageView addCust;
    RecyclerView cust_list_rv;
    ArrayList<CustomerModel> cust_list;
    customer_list_adapter adapter;

    public customer_list() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);
        addCust = view.findViewById(R.id.add_cust);
        cust_list = new ArrayList<>();
        cust_list_rv = view.findViewById(R.id.custList_rv);
        cust_list_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false));


        addCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ImportAndEnterCustomerActivity.class);
                getActivity().getApplicationContext().startActivity(intent);
                getActivity().finish();
            }
        });

        getData(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<CustomerModel> arrayList) {
                Log.i("size aa",String.valueOf(arrayList.size()));
                adapter = new customer_list_adapter(arrayList,getActivity());
                cust_list_rv.setAdapter(adapter);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getData(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<CustomerModel> arrayList) {
                adapter = new customer_list_adapter(arrayList,getActivity());
                cust_list_rv.setAdapter(adapter);
            }
        });



    }

    private void getData(final customer_list.FirebaseCallback firebaseCallback){
        final Query query = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance()
                .getCurrentUser().getPhoneNumber()).child("User customer details").orderByChild("name");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cust_list.clear();
                if(dataSnapshot.exists()){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        CustomerModel customer = dataSnapshot1.getValue(CustomerModel.class);
                        cust_list.add(customer);
                    }
                    firebaseCallback.onCallback(cust_list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private interface FirebaseCallback{
        void onCallback(ArrayList<CustomerModel> arrayList);
    }

}
