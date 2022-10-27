package com.example.ejercicio_1_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ejercicio_1_3.Transacciones.SQLiteConexion;
import com.example.ejercicio_1_3.Transacciones.Transacciones;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityActualizarPersona extends AppCompatActivity {

    SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDataBase,null,1);

    EditText txtIdAC, txtNombreAC, txtApellidoAC, txtEdadAC, txtCorreoAC, txtDireccionAC;
    Button btnAtrasAC, btnActualizarAC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_persona);

        btnActualizarAC = (Button) findViewById(R.id.btnActualizarAC);
        btnActualizarAC = (Button) findViewById(R.id.acbtnatras);

        txtIdAC = (EditText) findViewById(R.id.txtIdAC);
        txtNombreAC = (EditText) findViewById(R.id.txtNombreAC);
        txtApellidoAC = (EditText) findViewById(R.id.txtApellidoAC);
        txtEdadAC = (EditText) findViewById(R.id.txtEdadAC);
        txtCorreoAC = (EditText) findViewById(R.id.txtCorreoAC);
        txtDireccionAC = (EditText) findViewById(R.id.txtDireccionAC);

        txtIdAC.setText(getIntent().getStringExtra("codigo"));
        txtNombreAC.setText(getIntent().getStringExtra("nombre"));
        txtApellidoAC.setText(getIntent().getStringExtra("apelido"));
        txtEdadAC.setText(getIntent().getStringExtra("edad"));
        txtCorreoAC.setText(getIntent().getStringExtra("correo"));
        txtDireccionAC.setText(getIntent().getStringExtra("direccion"));

        btnActualizarAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditarContacto();
            }
        });
    }

    List<Integer> extraerNumeros(String cadena) {
        List<Integer> todosLosNumeros = new ArrayList<Integer>();
        Matcher encuentrador = Pattern.compile("\\d+").matcher(cadena);
        while (encuentrador.find()) {
            todosLosNumeros.add(Integer.parseInt(encuentrador.group()));
        }
        return todosLosNumeros;
    }

    private void EditarContacto() {
        //SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase,null,1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        String ObjCodigo = txtIdAC.getText().toString();

        ContentValues valores = new ContentValues();

        valores.put(Transacciones.nombre, txtNombreAC.getText().toString());
        valores.put(Transacciones.apellido, txtApellidoAC.getText().toString());
        valores.put(Transacciones.edad, txtEdadAC.getText().toString());
        valores.put(Transacciones.correo, txtCorreoAC.getText().toString());
        valores.put(Transacciones.direccion, txtDireccionAC.getText().toString());


        try {
            db.update(Transacciones.tablaPersonas,valores, Transacciones.id +" = "+ ObjCodigo, null);
            db.close();
            Toast.makeText(getApplicationContext(),"Se actualizo correctamente", Toast.LENGTH_SHORT).show();

            //volver abrir la ventana
            Intent intent = new Intent(this, ActivityListado.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();


        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"No se actualizo", Toast.LENGTH_SHORT).show();
        }

    }

}