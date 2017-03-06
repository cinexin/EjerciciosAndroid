package upv.ejercicios.modulo3.ejercicio1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);
    }

    /*
        this is called when "onClick" on "first_enter" button
     */
    public void firstEnter(View view) {
        Intent intent = new Intent(this, ActivityFirstEnter.class);
        startActivity(intent);
    }
}
