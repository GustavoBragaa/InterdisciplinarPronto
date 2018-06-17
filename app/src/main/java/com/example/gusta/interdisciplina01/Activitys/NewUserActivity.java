package com.example.gusta.interdisciplina01.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
import com.google.firebase.database.DatabaseReference;

import java.util.UUID;

public class NewUserActivity extends AppCompatActivity {
    //Declarando Atributos
    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private EditText editConfirSenha;
    private EditText editIdade;
    private Button btCadastrar;
    private Usuarios usuarios;
    private FirebaseAuth autenticacao;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        // Pegando valores dos atributos
        editNome = (EditText) findViewById(R.id.editNome);
        editIdade = (EditText) findViewById(R.id.editIdade);
        //Limitando tamanho de caracteres no campo
        editIdade.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (editIdade.getText().toString().length() > 2) {
                    Toast.makeText(NewUserActivity.this, " Você só pode digitar dois caracteres.", Toast.LENGTH_SHORT).show();
                    editIdade.setText(editIdade.getText().toString().substring(0, 2));

                    return false;

                }
                return false;
            }
        });
        // Pegando valores dos atributos
        editEmail = (EditText) findViewById(R.id.editEmail);
        editSenha = (EditText) findViewById(R.id.editSenha);
        editConfirSenha = (EditText) findViewById(R.id.editConfirSenha);
        btCadastrar = (Button) findViewById(R.id.btCadastrar);
        // metodo de Click do botão
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewUserActivity.this, " Por favor, Aguarde!", Toast.LENGTH_SHORT).show();
                if (!editNome.getText().toString().equals("") && !editEmail.getText().toString().equals("") && !editSenha.getText().toString().equals("") && editSenha.getText().toString().equals(editConfirSenha.getText().toString())) {
                    usuarios = new Usuarios();

                    String id = UUID.randomUUID().toString();


                    usuarios.setId(id);
                    usuarios.setNome(editNome.getText().toString());
                    usuarios.setIdade(Integer.parseInt(editIdade.getText().toString()));
                    usuarios.setEmail(EncodeString(editEmail.getText().toString()));
                    usuarios.setSenha(editSenha.getText().toString());


                    salvaDados(usuarios);

                    cadastrarUsuario(id);



                } else
                    Toast.makeText(NewUserActivity.this, " Algum Campo esta em branco ou As Senha não correspondem, Reveja!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Salvando dados no banco de dados
    private boolean salvaDados(Usuarios usuarios) {
        try {


            firebase = FireBase.getFireBase().child("Usuarios");
            firebase.child(usuarios.getId()).setValue(usuarios);




            return true;


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Cadastrando usuarios
    private void cadastrarUsuario(final String id) {

        autenticacao = FireBase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                DencodeString(usuarios.getEmail()),
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

                    Bundle bundle = new Bundle();
                    bundle.putString("id", id);
                    Intent intent = new Intent(NewUserActivity.this, MainActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);


                } else {

                    String erroExcecao = "";

                    try {

                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Por favor, Digite uma senha mais forte";

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O email digitado é invalido, digite um novo email!";

                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Este email ja existe no sistema!";

                    } catch (NullPointerException e) {
                        erroExcecao = "Algum campo Esta vazio!";

                    } catch (Exception e) {
                        erroExcecao = "Erro ao Efetuar cadastro";
                        e.printStackTrace();

                    }

                    Toast.makeText(NewUserActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_SHORT).show();
                }
            }

        });
    }




    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public static String DencodeString(String string) {
        return string.replace(",", ".");
    }

}
