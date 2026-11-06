package edu.puj.Model;

public enum Convenios {
    NINGUNO(0),
    ALMACEN(30),
    RESTAURANTE(60),
    CINE(120);
    private int minutos_gratis;
    Convenios(int minutos_gratis) {
        this.minutos_gratis=minutos_gratis;
    }
}
