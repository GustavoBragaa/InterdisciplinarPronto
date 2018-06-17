package com.example.gusta.interdisciplina01.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gusta.interdisciplina01.DAO.FireBase;
import com.example.gusta.interdisciplina01.Entidades.Usuarios;
import com.example.gusta.interdisciplina01.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class AlterandoDadosActivity extends AppCompatActivity {


    private EditText editIdade, editNome;
    private Button btAtualizar;
    private Usuarios usuarios;
    private DatabaseReference firebase;
    String email, senha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterando_dados);

        // Pegando valores dos atributos
        editNome = (EditText) findViewById(R.id.editNome);
        editIdade = (EditText) findViewById(R.id.editIdade);
        //Limitando tamanho de caracteres no campo
        editIdade.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (editIdade.getText().toString().length() > 2) {
                    Toast.makeText(AlterandoDadosActivity.this, " Você só pode digitar dois caracteres.", Toast.LENGTH_SHORT).show();
                    editIdade.setText(editIdade.getText().toString().substring(0, 2));

                    return false;

                }
                return false;
            }
        });


        btAtualizar = (Button) findViewById(R.id.btAtualizar);
        btAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editNome.getText().toString().equals("") && !editNome.getText().toString().equals("")) {


                    Intent intent = getIntent();
                    email = intent.getStringExtra("email");
                    senha = intent.getStringExtra("senha");

                    usuarios = new Usuarios();

                    usuarios.setNome(editNome.getText().toString());
                    usuarios.setIdade(Integer.parseInt(editIdade.getText().toString()));
                    usuarios.setEmail(EncodeString(email));
                    usuarios.setSenha(senha);

                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                    String user = currentFirebaseUser.getUid();

                    firebase = FireBase.getFireBase().child("Usuarios");
                    firebase.child(user).setValue(usuarios);


                    Intent Mostradados = new Intent(AlterandoDadosActivity.this, MostraDadosActivity.class);
                    startActivity(Mostradados);



                } else {
                    Toast.makeText(AlterandoDadosActivity.this, " Algum Campo esta em branco, Reveja!", Toast.LENGTH_SHORT).show();

                }
            }

        });


    }

    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }
}
