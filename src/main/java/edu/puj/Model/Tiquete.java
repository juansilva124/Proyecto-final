package edu.puj.Model;

import edu.puj.Excepciones.VehiculoNoEncontradoException;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Tiquete implements Serializable {
    private static final long  serialVersionUID = 1L;
    private int numero;
    private LocalDateTime entrada;
    private vehiculos vehiculo;
    private LocalDateTime salida;
    private long valor_de_parqueo;
    private long valor_recarga;
    private Convenios convenio;
    private boolean siguedentro =true;

    public Tiquete(int numero, vehiculos vehiculo, LocalDateTime entrada, LocalDateTime salida, long valorParqueo, long valorRecarga) {
        this.numero = numero;
        this.vehiculo = vehiculo;
        this.entrada = entrada;
        this.salida = salida;
        this.valor_de_parqueo = valorParqueo;
        this.valor_recarga = valorRecarga;
    }

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
    public void cerrartiquete(LocalDateTime fecha,boolean m) {
        vehiculoyaadentro();
        long minutos= ControladorFechas.calcularDiferenciaMinutos(entrada,fecha)-convenio.getMinutos_gratis();
        long minutosr =ControladorFechas.calcularDiferenciaMinutos(entrada,fecha);
        if(minutos<0){
            minutos=0;
        }
        long a=vehiculo.Calculartarifa(minutos);
        this.valor_recarga= vehiculo.Cargoadicional(minutosr);
        if(m) {
            this.valor_de_parqueo=0;
        }else {
            this.valor_de_parqueo = a;
        }
        siguedentro=false;
        this.salida=fecha;
    }
    public long getTotal() {
        return valor_de_parqueo + valor_recarga;
    }

    @Override
    public String toString() {
        return "\nTiquete " +numero +":\n placa= " + vehiculo.getPlaca()+"| tipo de vehiculo= " + vehiculo.getTipo()+ "| entrada= " + ControladorFechas.formatearFecha(entrada,"dd/MM/yyyy HH:mm") + "| salida= " + ControladorFechas.formatearFecha(salida,"dd/MM/yyyy HH:mm") + "| \nconvenio="  + convenio+"| Minutos gratis por convenio = "+convenio.getMinutos_gratis()+"\n"+"Valor de parqueadero = "+valor_de_parqueo+"\n"+"Valor de recarga = "+valor_recarga+"\n"+ "Total a pagar: "+getTotal();
    }
}
