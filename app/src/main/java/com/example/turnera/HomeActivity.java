package com.example.turnera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private ImageButton avatar;
    private TextView user, nombreCompleto, servicio, fecha, hora;
    private Button turno, detalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        avatar = findViewById(R.id.avatar);
        avatar.setImageResource(R.drawable.avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ProfileActivity.class);
                startActivity(i);
            }
        });

        user = findViewById(R.id.nombre);
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                user.setText(null);
            } else {
                user.setText(extras.getString("user"));
            }
        } else {
            user.setText((String) savedInstanceState.getSerializable("user"));
        }

        nombreCompleto = findViewById(R.id.apellido);
        nombreCompleto.setText("John Smith");

        String nombre = "John";
        String apellido = "Smith";

        turno = findViewById(R.id.nuevoturno);
        turno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), FormActivity.class);
                i.putExtra("nombre", nombre);
                i.putExtra("apellido", apellido);
                startActivity(i);
            }
        });

        fecha = findViewById(R.id.fecha);
        fecha.setText("22/5/2024");

        hora = findViewById(R.id.hora);
        hora.setText("15.00");

        servicio = findViewById(R.id.servicio);
        servicio.setText("Corte de pelo");

        detalle = findViewById(R.id.detalle);
        detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), DetailActivity.class);
                i.putExtra("fecha", fecha.getText().toString());
                i.putExtra("hora", hora.getText().toString());
                i.putExtra("servicio", servicio.getText().toString());
                startActivity(i);
            }
        });


    }
}