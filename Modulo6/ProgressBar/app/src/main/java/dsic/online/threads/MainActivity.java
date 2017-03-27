package dsic.online.threads;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    ProgressBar pb = null;
    TextView tv = null;

    Button bStart = null;
    Button bStop = null;

    Integer progress;

    boolean cancelled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = (ProgressBar) findViewById(R.id.pbProgress);
        tv = (TextView) findViewById(R.id.tvPercentage);

        bStart = (Button) findViewById(R.id.bStart);
        bStop = (Button) findViewById(R.id.bStop);

        bStart.setEnabled(true);
        bStop.setEnabled(false);

        updateProgress(0);

    }

    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.bStart:

                new ProgressBarBackgroundTask().execute();


                break;
            case R.id.bStop:
                cancelled = true;
                bStart.setEnabled(true);
                bStop.setEnabled(false);
                break;
        }
    }

    private void updateProgress(int progress) {

    }

    private class ProgressBarBackgroundTask extends AsyncTask<Void, Integer, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {
            updateProgress(0);



            cancelled = false;
            int progress = 0;
            while ((progress < 100) && (!cancelled)) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                progress++;
                publishProgress(progress);
            }
            if (cancelled)
                return Boolean.FALSE;
            else
                return Boolean.TRUE;
        }

        @Override
        protected void onPreExecute() {
            bStart.setEnabled(false);
            bStop.setEnabled(true);

        }

        @Override
        protected void onPostExecute(Boolean finished) {
            if (finished) {
                Toast.makeText(MainActivity.this, R.string.max_count, Toast.LENGTH_SHORT).show();
                bStart.setEnabled(true);
                bStop.setEnabled(false);
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progress = values[0].intValue();
            Log.d("[DEBUG]", values[0].toString());
            pb.setProgress(progress);
            tv.setText(progress + getResources().getString(R.string.out_of_100));
            // updateProgress(values[0].intValue());
        }
    }
}
