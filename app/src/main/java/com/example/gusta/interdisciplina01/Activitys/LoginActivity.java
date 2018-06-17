package com.example.gusta.interdisciplina01.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gusta.interdisciplina01.DAO.FireBase;
import com.example.gusta.interdisciplina01.Entidades.Usuarios;
import com.example.gusta.interdisciplina01.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    //Atributos
    private EditText editEmail;
    private EditText editSenha;
    private Button btLogar;
    private TextView tvCadastrar;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;
    private LoginButton loginButton;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;


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


        // Autenticação com facebook
        mCallbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
                logar2();

            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Operação cancelada!", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Aconteceu um erro inesperado, reinicie o App!", Toast.LENGTH_SHORT).show();


            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser currentUser) {
        if (currentUser != null)
            logar();
    }


    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Falha na autenticação.",
                                    Toast.LENGTH_SHORT).show();

                            updateUI(null);
                        }

                        // ...
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

                    logar();
                    Toast.makeText(LoginActivity.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(LoginActivity.this, "E-mail ou senha invalidos! ", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    // Metodo para chamar outra activity
    private void logar() {
        Intent novaIntent = new Intent(LoginActivity.this, MainActivity.class);
              startActivity(novaIntent);
    }

    // Metodo para chamar outra activity
    private void logar2() {
        Intent novaIntent = new Intent(LoginActivity.this, MainActivity2.class);
        startActivity(novaIntent);
    }
}

