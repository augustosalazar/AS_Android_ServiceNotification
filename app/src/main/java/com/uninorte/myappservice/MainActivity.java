package com.uninorte.myappservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void DetenerServicio(View view) {
        stopService(new Intent(MainActivity.this, MyService.class));
    }
    public void IniciarServicio(View view) {
        startService(new Intent(MainActivity.this, MyService.class));
    }

}
