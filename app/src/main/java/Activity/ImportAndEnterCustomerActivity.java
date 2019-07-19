package Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lenden.R;

public class ImportAndEnterCustomerActivity extends AppCompatActivity {
    private Button import_cust,enter_cust;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_andenter_customer);

        import_cust = findViewById(R.id.import_cust);
        enter_cust = findViewById(R.id.enter_cust);

        import_cust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImportAndEnterCustomerActivity.this, ImportCustomersPhonebook.class);
                intent.putExtra("user_phoneNumber",getIntent().getStringExtra("user_phoneNumber"));
                startActivity(intent);
                finish();
            }
        });


        enter_cust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImportAndEnterCustomerActivity.this, CreateCustomer.class);
                intent.putExtra("from where","manual entry");
                intent.putExtra("user_phoneNumber",getIntent().getStringExtra("user_phoneNumber"));
                startActivity(intent);

            }
        });

    }
}
