package com.campus.model;

import java.util.ArrayList;
import java.util.List;

public class Credenciales {

    List<Credencial> credenciales = new ArrayList<>();

    public List<Credencial> getCredenciales() {
        return credenciales;
    }

    public void setCredenciales(List<Credencial> credenciales) {
        this.credenciales = credenciales;
    }

    @Override
    public String toString() {
        String resultado = "<credenciales>";
        for (Credencial credencial: credenciales) {
            resultado = resultado +  credencial.toString();
        }
        resultado = resultado + "</credenciales>";
        return resultado;
    }
}
