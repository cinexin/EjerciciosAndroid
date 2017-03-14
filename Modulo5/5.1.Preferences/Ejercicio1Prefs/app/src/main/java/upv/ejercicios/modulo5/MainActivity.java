package upv.ejercicios.modulo5;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private String SETTINGS_FILE_NAME = "MySettings";
    private String SETTINGS_NAME_KEY = "Nombre";
    private String SETTINGS_PHONE_KEY = "Telefono";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recoverState(findViewById(R.layout.activity_main));
    }

    public void saveState(View view) {
        SharedPreferences _prefs = getSharedPreferences(SETTINGS_FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor _prefsEditor = _prefs.edit();

        // Get name
        String inputName = ( (EditText) findViewById(R.id.txt_name)).getText().toString();
        if (inputName != null)
            _prefsEditor.putString(SETTINGS_NAME_KEY,inputName);

        // Get phone
        String inputPhone = ((EditText) findViewById(R.id.txt_phone)).getText().toString();
        if (inputPhone != null)
            _prefsEditor.putString(SETTINGS_PHONE_KEY, inputPhone);

        _prefsEditor.commit();
    }

    public void recoverState(View view) {
        SharedPreferences _prefs = getSharedPreferences(SETTINGS_FILE_NAME, MODE_PRIVATE);

        // Get prefs: name
        String prefsName = _prefs.getString(SETTINGS_NAME_KEY,"No name");
        // Get prefs: phone
        String prefsPhone = _prefs.getString(SETTINGS_PHONE_KEY, "No phone");

        ( (EditText) findViewById(R.id.txt_name)).setText(prefsName);
        ((EditText) findViewById(R.id.txt_phone)).setText(prefsPhone);
    }

    public void clearState(View view) {
        SharedPreferences _sharedPrefs = getSharedPreferences(SETTINGS_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor _sharedPrefsEditor = _sharedPrefs.edit();

        _sharedPrefsEditor.clear();
        _sharedPrefsEditor.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState( findViewById(R.layout.activity_main));
    }
}
