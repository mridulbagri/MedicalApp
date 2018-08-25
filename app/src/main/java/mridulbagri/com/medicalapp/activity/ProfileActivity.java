package mridulbagri.com.medicalapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mridulbagri.com.medicalapp.R;
import mridulbagri.com.medicalapp.model.User;
import mridulbagri.com.medicalapp.utils.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {
 
    TextView textViewId, textViewUsername, textViewDob, textViewGender;

    TextView textViewMetal, textViewFirstName, textViewLastName, textViewAllergies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
 
 
        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewFirstName = (TextView) findViewById(R.id.textViewFirstName);
        textViewLastName = (TextView) findViewById(R.id.textViewLastName);
        textViewAllergies = (TextView) findViewById(R.id.textViewAllergies);
        textViewMetal = (TextView) findViewById(R.id.textViewMetal);
        textViewDob = (TextView) findViewById(R.id.textViewDOB);
        textViewGender = (TextView) findViewById(R.id.textViewGender);
 
 
        User user = SharedPrefManager.getInstance(this).getUser();
 
        textViewId.setText(String.valueOf(user.getId()));
        textViewUsername.setText(user.getUsername());
        textViewFirstName.setText(user.getFirstname());
        textViewLastName.setText(user.getLastname());
        textViewAllergies.setText(user.getAllergies());
        textViewMetal.setText(user.getMetals());
        textViewDob.setText(user.getDateofbirth());

        textViewGender.setText(user.getGender());

        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
    }
}