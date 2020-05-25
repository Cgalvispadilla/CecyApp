package com.example.cecyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cecyapp.layout.FormularioRegistro;
import com.example.cecyapp.layout.LayoutCliente;
import com.example.cecyapp.layout.LayoutModista;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;




public class MainActivity extends AppCompatActivity {
    private TextView  tvRegistro;
    private TextInputEditText tvCorreoEle;
    private TextInputEditText tvContraseña;
    private Button btnSesion;

    private String correo = "";
    private String contraseña = "";

    //se crea la variable de autenticación de firabase
    FirebaseAuth mAuth;
    //se crea la variable de la base de datos
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvRegistro = (TextView) findViewById(R.id.tv_registro);
        tvCorreoEle = (TextInputEditText) findViewById(R.id.imput_usuario);
        tvContraseña = (TextInputEditText) findViewById(R.id.imput_contra);
        btnSesion = (Button) findViewById(R.id.but_iniciarsesion);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();






        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = tvCorreoEle.getText().toString();
                contraseña = tvContraseña.getText().toString();
                if (!correo.isEmpty() && !contraseña.isEmpty()) {
                    ingresarUsuario();

                } else {
                    Toast.makeText(getApplicationContext(), "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
                }
            }
        });
        tvRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FormularioRegistro.class));
            }
        });
    }


    private void ingresarUsuario() {

        mAuth.signInWithEmailAndPassword(correo, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()) {

                    //hay que abrir otro activity
                    abrirInterfaz();

                } else {
                    Toast.makeText(getApplicationContext(), "¡Error Al iniciar sesión!, compruebe sus datos", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void abrirInterfaz(){

        String id=mAuth.getCurrentUser().getUid();
        mDatabase.child("cliente").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //se abre la interfaz cliente
                    startActivity(new Intent(getApplicationContext(), LayoutCliente.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabase.child("modista").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                if(dataSnapshot1.exists()){
                    Intent intent= new Intent(getApplicationContext(), LayoutModista.class);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}