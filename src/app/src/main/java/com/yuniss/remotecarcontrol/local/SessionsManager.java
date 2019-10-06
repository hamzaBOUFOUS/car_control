package com.yuniss.remotecarcontrol.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionsManager {
    private static String TAG = SessionsManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "ControlCarAppLogin";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_USER_ID = "user_id";


    public SessionsManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }


    public void setUserId(int userId) {

        editor.putInt(KEY_USER_ID, userId);

        // commit changes
        editor.commit();

        Log.d(TAG, "User added to session!");
    }

    public int getUserId() {
        return pref.getInt(KEY_USER_ID, 0);
    }

    public boolean userexist() {
        return pref.contains(KEY_USER_ID);
    }


    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void drop() {
        editor.clear();
    }


}
