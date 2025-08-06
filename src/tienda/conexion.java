/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author elric
 */
public class conexion {
    Connection connection = null;
    public void conectar(){
    try{
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
           
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/tienda_amazon",
                    "postgres", "Bojorquez1965");
        }catch (java.sql.SQLException sqle) {
            System.out.println("Error: " + sqle);
       }
    }
    
    public void desconectar() throws SQLException{
        connection.close();
    }
    
    public usuarios login(String usr, String pwd) throws SQLException{
        usuarios u = new usuarios();
        ResultSet res;
        
        conectar();
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contraseña = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, usr);
        ps.setString(2, pwd);
        res = ps.executeQuery();
        if(res.next()){
            u.setPerfil(res.getString("perfil"));
        }else{
            return null;
        }
        desconectar();
        
        return u;
    }
    
    public usuarios buscarUsuario(String id) throws SQLException{
        usuarios u = new usuarios();
        ResultSet res;
        
        conectar();
        
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));
        res = ps.executeQuery();
        
        if(res.next()){
            u.setID(Integer.parseInt(res.getString("id")));
            u.setNombre(res.getString("nombre"));
            u.setAp(res.getString("apellido_paterno"));
            u.setAm(res.getString("apellido_materno"));
            u.setNombre_usuario(res.getString("nombre_usuario"));
            u.setContraseña(res.getString("contraseña"));
            u.setPerfil(res.getString("perfil"));
            u.setCalificacion(Double.valueOf(res.getString("calificacion")));
        } else{
            return null;
        }
        
        desconectar();
        
        return u;
    }
    
    public int agregarUsuario(usuarios usr) throws SQLException{
        int rA = 0;
        
        conectar();
        String sql = "INSERT INTO usuarios (nombre,apellido_paterno,apellido_materno,perfil,nombre_usuario,contraseña,calificacion) VALUES(?,?,?,?,?,?,?);";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, usr.getNombre());
        st.setString(2, usr.getAp());
        st.setString(3, usr.getAm());
        st.setString(4, usr.getPerfil());
        st.setString(5, usr.getNombre_usuario());
        st.setString(6, usr.getContraseña());
        st.setDouble(7, usr.getCalificacion());
        
        rA = st.executeUpdate();
        desconectar();
        
        return rA;
    }
    
    public int eliminarUsuario(String id) throws SQLException{
        int rA = 0;
        
        conectar();
        String sql = "Delete FROM usuarios WHERE id = ?;";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, Integer.parseInt(id));
        
        rA = st.executeUpdate();
        desconectar();
        
        return rA;
    }
    
    public int editarUsuario(usuarios usr) throws SQLException{
        int rA = 0;
        
        conectar();
        String sql = "UPDATE usuarios SET nombre=?, apellido_paterno=?, apellido_materno=?, perfil=?, nombre_usuario=?, contraseña=?, calificacion=? WHERE id = ?;";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, usr.getNombre());
        st.setString(2, usr.getAp());
        st.setString(3, usr.getAm());
        st.setString(4, usr.getPerfil());
        st.setString(5, usr.getNombre_usuario());
        st.setString(6, usr.getContraseña());
        st.setDouble(7, usr.getCalificacion());
        st.setInt(8, usr.getID());
        
        rA = st.executeUpdate();
        desconectar();
        
        return rA;
    }
    
    public ArrayList<usuarios> buscarUsuariosVendedores(String perfil) throws SQLException{
        ArrayList<usuarios> lista = new ArrayList<>();
        String sql = "SELECT id FROM usuarios WHERE perfil = ?";
        
        conectar();

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, perfil);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            usuarios us = new usuarios();
            
            us.setID(Integer.parseInt(rs.getString("id")));
            
            lista.add(us);
        }
        
        desconectar();

        return lista;
    }
    
    public usuarios buscarUsuarioContraseña(String usuario) throws SQLException{
        usuarios u = new usuarios();
        ResultSet res;
        
        conectar();
        
        String sql = "SELECT contraseña FROM usuarios WHERE nombre_usuario = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, usuario);
        res = ps.executeQuery();
        
        if(res.next()){
            u.setContraseña(res.getString("contraseña"));
        } else{
            return null;
        }
        
        desconectar();
        
        return u;
    }
    
    public productos buscarProducto(String id) throws SQLException{
        productos pr = new productos();
        ResultSet res;
        
        conectar();
        
        String sql = "SELECT * FROM productos WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));
        res = ps.executeQuery();
        
        if(res.next()){
            pr.setID(Integer.parseInt(res.getString("id")));
            pr.setDescripcion(res.getString("descripcion"));
            pr.setCategoria(res.getString("categoria"));
            pr.setPrecio(Double.valueOf(res.getString("precio")));
            pr.setCantidad(Integer.parseInt(res.getString("cantidad")));
            pr.setCantidadVendida(Integer.parseInt(res.getString("cantidad_vendida")));
            pr.setCalificacion(Double.valueOf(res.getString("calificacion")));
            pr.setVendedorID(Integer.parseInt(res.getString("id_vendedor")));
            pr.setImagen(res.getString("imagen"));
        } else{
            return null;
        }
        
        desconectar();
        
        return pr;
    }
    
    public boolean buscarProductoEnInventario(productos pr) throws SQLException{
        ResultSet res;
        
        conectar();
        
        String sql = "SELECT * FROM productos WHERE descripcion=? AND categoria=? AND precio=? AND cantidad=? AND imagen=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, pr.getDescripcion());
        ps.setString(2, pr.getCategoria());
        ps.setDouble(3, pr.getPrecio());
        ps.setInt(4, pr.getCantidad());
        ps.setString(5, pr.getImagen());
        
        res = ps.executeQuery();
        
        if(res.next()){
            return true;
        } 
        
        desconectar();
        
        return false;
    }
    
    public int agregarProducto(productos pr) throws SQLException{
        int rA = 0;
        
        conectar();
        String sql = "INSERT INTO productos (descripcion,categoria,precio,cantidad,cantidad_vendida,calificacion,id_vendedor,imagen) VALUES(?,?,?,?,?,?,?,?);";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, pr.getDescripcion());
        st.setString(2, pr.getCategoria());
        st.setDouble(3, pr.getPrecio());
        st.setInt(4, pr.getCantidad());
        st.setInt(5, pr.getCantidadVendida());
        st.setDouble(6, pr.getCalificacion());
        st.setInt(7, pr.getVendedorID());
        st.setString(8, pr.getImagen());
        
        rA = st.executeUpdate();
        desconectar();
        
        return rA;
    }
    
    public int eliminarProducto(String id) throws SQLException{
        int rA = 0;
        
        conectar();
        String sql = "Delete FROM productos WHERE id = ?;";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, Integer.parseInt(id));
        
        rA = st.executeUpdate();
        desconectar();
        
        return rA;
    }
    
    public int editarProducto(productos pr) throws SQLException{
        int rA = 0;
        
        conectar();
        String sql = "UPDATE productos SET descripcion=?, categoria=?, precio=?, cantidad=?, cantidad_vendida=?, calificacion=?, id_vendedor=?, imagen=? WHERE id = ?;";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, pr.getDescripcion());
        st.setString(2, pr.getCategoria());
        st.setDouble(3, pr.getPrecio());
        st.setInt(4, pr.getCantidad());
        st.setInt(5, pr.getCantidadVendida());
        st.setDouble(6, pr.getCalificacion());
        st.setInt(7, pr.getVendedorID());
        st.setString(8, pr.getImagen());
        st.setInt(9, pr.getID());
        
        rA = st.executeUpdate();
        desconectar();
        
        return rA;
    }
    
    public ArrayList<productos> buscarTodosProductos() throws SQLException{
        
        ArrayList<productos> lista = new ArrayList<>();
        ResultSet res;
        
        conectar();
        
        String sql = "SELECT * FROM productos";
        PreparedStatement ps = connection.prepareStatement(sql);
        res = ps.executeQuery();
        
        while(res.next()){
            productos pr = new productos();
            
            pr.setID(Integer.parseInt(res.getString("id")));
            pr.setDescripcion(res.getString("descripcion"));
            pr.setCategoria(res.getString("categoria"));
            pr.setPrecio(Double.valueOf(res.getString("precio")));
            pr.setCantidad(Integer.parseInt(res.getString("cantidad")));
            pr.setCantidadVendida(Integer.parseInt(res.getString("cantidad_vendida")));
            pr.setCalificacion(Double.valueOf(res.getString("calificacion")));
            pr.setVendedorID(Integer.parseInt(res.getString("id_vendedor")));
            pr.setImagen(res.getString("imagen"));
            
            lista.add(pr);
        } 
        
        desconectar();
        
        return lista;
    }
    
    public ArrayList<productos> buscarproductosCategoria(String cat) throws SQLException{
        
        ArrayList<productos> lista = new ArrayList<>();
        ResultSet res;
        
        conectar();
        
        String sql = "SELECT * FROM productos WHERE categoria = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, cat);
        res = ps.executeQuery();
        
        while(res.next()){
            productos pr = new productos();
            
            pr.setID(Integer.parseInt(res.getString("id")));
            pr.setDescripcion(res.getString("descripcion"));
            pr.setCategoria(res.getString("categoria"));
            pr.setPrecio(Double.valueOf(res.getString("precio")));
            pr.setCantidad(Integer.parseInt(res.getString("cantidad")));
            pr.setCantidadVendida(Integer.parseInt(res.getString("cantidad_vendida")));
            pr.setCalificacion(Double.valueOf(res.getString("calificacion")));
            pr.setVendedorID(Integer.parseInt(res.getString("id_vendedor")));
            pr.setImagen(res.getString("imagen"));
            
            lista.add(pr);
        } 
        
        desconectar();
        
        return lista;
    }
    
}