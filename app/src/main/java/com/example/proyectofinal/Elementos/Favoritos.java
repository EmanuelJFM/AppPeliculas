package com.example.proyectofinal.Elementos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.renderscript.Element;
import android.widget.Toast;

import com.example.proyectofinal.Adaptador.AdaptadorFavoritos;
import com.example.proyectofinal.Adaptador.AdaptadorPelicula;
import com.example.proyectofinal.Database.DbPeliculasFavoritas;
import com.example.proyectofinal.Modelo.Pelicula;
import com.example.proyectofinal.R;
import com.example.proyectofinal.databinding.ElementofavBinding;

import java.util.ArrayList;
import java.util.List;

public class Favoritos extends AppCompatActivity {
    private RecyclerView listafav;
    private AdaptadorFavoritos ad;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        listafav = findViewById(R.id.favoritos);
        listafav.setAdapter(ad);
        listafav.setHasFixedSize(true);
        GridLayoutManager grid = new GridLayoutManager(this,1);
        listafav.setLayoutManager(grid);

        DbPeliculasFavoritas dbHelper = new DbPeliculasFavoritas(this, "PeliculasFavoritas.db", null, 1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "id",
                "titulo",
                "urlImg"
        };

        Cursor cursor = db.query(
                "PeliculasFavoritas",
                projection,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<Pelicula> peliculas = new ArrayList<>();
        while(cursor.moveToNext()) {
            Pelicula pelicula = new Pelicula();
            pelicula.setId(cursor.getLong(cursor.getColumnIndexOrThrow("id")));
            pelicula.setOriginal_title(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
            pelicula.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow("urlImg")));
            peliculas.add(pelicula);
        }
        cursor.close();

        if (peliculas.isEmpty()) {
            Toast.makeText(this, "Aún no has añadido alguna pelicula", Toast.LENGTH_SHORT).show();
        } else {
            ad = new AdaptadorFavoritos(this, peliculas);
            listafav.setAdapter(ad);
        }
    }
}

