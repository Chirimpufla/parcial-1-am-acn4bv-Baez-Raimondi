package com.example.turnera;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);
        Button login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

    }

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

            StringRequest sr = new StringRequest(Request.Method.POST, Constant.LOGIN_URL,
                    s -> {
                        Log.d("Response", s);

                        if (s.equals("success")){

                            SharedPreferences sp = MainActivity.this.getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            SharedPreferences.Editor e = sp.edit();
                            e.putString(Constant.USER_SHARED_PREF, USR);
                            e.apply();

                            carga.dismiss();

                            Intent i = new Intent(v.getContext(), HomeActivity.class);
                            startActivity(i);

                            Toast.makeText(v.getContext(), "Inicio correcto.", Toast.LENGTH_SHORT).show();
                        } else if (s.equals("failure")) {

                            Toast.makeText(v.getContext(), "El usuario o contraseña son incorrectos", Toast.LENGTH_LONG).show();
                            carga.dismiss();

                        } else {

                            Toast.makeText(v.getContext(), "El usuario o contraseña son incorrectos", Toast.LENGTH_LONG).show();
                            carga.dismiss();

                        }
                    },
                    volleyError -> {

                        Toast.makeText(v.getContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
                        carga.dismiss();

                    }){

                @Override
                protected Map<String, String> getParams() {

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