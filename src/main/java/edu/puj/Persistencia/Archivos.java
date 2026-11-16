package edu.puj.Persistencia;

import edu.puj.Excepciones.VehiculoYaAdentroException;
import edu.puj.Model.*;
import edu.puj.Negocio.Parqueadero;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Archivos {
    public static ArrayList<Tiquete> Leermovimientostxt (ArrayList<Tiquete> tiquetes, Parqueadero p){
        try(BufferedReader reader = new BufferedReader(new FileReader("Movimientos.txt"))){
            String a;
            Tiquete t;
            int numerotiquete =0;
            while((a=reader.readLine())!= null ) {
                if(!a.contains("#")) {

                    String[] partes = a.split("\\*");
                    if (partes[0].equalsIgnoreCase("S")) {
                        int aux=0;
                        try {
                            p.estarepetida(partes[1]);
                        } catch (VehiculoYaAdentroException e) {
                            aux = 1;
                        }catch (Exception e){
                            System.out.println(e.getClass()+" "+e.getMessage());
                        }
                        if(aux==1) {
                            LocalDateTime salida = ControladorFechas.deStringafechayhora(partes[2], "yyyy-MM-dd HH:mm");
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
                                System.out.println(a + ": El convenio no es valido");
                                continue;
                            }
                            Tiquete ti=null;
                            try {
                                ti =p.registrar_salida(partes[1], salida, con);

                            }catch (Exception e){
                                System.out.println(a+": "+e.getMessage());
                            }

                            if(ti!=null) {
                                System.out.println(a + ": Se regisitro la salida del tiquete numero " + ti.getNumero() + " correctamente");
                            }

                        }else{
                            System.out.println(a+": El vehiculo con placas "+partes[1]+" no esta registrado");
                        }
                    } else if (partes[0].equalsIgnoreCase("E")) {
                        LocalDateTime temp;
                        temp=ControladorFechas.deStringafechayhora(partes[3],"yyyy-MM-dd HH:mm");
                        if(partes[2].equalsIgnoreCase("Carro")){
                            Carro c =new Carro(partes[1]);
                            try {
                                c.laplacaesvalida();
                                p.estarepetida(c.getPlaca());
                                t=new Tiquete(c);
                                t.setEntrada(temp);
                                numerotiquete++;
                                t.setNumero(numerotiquete);
                                t.setSiguedentro(true);
                                tiquetes.add(t);
                                System.out.println(a+":Tiquete "+t.getNumero()+" fue asignado a el vehiculo con placa: "+t.getVehiculo().getPlaca());

                            }catch (Exception e){
                                System.out.println(a+": "+e.getMessage());
                            }
                        } else if (partes[2].equalsIgnoreCase("Moto")) {
                            Moto m =new Moto(partes[1]);
                            try {
                                m.laplacaesvalida();
                                p.estarepetida(m.getPlaca());
                                t=new Tiquete(m);
                                t.setEntrada(temp);
                                numerotiquete++;
                                t.setNumero(numerotiquete);
                                t.setSiguedentro(true);
                                tiquetes.add(t);
                                System.out.println(a+": Tiquete "+t.getNumero()+" fue asignado a el vehiculo con placa: "+t.getVehiculo().getPlaca());
                            }catch (Exception e){
                                System.out.println(a+": "+e.getMessage());
                            }
                        } else if (partes[2].equalsIgnoreCase("Electrico")) {
                            Electrico elec =new Electrico(partes[1]);
                            try {
                                elec.laplacaesvalida();
                                p.estarepetida(elec.getPlaca());
                                t=new Tiquete(elec);
                                t.setEntrada(temp);
                                numerotiquete++;
                                t.setNumero(numerotiquete);
                                t.setSiguedentro(true);
                                tiquetes.add(t);
                                System.out.println(a+": Tiquete "+t.getNumero()+" fue asignado a el vehiculo con placa: "+t.getVehiculo().getPlaca());
                            }catch (Exception e){
                                System.out.println(a+": "+e.getMessage());
                            }
                        }else{
                            System.out.println(a+": El tipo de vehiculo es incorrecto");
                        }
                    }else {
                        System.out.println(a+":La linea no es valida");
                    }
                }
            }
        }catch(IOException e){
            System.out.println("Error al leer el archivo Movimientos.txt: " + e.getMessage());
        }catch (Exception e){
            System.out.println("Error leyendo los datos de Movimientos.txt: "+e.getClass()+ e.getMessage());
        }
        return tiquetes;
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
    public static void escribirmovimientostxt(){
        String archivo="Movimientos.txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(archivo,true))) {
            writer.write("uy");
        }catch(IOException e){
            System.out.println("Error al guardar el archivo Movimientos.txt: " + e.getMessage());
        }
    }

    public static void serializarsistema (){

    }
}
