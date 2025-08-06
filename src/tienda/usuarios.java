/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda;

import javax.swing.JOptionPane;

/**
 *
 * @author elric
 */
public class usuarios {
    private int ID;
    private String nombre;
    private String ap;
    private String am;
    private String perfil;
    private String nombre_usuario;
    private String contraseña;
    private Double calificacion;

    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public String getAm() {
        return am;
    }

    public void setAm(String am) {
        this.am = am;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    public String toString(){
        String result;
        result = String.valueOf(ID) + "|" + nombre + "|" + ap + "|" + am + "|" + perfil + "|" +  nombre_usuario + "|" + contraseña + "|" + calificacion + "|" + "\n";
        return result;
    }
    
    public void actualizarCalificacion(int existence){
        this.calificacion = (this.calificacion + existence)/2;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }
}
