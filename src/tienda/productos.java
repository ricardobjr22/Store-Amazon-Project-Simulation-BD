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
public class productos {
    private int ID;
    private int vendedorID;
    private String descripcion;
    private String categoria;
    private Double precio;
    private int cantidad;
    private int cantidadVendida;
    private Double calificacion;
    private String imagen;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double Precio) {
        this.precio = Precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.cantidad = Cantidad;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }
    
    public String toString(){
        String result;
        result = String.valueOf(ID) + "|" + String.valueOf(vendedorID) + "|" + descripcion + "|" + categoria + "|" + String.valueOf(precio) + "|" + cantidad + "|" +  cantidadVendida + "|" + calificacion + "|" +  imagen + "|" +"\n";
        return result;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    public boolean quitarExistencia(int existence){
        if(existence <= this.cantidad){
            this.cantidad = cantidad - existence;
            return true;
        }else{
            JOptionPane.showMessageDialog(null,"No hay suficientes articulos");
        }
        return false;
    }
    
    public void añadirCantidad(int existence){
        this.cantidadVendida = cantidadVendida + existence;
    }
    
    public void actualizarCalificacion(int existence){
        this.calificacion = (this.calificacion + existence)/2;
    }

    public int getVendedorID() {
        return vendedorID;
    }

    public void setVendedorID(int vendedorID) {
        this.vendedorID = vendedorID;
    }
    
}
