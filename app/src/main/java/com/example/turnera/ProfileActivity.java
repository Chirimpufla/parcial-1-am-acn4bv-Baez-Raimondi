package com.example.turnera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private ImageView avatar;
    private EditText mail, nombre, pass;
    private Button cambiar,guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        avatar = findViewById(R.id.profile_avatar);
        avatar.setImageResource(R.drawable.avatar);

        mail = findViewById(R.id.profile_user);
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                mail.setText(null);
            } else {
                mail.setText(extras.getString("user"));
            }
        } else {
            mail.setText((String) savedInstanceState.getSerializable("user"));
        }

        nombre = findViewById(R.id.profile_name);
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                nombre.setText(null);
            } else {
                nombre.setText(extras.getString("nombre"));
            }
        } else {
            nombre.setText((String) savedInstanceState.getSerializable("nombre"));
        }

        guardar = findViewById(R.id.guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), HomeActivity.class);
                i.putExtra("user", mail.getText().toString());
                i.putExtra("name", nombre.getText().toString());
                startActivity(i);
            }
        });

        pass = findViewById(R.id.profile_password);
        pass.setHint("Cambiar Contraseña");

        cambiar = findViewById(R.id.cambiar);
        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), HomeActivity.class);
                i.putExtra("pass", pass.getText().toString());
                startActivity(i);
            }
        });
    }
}