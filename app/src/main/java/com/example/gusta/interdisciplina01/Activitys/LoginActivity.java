package com.example.gusta.interdisciplina01.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gusta.interdisciplina01.DAO.FireBase;
import com.example.gusta.interdisciplina01.Entidades.Usuarios;
import com.example.gusta.interdisciplina01.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    //Atributos
    private EditText editEmail;
    private EditText editSenha;
    private Button btLogar;
    private TextView tvCadastrar;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        tvCadastrar = findViewById(R.id.tvCadastrar);
        tvCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ChamarnewUser = new Intent(LoginActivity.this, NewUserActivity.class);
                startActivity(ChamarnewUser);
            }
        });

        //Recebendo id's
        editEmail = (EditText) findViewById(R.id.editEmail);
        editSenha = (EditText) findViewById(R.id.editSenha);
        btLogar = (Button) findViewById(R.id.btLogar);


        tvCadastrar = (TextView) findViewById(R.id.tvCadastrar);
        tvCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ChamaCadastrar = new Intent(LoginActivity.this, NewUserActivity.class);
                startActivity(ChamaCadastrar);
            }
        });

        //Criando evento de click para o botão
        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(LoginActivity.this, "Aguarde um Momento.", Toast.LENGTH_SHORT).show();

                //Se email e senha não forem vazios
                if (!editEmail.getText().toString().equals("") && !editSenha.getText().toString().equals("")) {

                    //Instanciando Objeto
                    usuarios = new Usuarios();
                    //Mandando valores para classe Usuarios
                    usuarios.setEmail(editEmail.getText().toString());
                    usuarios.setSenha(editSenha.getText().toString());
                    //Chamando Metodo
                    validarLogin();

                } else {
                    //Se forem vazios
                    Toast.makeText(LoginActivity.this, "Campo E-mail ou Senha esta vazio, por favor, Preencha!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //autenticação de email e senha
    private void validarLogin() {
        autenticacao = FireBase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuarios.getEmail(), usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    ChamarMain();
                    Toast.makeText(LoginActivity.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(LoginActivity.this, "E-mail ou senha invalidos! ", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void ChamarMain() {

        Intent ChamaMain = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(ChamaMain);
    }


}

