package com.example.turnera;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private ImageButton avatar;
    private TextView user, nombreCompleto;
    private Button turno, detalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle extraPass = getIntent().getExtras();
        assert extraPass != null;
        if (extraPass.getString("pass") != null){
            Toast.makeText(this, "Contraseña modificada", Toast.LENGTH_LONG).show();
        }

        avatar = findViewById(R.id.avatar);
        avatar.setImageResource(R.drawable.avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ProfileActivity.class);
                i.putExtra("user", user.getText().toString());
                i.putExtra("nombre", nombreCompleto.getText().toString());
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

        turno = findViewById(R.id.nuevoturno);
        turno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Turnos.class);
                startActivity(i);
            }
        });

        detalle = findViewById(R.id.mis_turnos);
        detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Turnos.class);
                i.putExtra("user", user.getText().toString());
                startActivity(i);
            }
        });
    }
}