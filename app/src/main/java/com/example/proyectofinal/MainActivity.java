package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.proyectofinal.Adaptador.AdaptadorPelicula;
import com.example.proyectofinal.Elementos.Favoritos;
import com.example.proyectofinal.Elementos.LoginForm;
import com.example.proyectofinal.Interfaz.IPelicula;
import com.example.proyectofinal.Modelo.Pelicula;
import com.example.proyectofinal.Modelo.Resultado;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private Retrofit rfit;
    private SearchView buscar;
    private AdaptadorPelicula ad;
    private RecyclerView listado;
    private Toolbar barra;
    private long page;
    private boolean cargar;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        listado = findViewById(R.id.lista);
        barra = findViewById(R.id.barra);
        setSupportActionBar(barra);
        buscar = findViewById(R.id.txtBuscar);
        ad = new AdaptadorPelicula(this);
        listado.setAdapter(ad);
        listado.setHasFixedSize(true);
        GridLayoutManager grid = new GridLayoutManager(this,2);
        buscar.setOnQueryTextListener(this);
        listado.setLayoutManager(grid);
        listado.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy){
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){
                    int visible = grid.getItemCount();
                    int total = grid.getItemCount();
                    int vitems=grid.findFirstVisibleItemPosition();
                    if(cargar==true){
                        if((visible+vitems)>=total){
                            cargar = false;
                            page+=1;
                            obtener(page);
                        }
                    }
                }
            }
        });

        rfit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/movie/").
                addConverterFactory(GsonConverterFactory.create()).build();
        cargar = true;
        page = 1;
        obtener(page);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId()==R.id.favoritos){
            Intent fav = new Intent(this, Favoritos.class);
            this.startActivity(fav);


        }
        return true;
    }

    private void obtener(long page){
        IPelicula s = rfit.create(IPelicula.class);
        Call<Resultado> respuesta = s.obtenerdatos("7be72508776961f3948639fbd796bccd", page);
        respuesta.enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response){
                cargar = true;
                if(response.isSuccessful()){
                    Resultado res = response.body();
                    ArrayList<Pelicula> lista = res.getResults();
                    ad.addPeliculas(lista);
                }else{
                    cargar=true;

                }
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t){

            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query){
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s){
        ad.filtrado(s);
        return false;
    }
}