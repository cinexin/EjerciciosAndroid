package dsic.online.geolocation;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    final int DONT_LOCATE = 0;
    final int LOCATE = 1;

    int state = DONT_LOCATE;

    EditText etLongitude = null;
    EditText etLatitude = null;
    TextView tvAddress = null;
    LocationManager locationManager = null;
    MyLocationListener myLocationListener = null;
    String locationProvider;
    GoogleMap googleMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // DONE: Get and display The MapView
        /* View view = findViewById(R.id.myMap);
        MapsInitializer.initialize(getApplicationContext());
        ((MapView) findViewById(R.id.myMap)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        }); */


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);

        etLongitude = (EditText) findViewById(R.id.etLongitude);
        etLatitude = (EditText) findViewById(R.id.etLatitude);
        tvAddress = (TextView) findViewById(R.id.tvAddress);

        state = DONT_LOCATE;


        // DONE: Get references to the LocationManager and our own defined LocationListener

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            locationProvider = LocationManager.GPS_PROVIDER;
        else {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        }

        myLocationListener = new MyLocationListener();


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
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(39.472864, -0.401238)));
        googleMap.moveCamera(CameraUpdateFactory.zoomIn());
        this.googleMap = googleMap;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_locate:
                state = LOCATE;

                // DONE: Request location updates from the LocationManager
                try {
                    locationManager.requestLocationUpdates(locationProvider, 0, 0, myLocationListener);
                } catch (SecurityException securityEx) {
                    Log.e("[ERROR]", "Couldn't get location updates. Check your GPS/Network provider: " +
                            securityEx.getMessage());
                    return false;
                }
                break;
            case R.id.action_dont_locate:
                state = DONT_LOCATE;
                // DONE: Stop receiving location updates
                locationManager.removeUpdates(myLocationListener);
                break;

            case R.id.acction_add_marker:
                // TODO: add marker to the map...

                if (etLatitude.getText() != null && etLongitude.getText() != null) {
                    MarkerOptions markerOptions =  new MarkerOptions();
                    LatLng locationLatLng = new LatLng(Double.valueOf(etLatitude.getText().toString()), Double.valueOf(etLongitude.getText().toString())) ;
                    markerOptions.position(locationLatLng);
                    Log.d("[DEBUG]", locationLatLng.toString());
                    markerOptions.title("Marker");
                    markerOptions.snippet("Snippet");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    this.googleMap.addMarker(markerOptions);

                } else {
                    Toast.makeText(this, "No location to mark", Toast.LENGTH_SHORT).show();
                    return true;
                }
                break;
        }
        supportInvalidateOptionsMenu();
        return true;
    }

    // DONE: Create a new private class implementing LocationListener
    private class MyLocationListener implements LocationListener {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.w("[WARNING]","GPS Status changed. Provider: " + provider + " status " + status);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.i("[INFO]", "Provider enabled: " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.w("[WARNING]","Provider disabled: " + provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            etLongitude.setText(String.valueOf(location.getLongitude()));
            etLatitude.setText(String.valueOf(location.getLatitude()));
            Log.d("[DEBUG]", location.toString());
            new LocationTranslationTask().execute(location);
        }
    }

    // DONE: Create an AsyncTask to translate latitude and longitude into a human readable address using Geocoder
    private class LocationTranslationTask extends AsyncTask<Location, Void, List<Address>> {

        @Override
        protected List<Address> doInBackground(Location... params) {
            Location location = params[0];

            Log.d("[DEBUG]", "Translating location from: " + location.toString());
            Geocoder geocoder = new Geocoder(getApplicationContext());
            try {
                Log.d("[DEBUG]", "Longitude: " + Double.valueOf(location.getLongitude()).doubleValue());
                Log.d("[DEBUG]", "Latitude: " + Double.valueOf(location.getLatitude()).doubleValue());
                List<Address> addresses = geocoder.getFromLocation(Double.valueOf(location.getLatitude()).doubleValue(),
                        Double.valueOf(location.getLongitude()).doubleValue(),
                         5);
                Log.d("[DEBUG]", "Addresses size: " + String.valueOf(addresses.size()));
                for (Address address: addresses) {
                    Log.d("[DEBUG]", address.toString());
                }

                return addresses;
            } catch (IOException ioEx) {
                Log.e("[ERROR]", "Error while trying to translate location. Cause: " + ioEx.getMessage());
                return null;
            } catch (IllegalArgumentException argEx) {
                Log.e("[ERROR]", "Error while trying to translate location. Cause: " + argEx.getMessage());
                Log.d("[DEBUG]", "Longitude: " + location.getLongitude());
                Log.d("[DEBUG]", "Latitude: " + location.getLatitude());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {
            super.onPostExecute(addresses);
            if (addresses != null) {
                for (Address address: addresses) {
                    Log.d("[DEBUG]", address.toString());
                    tvAddress.setText(address.getLocality() + "," + address.getCountryName());
                }
            }
        }
    }
}
