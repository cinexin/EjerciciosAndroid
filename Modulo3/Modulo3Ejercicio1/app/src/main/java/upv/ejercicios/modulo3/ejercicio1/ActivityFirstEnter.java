package upv.ejercicios.modulo3.ejercicio1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by migui on 0005.
 */

public class ActivityFirstEnter extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_first_enter);
    }

    public void secondEnter(View view) {
        Intent intent = new Intent(this, FinalState.class);
        startActivity(intent);
    }
}
