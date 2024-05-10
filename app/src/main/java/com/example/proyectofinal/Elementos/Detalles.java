package com.example.proyectofinal.Elementos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyectofinal.Database.DbPeliculasFavoritas;
import com.example.proyectofinal.databinding.ActivityDetallesBinding;

public class Detalles extends AppCompatActivity {

    ActivityDetallesBinding b;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        b = ActivityDetallesBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        Bundle datos = getIntent().getExtras();
        long id = datos.getLong("indice");
        String fecha = datos.getString("fecha");
        String titulo = datos.getString("titulo");
        String descripcion = datos.getString("descripcion");
        String imagen = datos.getString("imagen");
        String urlImg = "https://image.tmdb.org/t/p/original//" + imagen;
        Glide.with(this).load(urlImg).into(b.img);
        b.txtIndice.setText("INDICE:" + String.valueOf(id));
        b.txtFecha.setText("FECHA DE LANZAMIENTO:" + fecha);
        b.txtTitulo.setText(titulo);
        b.txtDescripcion.setText(descripcion);

        b.btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Detalles.this)
                        .setTitle("Confirmar acción")
                        .setMessage("¿Quieres añadir esta película a tu lista de favoritos?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DbPeliculasFavoritas dbHelper = new DbPeliculasFavoritas(Detalles.this,"PeliculasFavoritas.db", null, 1);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();

                                ContentValues values = new ContentValues();
                                values.put("titulo", titulo);
                                values.put("urlImg", urlImg);
                                long newRowId = db.insert("PeliculasFavoritas", null, values);
                                Toast.makeText(Detalles.this, "Se ha añadido la película a favoritos con éxito", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        });


    }}
