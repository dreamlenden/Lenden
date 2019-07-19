package Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenden.DataModels.ContactModel;
import com.example.lenden.DataModels.CustomerModel;
import com.example.lenden.DataModels.UserDetails;
import com.example.lenden.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateCustomer extends AppCompatActivity {

    LinearLayout skiplayout;
    private Spinner state_spinner;
    private Button submit;
    private EditText phoneNumbertv, nametv, citytv, addresstv;
    private String phoneNumber="", name="", pincode="", city="", address="", state = "",user_phoneNumber="",user_id="";
    private ArrayList<ContactModel> contacts;
    private ArrayList<CustomerModel> customers;
    private TextView skip,skipall;
    private DatabaseReference databaseReference;
    //private FirebaseFirestore db = FirebaseFirestore.getInstance();
    int count=0,no_of_contacts=0;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        skiplayout = findViewById(R.id.skip_layout);
        phoneNumbertv = findViewById(R.id.phoneNumber);
        nametv = findViewById(R.id.name);
        citytv = findViewById(R.id.city);
        addresstv = findViewById(R.id.address);
        submit = findViewById(R.id.submit);
        skip=findViewById(R.id.skip);
        skipall=findViewById(R.id.skip_all);
        state_spinner = findViewById(R.id.state_spinner);
        customers = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        user_phoneNumber = firebaseAuth.getCurrentUser().getPhoneNumber();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        ArrayAdapter<CharSequence> stateNames = ArrayAdapter.createFromResource(this,R.array.states,android.R.layout.simple_spinner_item);
        stateNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state_spinner.setAdapter(stateNames);


        state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final String from = getIntent().getStringExtra("from where");
        if(from.equals("manual entry")){
            skiplayout.setVisibility(View.GONE);
            no_of_contacts=1;
        } else {
            contacts = getIntent().getParcelableArrayListExtra("contacts");
            no_of_contacts = contacts.size();
            Log.i("haaji", contacts.get(0).getName() + " -- " + contacts.get(0).getPhoneNumber());


            name = contacts.get(count).getName();
            phoneNumber = contacts.get(count).getPhoneNumber();
            phoneNumbertv.setText(phoneNumber);
            nametv.setText(name);
            nametv.setEnabled(false);
            phoneNumbertv.setEnabled(false);
        }



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getUserId(new FirebaseCallback() {
                    @Override
                    public void onCallback(String value) {
                        if(from.equals("manual entry")){
                            name=nametv.getText().toString();
                            phoneNumber=phoneNumbertv.getText().toString();
                        }

                        city=citytv.getText().toString();
                        address=addresstv.getText().toString();


                        Boolean validate = validateCredentials(phoneNumber, name, city, address, state);
                        Log.i("validation",phoneNumber+" - "+name+" - "+pincode+" - "+city+" - "+address+" - "+state);
                        if (validate) {
                            customers.add(new CustomerModel(value+phoneNumber,phoneNumber,name,address,state,city));
                            Toast.makeText(CreateCustomer.this, phoneNumber + "  " + name, Toast.LENGTH_SHORT).show();
                            if(state.equals("Select a state"))
                                state="";
                            CustomerModel customer = new CustomerModel(value+"__"+phoneNumber,phoneNumber,name,address,state,city);



                            Log.i("count and size",count+"  "+no_of_contacts);
                            if (count == (no_of_contacts - 1)) {
                                databaseReference.child(user_phoneNumber).child("User customer details").child(phoneNumber).setValue(customer)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(CreateCustomer.this,"Customers added", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(CreateCustomer.this,MainScreenActivity.class);
                                                    startActivity(intent);
                                                    finish();}
                                                else
                                                    Toast.makeText(CreateCustomer.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        });

                            } else {

                                databaseReference.child(user_phoneNumber).child("User customer details").child(phoneNumber).setValue(customer);
                                count++;
                                name=contacts.get(count).getName();
                                phoneNumber=contacts.get(count).getPhoneNumber();
                                phoneNumbertv.setText(phoneNumber);
                                nametv.setText(name);
                                pincode = "";
                                city = "";
                                citytv.setText("");
                                state = "Select a state";
                                state_spinner.setSelection(0);
                                address = "";
                                addresstv.setText("");
                            }


                        }
                    }
                });
                //Toast.makeText(CreateCustomer.this,name+"  "+phoneNumber,Toast.LENGTH_LONG).show();






            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (count == (no_of_contacts - 1)) {
                    Toast.makeText(CreateCustomer.this, "yipee", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateCustomer.this, MainScreenActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    count++;
                    name = contacts.get(count).getName();
                    phoneNumber = contacts.get(count).getPhoneNumber();
                    phoneNumbertv.setText(phoneNumber);
                    nametv.setText(name);
                }

            }
        });


        skipall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=no_of_contacts;
                Toast.makeText(CreateCustomer.this,"finish",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateCustomer.this, MainScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });






    }

    private Boolean validateCredentials(String phoneNumber,String name,String city,String address,String state) {
        if (!isNetworkAvailable()) {
            Toast.makeText(CreateCustomer.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (name.equals("")) {
            nametv.setError("Enter a Name");
            return false;
        }


        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getUserId(final FirebaseCallback firebaseCallback){
        databaseReference.child("Users Details").child(user_phoneNumber).child("user_id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                firebaseCallback.onCallback(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private interface FirebaseCallback{
        void onCallback(String value);
    }
}
