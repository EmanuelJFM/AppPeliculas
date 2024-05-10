package com.example.proyectofinal.Modelo;

public class Pelicula {
    private long id;
    private String original_title;
    private String poster_path;

    private String release_date;
    private String overview;

    public long getId(){
       return id;
    }

    public void setId(long id){
        this.id = id;
    }
    public String getOriginal_title(){
        return original_title;
    }

    public void setOriginal_title(String original_title){
        this.original_title = original_title;
    }

    public String getPoster_path(){
        return poster_path;
    }

    public void setPoster_path(String poster_path){
        this.poster_path = poster_path;
    }

    public String getRelease_date(){
        return release_date;
    }

    public void setRelease_date(String release_date){
        this.release_date = release_date;
    }

    public String getOverview(){
        return overview;
    }

    public void setOverview(String overview){
        this.overview = overview;
    }


}
