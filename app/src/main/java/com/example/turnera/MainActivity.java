package com.example.turnera;

import static com.example.turnera.R.drawable.logo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity {

    private EditText user, pass;
    private ProgressDialog carga;
    private ImageView logo;
    private Button login, registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo = findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo);

        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);

        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

        registro = findViewById(R.id.register);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), registerActivity.class);
                startActivity(i);
            }
        });

    }

    //Esta funcion fue preparada para usarse con PHP y MySQL
    private void login(View v){
        final String USR = user.getText().toString().trim();
        final String PASS = pass.getText().toString().trim();

        if(USR.isEmpty()){
            user.setError("Por favor ingrese su mail!");
            user.requestFocus();
        } else if (PASS.isEmpty()) {
            pass.setError("Por favor ingrese su contraseña!");
            pass.requestFocus();
        } else {
            carga = new ProgressDialog(this);
            carga.setTitle("Iniciando sesion");
            carga.setMessage("Por favor espere...");
            carga.show();

            StringRequest sr = new StringRequest(Request.Method.POST, Constant.LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {

                    Log.d("Response", s);

                    if (s.contains("1")){
                        carga.dismiss();
                        Intent i = new Intent(v.getContext(), HomeActivity.class);
                        i.putExtra(Constant.KEY_USER, user.getText().toString());
                        startActivity(i);
                        Toast.makeText(v.getContext(), "Inicio Exitoso!!!", Toast.LENGTH_SHORT).show();
                    } else if (s.contains("0")){
                        carga.dismiss();
                        Toast.makeText(v.getContext(), "El usuario o contraseña son incorrectos", Toast.LENGTH_LONG).show();
                    } else {
                        carga.dismiss();
                        Toast.makeText(v.getContext() ,s, Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    carga.dismiss();
                    Toast.makeText(v.getContext(), "Error al conectarse con el servidor.", Toast.LENGTH_LONG).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put(Constant.KEY_USER, USR);
                    params.put(Constant.KEY_PASS, PASS);
                    return params;
                }
            };

            RequestQueue rq = Volley.newRequestQueue(v.getContext());
            sr.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));
            rq.add(sr);
        }
    }
}