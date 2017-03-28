package dsic.online.communication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dsic.online.communication.classes.InternetConnectionChecker;
import dsic.online.communication.classes.WeatherHttpClient;

/**
 * Created by migui on 0028.
 */

public class HTML_HTTP_Activity extends AppCompatActivity {

    private ProgressDialog webOpenProgressDialog;
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("[INFO]", "HI!...");
        if (new InternetConnectionChecker().checkInternetConnection(this)) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_html_http);
            String city = "Valencia,ES";
            HTMLWeatherTask htmlWeatherTask = new HTMLWeatherTask();
            Log.d("[DEBUG]", "I'm launching HTMLWeatherTask...");
            htmlWeatherTask.execute(city);
            this.webView = (WebView) findViewById(R.id.webView);
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



    private class HTMLWeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Log.d("[DEBUG]", "doInBackground(): " + params[0]);
            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));
            Log.d("[DEBUG]" , "doInBackground(): " + data);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            // myWebClient webClient = new myWebClient();
            webView.loadData(s,"text/html", null);
        }
    }

    private class myWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // Load the given URL on our WebView.
            view.loadUrl(url);

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // When the page has finished loading, hide progress dialog and progress bar
            super.onPageFinished(view, url);
            webOpenProgressDialog.dismiss();
        }
    }
}
