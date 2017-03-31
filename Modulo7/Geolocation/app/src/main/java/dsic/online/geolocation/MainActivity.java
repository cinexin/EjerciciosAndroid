package dsic.online.geolocation;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final int DONT_LOCATE = 0;
    final int LOCATE = 1;

    int state = DONT_LOCATE;

    EditText etLongitude = null;
    EditText etLatitude = null;
    TextView tvAddress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLongitude = (EditText) findViewById(R.id.etLongitude);
        etLatitude = (EditText) findViewById(R.id.etLatitude);
        tvAddress = (TextView) findViewById(R.id.tvAddress);

        state = DONT_LOCATE;

        // TODO: Get references to the LocationManager and our own defined LocationListener

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        switch (state) {
            case DONT_LOCATE:
                menu.findItem(R.id.action_locate).setVisible(true);
                menu.findItem(R.id.action_dont_locate).setVisible(false);
                break;
            case LOCATE:
                menu.findItem(R.id.action_locate).setVisible(false);
                menu.findItem(R.id.action_dont_locate).setVisible(true);
                break;
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_locate:
                state = LOCATE;

                // TODO: Request location updates from the LocationManager

                break;
            case R.id.action_dont_locate:
                state = DONT_LOCATE;

                // TODO: Stop receiving location updates

                break;
        }
        supportInvalidateOptionsMenu();
        return true;
    }

    // TODO: Create a new private class implementing LocationListener

    // TODO: Create an AsyncTask to translate latitude and longitude into a human readable address using Geocoder

}
