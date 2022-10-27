package com.example.ejercicio_1_3.Transacciones;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class Transacciones {
    /*Nombre de la Base de datos */
    public static final String NameDataBase = "DBPM01";
    /* Creacion de tablas de la BB */
    public static final String tablaPersonas = "personas";

    /* Campos de la Tabla Empleados */
    public static final String id = "id";
    public static final String nombre = "nombre";
    public static final String apellido = "apellido";
    public static final String edad = "edad";
    public static final String correo = "correo";
    public static final String direccion = "direccion";

    /* Sentencias SQL para crear tabla */
    public static final String CreateTablePersonas = "CREATE TABLE personas " +
            "( id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            " nombre TEXT, apellido TEXT, edad INTEGER, "+
            " correo TEXT, direccion TEXT)";

    public static final String DropTablePersonas = "DROP TABLE IF EXISTS personas";
}
