package Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.lenden.DataModels.TransactionModel;
import com.example.lenden.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

public class Cusotmer_add_transaction extends AppCompatActivity {

    Spinner trans_type_spinner;
    EditText amounttv;
    Button make_trans;
    String trans_type,amount,user_phoneNumber,cust_phoneNumber,cust_id,user_id,trans_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusotmer_add_transaction);

        trans_type_spinner = findViewById(R.id.transType_spinner);
        amounttv = findViewById(R.id.trans_amt);
        make_trans = findViewById(R.id.make_trans);
        user_phoneNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        cust_phoneNumber = getIntent().getStringExtra("cust_phoneNumber");

        ArrayAdapter<CharSequence> stateNames = ArrayAdapter.createFromResource(this, R.array.Trans_type, android.R.layout.simple_spinner_item);
        stateNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trans_type_spinner.setAdapter(stateNames);
        trans_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                trans_type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        make_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = amounttv.getText().toString();
                getDetails(new FirebaseCallback() {
                    @Override
                    public void onCallbackUser_id(String u_id) {
                        user_id=u_id;
                    }

                    @Override
                    public void onCallbackCust_id(String c_id) {
                        cust_id=c_id;
                    }

                    @Override
                    public void onCallbackTrans_id(String Tr_id) {
                        trans_id=Tr_id;
                        TransactionModel transaction = new TransactionModel(cust_id+"__"+trans_id,cust_phoneNumber,cust_id,trans_type,amount,
                                user_id, DateFormat.getDateTimeInstance().format(new Date()),
                                "User","User",
                                DateFormat.getDateTimeInstance().format(new Date()),DateFormat.getDateTimeInstance().format(new Date()));

                        FirebaseDatabase.getInstance().getReference().child(user_phoneNumber).child("Customer's transaction")
                                .child(cust_phoneNumber).child(DateFormat.getDateTimeInstance().format(new Date()))
                                .setValue(transaction).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(Cusotmer_add_transaction.this,"yeyy doneee",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });



            }
        });

    }

    private void getDetails(final FirebaseCallback firebaseCallback) {
        Query user_idQuery = FirebaseDatabase.getInstance().getReference().child("Users Details").child(user_phoneNumber).child("user_id");
        user_idQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    user_id = dataSnapshot.getValue().toString();
                    firebaseCallback.onCallbackUser_id(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        Log.i("pehla",user_id);

        Query cust_idQuery = FirebaseDatabase.getInstance().getReference().child(user_phoneNumber)
                .child("User customer details")
                .child(cust_phoneNumber)
                .child("customer_id");
        cust_idQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    cust_id=dataSnapshot.getValue().toString();
                Log.i("bhai mere",String.valueOf(dataSnapshot.getChildrenCount()));
                firebaseCallback.onCallbackCust_id(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Log.i("doosra",cust_id);


        Query trans_idQuery = FirebaseDatabase.getInstance().getReference().child(user_phoneNumber)
                .child("Customer's transaction").child(cust_phoneNumber)
                .orderByChild("trans_id");

        trans_idQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int max = 0;

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        TransactionModel trans = dataSnapshot1.getValue(TransactionModel.class);
                        String id[] = trans.getTrans_id().split("_");
                        if (Integer.parseInt(id[id.length-1]) > max)
                            max = Integer.parseInt(id[id.length-1]);
                        max = max + 1;
                        Log.i("max yha", String.valueOf(max));
                        String maxStr = String.valueOf(max);
                        for (int i = 0; i < 6 - maxStr.length(); i++) {
                            trans_id = trans_id + "0";
                        }
                        trans_id = trans_id+maxStr;
                        firebaseCallback.onCallbackTrans_id(trans_id);


                    }
                } else {
                    trans_id="000001";
                    firebaseCallback.onCallbackTrans_id("000001");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Log.i("teesra",trans_id);


    }



    private interface FirebaseCallback{
        void onCallbackUser_id(String u_id);
        void onCallbackCust_id(String c_id);
        void onCallbackTrans_id(String Tr_id);
    }


}
