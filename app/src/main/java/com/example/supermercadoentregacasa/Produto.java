package com.example.supermercadoentregacasa;

public class Produto {
    public String id;
    public String nome;
    public double quantidade;

    @Override
    public String toString() {
        return nome + " - " + quantidade;
    }
}

