package br.com.thallesr.thallesdatabase.model;

public class Transaction {
    static public int totaltransactions = 0;
    public final int id;

    public Transaction(){
        this.id = ++totaltransactions;
    }
}
