package edu.puj.Model;
//Original
import edu.puj.Excepciones.PlacaInvalidaException;

public abstract class vehiculos {
    private String placa;

    public vehiculos(String placa) {
        this.placa = placa;
    }

    public String getPlaca(){
        return this.placa;
    }
    public abstract String getTipo();

    public abstract long getPrecioporminuto();

    public boolean laplacaesvalida() throws PlacaInvalidaException {
        if (this.placa.length() != 6) {
            throw new PlacaInvalidaException("La placa es invialida ya que no tiene 6 digitos");
        }

        for (int i = 0; i < 3; i++) {

            if (!Character.isLetter(placa.charAt(i))) {
                throw new PlacaInvalidaException(
                        "Los primeros 3 caracteres deben ser letras"
                );
            }
        }

        for (int i = 3; i < 6; i++) {

            if (!Character.isDigit(placa.charAt(i))) {
                throw new PlacaInvalidaException(
                        "Los últimos 3 caracteres deben ser números"
                );
            }
        }


        return true;
    }
    public abstract long Calculartarifa(int minutos);
}
