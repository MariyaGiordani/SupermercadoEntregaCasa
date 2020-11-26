package com.example.supermercadoentregacasa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormularioActivity extends AppCompatActivity {
    private EditText etNome, etQuantidade, etPreco;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etNome = findViewById(R.id.etNome);
        etQuantidade = findViewById(R.id.etQuantidade);
        etPreco = findViewById(R.id.etPreco);
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
        String preco = etPreco.getText().toString();

        if( !nome.isEmpty() ){
            Produto prod = new Produto();
            prod.setNome(nome);
            if( quantidade.isEmpty() ){
                prod.setQuantidade(0.00);
            }else {
                quantidade = quantidade.replace("," , ".");
                prod.setQuantidade(Double.valueOf( quantidade ));
            }

            if( preco.isEmpty() ){
                prod.setPreco(0.00);
            }else {
                preco = preco.replace("," , ".");
                prod.setPreco(Double.valueOf( preco ));
            }

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference();
            reference.child("produtos").push().setValue( prod );
            finish();
        }
    }
}
