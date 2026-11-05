package edu.puj.Excepciones;

public class FechaSalidaInvalidaException extends ParqueaderoException {
    public FechaSalidaInvalidaException() {
        super("La fecha y hora de salida es anterior a la de entrada");
    }
}
