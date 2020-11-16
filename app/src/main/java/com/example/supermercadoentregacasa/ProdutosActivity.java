package com.example.supermercadoentregacasa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class ProdutosActivity extends AppCompatActivity {

    private ListView lvProdutos;
    private List<Produto> listaDeProdutos;
    private ArrayAdapter adapter;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProdutosActivity.this, FormularioActivity.class);
                startActivity( intent );
            }
        });

        listaDeProdutos = new ArrayList<>();

        lvProdutos = findViewById(R.id.lvProdutos);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaDeProdutos );
        lvProdutos.setAdapter( adapter );


    }

    @Override
    protected void onStart() {
        super.onStart();

        listaDeProdutos.clear();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        query = reference.child("produtos").orderByChild("nome");

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Produto p = new Produto();
                p.id = snapshot.getKey();
                p.nome = snapshot.child("nome").getValue( String.class );
                p.quantidade = snapshot.child("quantidade").getValue( Double.class );

                listaDeProdutos.add( p );
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Produto p = snapshot.getValue(Produto.class);
                listaDeProdutos.remove( p );
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        query.addChildEventListener( childEventListener );

    }

    @Override
    protected void onStop() {
        super.onStop();

        query.removeEventListener( childEventListener );

    }
}