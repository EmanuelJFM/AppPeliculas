package com.example.proyectofinal.Adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectofinal.Elementos.Detalles;
import com.example.proyectofinal.Elementos.Favoritos;
import com.example.proyectofinal.Modelo.Pelicula;
import com.example.proyectofinal.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdaptadorPelicula extends RecyclerView.Adapter<AdaptadorPelicula.ViewHolder> {
    private ArrayList<Pelicula>data;

    private ArrayList<Pelicula> dataOriginal;
    private Context context;

    public AdaptadorPelicula(Context context){
        this.context=context;
        this.data = new ArrayList<>();
        this.dataOriginal = new ArrayList<>();
    }
    @NonNull
    @Override
    public AdaptadorPelicula.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPelicula.ViewHolder holder, int position){
        Pelicula p = data.get(position);
        holder.id.setText(String.valueOf(p.getId()));
        holder.nombre.setText(p.getOriginal_title());
        holder.posicion.setText(String.valueOf(position));
        holder.posicion.setVisibility(View.INVISIBLE);
        Glide.with(context).load(
                "https://image.tmdb.org/t/p/original//"+p.getPoster_path()).centerCrop().into(holder.imagen);
    }
    public void filtrado(final String buscar){
        int longitud = buscar.length();
        if(longitud == 0){
            data.clear();
            data.addAll(dataOriginal);
        }else {
            List<Pelicula> collecion;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                collecion = dataOriginal.stream()
                        .filter(i -> i.getOriginal_title().toLowerCase().contains(buscar.toLowerCase()))
                        .collect(Collectors.toList());
            } else {
                collecion = new ArrayList<>();
                for (Pelicula p : dataOriginal) {
                    if (p.getOriginal_title().toLowerCase().contains(buscar.toLowerCase())) {
                        collecion.add(p);
                    }
                }
            }
            data.clear();
            data.addAll(collecion);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

    public void addPeliculas(ArrayList<Pelicula> listap){
        data.addAll(listap);
        dataOriginal.addAll(listap);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagen;
        private TextView id, nombre, posicion;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imagen =itemView.findViewById(R.id.imagen);
            id = itemView.findViewById(R.id.txtId);
            nombre = itemView.findViewById(R.id.txtNombre);
            posicion = itemView.findViewById(R.id.txtPos);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    int pos = Integer.parseInt(posicion.getText().toString());
                    long indice = Long.parseLong(id.getText().toString());
                    Pelicula p = data.get(pos);
                    Context c = v.getContext();
                    Intent ventana = new Intent(c, Detalles.class);
                    ventana.putExtra("indice", indice);
                    ventana.putExtra("fecha", p.getRelease_date());
                    ventana.putExtra("titulo", p.getOriginal_title());
                    ventana.putExtra("descripcion", p.getOverview());
                    ventana.putExtra("imagen", p.getPoster_path());
                    c.startActivity(ventana);

                    Intent ventana2 = new Intent(c, Favoritos.class);
                    ventana2.putExtra("img", p.getPoster_path());
                    ventana2.putExtra("nombre", p.getOriginal_title());
                }
            });
        }
    }
}
