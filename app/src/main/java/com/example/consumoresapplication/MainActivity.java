package com.example.consumoresapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.consumoresapplication.entity.comment;
import com.example.consumoresapplication.service.UserService;
import com.example.consumoresapplication.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Spinner   spnUsuarios;
    ArrayAdapter<String> adaptadorUsuarios;
    ArrayList<String> listaCommment = new ArrayList<String>();

    Button btnFiltrar;
    TextView txtResultado;

    //servicio de retrofit

    UserService userService;
    List<comment> lstSalida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        adaptadorUsuarios = new ArrayAdapter<String>(
                this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listaCommment);
        spnUsuarios = findViewById(R.id.spnUsuarios);
        spnUsuarios.setAdapter(adaptadorUsuarios);

        btnFiltrar = findViewById(R.id.btnFiltrar);
        txtResultado =findViewById(R.id.txtResultado);

        userService = ConnectionRest.getConnecion().create(UserService.class);
        cargaComment();

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           int idUsuario = (int)spnUsuarios.getSelectedItemId();
           comment objUser = lstSalida.get(idUsuario);
           String salida = "User: ";

                salida +="PostId: " + objUser.getPostId() +"\n";
                salida +="Id: " + objUser.getId() +"\n";
                salida +="Name: " + objUser.getName() +"\n";
                salida +="Email: " + objUser.getEmail() +"\n";
                salida +="Body: " + objUser.getBody() +"\n";

           txtResultado.setText(salida);

            }
        });
    }

    void cargaComment(){

      Call<List<comment>> call = userService.listaCommment();
      call.enqueue(new Callback<List<comment>>() {
          @Override
          public void onResponse(Call<List<comment>> call, Response<List<comment>> response) {

              if (response.isSuccessful()){
             lstSalida = response.body();
             for (comment obj: lstSalida){

                 String idAndName = obj.getId() + " - " + obj.getName();
                 listaCommment.add(idAndName);
             }
              }
              adaptadorUsuarios.notifyDataSetChanged();

          }

          @Override
          public void onFailure(Call<List<comment>> call, Throwable t) {

          }
      });
    }
}