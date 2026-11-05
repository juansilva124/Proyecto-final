package edu.puj.Excepciones;

public class FechaInvalidaException extends ParqueaderoException {
    public FechaInvalidaException() {
        super("La fecha final de la mensualidad es anterior a la de inicio");
    }
}
