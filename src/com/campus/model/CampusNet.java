package com.campus.model;

import java.util.ArrayList;
import java.util.List;

public class CampusNet {
	//Como indica el enunciado, los alumnos pueden estar en una, ninguna o varias asignaturas,
    // pero no dice como mostrar los alumnos que no están en ninguna asignatura, con lo que
    // entiendo que se debe tener al mismo nivel de las asignaturas, los alumnos que no estén
    // dentro de esas asignaturas, por lo tanto, creo un atributo que es una lista de alumnos
    // y en el método toString para que recorra las asignaturas y las convierta en en <alumno>...</alumno>

    List<Asignatura> asignaturas = new ArrayList<>();
    List<Alumno> alumnos = new ArrayList<>();
    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    @Override
    public String toString() {
        String resultado = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        //Despues de crear el DTD en RESOURCES, lo añadimos a la cabecera del campus.xml
        resultado += "<!DOCTYPE campusNet SYSTEM \"campus.dtd\">\n"; 
        resultado += "<campusNet>\n";
        //Asignaturas
        for (Asignatura asignatura: asignaturas) {
            resultado += asignatura.toString();
        }
        //Alumnos que no estan en asignaturas
        for (Alumno alumno: alumnos) {
            resultado += alumno.toString();
        }
        resultado += "</campusNet>";

        return resultado ;
    }
}
