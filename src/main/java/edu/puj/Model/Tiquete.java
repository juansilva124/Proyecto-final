package edu.puj.Model;

import edu.puj.Excepciones.FechaSalidaInvalidaException;
import edu.puj.Excepciones.VehiculoNoEncontradoException;
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
    private Convenios convenio;
    private boolean siguedentro;

    public boolean isSiguedentro() {
        return siguedentro;
    }

    public void setSiguedentro(boolean siguedentro) {
        this.siguedentro = siguedentro;
    }

    public Convenios getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenios convenio) {
        this.convenio = convenio;
    }

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

    public boolean vehiculoyaadentro()throws VehiculoNoEncontradoException {
        if(siguedentro){
            return true;
        }
        throw  new VehiculoNoEncontradoException("El vehiculo ya salio del parqueadero");
    }
    public void cerrartiquete(LocalDateTime fecha) {
        vehiculoyaadentro();
        long minutos= ControladorFechas.calcularDiferenciaMinutos(entrada,fecha)-convenio.getMinutos_gratis();
        if(minutos<0){
            minutos=0;
        }
        long a=vehiculo.Calculartarifa(minutos);
        if(vehiculo instanceof Electrico){
            Electrico v=(Electrico) vehiculo;
            valor_recarga= v.costorecarga(minutos);
        }else{
            valor_recarga=0;
        }
        this.valor_de_parqueo = a;
        siguedentro=false;
    }

}
