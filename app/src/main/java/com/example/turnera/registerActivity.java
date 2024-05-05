package com.example.turnera;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class registerActivity extends AppCompatActivity {

    private ImageView avatar;
    private EditText nombre, apellido, telefono, mail;
    private Button registro;
    private ProgressDialog carga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        avatar = findViewById(R.id.register_avatar);
        avatar.setImageResource(R.drawable.avatar);

        nombre = findViewById(R.id.register_name);

        apellido = findViewById(R.id.register_surname);

        mail = findViewById(R.id.register_mail);

        telefono = findViewById(R.id.register_phone);

        registro = findViewById(R.id.register_button);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar(v);
            }
        });
    }

    private void registrar(View v){
        final String NAME = nombre.getText().toString().trim();
        final String SURNAME = apellido.getText().toString().trim();
        final String USR = mail.getText().toString().trim();
        final String PHONE = telefono.getText().toString().trim();

        if (NAME.isEmpty()){
            nombre.setError("Este campo es requerido");
            nombre.requestFocus();
        } else if (SURNAME.isEmpty()){
            apellido.setError("Este campo es requerido");
            apellido.requestFocus();
        } else if (USR.isEmpty()){
            mail.setError("Este campo es requerido");
            mail.requestFocus();
        } else {
            carga = new ProgressDialog(this);
            carga.setTitle("Registro en curso.");
            carga.setMessage("Por favor espere...");
            carga.show();

            StringRequest sr = new StringRequest(Request.Method.POST, Constant.REGISTER_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {

                    Log.d("Response", s);

                    if (s.equals("0")){
                        carga.dismiss();
                        Toast.makeText(v.getContext(), "Error al registrar el usuario. Intente de nuevo mas tarde.", Toast.LENGTH_SHORT).show();
                    } else if (s.equals("2")){
                        carga.dismiss();
                        Toast.makeText(v.getContext(), "El usuario ya existe.", Toast.LENGTH_SHORT).show();
                    }else if (s.equals("1")){
                        carga.dismiss();
                        Intent i = new Intent(v.getContext(), MainActivity.class);
                        startActivity(i);
                        Toast.makeText(v.getContext(), "Registrado exitosamente.", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    carga.dismiss();
                    Toast.makeText(v.getContext(), "Error al registrar el usuario. Por favor intente mas tarde.", Toast.LENGTH_LONG).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    if (!PHONE.isEmpty()){
                        params.put(Constant.KEY_NAME, NAME);
                        params.put(Constant.KEY_SURNAME, SURNAME);
                        params.put(Constant.KEY_USER, USR);
                        params.put(Constant.KEY_PHONE, PHONE);
                    } else {
                        params.put(Constant.KEY_NAME, NAME);
                        params.put(Constant.KEY_SURNAME, SURNAME);
                        params.put(Constant.KEY_USER, USR);
                        params.put(Constant.KEY_PHONE, "0");
                    }
                    return params;
                }
            };
            RequestQueue rq = Volley.newRequestQueue(v.getContext());
            sr.setRetryPolicy(new DefaultRetryPolicy(10 *1000, 1, 1.0f));
            rq.add(sr);
        }
    }
}