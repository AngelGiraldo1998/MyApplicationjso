package com.example.myapplicationjso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import WebService.WebService;

public class MainActivity extends AppCompatActivity implements com.example.myapplication.WebService.Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void MOSTRAR (View MOSTRAR){
        EditText Correo = findViewById(R.id.txtUsuario);
        EditText Contraseña = findViewById(R.id.txtPassword);
        String correo;
        String  contraseña;
        correo=Correo.getText().toString();
        contraseña=Contraseña.getText().toString();
        //codigo para concetar a internet
        Bundle bundle = this.getIntent().getExtras();
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("correo",correo);
        datos.put("clave",contraseña);
        WebService ws= new WebService(" https://api.uealecpeterson.net/public/login"
                ,datos, MainActivity.this, MainActivity.this);
        ws.execute("POST");
    }
        @Override
        public void processFinish(String result) throws JSONException {
            TextView informacion =findViewById(R.id.txtInformacion);
            JSONObject jsoninformacion = new JSONObject(result);

            if (jsoninformacion.has("error"))
            {
                informacion.setText(jsoninformacion.getString("error"));
            }
            else
            {
                Bundle b = new Bundle();
                b.putString("TOKEN", jsoninformacion.getString("access_token"));
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        }
}