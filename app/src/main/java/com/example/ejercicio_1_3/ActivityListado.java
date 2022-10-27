package com.example.ejercicio_1_3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.ejercicio_1_3.Clases.Persona;
import com.example.ejercicio_1_3.Transacciones.SQLiteConexion;
import com.example.ejercicio_1_3.Transacciones.Transacciones;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityListado extends AppCompatActivity {


    SQLiteConexion conexion;
    ListView lista;
    ArrayList<Persona> listaPersonas;
    ArrayList<String> ArregloPersonas;
    EditText txtBuscar;
    Button btnAtras, btnActualizar, btnEliminar;
    Intent intent;
    Persona persona;
    int previousPosition = 1;
    int count=1;
    long previousMil=0;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        lista = (ListView) findViewById(R.id.LisViewContactos);
        intent = new Intent(getApplicationContext(), ActivityActualizarPersona.class);

        conexion = new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);

        obtenerlistaContactos();

        //llenar grip con datos contactos
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, ArregloPersonas);
       lista.setAdapter(adp);

        /*BUSCAR CONTACTOS EN LISTA*/
        txtBuscar = (EditText) findViewById(R.id.txtBuscar);


        txtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                adp.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title

                alertDialogBuilder.setTitle("Eliminar Contacto");


                // set dialog message
                alertDialogBuilder
                        .setMessage("¿Está seguro de eliminar el contacto?")
                        .setCancelable(false)
                        .setPositiveButton("SI",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // si el usuario da click en si procede a llamar el metodo de eliminar
                                eliminarContacto();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                {
                    previousPosition=i;
                    count=1;
                    previousMil=System.currentTimeMillis();
                    //un clic
                    persona = listaPersonas.get(i);//lleno la lista de contacto
                    setContactoSeleccionado();
                }
            }


        });


    }

    private void eliminarContacto() {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        int obtenerCodigo = persona.getCodigo();

        db.delete(Transacciones.tablaPersonas,Transacciones.id +" = "+ obtenerCodigo, null);

        Toast.makeText(getApplicationContext(), "Registro eliminado con exito, Codigo " + obtenerCodigo
                ,Toast.LENGTH_LONG).show();
        db.close();

        //despues de eliminar vuelve a abrir la activida, limpiando los menu anteriores
        Intent intent = new Intent(this, ActivityListado.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void setContactoSeleccionado() {


        intent.putExtra("codigo", persona.getCodigo()+"");
        intent.putExtra("nombre", persona.getNombre());
        intent.putExtra("apellido", persona.getApellido()+"");
        intent.putExtra("edad", persona.getEdad()+"");
        intent.putExtra("correo", persona.getCorreo());
        intent.putExtra("direccion", persona.getDireccion());
     }
/////

    private void obtenerlistaContactos() {
         SQLiteDatabase db = conexion.getReadableDatabase();

         Persona list_contact = null;

         listaPersonas = new ArrayList<Persona>();

         Cursor cursor = db.rawQuery("SELECT * FROM "+ Transacciones.tablaPersonas, null);

         while (cursor.moveToNext())
        {
            list_contact = new Persona();
            list_contact.setCodigo(cursor.getInt(0));
            list_contact.setNombre(cursor.getString(1));
            list_contact.setApellido(cursor.getString(2));
            list_contact.setEdad(cursor.getInt(3));
            list_contact.setCorreo(cursor.getString(4));
            list_contact.setDireccion(cursor.getString(5));
            listaPersonas.add(list_contact);
        }
        cursor.close();
         llenarlista();

    }

    private void llenarlista()
    {
        ArregloPersonas = new ArrayList<String>();

        for (int i=0; i<listaPersonas.size();i++)
        {
            ArregloPersonas.add(listaPersonas.get(i).getNombre()+" | "+
                    listaPersonas.get(i).getApellido()+
                    listaPersonas.get(i).getEdad());

        }
    }
}