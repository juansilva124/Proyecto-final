package edu.puj.Negocio;

import edu.puj.Model.Convenios;
import edu.puj.Model.Mensualidad;
import edu.puj.Model.Tiquete;
import edu.puj.Model.vehiculos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Parqueadero  implements  IParqueadero{
    private int siguientenumero;
    private ArrayList<Tiquete>tiquetes=new ArrayList<>();
    private ArrayList<Mensualidad>Mensualidades=new ArrayList<>();

    public int getSiguientenumero() {
        return siguientenumero;
    }

    public void setSiguientenumero(int siguientenumero) {
        this.siguientenumero = siguientenumero;
    }

    public ArrayList<Tiquete> getTiquetes() {
        return tiquetes;
    }
    public Tiquete registrar_entrada(vehiculos v, LocalDateTime entrada){
        return null;
    }
    public Tiquete registrar_salida(String placa, LocalDateTime salida, Convenios convenio){
        return null;
    }
    public List<String> reporteVehiculosAdentro(){
        return null;
    }
    public List<String> reporteCierre(LocalDate fecha){
        return null;
    }
    public List<String>  reportePlacasFrecuentes(){
        return null;
    }
    public void setTiquetes(ArrayList<Tiquete> tiquetes) {
        this.tiquetes = tiquetes;
    }

    public ArrayList<Mensualidad> getMensualidades() {
        return Mensualidades;
    }

    public void setMensualidades(ArrayList<Mensualidad> mensualidades) {
        Mensualidades = mensualidades;
    }
    public void agregar_mensualidad(Mensualidad m){

    }
}
