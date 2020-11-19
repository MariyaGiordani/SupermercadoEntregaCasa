package com.example.supermercadoentregacasa;

public class Produto {
    public String id;
    public String nome;
    public double quantidade;
    public double preco;

    @Override
    public String toString() {
        return nome + " - Preço: " + preco +" Quantidade: " + quantidade;
    }
}

