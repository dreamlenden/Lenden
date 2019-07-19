package Activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.lenden.R;

import fragments.customer_list;
import fragments.dahsboard;

public class MainScreenActivity extends AppCompatActivity {
    ImageView dashboard,cust_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        dashboard = findViewById(R.id.dashboard);
        cust_list = findViewById(R.id.cust_list);

        switchToFragmentDashboard();

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToFragmentDashboard();
            }
        });

        cust_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToFragmentCustList();
            }
        });



    }

    private void switchToFragmentDashboard() {
        cust_list.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        dashboard.setColorFilter( ContextCompat.getColor( MainScreenActivity.this, R.color.Black), android.graphics.PorterDuff.Mode.MULTIPLY );
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame, new dahsboard()).commit();
    }

    private void switchToFragmentCustList() {
        dashboard.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        cust_list.setColorFilter( ContextCompat.getColor( MainScreenActivity.this, R.color.Black), android.graphics.PorterDuff.Mode.MULTIPLY );
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame, new customer_list()).commit();
    }
}
