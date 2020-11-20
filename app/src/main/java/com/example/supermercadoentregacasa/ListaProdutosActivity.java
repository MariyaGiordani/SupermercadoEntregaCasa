package com.example.supermercadoentregacasa;

public class ListaProdutosActivity {
    public String id;
    public String nome;
    public double preco;

    @Override
    public String toString() {
        return nome + "    -    Preço: " + preco;
    }
}
