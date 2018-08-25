package mridulbagri.com.medicalapp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import mridulbagri.com.medicalapp.activity.LoginActivity;
import mridulbagri.com.medicalapp.model.User;

public class SharedPrefManager {
 
    //the constants
    private static final String SHARED_PREF_NAME = "medicalsharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_FIRSTNAME = "keyfirstname";
    private static final String KEY_LASTNAME = "keylastname";
    private static final String KEY_ALLERGIES = "keyallergies";
    private static final String KEY_METALS = "keymetals";
    private static final String KEY_DOB = "keydob";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_ID = "keyid";
 
    private static SharedPrefManager mInstance;
    private static Context mCtx;
 
    private SharedPrefManager(Context context) {
        mCtx = context;
    }
 
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
 

    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_FIRSTNAME, user.getFirstname());
        editor.putString(KEY_LASTNAME, user.getLastname());
        editor.putString(KEY_ALLERGIES, user.getAllergies());
        editor.putString(KEY_METALS, user.getMetals());
        editor.putString(KEY_DOB, user.getDateofbirth());
        editor.putString(KEY_GENDER, user.getGender());
        editor.apply();
    }
 
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }
 
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_FIRSTNAME, null),
                sharedPreferences.getString(KEY_LASTNAME, null),
                sharedPreferences.getString(KEY_ALLERGIES, null),
                sharedPreferences.getString(KEY_METALS, null),
                sharedPreferences.getString(KEY_DOB, null),
                sharedPreferences.getString(KEY_GENDER, null)
        );
    }
 
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}