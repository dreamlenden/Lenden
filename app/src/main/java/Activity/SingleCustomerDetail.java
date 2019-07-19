package Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lenden.DataModels.CustomerModel;
import com.example.lenden.DataModels.TransactionModel;
import com.example.lenden.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapter.customer_latest_trans_adapter;

public class SingleCustomerDetail extends AppCompatActivity {

    EditText cust_nametv,cust_phoneNumbertv;
    Button add_trans;
    private String cust_name,cust_phoneNumber;
    private RecyclerView latest_trans;
    ArrayList<TransactionModel> latest_transactions;
    customer_latest_trans_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_customer_detail);
        cust_nametv=findViewById(R.id.customer_name);
        cust_phoneNumbertv = findViewById(R.id.customer_mobileNumber);
        add_trans = findViewById(R.id.add_trans);
        latest_trans = findViewById(R.id.latest_trans);
        latest_trans.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        latest_transactions = new ArrayList<>();
        cust_name= getIntent().getStringExtra("cust_name");
        cust_phoneNumber = getIntent().getStringExtra("cust_phoneNumber");
        cust_nametv.setText(cust_name);
        cust_phoneNumbertv.setText(cust_phoneNumber);

        getData(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<TransactionModel> arrayList) {
                adapter = new customer_latest_trans_adapter(SingleCustomerDetail.this,arrayList);
                Log.i("assasasa",String.valueOf(arrayList.size()));
                latest_trans.setAdapter(adapter);
                Log.i("size size isze",String.valueOf(latest_trans.getChildCount()));
            }
        });


        add_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleCustomerDetail.this,Cusotmer_add_transaction.class);
                intent.putExtra("cust_phoneNumber",cust_phoneNumber);
                startActivity(intent);
            }
        });



    }


    @Override
    public void onStart() {
        super.onStart();
        getData(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<TransactionModel> arrayList) {
                adapter = new customer_latest_trans_adapter(SingleCustomerDetail.this,arrayList);
                Log.i("assasasa448498459.465",String.valueOf(arrayList.size()));
                latest_trans.setAdapter(adapter);
            }
        });



    }

    private void getData(final SingleCustomerDetail.FirebaseCallback firebaseCallback){
        Log.i("abc",FirebaseAuth.getInstance().getCurrentUser().toString());
        final Query query = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance()
                .getCurrentUser().getPhoneNumber()).child("Customer's transaction").child(cust_phoneNumber).orderByChild("timestamp").limitToLast(5);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                latest_transactions.clear();
                if(dataSnapshot.exists()){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        TransactionModel customer = dataSnapshot1.getValue(TransactionModel.class);
                        latest_transactions.add(customer);
                    }
                    Log.i("brosososs",String.valueOf(latest_transactions.size()));
                    firebaseCallback.onCallback(latest_transactions);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private interface FirebaseCallback{
        void onCallback(ArrayList<TransactionModel> arrayList);
    }
}
