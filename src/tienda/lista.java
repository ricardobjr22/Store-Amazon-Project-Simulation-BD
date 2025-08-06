/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda;

import java.util.ArrayList;

/**
 *
 * @author elric
 */
public class lista {
    private ArrayList<productos> list_pr;
    private int index, posicion;
    
    public lista(ArrayList<productos> pr){
        this.list_pr = pr;
       
    }
    
    public ArrayList<productos> ActualizarPr(){
        return this.list_pr;
    }
    
    public void AgregarPr(productos p){
      
        this.list_pr.add(p);
    }
    
    public productos BuscarPr(String id){
        productos ob=new productos();
        this.index=0;
        for(productos x: this.list_pr){
            if(id.equals(x.getID())){
                ob.setID(x.getID());
                ob.setDescripcion(x.getDescripcion());
                ob.setCategoria(x.getCategoria());
                ob.setPrecio(x.getPrecio());
                ob.setCantidad(x.getCantidad());
                ob.setCantidadVendida(x.getCantidadVendida());
                ob.setCalificacion(x.getCalificacion());
                ob.setImagen(x.getImagen());
                this.posicion=this.index;
            }
            this.index++;
        }
    return ob;
    }
    
    public int getPosicionPr(){
        return this.posicion;
    }
    
    public void RemoverPr(int index){
        this.list_pr.remove(index);
    }
    
    public productos getProducto(int indice){
        return this.list_pr.get(indice);
    }
    
    public int getTamaño_pr(){
        return this.list_pr.size();
    }
    
    public void EditarPr(productos producto,int index){
        RemoverPr(index);
        this.list_pr.add(index, producto);
    }
}
