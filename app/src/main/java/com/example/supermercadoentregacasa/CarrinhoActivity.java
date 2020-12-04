package com.example.supermercadoentregacasa;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoActivity extends AppCompatActivity {
    private ListView lvProdutos;
    private List<Produto> listaDeProdutos;
    private ListaCarrinhoAdapter adapterProduto;
    private ChildEventListener childEventListener;
    private Query query;
    private int count = 1;
    private TextView produtoQuantidade;
    private ImageView plus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvProdutos = findViewById(R.id.lvProdutosCarrinho);

        listaDeProdutos = new ArrayList<>();
        listaDeProdutos = (ArrayList<Produto>) getIntent().getSerializableExtra("listaCarrinho");
        Log.i("Entrar1", String.valueOf(listaDeProdutos));
        adapterProduto = new ListaCarrinhoAdapter(CarrinhoActivity.this, listaDeProdutos);
        Log.i("Entrar2", String.valueOf(listaDeProdutos));
        lvProdutos.setAdapter(adapterProduto);
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

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Produto prod = new Produto();
                prod.setNome( dataSnapshot.child("nome").getValue(String.class) );
                prod.setPreco( dataSnapshot.child("preco").getValue( Double.class) );
                prod.setKey( dataSnapshot.getKey() );
                listaDeProdutos.add( prod );
                Log.i("Entrar6", String.valueOf(listaDeProdutos));
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
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
