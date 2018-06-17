package com.example.gusta.interdisciplina01.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gusta.interdisciplina01.DAO.FireBase;
import com.example.gusta.interdisciplina01.Entidades.Usuarios;
import com.example.gusta.interdisciplina01.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MostraDadosActivity extends AppCompatActivity {

    private DatabaseReference firebase;
    private ValueEventListener valuevalueEventListener;
    private TextView txt1, txt2;
    private Usuarios usuarios;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_dados);

        Intent intent = getIntent();
        final String retorna = intent.getStringExtra("id");

        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt1);
        firebase = FireBase.getFireBase().child("Usuarios").child(retorna);


//        valuevalueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    usuarios.add(postSnapshot.getValue(Usuarios.class));
//                    txt1.setText(retorna);
//
//                    String nome = usuarios.getNome();
//
//
//                    txt2.setText(nome);
//
//
//                    Log.d("guga: ", usuarios.getNome());
//                    Log.d("Author: ", usuarios.getEmail());
//
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//            ;
//
//
//        };


    }
}

