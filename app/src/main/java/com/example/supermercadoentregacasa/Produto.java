package com.example.supermercadoentregacasa;
import java.io.Serializable;

public class Produto implements Serializable {
    private int id;
    private String nome;
    private double quantidade;
    private double preco;
    private String key;

    public Produto(int id, String nome, double quantidade, double preco, String key) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.key = key;
    }

    public Produto() {

    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }


    @Override
    public String toString() {
        if ( quantidade == 0 )
            return this.nome;
        else
            return nome +"( Quantidade: " + quantidade +" ) - Preço: " + preco;
    }
}

