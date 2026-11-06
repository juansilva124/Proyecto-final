package edu.puj.Persistencia;

import edu.puj.Excepciones.PlacaInvalidaException;
import edu.puj.Excepciones.VehiculoNoEncontradoException;
import edu.puj.Excepciones.VehiculoYaAdentroException;
import edu.puj.Model.*;
import edu.puj.Negocio.Parqueadero;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Archivos {
    public static ArrayList<Tiquete> Leermovimientostxt (ArrayList<Tiquete> tiquetes, Parqueadero p) throws VehiculoNoEncontradoException {
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
                            Tiquete.estarepetida(tiquetes,partes[1]);
                            throw  new VehiculoNoEncontradoException("El vehiculo con placas "+ partes[1]+" no ha entrado al estacionamiento");
                        } catch (VehiculoYaAdentroException e) {
                            aux=1;
                        }catch(VehiculoNoEncontradoException e){
                            System.out.println(e.getMessage());
                        } catch (Exception e){
                            System.out.println(e.getClass()+" "+e.getMessage());
                        }
                        if(aux==1){
                            for(Tiquete ti: tiquetes){
                                if(ti.getVehiculo().getPlaca().equals(partes[1])){
                                    LocalDateTime salida= ControladorFechas.deStringafechayhora(partes[2],"yyyy-MM-dd HH:mm");
                                    Convenios con = null;
                                    if(partes[3].equalsIgnoreCase("Ninguno")){
                                        con=Convenios.NINGUNO;
                                    }else if (partes[3].equalsIgnoreCase("Almacen")){
                                        con=Convenios.ALMACEN;
                                    }else if (partes[3].equalsIgnoreCase("Restaurante")){
                                        con=Convenios.RESTAURANTE;
                                    }else if (partes[3].equalsIgnoreCase("Cine")){
                                        con=Convenios.CINE;
                                    }else{
                                        System.out.println(a+": El convenio no es valido");
                                        break;
                                    }
                                    p.registrar_salida(partes[1],salida,con);
                                    System.out.println(a+": Se regisitro la salida del tiquete numero "+ti.getNumero()+" correctamente");
                                    break;
                                }
                            }
                        }
                    } else if (partes[0].equalsIgnoreCase("E")) {
                        if(partes[2].equalsIgnoreCase("Carro")){
                            Carro c =new Carro(partes[1]);
                            try {
                                c.laplacaesvalida();
                                Tiquete.estarepetida(tiquetes,c.getPlaca());
                                t=new Tiquete(c);
                                LocalDateTime temp;
                                temp=ControladorFechas.deStringafechayhora(partes[3],"yyyy-MM-dd HH:mm");
                                t.setEntrada(temp);
                                numerotiquete++;
                                t.setNumero(numerotiquete);
                                tiquetes.add(t);
                                System.out.println(a+":Tiquete "+t.getNumero()+" fue asignado a el vehiculo con placa: "+t.getVehiculo().getPlaca());

                            }catch (PlacaInvalidaException e){
                                System.out.println(a+": "+e.getMessage());
                            }
                        } else if (partes[2].equalsIgnoreCase("Moto")) {
                            Moto m =new Moto(partes[1]);
                            try {
                                m.laplacaesvalida();
                                Tiquete.estarepetida(tiquetes,m.getPlaca());
                                t=new Tiquete(m);
                                LocalDateTime temp;
                                temp=ControladorFechas.deStringafechayhora(partes[3],"yyyy-MM-dd HH:mm");
                                t.setEntrada(temp);
                                numerotiquete++;
                                t.setNumero(numerotiquete);
                                tiquetes.add(t);
                                System.out.println(a+": Tiquete "+t.getNumero()+" fue asignado a el vehiculo con placa: "+t.getVehiculo().getPlaca());
                            }catch (PlacaInvalidaException e){
                                System.out.println(a+": "+e.getMessage());
                            }
                        } else if (partes[2].equalsIgnoreCase("Electrico")) {
                            Electrico elec =new Electrico(partes[1]);
                            try {
                                elec.laplacaesvalida();
                                Tiquete.estarepetida(tiquetes,elec.getPlaca());
                                t=new Tiquete(elec);
                                LocalDateTime temp;
                                temp=ControladorFechas.deStringafechayhora(partes[3],"yyyy-MM-dd HH:mm");
                                t.setEntrada(temp);
                                numerotiquete++;
                                t.setNumero(numerotiquete);
                                tiquetes.add(t);
                                System.out.println(a+": Tiquete "+t.getNumero()+" fue asignado a el vehiculo con placa: "+t.getVehiculo().getPlaca());
                            }catch (PlacaInvalidaException e){
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
    public static void escribirmovimientostxt(){
        String archivo="Movimientos.txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(archivo,true))) {
            writer.write("uy");
        }catch(IOException e){
            System.out.println("Error al guardar el archivo Movimientos.txt: " + e.getMessage());
        }
    }
}
