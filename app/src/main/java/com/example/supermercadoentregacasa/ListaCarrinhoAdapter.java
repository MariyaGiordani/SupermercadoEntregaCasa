package com.example.supermercadoentregacasa;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListaCarrinhoAdapter extends BaseAdapter {
    private List<Produto> listaProdutos;
    private Context context;
    private LayoutInflater inflater;
    private int count = 1;

    public ListaCarrinhoAdapter(Context context, List<Produto> listaProdutos){
        this.context = context;
        this.listaProdutos = listaProdutos;
        this.inflater = LayoutInflater.from( context );
        Log.i("Entrar5", String.valueOf(listaProdutos));
    }

    @Override
    public int getCount() {
        return listaProdutos.size();
    }

    @Override
    public Object getItem(int i) {
        return listaProdutos.get( i );
    }

    @Override
    public long getItemId(int i) {
        return listaProdutos.get( i ).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.i("Entrar4", String.valueOf(listaProdutos));
        ListaCarrinhoAdapter.ItemSuporte item;

        if ( view == null){
            view = inflater.inflate(R.layout.lista_item, null);
            item = new ListaCarrinhoAdapter.ItemSuporte();
            item.cartPlusImg = (ImageView) view.findViewById(R.id.cart_plus_img);
            item.cartMinusImg = (ImageView) view.findViewById(R.id.cart_minus_img);
            item.produtoQuantidade = (TextView) view.findViewById(R.id.produtoQuantidade);
            item.itemNome = (TextView) view.findViewById(R.id.itemNome);
            item.itemPreco = (TextView) view.findViewById(R.id.itemPreco);
            item.layout = (LinearLayout) view.findViewById(R.id.layout);
            view.setTag( item );
        }else {
            item = (ListaCarrinhoAdapter.ItemSuporte) view.getTag();
        }
        Produto produto = listaProdutos.get( i );
        item.itemNome.setText( produto.getNome());
        item.itemPreco.setText( String.valueOf( produto.getPreco()));
        item.produtoQuantidade.setText(String.valueOf(count));

        item.cartPlusImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               count ++;
               Log.i("Entrar40", String.valueOf(count));
            }
        });
        return view;
    }


    private class ItemSuporte{
        TextView itemNome, itemPreco, produtoQuantidade;
        ImageView cartPlusImg, cartMinusImg;
        LinearLayout layout;
    }
}
