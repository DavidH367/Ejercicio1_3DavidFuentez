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

public class MainActivity extends AppCompatActivity {
    SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDataBase,null,1);

    EditText txtNombre, txtApellido, txtEdad, txtCorreo, txtDireccion;
    Button btnSalvar, btnConsultar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtEdad = (EditText) findViewById(R.id.txtEdad);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtDireccion = (EditText) findViewById(R.id.txtDireccion);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnConsultar = (Button) findViewById(R.id.btnConsultar);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ActivityListado.class);
                startActivity(i);
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarEmpleado();
            }
        });

    }

    private void AgregarEmpleado() {
        /* Conexion e Inserccion a la base de datos */
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombre, txtNombre.getText().toString());
        valores.put(Transacciones.apellido, txtApellido.getText().toString());
        valores.put(Transacciones.edad, txtEdad.getText().toString());
        valores.put(Transacciones.correo, txtCorreo.getText().toString());

        Long resultado = db.insert(Transacciones.tablaPersonas, Transacciones.id, valores);

        Toast.makeText(getApplicationContext(), "Registro Ingresado", Toast.LENGTH_LONG).show();

        db.close();

        ClearScreen();
    }

    private void ClearScreen()
    {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEdad.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
    }


}