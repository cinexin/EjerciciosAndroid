package dsic.online.threads;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    ProgressBar pb = null;
    TextView tv = null;

    Button bStart = null;
    Button bStop = null;

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
                updateProgress(0);

                bStart.setEnabled(false);
                bStop.setEnabled(true);

                cancelled = false;
                int progress = 0;
                while ((progress < pb.getMax()) && (!cancelled)) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                    progress++;
                    updateProgress(progress);
                }

                Toast.makeText(this, R.string.max_count, Toast.LENGTH_SHORT).show();

                bStart.setEnabled(true);
                bStop.setEnabled(false);

                break;
            case R.id.bStop:
                cancelled = true;
                bStart.setEnabled(true);
                bStop.setEnabled(false);
                break;
        }
    }

    private void updateProgress(int progress) {
        pb.setProgress(progress);
        tv.setText(progress + getResources().getString(R.string.out_of_100));
    }

    private class ProgressBarBackgroundTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
