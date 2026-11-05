package edu.puj.Model;

import edu.puj.Excepciones.FechaSalidaInvalidaException;
import edu.puj.Excepciones.VehiculoYaAdentroException;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Tiquete{
    private int numero;
    private LocalDateTime entrada;
    private vehiculos vehiculo;
    private LocalDateTime salida;
    private long valor_de_parqueo;
    private long valor_recarga;


    public Tiquete (vehiculos vehiculo){
        this.vehiculo=vehiculo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }

    public vehiculos getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(vehiculos vehiculo) {
        this.vehiculo = vehiculo;
    }

    public LocalDateTime getSalida() {
        return salida;
    }

    public void setSalida(LocalDateTime salida) {
        this.salida = salida;
    }

    public long getValor_de_parqueo() {
        return valor_de_parqueo;
    }

    public void setValor_de_parqueo(long valor_de_parqueo) {
        this.valor_de_parqueo = valor_de_parqueo;
    }

    public long getValor_recarga() {
        return valor_recarga;
    }

    public void setValor_recarga(long valor_recarga) {
        this.valor_recarga = valor_recarga;
    }

    public boolean vehiculoyaadentro(){
        if(salida==null){
            return true;
        }
        return false;
    }
    public void cerrartiquete(LocalDateTime fecha)throws FechaSalidaInvalidaException {
        if(ControladorFechas.esAntes(fecha,this.entrada));
    }

    public static void estarepetida (ArrayList<Tiquete>t,String placa) throws VehiculoYaAdentroException{
        for(Tiquete ti:t){
            if(ti.getVehiculo().getPlaca().equalsIgnoreCase(placa)){
                throw  new VehiculoYaAdentroException("El vehiculo con placa "+placa+" ya se encuentra dentro del parqueadero");
            }
        }
    }
}
