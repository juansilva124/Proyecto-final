package edu.puj.Negocio;

import edu.puj.Excepciones.FechaSalidaInvalidaException;
import edu.puj.Excepciones.VehiculoNoEncontradoException;
import edu.puj.Excepciones.VehiculoYaAdentroException;
import edu.puj.Model.*;
import edu.puj.Persistencia.Archivos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Parqueadero implements IParqueadero, Serializable {
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
        ArrayList<String> cierre=new ArrayList<>();
        String nombrear="Cierre ";
        String titulo=nombrear.concat(ControladorFechas.formatearFecha(fecha,"yyyy-MM-dd"));
        nombrear=titulo.concat(".txt");
        cierre.add(titulo);
        int c=0;
        int m=0;
        int E=0;
        int totalv=0;
        long totalc=0;
        long totalm=0;
        long totalE=0;
        long totalp=0;
        long totalr=0;
        for(Tiquete t:tiquetes){
            if(t.getSalida()!=null) {
                String sal = ControladorFechas.formatearFecha(t.getSalida(), "yyyy-MM-dd");
                LocalDate sa = ControladorFechas.convertirTextoAFecha(sal, "yyyy-MM-dd");
                if (!t.isSiguedentro() && ControladorFechas.esIgual(sa, fecha)) {
                    totalv++;
                    if (t.getVehiculo() instanceof Electrico) {
                        E++;
                        totalE = t.getValor_de_parqueo() + totalE;
                        totalr = totalr + t.getValor_recarga();
                    } else if (t.getVehiculo() instanceof Carro) {
                        c++;
                        totalc = totalc + t.getValor_de_parqueo();
                    } else if (t.getVehiculo() instanceof Moto) {
                        m++;
                        totalm = totalm + t.getValor_de_parqueo();
                    }
                }
            }
        }
        totalp=totalc+totalE+totalm;
        cierre.add((String.valueOf(totalv)));
        cierre.add((String.valueOf(totalp)));
        cierre.add((String.valueOf(totalr)));
        cierre.add((String.valueOf(totalp+totalr)));
        cierre.add((String.valueOf(c)));
        cierre.add((String.valueOf(totalc)));
        cierre.add((String.valueOf(m)));
        cierre.add((String.valueOf(totalm)));
        cierre.add((String.valueOf(E)));
        cierre.add((String.valueOf(totalE)));
        Archivos.escribircierre(nombrear,cierre);
        return cierre;
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
