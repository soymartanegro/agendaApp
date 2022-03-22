package com.example.agenda.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.agenda.entidades.contactos;

import java.util.ArrayList;

public class DbContactos extends DbHelper {

    Context context;

    public DbContactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarContacto(String name, String telephone, String email){

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("telephone", telephone);
            values.put("email", email);

             id = db.insert(TABLE_CONTACTOS, null, values);
        }catch (Exception ex){
            ex.toString();
        }
        return  id;

    }

    public ArrayList<contactos> mostrarContactos(){
        DbHelper dbHelper =new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<contactos> listaContactos = new ArrayList<>();
        contactos contacto = null;
        Cursor cursorContactos = null;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS, null);
        if(cursorContactos.moveToFirst()){
            do{
                contacto = new contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setName(cursorContactos.getString(1));
                contacto.setTelephone(cursorContactos.getString(2));
                contacto.setEmail(cursorContactos.getString(3));

                listaContactos.add(contacto);

            } while (cursorContactos.moveToNext());
        }

        cursorContactos.close();
        return listaContactos;
    }

    public contactos VerContacto(int id){
        DbHelper dbHelper =new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        contactos contacto = null;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " WHERE id = " + id + " LIMIT 1" ,null);
        if(cursorContactos.moveToFirst()){

                contacto = new contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setName(cursorContactos.getString(1));
                contacto.setTelephone(cursorContactos.getString(2));
                contacto.setEmail(cursorContactos.getString(3));

        }

        cursorContactos.close();

        return contacto;
    }

    public boolean editarContacto(int id, String name, String telephone, String email){

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        System.out.println(id);

        try {
            //db.beginTransaction();
            db.execSQL("UPDATE " + TABLE_CONTACTOS + " SET name = '" + name + "', telephone = '" + telephone + "', email = '" + email + "' WHERE id='" + id + "' ");
            ContentValues cv = new ContentValues();
            //cv.put("telephone","12345"); //These Fields should be your String values of actual column names

            //db.update(TABLE_CONTACTOS, cv, "id = ?", new String[]{String.valueOf(id)});

            //db.endTransaction();
            correcto = true;

        }catch (Exception ex){
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }
        return  correcto;

    }

    public boolean elimarContacto(int id){

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            db.execSQL(" DELETE FROM " + TABLE_CONTACTOS + " WHERE id = '" + id + "'");
            correcto = true;

        }catch (Exception ex){
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }
        return  correcto;

    }
}
