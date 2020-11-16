package com.example.supermercadoentregacasa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormularioActivity extends AppCompatActivity {
    private EditText etNome, etQuantidade;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etNome = findViewById(R.id.etNome);
        etQuantidade = findViewById(R.id.etQuantidade);
        btnSalvar = findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });
    }

    private void salvar(){
        String nome = etNome.getText().toString();
        String quantidade = etQuantidade.getText().toString();

        if( !nome.isEmpty() ){
            Produto prod = new Produto();
            prod.nome = nome;
            if( quantidade.isEmpty() ){
                prod.quantidade = 0.0;
            }else {
                quantidade = quantidade.replace("," , ".");
                prod.quantidade = Double.valueOf( quantidade );
            }
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference();
            reference.child("produtos").push().setValue( prod );
            finish();
        }
    }
}
