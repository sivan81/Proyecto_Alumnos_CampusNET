package com.campus.model;

public class Alumno {
	
	String nombre;
    String apellidos;
    Integer nota;

    public Alumno() {
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getNota() {
        return this.nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    //Convierte el objeto en un nodo con el siguiente formato
    public String toString() {
        String resultado = "<alumno>\n";
        resultado = resultado + "<nombre>" + this.nombre + "</nombre>\n";
        resultado = resultado + "<apellidos>" + this.apellidos + "</apellidos>\n";
        if (this.nota != null) {
            resultado = resultado + "<nota>" + this.nota + "</nota>\n";
        }

        resultado = resultado + "</alumno>\n";
        return resultado;
    }

}
