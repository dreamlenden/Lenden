package Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenden.DataModels.TransactionModel;
import com.example.lenden.DataModels.UserDetails;
import com.example.lenden.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class WelcomeActivity extends AppCompatActivity {
private Button signup;
   // private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        signup = findViewById(R.id.sign_up);

        //requestLocationPermissions();
        //Log.i("imei idhr h",)

        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
            Log.i("hey bhagwan",FirebaseAuth.getInstance().getCurrentUser().getUid() + " --- "+FirebaseAuth.getInstance().getCurrentUser().toString() +
                    " --- "+FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
        else Log.i("yarrr","chl bro chl");

        /*authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {*/
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                Log.i("userss",user.getPhoneNumber());
                if(user!=null){
                    Log.i("bhaiiiiiii",user.getUid());
                    Intent intent = new Intent(WelcomeActivity.this,MainScreenActivity.class);
                    intent.putExtra("user_phoneNumber",user.getPhoneNumber());
                    startActivity(intent);
                    finish();
                } else {
                    requestLocationPermissions();
                }
            /*}
        };*/
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this,SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });









    }

    @Override
    protected void onStart() {
        super.onStart();
      // firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
       // firebaseAuth.removeAuthStateListener(authStateListener);
    }

    private void requestLocationPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)  ) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of maps api")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(WelcomeActivity.this,
                                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_CONTACTS,Manifest.permission.READ_PHONE_STATE}, 1);
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
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_CONTACTS,Manifest.permission.READ_PHONE_STATE}, 1);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }

        }
    }




}
