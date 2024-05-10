package com.example.proyectofinal.Adaptador;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectofinal.Database.DbPeliculasFavoritas;
import com.example.proyectofinal.Modelo.Pelicula;
import com.example.proyectofinal.R;

import java.util.ArrayList;

public class AdaptadorFavoritos extends RecyclerView.Adapter<AdaptadorFavoritos.ViewHolder> {
    private ArrayList<Pelicula> data;
    private Context context;

    public AdaptadorFavoritos(Context context, ArrayList<Pelicula> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public AdaptadorFavoritos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elementofav, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorFavoritos.ViewHolder holder, int position){
        Pelicula p = data.get(position);
        holder.titulo.setText(p.getOriginal_title());
        Glide.with(context).load(
                "https://image.tmdb.org/t/p/original//"+p.getPoster_path()).centerCrop().into(holder.imagen);
        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    new AlertDialog.Builder(context)
                            .setTitle("Confirmar eliminación")
                            .setMessage("¿Estás seguro de que quieres eliminar esta película?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    long peliculaId = data.get(position).getId();
                                    eliminarPelicula(peliculaId);
                                    Toast.makeText(context,"Se ha eliminado la pelicula con éxito",Toast.LENGTH_SHORT).show();
                                    data.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, data.size());
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });


    }

    private void eliminarPelicula(long id) {
        DbPeliculasFavoritas dbHelper = new DbPeliculasFavoritas(context,"PeliculasFavoritas.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = "id" + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        db.delete("PeliculasFavoritas", selection, selectionArgs);
    }


    @Override
    public int getItemCount(){
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagen;
        private TextView titulo;
        private Button eliminar;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imagen = itemView.findViewById(R.id.imgFav);
            titulo = itemView.findViewById(R.id.txtTitle);
            eliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}

