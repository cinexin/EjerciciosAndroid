package upv.ejercicios.Modulo3Ejercicio2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class CicloVidaActivity extends Activity {
    StringBuilder mensajes = new StringBuilder();
    TextView visor ;

    private void log(String text) {
        Log.d("Ciclo vida", text);
        mensajes.append(text);
        mensajes.append("\n");
        visor.setText(mensajes.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        visor = new TextView(this);
        visor.setTextSize(24);
        visor.setText(mensajes.toString());
        setContentView(visor);
        log(getResources().getString(R.string.on_create_msg_log));

    }

    @Override
    protected void onResume() {
        super.onResume();
        log(getResources().getString(R.string.on_resume_msg_log));
    }

    @Override
    protected void onStop() {
        super.onStop();
        log(getResources().getString(R.string.on_stop_msg_log));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log(getResources().getString(R.string.on_destroy_msg_log));
    }
}
