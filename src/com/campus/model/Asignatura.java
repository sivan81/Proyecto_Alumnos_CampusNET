package com.campus.model;

import java.util.ArrayList;
import java.util.List;

public class Asignatura {
	
	String id;
    List<Alumno> alumnos = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    //Devuelve el nodo asignatura pero con todos los alumnos dentro
    @Override
    public String toString() {
        String resultado = "<asignatura id='" + id + "'>\n";
        for (Alumno alumno: alumnos) {
            resultado += alumno.toString();
        }
        resultado += "</asignatura>\n";

        return resultado;
    }
}
