package com.example.proyectofinal.Interfaz;

import com.example.proyectofinal.Modelo.Resultado;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IPelicula {
    @GET("popular")
    Call<Resultado> obtenerdatos(@Query("api_key") String api_key, @Query("page") long page);
}
