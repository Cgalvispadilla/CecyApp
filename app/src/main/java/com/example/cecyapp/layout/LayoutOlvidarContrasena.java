package com.example.cecyapp.layout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cecyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class LayoutOlvidarContrasena extends AppCompatActivity {

    private TextInputEditText tvCorreo, tvContraseña1, tvContraseña2;
    private Button btnContra;
    private  String correo, contraseña1, contraseña2,  contraVieja;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_olvidar_contrasena);
        tvCorreo =(TextInputEditText) findViewById(R.id.imput_recuperarcorreo);
        tvContraseña1=(TextInputEditText) findViewById(R.id.imput_newpass1);
        tvContraseña2=(TextInputEditText) findViewById(R.id.imput_newpass2);
        btnContra=(Button) findViewById(R.id.bt_confirmarcambio);

        mAuth= FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

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

        if(!correo.isEmpty()&&!contraseña1.isEmpty()&&!contraseña2.isEmpty()) {
            if(contraseña2.equals(contraseña1)){
                if(user.getEmail().equals(correo)){
                    mDatabase.child("Cliente").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                contraVieja=dataSnapshot.child("contraseña").getValue().toString();
                            }
                                AuthCredential credential = EmailAuthProvider
                                        .getCredential(user.getEmail(),contraVieja);
                                user.reauthenticate(credential)  .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isComplete()){
                                            user.updatePassword(contraseña1)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Map<String,Object> actualización = new HashMap<>();
                                                                actualización.put("contraseña",contraseña1);
                                                                mDatabase.child("Cliente").updateChildren(actualización);
                                                                Toast.makeText(getApplicationContext(),"Se cambio correctamente la contraseña", Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            }
        }









    }
}
