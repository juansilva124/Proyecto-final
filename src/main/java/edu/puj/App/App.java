package edu.puj.App;

import edu.puj.Model.*;

import java.util.ArrayList;
public class App 
{

    public static void main(String[] args) {
        ArrayList<Tiquete>t=new ArrayList<Tiquete>();
        t=Archivos.Leermovimientostxt(t);
    }
}
