package edu.puj.Model;

import java.time.LocalDate;

public class Mensualidad {
    private String placa;
    private LocalDate inicio;
    private LocalDate fin;
    public Mensualidad(String placa, LocalDate inicio, LocalDate fin) {
        this.placa = placa;
        this.inicio = inicio;
        this.fin = fin;
    }
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public boolean cubre(LocalDate fecha){
        if(ControladorFechas.esDespues(fecha,fin)|| ControladorFechas.esAntes(fecha,inicio)){
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "mensualidad{" +
                "placa='" + placa + '\'' +
                ", inicio=" + inicio +
                ", fin=" + fin +
                '}';
    }
}
