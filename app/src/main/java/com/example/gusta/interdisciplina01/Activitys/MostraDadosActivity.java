package com.example.gusta.interdisciplina01.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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


    private TextView txt1, txt2, txt3, txt4;
    private Button btAlteraDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_dados);




        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String user = currentFirebaseUser.getUid();


        DatabaseReference raiz = FirebaseDatabase.getInstance().getReference();
        raiz.child("Usuarios").child(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Usuarios user = dataSnapshot.getValue(Usuarios.class);


                txt1 = (TextView) findViewById(R.id.txt1);
                txt2 = (TextView) findViewById(R.id.txt2);
                txt3 = (TextView) findViewById(R.id.txt3);
                txt4 = (TextView) findViewById(R.id.txt4);

                txt1.setText("E-Mail: " + DencodeString(user.getEmail()));
                txt2.setText("Nome: " + user.getNome());
                txt3.setText("Idade: " + user.getIdade());
                txt4.setText("Senha: " + user.getSenha());

                final String email = DencodeString(user.getEmail());
                final String senha = user.getSenha();


                btAlteraDados = (Button) findViewById(R.id.btAlteraDados);
                btAlteraDados.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(MostraDadosActivity.this);
                        builder.setCancelable(false);
                        builder.setTitle("Atenção");
                        builder.setMessage("Você só podera atualizar nome e idade. "

                        );
                        builder.setPositiveButton("Aceitar", new DialogInterface.OnClickListener() {

                            // Se Aceitar Chama Activity Filiais
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent atualizarIntent = new Intent(MostraDadosActivity.this, AlterandoDadosActivity.class);
                                // Passando valor para a intent
                                atualizarIntent.putExtra("email", email);
                                atualizarIntent.putExtra("senha", senha);
                                // Startando Intent
                                startActivity(atualizarIntent);


                            }
                        });

                        builder.setNegativeButton("Sair", new DialogInterface.OnClickListener() {
                            // Se recusar Chama Activity Motivo
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Finaliza a Apllcaçao
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                finish();
                                startActivity(intent);


                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();





                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public static String DencodeString(String string) {
        return string.replace(",", ".");
    }
}

