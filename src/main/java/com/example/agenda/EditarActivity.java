package com.example.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.db.DbContactos;
import com.example.agenda.entidades.contactos;

public class EditarActivity extends AppCompatActivity {

    EditText txtNombre, txtTelephone, txtEmail;
    Button btnGuarda;
    boolean correcto = false;

    contactos contacto;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        txtNombre = findViewById(R.id.txtNombre);
        txtTelephone = findViewById(R.id.txtTelephone);
        txtEmail = findViewById(R.id.txtEmail);
        btnGuarda = findViewById(R.id.btnGuarda);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                id = Integer.parseInt(null);

            }else {
                id = extras.getInt("ID");
            }

        }else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        DbContactos dbContactos = new DbContactos(EditarActivity.this);
        contacto = dbContactos.VerContacto(id);

        if (contacto !=null){
            txtNombre.setText(contacto.getName());
            txtTelephone.setText(contacto.getTelephone());
            txtEmail.setText(contacto.getEmail());

        }

        btnGuarda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("asdf");
                //Toast.makeText(EditarActivity.this, "asdad", Toast.LENGTH_SHORT).show();
                if(true || txtNombre.getText().toString().equals(" ") && !txtTelephone.getText().toString().equals(" ")){
                  correcto =  dbContactos.editarContacto(id, txtNombre.getText().toString(),txtTelephone.getText().toString(),txtEmail.getText().toString());

                  if(correcto){
                      Toast.makeText(EditarActivity.this, "YOUR VIP CONTACT HAD BEEN MODIFIED", Toast.LENGTH_LONG).show();
                      verRegistro();
                  } else {
                      Toast.makeText(EditarActivity.this, "ERROR, NOT MODIFIED", Toast.LENGTH_LONG).show();
                  }
                }else {
                    Toast.makeText(EditarActivity.this, "YOU ARE MISSING SOME INFORMATION, HAVE A LOOK! ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void verRegistro(){
        Intent intent = new Intent(this, VerActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}
