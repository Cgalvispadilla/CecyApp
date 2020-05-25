package com.example.cecyapp.layout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cecyapp.MainActivity;
import com.example.cecyapp.R;
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


import java.util.HashMap;
import java.util.Map;


public class FormularioRegistro extends AppCompatActivity {
    //se crea el spiner para la selección de tipo de usario
    private Spinner spinner;
    //se agregan lo edittext referente al formulario
    private TextInputEditText etId, etNombres, etApellidos, etEdad, etDireccion, etContraseña, etCorreo, etCelular;
    //se agrega el boton de registro
    private Button btnRegistrar;
    private Button btnIrLogin;

    //variable de datos qe se registraran
    private int id=0, edad=0;
    private String nombre="", apellidos="", correo="", contraseña="", direccion="", celular="";

    //se crea la variable de autenticación de firabase
    FirebaseAuth mAuth;
    //se crea la variable de la base de datos
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_registro);
        //se inician los
        etId= (TextInputEditText) findViewById(R.id.etId);
        etNombres = (TextInputEditText) findViewById(R.id.etNombreRegistro);
        etApellidos = (TextInputEditText) findViewById(R.id.etApellido);
        etEdad = (TextInputEditText) findViewById(R.id.etEdad);
        etDireccion = (TextInputEditText) findViewById(R.id.etDireccion);
        etCorreo = (TextInputEditText) findViewById(R.id.etCorreo);
        etContraseña = (TextInputEditText) findViewById(R.id.etContraseña);
        etCelular = (TextInputEditText) findViewById(R.id.etCelular);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnIrLogin = (Button) findViewById(R.id.btnIrLogin);

        String [] opciones= {"Tipo de usario", "Cliente", "Modista"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
        spinner.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = Integer.parseInt(etId.getText().toString());
                nombre = etNombres.getText().toString();
                apellidos= etApellidos.getText().toString();
                edad= Integer.parseInt(etEdad.getText().toString());
                direccion = etDireccion.getText().toString();
                correo = etCorreo.getText().toString();
                contraseña = etContraseña.getText().toString();
                celular= etCelular.getText().toString();
                //se guarda donde seleccionara el tipo de usario
                if(spinner.getSelectedItemPosition()>0) {
                    String seleccion = spinner.getSelectedItem().toString();
                }else{
                    Toast.makeText(getApplicationContext(), "No ha seleccionado el tipo de usario",Toast.LENGTH_LONG).show();
                }

                //se valida que ningun campo se encuentre vació
                if(id!=0 && edad != 0 && !nombre.isEmpty() && !apellidos.isEmpty() && !direccion.isEmpty() && !celular.isEmpty() && !correo.isEmpty() && !contraseña.isEmpty()){
                    //se valida que la contraseña tenga más de 6 caracteres
                    if(contraseña.length()>6) {
                        registrarUsario();
                    }else{
                        Toast.makeText(getApplicationContext(), "La contraseña debe de tener más de 6 caracteres",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "¡Error! Se encuentra algun campo vació, por favor llenar todos",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnIrLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }


    private void registrarUsario() {
        //se registra el usario por medio de la identificación de firabase
        mAuth.createUserWithEmailAndPassword(correo, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()) {
                    String idObt = mAuth.getCurrentUser().getUid();
                    //si selecciona al cliente, se crea la tabla

                    //se guarda en un map que se le pasara a la base de datos
                    Map<String, Object> map = new HashMap<>();
                    map.put("nombres", nombre);
                    map.put("id", id);
                    map.put("apellidos", apellidos);
                    map.put("edad", edad);
                    map.put("direccion", direccion);
                    map.put("correo", correo);
                    map.put("contraseña", contraseña);
                    map.put("celular", celular);
                    //se agregan los datos a la database realtime
                    mDatabase.child("cliente").child(idObt).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //se abre el activity de login
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            //se cierra el activity del registro para evitar que el usario que se registro vuelva
                            finish();
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //se valida que el usario ya haya iniciado sesión
        if(mAuth.getCurrentUser()!=null){
            //startActivity(new Intent(getApplicationContext(), )); se abre la otra activity
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
                        //se abre la interfaz modista
                        startActivity(new Intent(getApplicationContext(), LayoutModista.class));
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
        }




