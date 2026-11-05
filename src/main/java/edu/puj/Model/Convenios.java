package edu.puj.Model;

public enum Convenios {
    Ninguno(0),
    Almacen(30),
    Restaurante(60),
    Cine(120);
    private int minutos_gratis;
    Convenios(int minutos_gratis) {
        this.minutos_gratis=minutos_gratis;
    }
}
