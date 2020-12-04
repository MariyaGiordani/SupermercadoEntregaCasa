package com.example.supermercadoentregacasa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvProdutos;
    private ListView carrinho;
    private List<Produto> listaCarrinho;
    private List<Produto> listaDeProdutos;
    private AdapterProduto adapterProduto;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;
    private Query query;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvProdutos = findViewById(R.id.lvProdutos);
        carrinho = findViewById(R.id.action_drawer_carrinho);
        listaCarrinho = new ArrayList<>();
        listaDeProdutos = new ArrayList<>();
        adapterProduto = new AdapterProduto(MainActivity.this,
                listaDeProdutos);
        lvProdutos.setAdapter(adapterProduto);
        lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listaCarrinho.add((Produto) parent.getItemAtPosition(position));
                count ++;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Sair");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_drawer_carrinho) {
            Log.i("Entrar1", String.valueOf(listaCarrinho));
            Intent intent;
            intent = new Intent(MainActivity.this, CarrinhoActivity.class);
            intent.putExtra("listaCarrinho", (ArrayList<Produto>) listaCarrinho);
            startActivity( intent );
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if( item.toString().equals("Sair")){
            FirebaseAuth auth = FirebaseAuth.getInstance();
            if( auth.getCurrentUser() != null ){
                auth.signOut();
            }
        }

        return super.onOptionsItemSelected(item);
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
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Produto prod = new Produto();
                prod.setNome( dataSnapshot.child("nome").getValue(String.class) );
                prod.setPreco( dataSnapshot.child("preco").
                        getValue( Double.class) );
                prod.setKey( dataSnapshot.getKey() );
                listaDeProdutos.add( prod );
                adapterProduto.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
