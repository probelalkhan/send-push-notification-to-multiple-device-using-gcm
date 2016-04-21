package net.simplifiedcoding.gcmpushnotification;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Belal on 4/20/2016.
 */
public class SharedPrefManager {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String SHARED_PREF = "gcmpushnotification";
    private static final String KEY_IS_USER_ADDED = "isuseradded";
    private static final String KEY_USER_EMAIL = "useremail";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_USER_ID= "userid";

    public SharedPrefManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean addUser(int id, String name, String email){
        editor.putInt(KEY_USER_ID,id);
        editor.putString(KEY_USER_EMAIL,email);
        editor.putString(KEY_USER_NAME,name);
        editor.putBoolean(KEY_IS_USER_ADDED,true);
        editor.apply();
        return true;
    }


    public int getUserId(){
        return sharedPreferences.getInt(KEY_USER_ID, 1);
    }

    public String getUserName(){
        return sharedPreferences.getString(KEY_USER_NAME,"");
    }

    public String getUserEmail(){
        return sharedPreferences.getString(KEY_USER_EMAIL,"");
    }

    public boolean isUserAdded(){
        return sharedPreferences.getBoolean(KEY_IS_USER_ADDED, false);
    }
}
