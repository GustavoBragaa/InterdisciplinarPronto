package com.example.gusta.interdisciplina01.Activitys;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.gusta.interdisciplina01.R;
import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private Button btMaps, btNuvem, btDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Pegando id e chamando activity
        btMaps = (Button) findViewById(R.id.btMaps);
        btMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ChamaMaps = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(ChamaMaps);
            }
        });


        //Pegando id e chamando activity
        btDados = (Button) findViewById(R.id.btDados);
        btDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ChamaDados = new Intent(MainActivity.this, MostraDadosActivity.class);
                startActivity(ChamaDados);

                startActivity(ChamaDados);
            }
        });


        //Pegando id e chamando activity
        btNuvem = (Button) findViewById(R.id.btNuvem);
        btNuvem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ChamaMaps = new Intent(MainActivity.this, NuvemActivity.class);
                startActivity(ChamaMaps);
            }
        });


    }

    public void sair(View v) {
        AccessToken.setCurrentAccessToken(null);
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        FirebaseAuth.getInstance().signOut();
        startActivity(loginIntent);
        finish();
    }
}
