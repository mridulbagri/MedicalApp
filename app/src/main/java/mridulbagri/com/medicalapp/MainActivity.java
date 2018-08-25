package mridulbagri.com.medicalapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import mridulbagri.com.medicalapp.activity.LoginActivity;
import mridulbagri.com.medicalapp.activity.ProfileActivity;
import mridulbagri.com.medicalapp.handler.RequestHandler;
import mridulbagri.com.medicalapp.model.User;
import mridulbagri.com.medicalapp.utils.SharedPrefManager;
import mridulbagri.com.medicalapp.utils.URLs;

public class MainActivity extends AppCompatActivity {

    EditText editTextUsername, editTextFirstName, editTextLastName, editTextDOB , editTextAllergies , editTextMetals, editTextPassword;
    RadioGroup radioGroupGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
            return;
        }



        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextDOB = (EditText) findViewById(R.id.editTextDOB);
        editTextAllergies = (EditText) findViewById(R.id.editTextAllergies);
        editTextMetals = (EditText) findViewById(R.id.editTextMetals);

        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGender);


        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });


    }

    private void registerUser() {
        final String username = editTextUsername.getText().toString().trim();
        final String firstname = editTextFirstName.getText().toString().trim();
        final String lastname = editTextLastName.getText().toString().trim();
        final String dob = editTextDOB.getText().toString().trim();
        final String allergies = editTextAllergies.getText().toString().trim();
        final String metals = editTextMetals.getText().toString().trim();


        final String password = editTextPassword.getText().toString().trim();

        final String gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(firstname)) {
            editTextFirstName.setError("Please enter firstname");
            editTextFirstName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lastname)) {
            editTextLastName.setError("Please enter lastname");
            editTextLastName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(dob)) {
            editTextDOB.setError("Please enter dob");
            editTextDOB.requestFocus();
            return;
        }

//        if (TextUtils.isEmpty(allergies)) {
//            editTextAllergies.setError("Please enter username");
//            editTextAllergies.requestFocus();
//            return;
//        }
//
//        if (TextUtils.isEmpty(metals)) {
//            editTextMetals.setError("Please enter username");
//            editTextMetals.requestFocus();
//            return;
//        }


        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }

        class RegisterUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("firstname", firstname);
                params.put("lastname", lastname);
                params.put("dob", dob);
                params.put("alleriges", allergies);
                params.put("metals", allergies);

                params.put("password", password);
                params.put("gender", gender);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject obj = new JSONObject(s);

                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        JSONObject userJson = obj.getJSONObject("user");

                        User user = new User(
                                userJson.getInt("id"),
                                userJson.getString("username"),
                                userJson.getString("firstname"),
                                userJson.getString("lastname"),
                                userJson.getString("dob"),
                                userJson.getString("allergies"),
                                userJson.getString("metals"),
                                userJson.getString("gender")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute();
    }

}
