package edu.puj.Negocio;

import edu.puj.Excepciones.FechaSalidaInvalidaException;
import edu.puj.Excepciones.VehiculoNoEncontradoException;
import edu.puj.Excepciones.VehiculoYaAdentroException;
import edu.puj.Model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Parqueadero implements IParqueadero {
    private int siguientenumero;
    private ArrayList<Tiquete> tiquetes=new ArrayList<>();
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
    public Tiquete registrar_salida(String placa, LocalDateTime salida, Convenios convenio)throws FechaSalidaInvalidaException {
        for(Tiquete t:this.tiquetes){
            for(Mensualidad m: Mensualidades){
                if(m.getPlaca().equals(placa)){
                    String sal=ControladorFechas.formatearFecha(salida,"yyyy-MM-dd");
                    if(m.cubre(ControladorFechas.convertirTextoAFecha(sal,"yyyy-MM-dd"))){
                        System.out.println("Socio sea serio");
                        break;
                    }
                }
            }
            if(t.getVehiculo().getPlaca().equals(placa)&&t.isSiguedentro()){
                if(ControladorFechas.esAntes(salida,t.getEntrada())){
                    throw new FechaSalidaInvalidaException();
                }
                t.setConvenio(convenio);
                try {
                    t.cerrartiquete(salida);
                } catch (Exception e) {
                    t.setConvenio(null);
                }
                return t;
            }
        }
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
        Mensualidades.add(m);
    }
    public void estarepetida (String placa) throws VehiculoYaAdentroException {
        for(Tiquete ti:this.tiquetes){
            if(ti.getVehiculo().getPlaca().equalsIgnoreCase(placa) && ti.isSiguedentro()){
                throw  new VehiculoYaAdentroException("El vehiculo con placa "+placa+" ya se encuentra dentro del parqueadero");
            }
        }
    }
    public void aunestaadentro(String placa) throws VehiculoNoEncontradoException {
        for(Tiquete t : this.tiquetes){
            if(t.getVehiculo().getPlaca().equalsIgnoreCase(placa)){
                if(t.vehiculoyaadentro()){
                    System.out.println(t.vehiculoyaadentro());
                    break;
                }
                break;
            }
        }
    }
}
