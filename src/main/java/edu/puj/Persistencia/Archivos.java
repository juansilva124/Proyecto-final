package edu.puj.Persistencia;

import edu.puj.Excepciones.VehiculoYaAdentroException;
import edu.puj.Model.*;
import edu.puj.Negocio.IParqueadero;
import edu.puj.Negocio.Parqueadero;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Archivos {
    public static ArrayList<String> Leermovimientostxt (ArrayList<Tiquete> tiquetes, Parqueadero p){
        ArrayList<String> movimientos = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("Movimientos.txt"))){
            String a;
            Tiquete t;
            int numerotiquete = p.getSiguientenumero();
            while((a=reader.readLine())!= null ) {
                if(!a.contains("#")) {

                    String[] partes = a.split("\\*");
                    if(partes.length!=4){
                        movimientos.add(a + ": La linea no tiene los datos necesarios");
                        continue;
                    }
                    if (partes[0].equalsIgnoreCase("S")) {
                        int aux=0;
                        try {
                            p.estarepetida(partes[1]);
                        } catch (VehiculoYaAdentroException e) {
                            aux = 1;
                        }catch (Exception e){
                            movimientos.add(e.getClass()+" "+e.getMessage());
                        }
                        if(aux==1) {
                            LocalDateTime salida;
                            try {
                                salida = ControladorFechas.deStringafechayhora(partes[2], "yyyy-MM-dd HH:mm");
                            } catch (DateTimeParseException e) {
                                movimientos.add(a + ": La fecha no tiene el formato correcto");
                                continue;
                            }
                            Convenios con;
                            if (partes[3].equalsIgnoreCase("Ninguno")) {
                                con = Convenios.NINGUNO;
                            } else if (partes[3].equalsIgnoreCase("Almacen")) {
                                con = Convenios.ALMACEN;
                            } else if (partes[3].equalsIgnoreCase("Restaurante")) {
                                con = Convenios.RESTAURANTE;
                            } else if (partes[3].equalsIgnoreCase("Cine")) {
                                con = Convenios.CINE;
                            } else {
                                movimientos.add(a + ": El convenio no es valido");
                                continue;
                            }
                            Tiquete ti=null;
                            try {
                                ti =p.registrar_salida(partes[1], salida, con);

                            }catch (Exception e){
                                movimientos.add(a+": "+e.getMessage());
                            }

                            if(ti!=null) {
                                movimientos.add(a + ": Se regisitro la salida del tiquete numero " + ti.getNumero() + " correctamente");
                            }

                        }else{
                            movimientos.add(a+": El vehiculo con placas "+partes[1]+" no esta registrado");
                        }
                    } else if (partes[0].equalsIgnoreCase("E")) {
                        LocalDateTime temp;
                        try {
                            temp = ControladorFechas.deStringafechayhora(partes[3], "yyyy-MM-dd HH:mm");
                        } catch (DateTimeParseException e) {
                            movimientos.add(a + ": La fecha no tiene el formato correcto");
                            continue;
                        }
                        if(partes[2].equalsIgnoreCase("Carro")){
                            Carro c =new Carro(partes[1]);
                            try {
                                c.laplacaesvalida();
                                p.estarepetida(c.getPlaca());
                                t=new Tiquete(c);
                                t.setEntrada(temp);
                                t.setNumero(numerotiquete);
                                t.setSiguedentro(true);
                                tiquetes.add(t);
                                movimientos.add(a+":Tiquete "+t.getNumero()+" fue asignado a el vehiculo con placa: "+t.getVehiculo().getPlaca());
                                numerotiquete++;
                                p.setSiguientenumero(numerotiquete);
                            }catch (Exception e){
                                movimientos.add(a+": "+e.getMessage());
                            }
                        } else if (partes[2].equalsIgnoreCase("Moto")) {
                            Moto m =new Moto(partes[1]);
                            try {
                                m.laplacaesvalida();
                                p.estarepetida(m.getPlaca());
                                t=new Tiquete(m);
                                t.setEntrada(temp);
                                t.setNumero(numerotiquete);
                                t.setSiguedentro(true);
                                tiquetes.add(t);
                                movimientos.add(a+": Tiquete "+t.getNumero()+" fue asignado a el vehiculo con placa: "+t.getVehiculo().getPlaca());
                                numerotiquete++;
                                p.setSiguientenumero(numerotiquete);
                            }catch (Exception e){
                                movimientos.add(a+": "+e.getMessage());
                            }
                        } else if (partes[2].equalsIgnoreCase("Electrico")) {
                            Electrico elec =new Electrico(partes[1]);
                            try {
                                elec.laplacaesvalida();
                                p.estarepetida(elec.getPlaca());
                                t=new Tiquete(elec);
                                t.setEntrada(temp);
                                t.setNumero(numerotiquete);
                                t.setSiguedentro(true);
                                tiquetes.add(t);
                                movimientos.add(a+": Tiquete "+t.getNumero()+" fue asignado a el vehiculo con placa: "+t.getVehiculo().getPlaca());
                                numerotiquete++;
                                p.setSiguientenumero(numerotiquete);
                            }catch (Exception e){
                                movimientos.add(a+": "+e.getMessage());
                            }
                        }else{
                            movimientos.add(a+": El tipo de vehiculo es incorrecto");
                        }
                    }else {
                        movimientos.add(a+":La linea no es valida");
                    }
                }
            }
        }catch(IOException e){
            System.out.println("Error al leer el archivo Movimientos.txt: " + e.getMessage());
        }catch (Exception e){
            System.out.println("Error leyendo los datos de Movimientos.txt: "+e.getClass()+ e.getMessage());
        }
        return movimientos;
    }
    public static  void escribircierre(String nombrear, List<String>cierre){
        try(BufferedWriter w=new BufferedWriter(new FileWriter(nombrear))){
            w.write(cierre.get(0)+"\n");
            w.write("Tiquetes = "+cierre.get(1)+"\n");
            w.write("Parqueo = "+cierre.get(2)+"\n");
            w.write("Recargas = "+cierre.get(3)+"\n");
            w.write("Total = "+cierre.get(4)+"\n");
            w.write("CARRO = "+"Tiquetes = "+cierre.get(5)+" Total = "+cierre.get(6)+"\n");
            w.write("MOTO = "+"Tiquetes = "+cierre.get(7)+" Total = "+cierre.get(8)+"\n");
            w.write("ELECTRICO = "+"Tiquetes = "+cierre.get(9)+" Total = "+cierre.get(10)+"\n");
        }catch (IOException e){
            System.out.println("Error creando el archivo del cierre: " + e.getMessage());
        }catch (Exception e){
            System.out.println("Error creando el archivo del cierre: "+e.getClass()+ e.getMessage());
        }
    }
    public static void serializarsistema (Parqueadero p){
        String nombrear ="Sistema.dat";
        try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(nombrear))){
                os.writeObject(p);
        }catch (IOException e){
            System.out.println("Error al serializar el sistema:" + e.getMessage());
        }catch (Exception e){
        System.out.println("Error al serializar el sistema: "+e.getClass()+ e.getMessage());
    }
    }
    public static List<String> cargarMensualidades(String ruta, IParqueadero sistema) {

        ArrayList<String> resultados = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {

            String linea;
            int numeroLinea = 0;

            while ((linea = lector.readLine()) != null) {

                numeroLinea++;

                if (linea.trim().isEmpty() || linea.trim().startsWith("#")) {
                    continue;
                }

                try {

                    String[] datos = linea.split("\\*");
                    if (datos.length != 3) {
                        throw new IllegalArgumentException(
                                "La línea debe tener tres datos.");
                    }

                    String placa = datos[0].trim();

                    LocalDate inicio = LocalDate.parse(datos[1].trim());

                    LocalDate fin = LocalDate.parse(datos[2].trim());

                    if(ControladorFechas.esAntes(fin,inicio)||ControladorFechas.esIgual(inicio,fin)){
                        System.out.println(linea+": La fecha de inicio es despues de la de fin");
                        continue;
                    }

                    Mensualidad mensualidad = new Mensualidad(placa, inicio, fin);

                    sistema.agregar_mensualidad(mensualidad);

                    resultados.add("Mensualidad cargada correctamente: " + placa);

                } catch (Exception e) {

                    resultados.add("Error en la línea " + numeroLinea + ": " + e.getMessage());
                }
            }

        } catch (IOException e) {

            resultados.add("Error al leer el archivo: " + e.getMessage());
        }

        return resultados;
    }
    public static Parqueadero cargarSistema(String ruta) {

        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ruta))) {

            Object objeto = entrada.readObject();

            if (objeto instanceof Parqueadero) {

                return (Parqueadero) objeto;

            } else {

                throw new IllegalStateException("El archivo no contiene un parqueadero válido.");
            }

        } catch (IOException | ClassNotFoundException e) {

            throw new IllegalStateException("Error al cargar el sistema: " + e.getMessage());
        }
    }

}

