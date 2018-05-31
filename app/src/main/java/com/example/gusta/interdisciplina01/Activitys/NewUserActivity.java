package com.example.gusta.interdisciplina01.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gusta.interdisciplina01.DAO.FireBase;
import com.example.gusta.interdisciplina01.Entidades.Usuarios;
import com.example.gusta.interdisciplina01.Helper.Base64Custom;
import com.example.gusta.interdisciplina01.Helper.Preferencias;
import com.example.gusta.interdisciplina01.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class NewUserActivity extends AppCompatActivity {
    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private EditText editConfirSenha;
    private Button btCadastrar;
    private Usuarios usuarios;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        editNome = (EditText) findViewById(R.id.editNome);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editSenha = (EditText) findViewById(R.id.editSenha);
        editConfirSenha = (EditText) findViewById(R.id.editConfirSenha);
        btCadastrar = (Button) findViewById(R.id.btCadastrar);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewUserActivity.this, " Por favor, Aguarde!", Toast.LENGTH_SHORT).show();
                if (!editNome.getText().toString().equals("") && !editEmail.getText().toString().equals("") && !editSenha.getText().toString().equals("") && editSenha.getText().toString().equals(editConfirSenha.getText().toString())) {
                    usuarios = new Usuarios();
                    usuarios.setNome(editNome.getText().toString());
                    usuarios.setEmail(editEmail.getText().toString());
                    usuarios.setSenha(editSenha.getText().toString());

                    cadastrarUsuario();
                } else
                    Toast.makeText(NewUserActivity.this, " Algum Campo esta em branco ou As Senha não correspondem, Reveja!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void cadastrarUsuario() {

        autenticacao = FireBase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuarios.getEmail(),
                usuarios.getSenha()).addOnCompleteListener(NewUserActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Se der certo a criação
                if (task.isSuccessful()) {
                    Toast.makeText(NewUserActivity.this, "Usuario Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                    String identificadorUsuario = Base64Custom.codificarbase64(usuarios.getEmail());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuarios.setId(identificadorUsuario);
                    usuarios.salvar();

                    Preferencias preferencias = new Preferencias(NewUserActivity.this);
                    preferencias.gravarUsuarioPreferencias(identificadorUsuario, usuarios.getNome());

                    abrirLogin();
                } else {

                    String erroExcecao = "";

                    try {

                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Por favor, Digite uma senha mais forte, com pelo menos 8 caracteres!";

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O email digitado é invalido, digite um novo email!";

                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Este email ja existe no sistema!";

                    } catch (NullPointerException e) {
                        erroExcecao = "Algum campo Esta vazio!";

                    } catch (Exception e) {
                        erroExcecao = "Erro ao Efetuar cadastro";
                        e.printStackTrace();


                        Toast.makeText(NewUserActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_SHORT).show();

                    }
                }
            }

        });
    }


    public void abrirLogin() {
        Intent intent = new Intent(NewUserActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}

