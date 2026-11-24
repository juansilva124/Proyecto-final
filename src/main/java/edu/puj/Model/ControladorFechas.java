package edu.puj.Model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;


public class ControladorFechas {

    public static LocalDateTime obtenerFechaHoraActual() {
        return LocalDateTime.now();
    }
    public static String formatearFecha(LocalDate fecha, String formato) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
        return fecha.format(formatter);
    }
    public static String formatearFecha(LocalDateTime fecha, String formato) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
        return fecha.format(formatter);
    }
    public static LocalDate convertirTextoAFecha(String texto, String formato) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
        return LocalDate.parse(texto, formatter);
    }
    public static long calcularDiferenciaMinutos(LocalDateTime inicio, LocalDateTime fin) {
        return ChronoUnit.MINUTES.between(inicio, fin);
    }
    public static boolean esAntes(LocalDateTime fecha1, LocalDateTime fecha2) {

        return fecha1.isBefore(fecha2);
    }
    public static boolean esAntes(LocalDate fecha1, LocalDate fecha2) {
        return fecha1.isBefore(fecha2);
    }
    public static boolean esDespues(LocalDate fecha1, LocalDate fecha2) {
        return fecha1.isAfter(fecha2);
    }
    public static boolean esIgual(LocalDate fecha1, LocalDate fecha2) {
        return fecha1.isEqual(fecha2);
    }
    public static LocalDateTime deStringafechayhora(String texto, String formato) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
        return LocalDateTime.parse(texto, formatter);
    }
}
