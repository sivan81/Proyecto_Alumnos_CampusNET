package com.campus.model;

public class Credencial {
    String usuario;
    String password;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "<credencial><usuario>" + usuario + "</usuario><password>" + password + "</passwors></credencial>";
    }
}
