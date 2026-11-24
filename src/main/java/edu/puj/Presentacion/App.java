package edu.puj.Presentacion;

import edu.puj.Model.*;
import edu.puj.Negocio.IParqueadero;
import edu.puj.Negocio.Parqueadero;
import edu.puj.Persistencia.Archivos;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sn =new Scanner(System.in);
        Parqueadero parqueadero = new Parqueadero();
        int opcion = -1;
        while (opcion != 0) {
        System.out.println("\n===== PARQUEADERO =====");
        System.out.println("1. Cargar mensualidades");
        System.out.println("2. Procesar movimientos");
        System.out.println("3. Registrar entrada");
        System.out.println("4. Registrar una salida");
        System.out.println("5. Ver vehículos adentro");
        System.out.println("6. Cierre de caja de un día");
        System.out.println("7. Ver placas frecuentes");
        System.out.println("8. Guardar el sistema");
        System.out.println("9. Cargar sistema");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
            try {
                opcion = sn.nextInt();

                switch (opcion) {
                    case 1:
                        cargarMensualidades(sn,parqueadero);
                        break;
                    case 2:
                        leermovimientos(parqueadero);
                        break;
                    case 3:
                        registrarEntrada(sn,parqueadero);
                        break;
                    case 4:
                        registrarsalida(sn,parqueadero);
                        break;
                    case 5:
                        verVehiculosAdentro(parqueadero);
                        break;
                    case 6:
                        crearreportetxt(sn,parqueadero);
                        break;
                    case 7:
                        verPlacasFrecuentes(parqueadero);
                        break;
                    case 8:
                        Archivos.serializarsistema(parqueadero);
                        System.out.println("El sistemas se guardo correctamente en el archivo Sistema.dat");
                        break;
                    case 9:
                        cargarSistema(sn,parqueadero);
                        break;

                    case 0:
                        System.out.println("Saliendo del programa.");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un número.");
            }
        }
    }



    public static void cargarMensualidades(Scanner lector, IParqueadero sistema) {

        System.out.print("Ingrese la ruta del archivo de mensualidades: ");
        String ruta = lector.nextLine();

        try {

            List<String> resultados = Archivos.cargarMensualidades(ruta, sistema);

            for (String resultado : resultados) {
                System.out.println(resultado);
            }

        } catch (Exception e) {
            System.out.println("Error al cargar mensualidades: "
                    + e.getMessage());
        }
    }

    public  static void leermovimientos (Parqueadero parqueadero){

        ArrayList<String> a = Archivos.Leermovimientostxt(parqueadero.getTiquetes(),parqueadero);
        for(String s: a){
            System.out.println(s);
        }
    }

    public static void registrarEntrada(Scanner lector,IParqueadero sistema) {

        try {

            System.out.print("Ingrese la placa del vehículo: ");
            String placa = lector.next().toUpperCase();
            System.out.println("Seleccione el tipo de vehículo:");
            System.out.println("1. Carro");
            System.out.println("2. Moto");
            System.out.println("3. Carro eléctrico");
            System.out.print("Opción: ");

            int tipo = lector.nextInt();
            vehiculos vehiculo;

            switch (tipo) {

                case 1:
                    vehiculo = new Carro(placa);
                    break;

                case 2:
                    vehiculo = new Moto(placa);
                    break;

                case 3:
                    vehiculo = new Electrico(placa);
                    break;

                default:
                    System.out.println("Tipo de vehículo no válido.");
                    return;
            }
            System.out.print("Ingrese la fecha y hora de entrada " + "(dd/MM/yyyy HH:mm): ");
            lector.nextLine();
            LocalDateTime entrada = ControladorFechas.deStringafechayhora(lector.nextLine(), "dd/MM/yyyy HH:mm");

            Tiquete tiquete = sistema.registrar_entrada(vehiculo, entrada);

            System.out.println("\nEntrada registrada correctamente.");
            System.out.println("Se le asigno el tiquete "+tiquete.getNumero()+" al vehiculo con placas: "+tiquete.getVehiculo().getPlaca());

        } catch (Exception e) {
            System.out.println("No se pudo registrar la entrada: " + e.getMessage());
        }
    }

    public static void registrarsalida(Scanner sc,Parqueadero p){
        System.out.println("Ingrese la placa del vehiculo al que le desea registrar la salida");
        try {
            String placa = sc.next();
            Convenios convenios=null;
            Tiquete t = p.buscarAbierto(placa);
            while(convenios==null) {
                System.out.println("Ingresa el convenio");
                String convenio = sc.next();
                if (convenio.equalsIgnoreCase("NINGUNO")) {
                    convenios = Convenios.NINGUNO;
                } else if (convenio.equalsIgnoreCase("ALMACEN")) {
                    convenios = Convenios.ALMACEN;
                } else if (convenio.equalsIgnoreCase("RESTAURANTE")) {
                    convenios = Convenios.RESTAURANTE;
                } else if (convenio.equalsIgnoreCase("CINE")) {
                    convenios = Convenios.CINE;
                }
            }
            t=p.registrar_salida(placa,ControladorFechas.obtenerFechaHoraActual(),convenios);
            System.out.println(t.toString());
            System.out.println("Se registro la salida correctamente");
        }catch (Exception e){
            sc.nextLine();
        }
    }
    public static void verVehiculosAdentro(IParqueadero sistema) {

        ArrayList<String> reporte = sistema.reporteVehiculosAdentro();

        if (reporte.isEmpty()) {
            System.out.println("No hay vehículos dentro del parqueadero.");
        } else {

            System.out.println("\n===== VEHÍCULOS ADENTRO =====");

            for (int i =2;i<reporte.toArray().length; i=i+3) {
                System.out.println("Tiquete numero:"+reporte.get(i-2));
                System.out.println("Placa del vehiculo:"+reporte.get(i-1));
                System.out.println("Tipo de vehiculo:"+reporte.get(i));
                System.out.println("----------------------------------");
            }
        }
    }
    public static void crearreportetxt(Scanner sc, Parqueadero p){
        List<String>reportedia=new ArrayList<>();
        System.out.println("Ingrese la fecha de la cual quiere realizar el reporte en este formato (yyyy/mm/dd)");
        try{
            String fechastring=sc.next();
            reportedia=p.reporteCierre(ControladorFechas.convertirTextoAFecha(fechastring,"yyyy/MM/dd"));
            System.out.println(reportedia.get(0));
            System.out.println("Tiquetes = " + reportedia.get(1));
            System.out.println("Parqueo = " + reportedia.get(2));
            System.out.println("Recargas = " + reportedia.get(3));
            System.out.println("Total = " + reportedia.get(4));
            System.out.println("CARRO = " + "Tiquetes = " + reportedia.get(5) + " Total = " + reportedia.get(6));
            System.out.println("MOTO = " + "Tiquetes = " + reportedia.get(7) + " Total = " + reportedia.get(8));
            System.out.println("ELECTRICO = " + "Tiquetes = " + reportedia.get(9) + " Total = " + reportedia.get(10));
            System.out.println("\nCierre del dia creado correctamente");
        } catch (Exception e) {
            System.out.println("Error generando el cierre del dia: "+e.getMessage());
        }
    }
    public static void verPlacasFrecuentes(IParqueadero sistema) {

        List<String> reporte = sistema.reportePlacasFrecuentes();

        if (reporte.isEmpty()) {
            System.out.println("No hay registros de vehículos.");
        } else {

            System.out.println("\n===== PLACAS FRECUENTES =====");

            for (String dato : reporte) {
                System.out.println(dato);
            }
        }
    }

    public static void cargarSistema(Scanner lector,IParqueadero sistema) {

        System.out.print("Ingrese la ruta del archivo sistema.dat: ");
        String ruta = lector.nextLine();

        try {

            sistema = Archivos.cargarSistema(ruta);

            System.out.println("Sistema cargado correctamente.");

        } catch (Exception e) {

            System.out.println("No se pudo cargar el sistema: " + e.getMessage());
        }
    }
}
