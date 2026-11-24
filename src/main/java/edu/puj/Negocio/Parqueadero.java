package edu.puj.Negocio;

import edu.puj.Excepciones.*;
import edu.puj.Model.*;
import edu.puj.Persistencia.Archivos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Parqueadero implements IParqueadero, Serializable {
    private static final long  serialVersionUID = 1L;
    private int siguientenumero=1;
    private ArrayList<Tiquete> tiquetes=new ArrayList<>();
    private ArrayList<Mensualidad>Mensualidades=new ArrayList<>();

    public int getSiguientenumero() {
        return siguientenumero;
    }

    public void setSiguientenumero(int siguientenumero) {
        this.siguientenumero = siguientenumero;
    }

    public ArrayList<Tiquete> getTiquetes() {
        return tiquetes;
    }
    @Override
    public Tiquete registrar_entrada(vehiculos v, LocalDateTime entrada) {
        v.laplacaesvalida();
        if (buscarAbierto(v.getPlaca()) != null) {
            throw new VehiculoYaAdentroException(
                    "El vehículo ya se encuentra en el parqueadero."
            );
        }

        Tiquete nuevo = new Tiquete(siguientenumero, v, entrada, null, 0, 0);
        tiquetes.add(nuevo);
        siguientenumero++;

        return nuevo;
    }
    public Tiquete buscarAbierto(String placa) {
        for (Tiquete t : tiquetes) {
            if (t.getVehiculo().getPlaca().equals(placa) && t.isSiguedentro()) {
                return t;
            }
        }
        return null;
    }
    public Tiquete registrar_salida(String placa, LocalDateTime salida, Convenios convenio)throws FechaSalidaInvalidaException, VehiculoNoEncontradoException {
        for (Tiquete t : this.tiquetes) {
            if (t.getVehiculo().getPlaca().equals(placa) && t.isSiguedentro()) {
                if (ControladorFechas.esAntes(salida, t.getEntrada())) {
                    throw new FechaSalidaInvalidaException();
                }
                boolean mensualidad = false;
                LocalDate diaEntrada = t.getEntrada().toLocalDate();
                LocalDate diaSalida = salida.toLocalDate();
                for (Mensualidad m : Mensualidades) {
                    if (m.getPlaca().equals(placa) && m.cubre(diaEntrada) && m.cubre(diaSalida)) {
                        mensualidad = true;
                    }
                }
                t.setConvenio(convenio);
                try {
                    t.cerrartiquete(salida, mensualidad);
                } catch (Exception e) {
                    t.setConvenio(null);
                }
                return t;
            }
        }
        throw new VehiculoNoEncontradoException("El vehiculo con placa " + placa + " no fue encontrado");
    }
    public List<String> reporteCierre(LocalDate fecha){
        ArrayList<String> cierre=new ArrayList<>();
        String nombrear="Cierre ";
        String titulo=nombrear.concat(ControladorFechas.formatearFecha(fecha,"yyyy-MM-dd"));
        nombrear=titulo.concat(".txt");
        cierre.add(titulo);
        int c=0;
        int m=0;
        int E=0;
        int totalv=0;
        long totalc=0;
        long totalm=0;
        long totalE=0;
        long totalp;
        long totalr=0;
        for(Tiquete t:tiquetes){
            if(t.getSalida()!=null) {
                String sal = ControladorFechas.formatearFecha(t.getSalida(), "yyyy-MM-dd");
                LocalDate sa = ControladorFechas.convertirTextoAFecha(sal, "yyyy-MM-dd");
                if (!t.isSiguedentro() && ControladorFechas.esIgual(sa, fecha)) {
                    totalv++;
                    if (t.getVehiculo().getTipo().equals("Electrico")) {
                        E++;
                        totalE = t.getValor_de_parqueo() + totalE;
                        totalr = totalr + t.getValor_recarga();
                    } else if (t.getVehiculo().getTipo().equals("Carro")) {
                        c++;
                        totalc = totalc + t.getValor_de_parqueo();
                    } else if (t.getVehiculo().getTipo().equals("Moto")) {
                        m++;
                        totalm = totalm + t.getValor_de_parqueo();
                    }
                }
            }
        }
        totalp=totalc+totalE+totalm;
        cierre.add((String.valueOf(totalv)));
        cierre.add((String.valueOf(totalp)));
        cierre.add((String.valueOf(totalr)));
        cierre.add((String.valueOf(totalp+totalr)));
        cierre.add((String.valueOf(c)));
        cierre.add((String.valueOf(totalc)));
        cierre.add((String.valueOf(m)));
        cierre.add((String.valueOf(totalm)));
        cierre.add((String.valueOf(E)));
        cierre.add((String.valueOf(totalE)));
        Archivos.escribircierre(nombrear,cierre);
        return cierre;
    }
    public void setTiquetes(ArrayList<Tiquete> tiquetes) {
        this.tiquetes = tiquetes;
    }

    public ArrayList<Mensualidad> getMensualidades() {
        return Mensualidades;
    }

    public void setMensualidades(ArrayList<Mensualidad> mensualidades) {
        Mensualidades = mensualidades;
    }
    public void agregar_mensualidad(Mensualidad m){
        Mensualidades.add(m);
    }
    public void estarepetida (String placa) throws VehiculoYaAdentroException {
        for(Tiquete ti:this.tiquetes){
            if(ti.getVehiculo().getPlaca().equalsIgnoreCase(placa) && ti.isSiguedentro()){
                throw  new VehiculoYaAdentroException("El vehiculo con placa "+placa+" ya se encuentra dentro del parqueadero");
            }
        }
    }
    @Override
    public List<String> reportePlacasFrecuentes() {

        ArrayList<String> placas = new ArrayList<>();
        ArrayList<Integer> visitas = new ArrayList<>();
        ArrayList<Long> pagos = new ArrayList<>();

        for (Tiquete t : tiquetes) {
            if (!t.isSiguedentro()) {
                String placa = t.getVehiculo().getPlaca();
                int posicion = placas.indexOf(placa);
                if (posicion == -1) {
                    placas.add(placa);
                    visitas.add(1);
                    pagos.add(t.getTotal());
                } else {
                    visitas.set(posicion, visitas.get(posicion) + 1);
                    pagos.set(posicion, pagos.get(posicion) + t.getTotal());
                }
            }
        }
        for (int i = 0; i < placas.size() - 1; i++) {
            for (int j = 0; j < placas.size() - 1 - i; j++) {
                boolean cambiar = false;
                if (visitas.get(j) < visitas.get(j + 1)) {
                    cambiar = true;

                } else if (visitas.get(j).equals(visitas.get(j + 1))) {
                    if (pagos.get(j) < pagos.get(j + 1)) {
                        cambiar = true;

                    } else if (pagos.get(j).equals(pagos.get(j + 1)) && placas.get(j).compareTo(placas.get(j + 1)) > 0) {
                        cambiar = true;
                    }
                }
                if (cambiar) {
                    String placaAux = placas.get(j);
                    placas.set(j, placas.get(j + 1));
                    placas.set(j + 1, placaAux);
                    int visitaAux = visitas.get(j);
                    visitas.set(j, visitas.get(j + 1));
                    visitas.set(j + 1, visitaAux);

                    long pagoAux = pagos.get(j);
                    pagos.set(j, pagos.get(j + 1));
                    pagos.set(j + 1, pagoAux);
                }
            }
        }

        ArrayList<String> reporte = new ArrayList<>();

        int limite = Math.min(3, placas.size());
        for (int i = 0; i < limite; i++) {
            reporte.add("Placa: " + placas.get(i) + " | Visitas: " + visitas.get(i) + " | Total pagado: " + pagos.get(i));
        }

        return reporte;
    }
    @Override
    public ArrayList<String> reporteVehiculosAdentro() {

        ArrayList<String> abiertos = new ArrayList<>();

        for (Tiquete t : tiquetes) {
            if (t.isSiguedentro()) {
                abiertos.add(String.valueOf(t.getNumero()));
                abiertos.add(String.valueOf(t.getVehiculo().getPlaca()));
                abiertos.add(String.valueOf(t.getVehiculo().getTipo()));
            }
        }
        return  abiertos;
    }
}
