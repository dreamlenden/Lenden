package Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lenden.DataModels.UserDetails;
import com.example.lenden.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

public class SignupdetailsActivity extends AppCompatActivity {

    private Spinner state_spinner, id_type_spinner;
    private EditText phoneNumbertv, nametv, fathersnametv, emailtv, pincodetv, citytv, addresstv, id_notv;
    private String phoneNumber, name, fathersname, emailid, pincode, city, id_no, address, state = "", id_type = "", imei,user_id="",uid="";
    private double latitude, longitude;
    private Button submit;
    private UserDetails user;
    private FusedLocationProviderClient fusedLocationClient;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    //private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_details);

        state_spinner = findViewById(R.id.state_spinner);
        id_type_spinner = findViewById(R.id.id_type_spinner);
        phoneNumbertv = findViewById(R.id.phoneNumber);
        nametv = findViewById(R.id.name);
        fathersnametv = findViewById(R.id.fathername);
        emailtv = findViewById(R.id.email);
        pincodetv = findViewById(R.id.pincode);
        citytv = findViewById(R.id.city);
        addresstv = findViewById(R.id.address);
        id_notv = findViewById(R.id.id_no);
        submit = findViewById(R.id.submit);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
            return;
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            longitude = location.getLongitude();
                            latitude = location.getLatitude();

                        } else
                            Toast.makeText(SignupdetailsActivity.this, "error", Toast.LENGTH_LONG).show();
                    }
                });

        phoneNumber = getIntent().getStringExtra("phoneNumber");
        phoneNumbertv.setText(phoneNumber);
        phoneNumbertv.setEnabled(false);


        ArrayAdapter<CharSequence> stateNames = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
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

        ArrayAdapter<CharSequence> id_types = ArrayAdapter.createFromResource(this, R.array.Id_type, android.R.layout.simple_spinner_item);
        id_types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        id_type_spinner.setAdapter(id_types);
        id_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserDetails();
                boolean validate = validateCredentials();
                if (validate) {
                    updateUid(new FirebaseCallback() {
                        @Override
                        public void onCallback(String value) {
                            uid=value;
                            Log.i("value aaja ",value);

                    Log.i("user id yha",uid);


                    user = new UserDetails(firebaseAuth.getUid(), phoneNumber, name, fathersname, emailid, pincode, city, state, address, id_type, id_no, uid, "shop",getImei(), Build.MANUFACTURER, Build.MODEL,
                            DateFormat.getDateTimeInstance().format(new Date()), latitude, longitude, "User", "User", DateFormat.getDateTimeInstance().format(new Date()), DateFormat.getDateTimeInstance().format(new Date()));


                    databaseReference.child("Users Details").child(phoneNumber).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(SignupdetailsActivity.this, ImportAndEnterCustomerActivity.class);
                                intent.putExtra("user_phoneNumber", phoneNumber);
                                startActivity(intent);
                                finish();
                            } else Toast.makeText(SignupdetailsActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                        }
                    });





                }
            }
        });


    }


    private void getUserDetails() {
        name = nametv.getText().toString();
        fathersname = fathersnametv.getText().toString();
        emailid = emailtv.getText().toString();
        pincode = pincodetv.getText().toString();
        city = citytv.getText().toString();
        id_no = id_notv.getText().toString();
        address = addresstv.getText().toString();
    }

    private Boolean validateCredentials() {
        if (!isNetworkAvailable()) {
            Toast.makeText(SignupdetailsActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (name.equals("")) {
            nametv.setError("Enter a Name");
            return false;
        }

        if (fathersname.equals("")) {
            fathersnametv.setError("Enter a Name");
            return false;
        }

        if (emailid.equals("")) {
            emailtv.setError("Enter a Name");
            return false;
        }

        if (state.equals("Select a state")) {
            Toast.makeText(SignupdetailsActivity.this, "Please select a state first", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (pincode.equals("")) {
            pincodetv.setError("Enter a Pincode");
            return false;
        }
        if (city.equals("")) {
            citytv.setError("Enter a city Name");
            return false;
        }
        if (id_no.equals("")) {
            id_notv.setError("Enter a Name");
            return false;
        }
        if (id_type.equals("Select an ID TYPE")) {
            Toast.makeText(SignupdetailsActivity.this, "Please select an id type first", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (address.equals("")) {
            addresstv.setError("Enter an address");
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


    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of maps api")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(SignupdetailsActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

     private String getImei() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }

        TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);;
         imei = tel.getDeviceId();

         return imei;

    }

    private void updateUid(final FirebaseCallback firebaseCallback){
        HashMap<String,String> stateCode = new HashMap<String,String>(){
            {
                put("Andhra Pradesh","AP");
                put("Aruncahal Pradesh","AR");
                put("Assam","AS");
                put("Bihar","BR");
                put("Chattisgarh","CG");
                put("Goa","GA");
                put("Gujrat","GJ");
                put("Haryana","HR");
                put("Himachal Pradesh","HP");
                put("Jammu and Kashmir","JK");
                put("Jharkhand","JH");
                put("Karnataka","KA");
                put("Kerela","KL");
                put("Madhya Pradesh","MP");
                put("Maharashtra","MH");
                put("Manipur"," MN");
                put("Meghalaya","ML");
                put("Mizoram","MZ");
                put("Nagaland","NL");
                put("Odisha","OD");
                put("Punjab","PB");
                put("Rajasthan","RJ");
                put("Sikkim","SK");
                put("Tamil Nadu","TN");
                put("Telangana","TS");
                put("Tripura","TR");
                put("Uttarakhand","UK");
                put("Uttar Pradesh","UP");
                put("West Bengal","WB");
                put("Andaman and Nicobar Islands","AN");
                put("Chandigarh","CH");
                put("Dadra and Nagar Haveli","DN");
                put("Daman and Diu","DD");
                put("Delhi","DL");
                put("Lakshadweep","LD");
                put("Pondicherry","PY");

            }
        };
        final String stcode=stateCode.get(state);

        Query query = FirebaseDatabase.getInstance().getReference().child("Users Details").orderByChild("user_id").startAt(stcode);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.i("aaja yarr",dataSnapshot.getValue().toString());
                if (dataSnapshot.exists()) {
                    int max = 0;

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        UserDetails userDetails = dataSnapshot1.getValue(UserDetails.class);
                        Log.i("finallll", userDetails.getUser_id());


                        String id[] = userDetails.getUser_id().split("_");
                        if (Integer.parseInt(id[1]) > max)
                            max = Integer.parseInt(id[1]);
                    }
                    //}
                    max = max + 1;
                    Log.i("max yha", String.valueOf(max));
                    String maxStr = String.valueOf(max);
                    for (int i = 0; i < 6 - maxStr.length(); i++) {
                        user_id = user_id + "0";
                    }
                    Log.i("gandi idhr ", user_id);
                    user_id = user_id + maxStr;
                    user_id = stcode + "_" + user_id;
                    Log.i("chl bro", user_id);
                    firebaseCallback.onCallback(user_id);
                } else {
                    user_id=stcode+"_"+"000001";
                    firebaseCallback.onCallback(user_id);
                }
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
