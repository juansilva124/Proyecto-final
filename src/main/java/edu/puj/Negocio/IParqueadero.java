package edu.puj.Negocio;

import edu.puj.Model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IParqueadero {
    public void agregar_mensualidad(Mensualidad m);
    public Tiquete registrar_entrada(vehiculos v, LocalDateTime entrada);
    public Tiquete registrar_salida(String placa,LocalDateTime salida,Convenios convenio);
    public List<String>reporteVehiculosAdentro();
    public List<String> reporteCierre(LocalDate fecha);
    public List<String>  reportePlacasFrecuentes();
    public List<Tiquete> getTiquetes();
}
