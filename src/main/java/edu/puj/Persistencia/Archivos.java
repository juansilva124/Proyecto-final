package edu.puj.Persistencia;

import edu.puj.Excepciones.PlacaInvalidaException;
import edu.puj.Excepciones.VehiculoNoEncontradoException;
import edu.puj.Excepciones.VehiculoYaAdentroException;
import edu.puj.Model.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Archivos {
    public static ArrayList<Tiquete> Leermovimientostxt (ArrayList<Tiquete> tiquetes) throws VehiculoNoEncontradoException {
        ArrayList<Tiquete> arr =tiquetes;
        try(BufferedReader reader = new BufferedReader(new FileReader("Movimientos.txt"))){
            String a;
            Tiquete t = null;
            int numerotiquete =0;
            while((a=reader.readLine())!= null ) {
                if(!a.contains("#")) {

                    String[] partes = a.split("\\*");
                    if (partes[0].equalsIgnoreCase("S")) {
                        int aux=0;
                        try {
                            Tiquete.estarepetida(arr,partes[1]);
                            throw  new VehiculoNoEncontradoException("El vehiculo con placas "+ partes[1]+" no ha entrado al estacionamiento");
                        } catch (VehiculoYaAdentroException e) {
                            System.out.println("Busacando tiquete");
                            aux=1;
                        }catch(VehiculoNoEncontradoException e){
                            System.out.println(e.getMessage());
                        } catch (Exception e){
                            System.out.println(e.getClass()+" "+e.getMessage());
                        }
                        if(aux==1){
                            for(Tiquete ti:arr){
                                if(ti.getVehiculo().getPlaca().equals(partes[1])){
                                    LocalDateTime salida= ControladorFechas.deStringafechayhora(partes[2],"yyyy-MM-dd HH:mm");
                                    System.out.println(salida);
                                    //Falta por terminar aqui
                                }
                            }
                        }
                    } else if (partes[0].equalsIgnoreCase("E")) {
                        if(partes[2].equalsIgnoreCase("Carro")){
                            Carro c =new Carro(partes[1]);
                            try {
                                c.laplacaesvalida();
                                Tiquete.estarepetida(arr,c.getPlaca());
                                t=new Tiquete(c);
                                LocalDateTime temp;
                                temp=ControladorFechas.deStringafechayhora(partes[3],"yyyy-MM-dd HH:mm");
                                t.setEntrada(temp);
                                numerotiquete++;
                                t.setNumero(numerotiquete);
                                arr.add(t);
                                System.out.println("Tiquete "+t.getNumero()+" fue asignado a el vehiculo con placa: "+t.getVehiculo().getPlaca());

                            }catch (PlacaInvalidaException e){
                                System.out.println(e.getMessage());
                            }
                        } else if (partes[2].equalsIgnoreCase("Moto")) {
                            Moto m =new Moto(partes[1]);
                            try {
                                m.laplacaesvalida();
                                Tiquete.estarepetida(arr,m.getPlaca());
                                t=new Tiquete(m);
                                LocalDateTime temp;
                                temp=ControladorFechas.deStringafechayhora(partes[3],"yyyy-MM-dd HH:mm");
                                t.setEntrada(temp);
                                numerotiquete++;
                                t.setNumero(numerotiquete);
                                arr.add(t);
                                System.out.println("Tiquete "+t.getNumero()+" fue asignado a el vehiculo con placa: "+t.getVehiculo().getPlaca());
                            }catch (PlacaInvalidaException e){
                                System.out.println(e.getMessage());
                            }
                        } else if (partes[2].equalsIgnoreCase("Electrico")) {
                            Electrico elec =new Electrico(partes[1]);
                            try {
                                elec.laplacaesvalida();
                                Tiquete.estarepetida(arr,elec.getPlaca());
                                t=new Tiquete(elec);
                                LocalDateTime temp;
                                temp=ControladorFechas.deStringafechayhora(partes[3],"yyyy-MM-dd HH:mm");
                                t.setEntrada(temp);
                                numerotiquete++;
                                t.setNumero(numerotiquete);
                                arr.add(t);
                                System.out.println("Tiquete "+t.getNumero()+" fue asignado a el vehiculo con placa: "+t.getVehiculo().getPlaca());
                            }catch (PlacaInvalidaException e){
                                System.out.println(e.getMessage());
                            }
                        }else{
                            System.out.println("El tipo de vehiculo es incorrecto");
                        }
                    }else {
                        System.out.println("La linea: "+ a +" no es valida");
                    }
                }
            }
        }catch(IOException e){
            System.out.println("Error al leer el archivo Movimientos.txt: " + e.getMessage());
        }catch (Exception e){
            System.out.println("Error leyendo los datos de Movimientos.txt: "+e.getClass()+ e.getMessage());
        }
        return arr;
    }
    public static void escribirmovimientostxt(){
        String archivo="Movimientos.txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(archivo,true))) {
            writer.write("uy");
        }catch(IOException e){
            System.out.println("Error al guardar el archivo Movimientos.txt: " + e.getMessage());
        }
    }
}
