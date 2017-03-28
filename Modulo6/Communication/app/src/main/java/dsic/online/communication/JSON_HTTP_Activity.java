package dsic.online.communication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import dsic.online.communication.classes.InternetConnectionChecker;
import dsic.online.communication.classes.JSONWeatherParser;
import dsic.online.communication.classes.WeatherHttpClient;
import dsic.online.communication.classes.WeatherInformation;
import dsic.online.communication.classes.WeatherInformationParaGSON;

public class JSON_HTTP_Activity extends AppCompatActivity {


    private TextView cityText;
    private TextView condDescr;
    private TextView temp;
    private TextView press;
    private TextView windSpeed;
    private TextView windDeg;

    private TextView hum;
    private ImageView imgView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (new InternetConnectionChecker().checkInternetConnection(this)) {
            setContentView(R.layout.activity_json_http);
            String city = "Valencia,ES";
            int infotype = WeatherInformation.GSON;

            cityText = (TextView) findViewById(R.id.cityText);
            condDescr = (TextView) findViewById(R.id.condDescr);
            temp = (TextView) findViewById(R.id.temp);
            hum = (TextView) findViewById(R.id.hum);
            press = (TextView) findViewById(R.id.press);
            windSpeed = (TextView) findViewById(R.id.windSpeed);
            windDeg = (TextView) findViewById(R.id.windDeg);
            imgView = (ImageView) findViewById(R.id.condIcon);

            ObtenerTiempoTask tiempoTask = new ObtenerTiempoTask(infotype);
            tiempoTask.execute(city);

        } else {
            new AlertDialog.Builder(this).
                    setTitle("Problema de conectividad").
                    setMessage("Por favor, inténtelo más tarde").
                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        }
    }


    private class ObtenerTiempoTask extends AsyncTask<String, byte[], WeatherInformation> {

        ObtenerTiempoTask(int infotype) {
            this.infoType = infotype;
        }

        private int infoType;

        @Override
        protected WeatherInformation doInBackground(String... params) {

            WeatherInformation weather = new WeatherInformation();

            // 1. Realizamos la petición y obtenemos los datos
            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));

            try {
                // 2. Parseamos los datos recibidos

                if (this.infoType == WeatherInformation.GSON) {
                    Gson gson = new Gson();
                    JSONObject datos = new JSONObject(data);
                    weather.setInformation(gson.fromJson(datos.toString(), WeatherInformationParaGSON.class));
                } else if (this.infoType == WeatherInformation.JSON) {
                    weather.setInformation(JSONWeatherParser.getWeather(data));
                } else {
                    throw new Exception();
                }


                String iconName = weather.getIconName();
                if (iconName != null) {
                    byte[] iconData = (new WeatherHttpClient()).getImage(iconName);
                    if (iconData != null) this.publishProgress(iconData);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return weather;

        }

        @Override
        protected void onProgressUpdate(byte[]... iconDataArray) {
            //Sólo necesitamos el primer elemento del Array;
            byte[] iconData = iconDataArray[0];

            if ((iconData != null) && (iconData.length > 0)) {
                Bitmap img = BitmapFactory.decodeByteArray(iconData, 0, iconData.length);
                imgView.setImageBitmap(img);
            }
        }

        @Override
        protected void onPostExecute(WeatherInformation weather) {
            super.onPostExecute(weather);

            cityText.setText(weather.getCity() + " , " + weather.getCountry());
            condDescr.setText(weather.getCondition() + "(" + weather.getDescription() + ")");
            temp.setText("" + weather.getTemperature() + "ºC");
            hum.setText("" + weather.getHumidity() + "%");
            press.setText("" + weather.getPressure() + " hPa");
            windSpeed.setText("" + weather.getWindSpeed() + " mps");
            windDeg.setText("" + weather.getWindDeg() + "º");
        }

    }

}
