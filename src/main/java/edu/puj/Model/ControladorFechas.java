package edu.puj.Model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;


public class ControladorFechas {


    // Obtener fecha actual
    public static LocalDate obtenerFechaActual() {
        return LocalDate.now();
    }

    // Obtener fecha y hora actual
    public static LocalDateTime obtenerFechaHoraActual() {
        return LocalDateTime.now();
    }

    // Formatear fecha LocalDate según formato
    public static String formatearFecha(LocalDate fecha, String formato) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
        return fecha.format(formatter);
    }
    public static String formatearFecha(LocalDateTime fecha, String formato) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
        return fecha.format(formatter);
    }

    // Convertir texto a LocalDate con formato
    public static LocalDate convertirTextoAFecha(String texto, String formato) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
        return LocalDate.parse(texto, formatter);
    }

    // Calcular días entre dos fechas
    public static long calcularDiferenciaDias(LocalDate inicio, LocalDate fin) {
        return ChronoUnit.DAYS.between(inicio, fin);
    }

    public static long calcularDiferenciaMinutos(LocalDateTime inicio, LocalDateTime fin) {
        return ChronoUnit.MINUTES.between(inicio, fin);
    }

    // Saber si una fecha es anterior a otra
    public static boolean esAntes(LocalDateTime fecha1, LocalDateTime fecha2) {

        return fecha1.isBefore(fecha2);
    }
    public static boolean esAntes(LocalDate fecha1, LocalDate fecha2) {
        return fecha1.isBefore(fecha2);
    }
    // Saber si una fecha es posterior a otra
    public static boolean esDespues(LocalDate fecha1, LocalDate fecha2) {
        return fecha1.isAfter(fecha2);
    }
    public static boolean esDespues(LocalDateTime fecha1, LocalDateTime fecha2) {
        return fecha1.isAfter(fecha2);
    }

    // Saber si dos fechas son iguales
    public static boolean esIgual(LocalDate fecha1, LocalDate fecha2) {
        return fecha1.isEqual(fecha2);
    }

    // Sumar o restar días, meses o años a una fecha
    public static LocalDate modificarFecha(LocalDate fecha, int dias, int meses, int años) {
        return fecha.plusDays(dias).plusMonths(meses).plusYears(años);
    }

    public static LocalDateTime deStringafechayhora(String texto, String formato) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
        return LocalDateTime.parse(texto, formatter);
    }
}
