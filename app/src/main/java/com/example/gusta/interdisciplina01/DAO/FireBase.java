package com.example.gusta.interdisciplina01.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by gusta on 02/04/2018.
 */

public class FireBase {

    private static DatabaseReference referenciaFireBase;
    private static FirebaseAuth autenticacao;

    public static DatabaseReference getFireBase() {
        //Testeando se a referencia for nula, cria referencia, se não retorna referencia
        if (referenciaFireBase == null) {
            referenciaFireBase = FirebaseDatabase.getInstance().getReference();
        }

        return referenciaFireBase;
    }


    public static  FirebaseAuth getFirebaseAutenticacao(){
        //Testeando se a autenticação for nula, cria autenticação, se não retorna autenticação
        if (autenticacao == null) {
            autenticacao = FirebaseAuth.getInstance();
        }

        return autenticacao;
    }
}
