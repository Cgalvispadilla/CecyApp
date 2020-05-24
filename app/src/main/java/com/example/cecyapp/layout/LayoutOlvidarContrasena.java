package com.example.cecyapp.layout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cecyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LayoutOlvidarContrasena extends AppCompatActivity {

    private TextView tvCorreo, tvContraseña1, tvContraseña2;
    private Button btnContra;
    private  String correo, contraseña1, contraseña2;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_olvidar_contrasena);
        tvCorreo =(TextView) findViewById(R.id.imput_recuperarcorreo);
        tvContraseña1=(TextView) findViewById(R.id.imput_newpass1);
        tvContraseña2=(TextView) findViewById(R.id.imput_newpass2);
        btnContra=(Button) findViewById(R.id.bt_confirmarcambio);

        mAuth= FirebaseAuth.getInstance();

        btnContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //se valida que no haya ningún campo vació

            }
        });

    }

    private void CambiarContraseña() {


        correo= tvCorreo.getText().toString();
        contraseña1=tvContraseña1.getText().toString();
        contraseña2=tvContraseña2.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(!correo.isEmpty()&&!contraseña1.isEmpty()&&!contraseña2.isEmpty()) {
            if(contraseña2.equals(contraseña1)){
                if(user.getEmail().equals(correo)){
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(user.getEmail(),);
                }

            }
        }


        AuthCredential credential = EmailAuthProvider
                .getCredential("user@example.com", "password1234");

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });


    }
}
