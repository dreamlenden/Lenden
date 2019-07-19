package Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenden.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SignupActivity extends AppCompatActivity {

    EditText phone;
    Button send;
    private String mVerificationId,otp;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthCredential credentiall;
    private FirebaseAuth firebaseAuth;
    private String phoneNumber;
    //private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        phone = findViewById(R.id.phone);
        send = findViewById(R.id.signup);
        firebaseAuth= FirebaseAuth.getInstance();

        final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Toast.makeText(SignupActivity.this,"OTP SUCCESSFULLY VERIFIED",Toast.LENGTH_SHORT).show();
                otp=credential.getSmsCode();
                Toast.makeText(SignupActivity.this," ye h otp "+otp,Toast.LENGTH_LONG).show();
                //credentiall = PhoneAuthProvider.getCredential(mVerificationId, otp);
                SigninWithPhone(credential);

                Log.d("idhr bhai idhr", "onVerificationCompleted:" + credential.getSmsCode());

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.

                Toast.makeText(SignupActivity.this,"this is problem  "+e.getMessage(),Toast.LENGTH_SHORT).show();
                Log.w("arree", "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("aa gya", "onCodeSent:" + verificationId);


                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                Toast.makeText(SignupActivity.this,"aa rha h bhai "+mVerificationId,Toast.LENGTH_SHORT).show();

                // ...
            }
        };


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = phone.getText().toString();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber,
                        5,
                        java.util.concurrent.TimeUnit.SECONDS,
                        SignupActivity.this,
                        mCallbacks);
            }
        });


    }

    private void SigninWithPhone(final PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final Intent intent1,intent2;
                                intent1 = new Intent(SignupActivity.this,MainScreenActivity.class);
                            intent2= new Intent(SignupActivity.this,SignupdetailsActivity.class);
                            intent1.putExtra("phoneNumber",phoneNumber);
                            intent2.putExtra("phoneNumber",phoneNumber);

                            FirebaseDatabase.getInstance().getReference().child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        startActivity(intent1);
                                    finish();}
                                    else {
                                        startActivity(intent2);
                                        finish();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });



                        } else {
                            Toast.makeText(SignupActivity.this,"Incorrect OTP",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
