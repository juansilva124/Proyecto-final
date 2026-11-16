package edu.puj.Model;

import java.io.Serializable;

public enum Convenios implements Serializable {
    NINGUNO(0),
    ALMACEN(30),
    RESTAURANTE(60),
    CINE(120);
    private int minutos_gratis;
    private static final long  serialVersionUID = 1L;
    Convenios(int minutos_gratis) {
        this.minutos_gratis=minutos_gratis;
    }

    public int getMinutos_gratis() {
        return minutos_gratis;
    }
}