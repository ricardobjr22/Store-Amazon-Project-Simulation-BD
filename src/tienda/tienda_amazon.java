/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author elric
 */
public class tienda_amazon extends javax.swing.JFrame {
    conexion conn;
    usuarios u;
    productos pr;
    productos pr2;
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel modelo2 = new DefaultTableModel();
    String pathImage;
    
    ArrayList<productos> list;
    lista lst;
    
    public tienda_amazon() throws IOException {
        initComponents();
        btnGuardarCambios_us.setVisible(false);
        btnGuardarCambios_pr.setVisible(false);
        jTabbedPane2.setEnabledAt(jTabbedPane2.indexOfComponent(jPanel3),false);
        jTabbedPane2.setEnabledAt(jTabbedPane2.indexOfComponent(jPanel4),false);
        jTabbedPane1.setEnabledAt(jTabbedPane1.indexOfComponent(jPanel5),false);
        jTabbedPane1.setEnabledAt(jTabbedPane1.indexOfComponent(jPanel8),false);
        jTabbedPane1.setEnabledAt(jTabbedPane1.indexOfComponent(jPanel6),false);
        jTabbedPane4.setEnabledAt(jTabbedPane4.indexOfComponent(jPanel9),false);
        jTable1.setDefaultRenderer(Object.class, new tableImagenes());
        String[]titulo = new String[]{"Producto ID","Descripcion","Precio","Cantidad","Vendidos","Calificacion", "Imagen"};
        modelo.setColumnIdentifiers(titulo);
        jTable1.setRowHeight(100);
        jTable1.setModel(modelo);
        jTable2.setDefaultRenderer(Object.class, new tableImagenes());
        String[]titulo2 = new String[]{"Producto ID","Descripcion","Precio","Cantidad", "Imagen"};
        modelo2.setColumnIdentifiers(titulo2);
        jTable2.setRowHeight(100);
        jTable2.setModel(modelo2);
        jInternalFrame1.setVisible(false);
        
        lblListoSalir.setBackground(Color.WHITE);
            lblEnviado.setBackground(Color.WHITE);
            lblEnCamino.setBackground(Color.WHITE);
            lblEntregado.setBackground(Color.WHITE);
        
        list = new ArrayList<productos>();
        
        lst = new lista(list);
        
    }
    
    public void Habilitar_us(){
        txtID_us.setEnabled(false);
        txtNombre_us.setEnabled(true);
        txtAp_us.setEnabled(true);
        txtAm_us.setEnabled(true);
        cbPerfil_us.setEnabled(true);
        txtNombreUsuario_us.setEnabled(true);
        txtContraseña_us.setEnabled(true);
        txtCalificacion_us.setEnabled(true);
    }
    
    public void Deshabilitar_us(){
        txtID_us.setEnabled(false);
        txtNombre_us.setEnabled(false);
        txtAp_us.setEnabled(false);
        txtAm_us.setEnabled(false);
        cbPerfil_us.setEnabled(false);
        txtNombreUsuario_us.setEnabled(false);
        txtContraseña_us.setEnabled(false);
        txtCalificacion_us.setEnabled(false);
    }
    
    public void Habilitar_pr(){
        txtID_pr.setEnabled(false);
        cbVendedorID.setEnabled(true);
        txtDescripcion.setEnabled(true);
        txtPrecio.setEnabled(true);
        txtCantidad_pr.setEnabled(true);
        cbCategoria_pr.setEnabled(true);
        txtVendidos.setEnabled(true);
        txtCalificacion.setEnabled(true);
    }
    
    public void Deshabilitar_pr(){
        txtID_pr.setEnabled(false);
        cbVendedorID.setEnabled(false);
        txtDescripcion.setEnabled(false);
        txtPrecio.setEnabled(false);
        txtCantidad_pr.setEnabled(false);
        cbCategoria_pr.setEnabled(false);
        txtVendidos.setEnabled(false);
        txtCalificacion.setEnabled(false);
    }
    
    public boolean comprobarTxt_us(){
        if(txtNombre_us.getText().equals("")){
            return false;
        }else if(txtAp_us.getText().equals("")){
            return false;
        }else if(txtAm_us.getText().equals("")){
            return false;
        }else if(cbPerfil_us.getSelectedItem().toString().equals("...")){
            return false;
        }else if(txtNombreUsuario_us.getText().equals("")){
            return false;
        }else if(txtContraseña_us.getText().equals("")){
            return false;
        }else if(txtCalificacion_us.getText().equals("")){
            return false;
        }
        
        return true;
    }
    
    public boolean comprobarTxt_pr(){
        if(txtDescripcion.getText().equals("")){
            return false;
        }else if(txtPrecio.getText().equals("")){
            return false;
        }else if(txtCantidad_pr.getText().equals("")){
            return false;
        }else if(cbCategoria_pr.getSelectedItem().toString().equals("...")){
            return false;
        }else if(txtVendidos.getText().equals("")){
            return false;
        }else if(txtCalificacion.getText().equals("")){
            return false;
        }else if(lblImagen.getIcon() == null){
            return false;
        }else if(cbVendedorID.getSelectedItem().toString().equals("...")){
            return false;
        }
        
        return true;
    }
    
    public usuarios buscarContraseña(String us) throws SQLException{
            u = new usuarios();
            conn = new conexion();
            
            u = conn.buscarUsuarioContraseña(us);
            
            return u;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUsuarios_lg = new javax.swing.JTextField();
        txtPassword_lg = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        btnRecupera = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtBuscar_us = new javax.swing.JTextField();
        btnBuscar_us = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtID_us = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNombre_us = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtAp_us = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtAm_us = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cbPerfil_us = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        txtNombreUsuario_us = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtContraseña_us = new javax.swing.JTextField();
        btnNuevo_us = new javax.swing.JButton();
        btnCancelar_us = new javax.swing.JButton();
        btnEliminar_us = new javax.swing.JButton();
        btnSalvar_us = new javax.swing.JButton();
        btnEditar_us = new javax.swing.JButton();
        btnGuardarCambios_us = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        txtCalificacion_us = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtUsuario_ps = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtEmail_ps = new javax.swing.JTextField();
        btnRecuperarContraseña = new javax.swing.JButton();
        btnSalir_ps = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        btnBuscar_pr = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtID_pr = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        cbCategoria_pr = new javax.swing.JComboBox();
        txtCantidad_pr = new javax.swing.JTextField();
        txtCalificacion = new javax.swing.JTextField();
        txtVendidos = new javax.swing.JTextField();
        lblImagen = new javax.swing.JLabel();
        btnQuitarFoto = new javax.swing.JButton();
        btnSalvar_pr = new javax.swing.JButton();
        btnCancelar_pr = new javax.swing.JButton();
        btnEliminar_pr = new javax.swing.JButton();
        btnEditar_pr = new javax.swing.JButton();
        btnGuardarCambios_pr = new javax.swing.JButton();
        btnNuevo_pr = new javax.swing.JButton();
        btnAgregarFoto = new javax.swing.JButton();
        javax.swing.JLabel lblVendedorID = new javax.swing.JLabel();
        cbVendedorID = new javax.swing.JComboBox();
        txtBuscar_pr = new javax.swing.JSpinner();
        jPanel8 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        cbCategoria_cp = new javax.swing.JComboBox();
        btnPonerCarrito = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        txtCantidad_cp = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btnQuitarCarrito = new javax.swing.JButton();
        btnComprar = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtPropietario = new javax.swing.JTextField();
        txtTarjeta = new javax.swing.JTextField();
        cbBanco = new javax.swing.JComboBox();
        dateCaducidad = new com.toedter.calendar.JDateChooser();
        txtCVV = new javax.swing.JTextField();
        btnConfirmarCompra = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jLabel24 = new javax.swing.JLabel();
        cbCalf = new javax.swing.JComboBox();
        btnConfirnCalf = new javax.swing.JButton();
        lblListoSalir = new javax.swing.JLabel();
        lblEnviado = new javax.swing.JLabel();
        lblEnCamino = new javax.swing.JLabel();
        lblEntregado = new javax.swing.JLabel();
        btnListoSalir = new javax.swing.JButton();
        btnEnviado = new javax.swing.JButton();
        btnCamino = new javax.swing.JButton();
        btnEntregado = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registro usuarios");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Usuario:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Contraseña:");

        txtUsuarios_lg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarios_lgActionPerformed(evt);
            }
        });

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnRecupera.setText("Recupera Contraseña");
        btnRecupera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecuperaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(249, 249, 249)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPassword_lg))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtUsuarios_lg, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(90, 90, 90)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                    .addComponent(btnRecupera, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(121, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsuarios_lg, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassword_lg, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(49, 49, 49)
                .addComponent(btnRecupera, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(112, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Login", jPanel2);

        jLabel3.setText("Buscar ID:");

        btnBuscar_us.setText("Buscar");
        btnBuscar_us.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscar_usActionPerformed(evt);
            }
        });

        jLabel4.setText("ID:");

        txtID_us.setEnabled(false);

        jLabel5.setText("Nombre:");

        txtNombre_us.setEnabled(false);

        jLabel6.setText("Apellido Paterno:");

        txtAp_us.setEnabled(false);

        jLabel7.setText("Apellido Materno:");

        txtAm_us.setEnabled(false);

        jLabel8.setText("Perfil:");

        cbPerfil_us.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "...", "Admin", "Vendedor", "Comprador" }));
        cbPerfil_us.setEnabled(false);
        cbPerfil_us.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPerfil_usActionPerformed(evt);
            }
        });

        jLabel9.setText("Nombre Usuario:");

        txtNombreUsuario_us.setEnabled(false);

        jLabel10.setText("Contraseña:");

        txtContraseña_us.setEnabled(false);

        btnNuevo_us.setText("Nuevo");
        btnNuevo_us.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo_usActionPerformed(evt);
            }
        });

        btnCancelar_us.setText("Cancelar");
        btnCancelar_us.setEnabled(false);
        btnCancelar_us.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar_usActionPerformed(evt);
            }
        });

        btnEliminar_us.setText("Eliminar");
        btnEliminar_us.setEnabled(false);
        btnEliminar_us.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar_usActionPerformed(evt);
            }
        });

        btnSalvar_us.setText("Salvar");
        btnSalvar_us.setEnabled(false);
        btnSalvar_us.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvar_usActionPerformed(evt);
            }
        });

        btnEditar_us.setText("Editar");
        btnEditar_us.setEnabled(false);
        btnEditar_us.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditar_usActionPerformed(evt);
            }
        });

        btnGuardarCambios_us.setText("Guardar Cambios");
        btnGuardarCambios_us.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCambios_usActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel25.setText("Calificacion:");

        txtCalificacion_us.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(btnNuevo_us, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(btnSalvar_us, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(btnCancelar_us, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(btnEliminar_us, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(btnEditar_us, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbPerfil_us, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtAm_us, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtNombre_us, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtID_us, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtAp_us, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(122, 122, 122)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBuscar_us, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(btnBuscar_us, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNombreUsuario_us, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtContraseña_us, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnGuardarCambios_us, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCalificacion_us, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar_us, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnBuscar_us, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtID_us, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtNombreUsuario_us, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNombre_us, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtAp_us, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtContraseña_us, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtAm_us, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(txtCalificacion_us, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(cbPerfil_us, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnGuardarCambios_us, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo_us, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar_us, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar_us, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar_us, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar_us, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        jTabbedPane2.addTab("Registro usuarios", jPanel3);

        jLabel20.setText("Usuario:");

        jLabel21.setText("Correo electronico:");

        btnRecuperarContraseña.setText("Recuperar contraseña");
        btnRecuperarContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecuperarContraseñaActionPerformed(evt);
            }
        });

        btnSalir_ps.setText("Salir");
        btnSalir_ps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir_psActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsuario_ps, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail_ps, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(351, 351, 351)
                        .addComponent(btnRecuperarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(btnSalir_ps, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(357, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnSalir_ps, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtUsuario_ps, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtEmail_ps, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(btnRecuperarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(201, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Recupera contraseña", jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        jTabbedPane1.addTab("Login", jPanel1);

        jLabel11.setText("Buscar ID:");

        btnBuscar_pr.setText("Buscar");
        btnBuscar_pr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscar_prActionPerformed(evt);
            }
        });

        jLabel12.setText("Producto ID:");

        jLabel13.setText("Descripcion:");

        jLabel14.setText("Categoria:");

        jLabel15.setText("Precio:");

        jLabel16.setText("Cantidad:");

        jLabel17.setText("Cantidad Vendida:");

        jLabel18.setText("Calificacion:");

        txtID_pr.setEnabled(false);

        txtPrecio.setEnabled(false);

        txtDescripcion.setEnabled(false);

        cbCategoria_pr.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "...", "Tecnologia", "Calzado", "Accesorios", " " }));
        cbCategoria_pr.setEnabled(false);

        txtCantidad_pr.setEnabled(false);

        txtCalificacion.setEnabled(false);

        txtVendidos.setEnabled(false);

        btnQuitarFoto.setText("Quitar foto");
        btnQuitarFoto.setEnabled(false);
        btnQuitarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarFotoActionPerformed(evt);
            }
        });

        btnSalvar_pr.setText("Salvar");
        btnSalvar_pr.setEnabled(false);
        btnSalvar_pr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvar_prActionPerformed(evt);
            }
        });

        btnCancelar_pr.setText("Cancelar");
        btnCancelar_pr.setEnabled(false);
        btnCancelar_pr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar_prActionPerformed(evt);
            }
        });

        btnEliminar_pr.setText("Eliminar");
        btnEliminar_pr.setEnabled(false);
        btnEliminar_pr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar_prActionPerformed(evt);
            }
        });

        btnEditar_pr.setText("Editar");
        btnEditar_pr.setEnabled(false);
        btnEditar_pr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditar_prActionPerformed(evt);
            }
        });

        btnGuardarCambios_pr.setText("Guardar Cambios");
        btnGuardarCambios_pr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCambios_prActionPerformed(evt);
            }
        });

        btnNuevo_pr.setText("Nuevo");
        btnNuevo_pr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo_prActionPerformed(evt);
            }
        });

        btnAgregarFoto.setText("Agregar foto");
        btnAgregarFoto.setEnabled(false);
        btnAgregarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarFotoActionPerformed(evt);
            }
        });

        lblVendedorID.setText("Vendedor ID:");

        cbVendedorID.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "...", " ", " " }));
        cbVendedorID.setEnabled(false);

        txtBuscar_pr.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtID_pr, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11)
                                .addGap(27, 27, 27)
                                .addComponent(txtBuscar_pr, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(lblVendedorID)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbVendedorID, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(btnBuscar_pr, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCalificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnQuitarFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(btnAgregarFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCantidad_pr, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbCategoria_pr, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(69, 69, 69)
                        .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 90, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(btnNuevo_pr, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSalvar_pr, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar_pr, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminar_pr, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditar_pr, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(btnGuardarCambios_pr, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtVendidos, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(btnBuscar_pr, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBuscar_pr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblVendedorID)
                            .addComponent(cbVendedorID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtID_pr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(cbCategoria_pr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtCantidad_pr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAgregarFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnQuitarFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtVendidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtCalificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar_pr, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar_pr, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar_pr, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar_pr, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo_pr, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarCambios_pr, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        jTabbedPane3.addTab("Subir Productos", jPanel7);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );

        jTabbedPane1.addTab("Ventas", jPanel5);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel22.setText("Categoria:");

        cbCategoria_cp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos", "Tecnologia", "Calzado", "Accesorios", " " }));
        cbCategoria_cp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoria_cpActionPerformed(evt);
            }
        });

        btnPonerCarrito.setText("Poner en el carrito");
        btnPonerCarrito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPonerCarritoActionPerformed(evt);
            }
        });

        jLabel23.setText("Cantidad:");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCategoria_cp, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCantidad_cp, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnPonerCarrito, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(cbCategoria_cp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(txtCantidad_cp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(btnPonerCarrito, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane4.addTab("Compras", jPanel10);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        btnQuitarCarrito.setText("Quitar del carrito");
        btnQuitarCarrito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarCarritoActionPerformed(evt);
            }
        });

        btnComprar.setText("Comprar Ya");
        btnComprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnQuitarCarrito, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(btnComprar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnQuitarCarrito, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(btnComprar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("Carrito", jPanel11);

        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setText("Nombre del propietario:");
        jPanel9.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 58, -1, -1));

        jLabel28.setText("Numero de tarjeta:");
        jPanel9.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 138, -1, -1));

        jLabel29.setText("Banco:");
        jPanel9.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(269, 218, -1, -1));

        jLabel30.setText("Fecha de Caducidad:");
        jPanel9.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 303, -1, -1));

        jLabel31.setText("CVV:");
        jPanel9.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 376, -1, -1));
        jPanel9.add(txtPropietario, new org.netbeans.lib.awtextra.AbsoluteConstraints(316, 55, 234, -1));
        jPanel9.add(txtTarjeta, new org.netbeans.lib.awtextra.AbsoluteConstraints(316, 135, 331, -1));

        cbBanco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "...", "Santander", "BBVA", "BanBajio", "BancoAzteca" }));
        jPanel9.add(cbBanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(316, 215, 201, -1));
        jPanel9.add(dateCaducidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(316, 303, 185, -1));
        jPanel9.add(txtCVV, new org.netbeans.lib.awtextra.AbsoluteConstraints(316, 373, 201, -1));

        btnConfirmarCompra.setText("Confirmar Compra");
        btnConfirmarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarCompraActionPerformed(evt);
            }
        });
        jPanel9.add(btnConfirmarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 250, 159, 49));

        jTabbedPane4.addTab("Pagar", jPanel9);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4)
        );

        jTabbedPane1.addTab("Compras", jPanel8);

        jPanel6.setAlignmentX(0.0F);
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jInternalFrame1.setTitle("Calificacion");
        jInternalFrame1.setOpaque(true);
        jInternalFrame1.setVisible(true);

        jLabel24.setText("Califica al vendedor:");
        jLabel24.setToolTipText("");

        cbCalf.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5" }));
        cbCalf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCalfActionPerformed(evt);
            }
        });

        btnConfirnCalf.setText("Confirmar");
        btnConfirnCalf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirnCalfActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbCalf, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)))
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addComponent(btnConfirnCalf, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(160, Short.MAX_VALUE))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel24)
                .addGap(56, 56, 56)
                .addComponent(cbCalf, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(btnConfirnCalf, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jPanel6.add(jInternalFrame1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 510, 350));

        lblListoSalir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblListoSalir.setText("Listo para salir");
        lblListoSalir.setAlignmentX(0.5F);
        lblListoSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblListoSalir.setOpaque(true);
        jPanel6.add(lblListoSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 220, 117, 38));

        lblEnviado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEnviado.setText("Enviado");
        lblEnviado.setAlignmentX(1.0F);
        lblEnviado.setAlignmentY(1.0F);
        lblEnviado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblEnviado.setOpaque(true);
        jPanel6.add(lblEnviado, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 220, 103, 38));

        lblEnCamino.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEnCamino.setText("En camino");
        lblEnCamino.setAlignmentX(1.0F);
        lblEnCamino.setAlignmentY(1.0F);
        lblEnCamino.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblEnCamino.setOpaque(true);
        jPanel6.add(lblEnCamino, new org.netbeans.lib.awtextra.AbsoluteConstraints(515, 220, 103, 38));

        lblEntregado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEntregado.setText("Entregado");
        lblEntregado.setAlignmentX(1.0F);
        lblEntregado.setAlignmentY(1.0F);
        lblEntregado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblEntregado.setOpaque(true);
        jPanel6.add(lblEntregado, new org.netbeans.lib.awtextra.AbsoluteConstraints(731, 220, 103, 38));

        btnListoSalir.setText("Confirmar Listo para salir");
        btnListoSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListoSalirActionPerformed(evt);
            }
        });
        jPanel6.add(btnListoSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 39, 203, 41));

        btnEnviado.setText("Confirmar Enviado");
        btnEnviado.setEnabled(false);
        btnEnviado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviadoActionPerformed(evt);
            }
        });
        jPanel6.add(btnEnviado, new org.netbeans.lib.awtextra.AbsoluteConstraints(328, 39, 173, 41));

        btnCamino.setText("Confirmar En Camino");
        btnCamino.setEnabled(false);
        btnCamino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaminoActionPerformed(evt);
            }
        });
        jPanel6.add(btnCamino, new org.netbeans.lib.awtextra.AbsoluteConstraints(564, 39, 174, 41));

        btnEntregado.setText("Confirmar Entrega");
        btnEntregado.setEnabled(false);
        btnEntregado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntregadoActionPerformed(evt);
            }
        });
        jPanel6.add(btnEntregado, new org.netbeans.lib.awtextra.AbsoluteConstraints(371, 324, 162, 48));

        jTabbedPane1.addTab("Tracking", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsuarios_lgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarios_lgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarios_lgActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        try {
            String usuario,pwd;
            conn = new conexion();
            usuario= txtUsuarios_lg.getText();
            pwd= txtPassword_lg.getText();

            u = new usuarios();
            
            u = conn.login(usuario, pwd);
            
            if(u == null){
                JOptionPane.showMessageDialog(null, "No hay acceso");
                return;
            } 
                
            if(String.valueOf(u.getPerfil()).equals("Admin")){
                jTabbedPane1.setEnabledAt(jTabbedPane1.indexOfComponent(jPanel5),true);
                jTabbedPane1.setEnabledAt(jTabbedPane1.indexOfComponent(jPanel8),true);
                jTabbedPane1.setEnabledAt(jTabbedPane1.indexOfComponent(jPanel6),true);
                
            }else if(String.valueOf(u.getPerfil()).equals("Vendedor")){
                jTabbedPane1.setEnabledAt(jTabbedPane1.indexOfComponent(jPanel5),true);
                jTabbedPane1.setEnabledAt(jTabbedPane1.indexOfComponent(jPanel8),false);
                jTabbedPane1.setEnabledAt(jTabbedPane1.indexOfComponent(jPanel6),true);
                btnEntregado.setVisible(false);
                btnListoSalir.setVisible(true);
                btnEnviado.setVisible(true);
                btnCamino.setVisible(true);
                if(lblListoSalir.getBackground()==Color.GREEN){
                    btnEnviado.setEnabled(true);
                }
                if(lblEnviado.getBackground()==Color.GREEN){
                    btnCamino.setEnabled(true);
                }
                
            }else if(String.valueOf(u.getPerfil()).equals("Comprador")){
                jTabbedPane1.setEnabledAt(jTabbedPane1.indexOfComponent(jPanel5),false);
                jTabbedPane1.setEnabledAt(jTabbedPane1.indexOfComponent(jPanel8),true);
                jTabbedPane1.setEnabledAt(jTabbedPane1.indexOfComponent(jPanel6),true);
                btnEntregado.setVisible(true);
                btnListoSalir.setVisible(false);
                btnEnviado.setVisible(false);
                btnCamino.setVisible(false);
                
                if(lblEntregado.getBackground()==Color.GREEN){
                    btnEntregado.setEnabled(true);
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "No hay acceso");
            }
        } catch (SQLException ex) {
            Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnBuscar_usActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscar_usActionPerformed
        try{
            conn = new conexion();
            u = new usuarios();
            
            if (txtBuscar_us.getText() == null) {
                JOptionPane.showMessageDialog(null, "Ingresa un ID válido.");
                return;
            } 
            
            int element = Integer.parseInt(txtBuscar_us.getText());
            u = conn.buscarUsuario(String.valueOf(element));
            
            if(u != null){
                txtID_us.setEnabled(true);
                txtID_us.setText(String.valueOf(u.getID()));
                txtID_us.setEnabled(false);
                txtNombre_us.setText(u.getNombre());
                txtAp_us.setText(u.getAp());
                txtAm_us.setText(u.getAm());
                cbPerfil_us.setSelectedItem(u.getPerfil());
                txtNombreUsuario_us.setText(u.getNombre_usuario());
                txtContraseña_us.setText(u.getContraseña());
                txtCalificacion_us.setText(String.valueOf(u.getCalificacion()));
                btnEliminar_us.setEnabled(true);
                btnEditar_us.setEnabled(true);
                btnCancelar_us.setEnabled(true);
                btnNuevo_us.setEnabled(false);
            }else{
                JOptionPane.showMessageDialog(null,"No existe el elemento...");
            }
        } catch (SQLException ex) {
            Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscar_usActionPerformed

    private void btnNuevo_usActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo_usActionPerformed
        btnNuevo_us.setEnabled(false);
        btnSalvar_us.setEnabled(true);
        btnCancelar_us.setEnabled(true);
        Habilitar_us();
    }//GEN-LAST:event_btnNuevo_usActionPerformed

    private void btnSalvar_usActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvar_usActionPerformed
        u = new usuarios();
        conn = new conexion();
        
        if(comprobarTxt_us()){
            txtID_us.setEnabled(false);
            u.setNombre(txtNombre_us.getText());
            u.setAp(txtAp_us.getText());
            u.setAm(txtAm_us.getText());
            u.setPerfil(cbPerfil_us.getSelectedItem().toString());
            u.setNombre_usuario(txtNombreUsuario_us.getText());
            u.setContraseña(txtContraseña_us.getText());
            u.setCalificacion(Double.parseDouble(txtCalificacion_us.getText()));
        }else{
            JOptionPane.showMessageDialog(null, "Los espacios de informacion no estan llenos en su totalidad");
            return;
        }
        
        try {
            if(conn.agregarUsuario(u)>0){
                JOptionPane.showMessageDialog(null, "Usuario agregado correctamente");
            }else{
                JOptionPane.showMessageDialog(null, "Error: No fue posible agregarla");
                return;
            }
        } catch (SQLException ex) {
        }
        
        txtNombre_us.setText("");
        txtAp_us.setText("");
        txtAm_us.setText("");
        cbPerfil_us.setSelectedItem("...");
        txtNombreUsuario_us.setText("");
        txtContraseña_us.setText("");
        txtCalificacion_us.setText("");
        btnEditar_us.setEnabled(false);
        btnEliminar_us.setEnabled(false);
        btnCancelar_us.setEnabled(false);
        btnSalvar_us.setEnabled(false);
        btnNuevo_us.setEnabled(true);
        Deshabilitar_us();
    }//GEN-LAST:event_btnSalvar_usActionPerformed

    private void btnCancelar_usActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar_usActionPerformed
        Deshabilitar_us();
        btnNuevo_us.setEnabled(true);
        btnSalvar_us.setEnabled(false);
        btnCancelar_us.setEnabled(false);
        btnEliminar_us.setEnabled(false);
        btnEditar_us.setEnabled(false);
        btnGuardarCambios_us.setVisible(false);
        txtNombre_us.setText("");
        txtAp_us.setText("");
        txtAm_us.setText("");
        cbPerfil_us.setSelectedItem("...");
        txtNombreUsuario_us.setText("");
        txtContraseña_us.setText("");
        txtCalificacion_us.setText("");
    }//GEN-LAST:event_btnCancelar_usActionPerformed

    private void btnEliminar_usActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar_usActionPerformed
        txtID_us.setEnabled(true);
        conn = new conexion();
        
        try {
            conn.eliminarUsuario(txtID_us.getText());
        } catch (SQLException ex) {
            Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        txtID_us.setEnabled(false);
        txtNombre_us.setText("");
        txtAp_us.setText("");
        txtAm_us.setText("");
        cbPerfil_us.setSelectedItem("...");
        txtNombreUsuario_us.setText("");
        txtContraseña_us.setText("");
        txtCalificacion_us.setText("");
        btnEditar_us.setEnabled(false);
        btnEliminar_us.setEnabled(false);
        btnCancelar_us.setEnabled(false);
        btnNuevo_us.setEnabled(true);
    }//GEN-LAST:event_btnEliminar_usActionPerformed

    private void btnEditar_usActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditar_usActionPerformed
        btnGuardarCambios_us.setVisible(true);
        btnEditar_us.setEnabled(false);
        btnEliminar_us.setEnabled(false);
        Habilitar_us();
    }//GEN-LAST:event_btnEditar_usActionPerformed

    private void btnGuardarCambios_usActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCambios_usActionPerformed
        u = new usuarios();
        conn = new conexion();
        
        if(comprobarTxt_us()){
            txtID_us.setEnabled(true);
            u.setID(Integer.parseInt(txtID_us.getText()));
            txtID_us.setEnabled(false);
            u.setNombre(txtNombre_us.getText());
            u.setAp(txtAp_us.getText());
            u.setAm(txtAm_us.getText());
            u.setPerfil(cbPerfil_us.getSelectedItem().toString());
            u.setNombre_usuario(txtNombreUsuario_us.getText());
            u.setContraseña(txtContraseña_us.getText());
            u.setCalificacion(Double.parseDouble(txtCalificacion_us.getText()));
        }else{
            JOptionPane.showMessageDialog(null, "Los espacios de informacion no estan llenos en su totalidad");
            return;
        }
        
        try {
            if(conn.editarUsuario(u)>0){
                JOptionPane.showMessageDialog(null, "Usuario editado correctamente");
            }else{
                JOptionPane.showMessageDialog(null, "Error: No fue posible editarlo");
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        txtNombre_us.setText("");
        txtAp_us.setText("");
        txtAm_us.setText("");
        cbPerfil_us.setSelectedItem("...");
        txtNombreUsuario_us.setText("");
        txtContraseña_us.setText("");
        btnGuardarCambios_us.setVisible(false);
        btnCancelar_us.setEnabled(false);
        btnNuevo_us.setEnabled(true);
        Deshabilitar_us();
    }//GEN-LAST:event_btnGuardarCambios_usActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        jTabbedPane2.setEnabledAt(jTabbedPane2.indexOfComponent(jPanel3),true);
        btnNuevo_us.setEnabled(true);
        txtBuscar_us.setEnabled(true);
        btnBuscar_us.setEnabled(true);
        btnSalir.setEnabled(true);
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        jTabbedPane2.setEnabledAt(jTabbedPane2.indexOfComponent(jPanel3),false);
        btnNuevo_us.setEnabled(false);
        txtBuscar_us.setEnabled(false);
        btnBuscar_us.setEnabled(false);
        btnSalir.setEnabled(false);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnBuscar_prActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscar_prActionPerformed
        pr = new productos();
        conn = new conexion();
        
        if (txtBuscar_pr.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Ingresa un ID válido.");
            return;
        }
        
        int element = (int)txtBuscar_pr.getValue();
        
        try {
            pr = conn.buscarProducto(String.valueOf(element));
        } catch (SQLException ex) {
            Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(pr != null){
            txtID_pr.setEnabled(true);
            txtID_pr.setText(String.valueOf(pr.getID()));
            txtID_pr.setEnabled(false);
            cbVendedorID.setSelectedItem(String.valueOf(pr.getVendedorID()));
            txtDescripcion.setText(pr.getDescripcion());
            txtPrecio.setText(String.valueOf(pr.getPrecio()));
            txtCantidad_pr.setText(String.valueOf(pr.getCantidad()));
            cbCategoria_pr.setSelectedItem(pr.getCategoria());
            txtVendidos.setText(String.valueOf(pr.getCantidadVendida()));
            txtCalificacion.setText(String.valueOf(pr.getCalificacion()));
            Image imagen = new ImageIcon(pr.getImagen()).getImage();
            ImageIcon icono = new ImageIcon(imagen.getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH));
            lblImagen.setIcon(icono);
            btnEliminar_pr.setEnabled(true);
            btnEditar_pr.setEnabled(true);
            btnCancelar_pr.setEnabled(true);
            btnNuevo_pr.setEnabled(false);
        }else{
            JOptionPane.showMessageDialog(null,"No existe el elemento...");
        }
    }//GEN-LAST:event_btnBuscar_prActionPerformed

    private void btnQuitarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarFotoActionPerformed
        lblImagen.setIcon(null);
    }//GEN-LAST:event_btnQuitarFotoActionPerformed

    private void btnSalvar_prActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvar_prActionPerformed
        pr = new productos();
        conn = new conexion();
        
        if(comprobarTxt_pr()){
            pr.setVendedorID(Integer.parseInt(cbVendedorID.getSelectedItem().toString()));
            pr.setDescripcion(txtDescripcion.getText());
            pr.setPrecio(Double.parseDouble(txtPrecio.getText()));
            pr.setCantidad(Integer.parseInt(txtCantidad_pr.getText()));
            pr.setCategoria(cbCategoria_pr.getSelectedItem().toString());
            pr.setCantidadVendida(Integer.parseInt(txtVendidos.getText()));
            pr.setCalificacion(Double.parseDouble(txtCalificacion.getText()));
            pr.setImagen(pathImage);
        }else{
            JOptionPane.showMessageDialog(null, "Los espacios de informacion no estan llenos en su totalidad");
            return;
        }
        
        boolean result = false;
        try {
            result = conn.buscarProductoEnInventario(pr);
        } catch (SQLException ex) {
            Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(result){
            JOptionPane.showMessageDialog(null, "Este articulo ya existe dentro del inventario");
            return;
        }
        
        try {
            if(conn.agregarProducto(pr)>0){
                JOptionPane.showMessageDialog(null, "Producto agregado correctamente");
            }else{
                JOptionPane.showMessageDialog(null, "Error: No fue posible agregarla");
                return;
            }
        } catch (SQLException ex) {
        }
        
        cbVendedorID.setSelectedItem("...");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtCantidad_pr.setText("");
        cbCategoria_pr.setSelectedItem("...");
        txtVendidos.setText("");
        txtCalificacion.setText("");
        lblImagen.setIcon(null);
        btnEditar_pr.setEnabled(false);
        btnEliminar_pr.setEnabled(false);
        btnCancelar_pr.setEnabled(false);
        btnSalvar_pr.setEnabled(false);
        btnQuitarFoto.setEnabled(false);
        btnAgregarFoto.setEnabled(false);
        btnNuevo_pr.setEnabled(true);
        Deshabilitar_pr();
    }//GEN-LAST:event_btnSalvar_prActionPerformed

    private void btnCancelar_prActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar_prActionPerformed
        Deshabilitar_pr();
        btnNuevo_pr.setEnabled(true);
        btnSalvar_pr.setEnabled(false);
        btnCancelar_pr.setEnabled(false);
        btnEliminar_pr.setEnabled(false);
        btnEditar_pr.setEnabled(false);
        btnGuardarCambios_pr.setVisible(false);
        btnQuitarFoto.setEnabled(false);
        btnAgregarFoto.setEnabled(false);
        cbVendedorID.setSelectedItem("...");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtCantidad_pr.setText("");
        cbCategoria_pr.setSelectedItem("...");
        txtVendidos.setText("");
        txtCalificacion.setText("");
        lblImagen.setIcon(null);
    }//GEN-LAST:event_btnCancelar_prActionPerformed

    private void btnEliminar_prActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar_prActionPerformed
        txtID_pr.setEnabled(true);
        conn = new conexion();
        
        try {
            conn.eliminarProducto(txtID_pr.getText());
        } catch (SQLException ex) {
            Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        txtID_pr.setEnabled(false);
        cbVendedorID.setSelectedItem("...");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtCantidad_pr.setText("");
        cbCategoria_pr.setSelectedItem("...");
        txtVendidos.setText("");
        txtCalificacion.setText("");
        lblImagen.setIcon(null);
        btnEditar_pr.setEnabled(false);
        btnEliminar_pr.setEnabled(false);
        btnCancelar_pr.setEnabled(false);
        btnNuevo_pr.setEnabled(true);
    }//GEN-LAST:event_btnEliminar_prActionPerformed

    private void btnEditar_prActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditar_prActionPerformed
        btnGuardarCambios_pr.setVisible(true);
        btnEditar_pr.setEnabled(false);
        btnEliminar_pr.setEnabled(false);
        btnQuitarFoto.setEnabled(true);
        btnAgregarFoto.setEnabled(true);
        Habilitar_pr();
    }//GEN-LAST:event_btnEditar_prActionPerformed

    private void btnGuardarCambios_prActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCambios_prActionPerformed
        pr = new productos();
        conn = new conexion();
        
        if(comprobarTxt_pr()){
            txtID_pr.setEnabled(true);
            pr.setID(Integer.parseInt(txtID_pr.getText()));
            txtID_pr.setEnabled(false);
            pr.setVendedorID(Integer.parseInt(cbVendedorID.getSelectedItem().toString()));
            pr.setDescripcion(txtDescripcion.getText());
            pr.setPrecio(Double.parseDouble(txtPrecio.getText()));
            pr.setCantidad(Integer.parseInt(txtCantidad_pr.getText()));
            pr.setCategoria(cbCategoria_pr.getSelectedItem().toString());
            pr.setCantidadVendida(Integer.parseInt(txtVendidos.getText()));
            pr.setCalificacion(Double.parseDouble(txtCalificacion.getText()));
        }else{
            JOptionPane.showMessageDialog(null, "Los espacios de informacion no estan llenos en su totalidad");
            return;
        }
        
        try {
            if(conn.editarProducto(pr)>0){
                JOptionPane.showMessageDialog(null, "Producto editado correctamente");
            }else{
                JOptionPane.showMessageDialog(null, "Error: No fue posible editarlo");
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        txtNombre_us.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtCantidad_pr.setText("");
        cbCategoria_pr.setSelectedItem("...");
        txtVendidos.setText("");
        txtCalificacion.setText("");
        lblImagen.setIcon(null);
        btnGuardarCambios_pr.setVisible(false);
        btnCancelar_pr.setEnabled(false);
        btnNuevo_pr.setEnabled(true);
        btnQuitarFoto.setEnabled(false);
        btnAgregarFoto.setEnabled(false);
        Deshabilitar_pr();
    }//GEN-LAST:event_btnGuardarCambios_prActionPerformed

    private void btnNuevo_prActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo_prActionPerformed
        ArrayList<usuarios> list = new ArrayList<>();
        conn = new conexion();
        
        try {
            list = conn.buscarUsuariosVendedores("Vendedor");
        } catch (SQLException ex) {
            Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        if(!list.isEmpty()){
            for(usuarios u : list){
                cbVendedorID.addItem(String.valueOf(u.getID()));
            }
        }
        
        btnNuevo_pr.setEnabled(false);
        btnSalvar_pr.setEnabled(true);
        btnCancelar_pr.setEnabled(true);
        btnQuitarFoto.setEnabled(true);
        btnAgregarFoto.setEnabled(true);
        Habilitar_pr();
    }//GEN-LAST:event_btnNuevo_prActionPerformed

    private void btnAgregarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarFotoActionPerformed

        JFileChooser file = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("JPG, PNG & GIF", "jpg","png","gif");
        file.setFileFilter(filtro);
       
        int respuesta = file.showOpenDialog(this);
        
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            File archivoOrigen = file.getSelectedFile();
            String nombreArchivo = archivoOrigen.getName();

            File carpetaDestino = new File("images");
            if (!carpetaDestino.exists()) {
                carpetaDestino.mkdir(); 
            }

            File archivoDestino = new File(carpetaDestino, nombreArchivo);

            try {
                Files.copy(archivoOrigen.toPath(), archivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al copiar la imagen: " + e.getMessage());
                return;
            }

            Image imagen = new ImageIcon(archivoDestino.getAbsolutePath()).getImage();
            ImageIcon icono = new ImageIcon(imagen.getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH));
            lblImagen.setIcon(icono);

            pathImage = archivoDestino.getPath();
        }
    }//GEN-LAST:event_btnAgregarFotoActionPerformed

    private void btnRecuperaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecuperaActionPerformed
        jTabbedPane2.setEnabledAt(jTabbedPane2.indexOfComponent(jPanel4),true);
        txtUsuario_ps.setEnabled(true);
        txtEmail_ps.setEnabled(true);
        btnSalir_ps.setEnabled(true);
        btnRecuperarContraseña.setEnabled(true);
    }//GEN-LAST:event_btnRecuperaActionPerformed

    private void btnSalir_psActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir_psActionPerformed
        jTabbedPane2.setEnabledAt(jTabbedPane2.indexOfComponent(jPanel4),false);
        txtUsuario_ps.setEnabled(false);
        txtEmail_ps.setEnabled(false);
        btnSalir_ps.setEnabled(false);
        btnRecuperarContraseña.setEnabled(true);
        txtUsuario_ps.setText("");
        txtEmail_ps.setText("");
    }//GEN-LAST:event_btnSalir_psActionPerformed

    private void btnRecuperarContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecuperarContraseñaActionPerformed
        try {
            u = new usuarios();
            u = buscarContraseña(txtUsuario_ps.getText());
            
            if("Vendedor".equals(u.getPerfil())){
                String correo = "hosttiendita2208@gmail.com";
                String contraseña = "xcnnricfpenxyzty";
                String emailDest = txtEmail_ps.getText();
                
                Properties p = new Properties();
                
                p.put("mail.smtp.host","smtp.gmail.com");
                p.setProperty("mail.smtp.starttls.enable", "true");
                p.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                p.setProperty("mail.smtp.port", "587");
                p.setProperty("mail.smtp.user", correo);
                p.setProperty("mail.smtp.auth", "true");
                
                Session s = Session.getDefaultInstance(p);
                
                MimeMessage mensaje = new MimeMessage(s);
                mensaje.setFrom(new InternetAddress(correo));
                mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDest));
                mensaje.setSubject("Recuperacion de contraseña");
                mensaje.setText("Tu contraseña es: " + u.getContraseña());
                
                Transport t = s.getTransport("smtp");
                t.connect(correo,contraseña);
                t.sendMessage(mensaje, mensaje.getAllRecipients());
                t.close();
            }
            
            if("Comprador".equals(u.getPerfil())){
                String correo = "hosttiendita2208@gmail.com";
                String contraseña = "";
                String emailDest = txtEmail_ps.getText();
                
                Properties p = new Properties();
                
                p.put("mail.smtp.host","smtp.gmail.com");
                p.setProperty("mail.smtp.starttls.enable", "true");
                p.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                p.setProperty("mail.smtp.port", "587");
                p.setProperty("mail.smtp.user", correo);
                p.setProperty("mail.smtp.auth", "true");
                
                Session s = Session.getDefaultInstance(p);
                
                MimeMessage mensaje = new MimeMessage(s);
                mensaje.setFrom(new InternetAddress(correo));
                mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDest));
                mensaje.setSubject("Recuperacion de contraseña");
                mensaje.setText("Tu contraseña es: " + u.getContraseña());
                
                Transport t = s.getTransport("smtp");
                t.connect(correo,contraseña);
                t.sendMessage(mensaje, mensaje.getAllRecipients());
                t.close();
            }
        } catch (MessagingException ex) {
            Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRecuperarContraseñaActionPerformed

    private void cbCategoria_cpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoria_cpActionPerformed
        if(cbCategoria_cp.getSelectedItem().toString().equals("Todos")){
            conn = new conexion();
            int fila =jTable1.getRowCount();
            int i = fila-1;
            while(i>=0){
                
                modelo.removeRow(i);
                
                i--;
            }
            
            ArrayList<productos> list = new ArrayList<>();
            
            try {
                list = conn.buscarTodosProductos();
            } catch (SQLException ex) {
                Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for(productos pr : list){
                modelo.addRow(new Object[]{
                    String.valueOf(pr.getID()), pr.getDescripcion(), String.valueOf(pr.getPrecio()), String.valueOf(pr.getCantidad()),String.valueOf(pr.getCantidadVendida()),
                    String.valueOf(pr.getCalificacion()),new JLabel(new ImageIcon(pr.getImagen()))
                });
            }
            
        }else if(cbCategoria_cp.getSelectedItem().toString().equals("Tecnologia")){
            conn = new conexion();
            int fila =jTable1.getRowCount();
            int i = fila-1;
            while(i>=0){
                
                modelo.removeRow(i);
                
                i--;
            }
            
            ArrayList<productos> list = new ArrayList<>();
            
            try {
                list = conn.buscarproductosCategoria("Tecnologia");
            } catch (SQLException ex) {
                Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for(productos pr : list){
                modelo.addRow(new Object[]{
                    String.valueOf(pr.getID()), pr.getDescripcion(), String.valueOf(pr.getPrecio()), String.valueOf(pr.getCantidad()),String.valueOf(pr.getCantidadVendida()),
                    String.valueOf(pr.getCalificacion()),new JLabel(new ImageIcon(pr.getImagen()))
                });
            }
            
        }else if(cbCategoria_cp.getSelectedItem().toString().equals("Calzado")){
            conn = new conexion();
            int fila =jTable1.getRowCount();
            int i = fila-1;
            while(i>=0){
                
                modelo.removeRow(i);
                
                i--;
            }
            ArrayList<productos> list = new ArrayList<>();
            
            try {
                list = conn.buscarproductosCategoria("Calzado");
            } catch (SQLException ex) {
                Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for(productos pr : list){
                modelo.addRow(new Object[]{
                    String.valueOf(pr.getID()), pr.getDescripcion(), String.valueOf(pr.getPrecio()), String.valueOf(pr.getCantidad()),String.valueOf(pr.getCantidadVendida()),
                    String.valueOf(pr.getCalificacion()),new JLabel(new ImageIcon(pr.getImagen()))
                });
            }
            
        }else if(cbCategoria_cp.getSelectedItem().toString().equals("Accesorios")){
            conn = new conexion();
            int fila =jTable1.getRowCount();
            int i = fila-1;
            while(i>=0){
                
                modelo.removeRow(i);
                
                i--;
            }
            ArrayList<productos> list = new ArrayList<>();
            
            try {
                list = conn.buscarproductosCategoria("Accesorios");
            } catch (SQLException ex) {
                Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for(productos pr : list){
                modelo.addRow(new Object[]{
                    String.valueOf(pr.getID()), pr.getDescripcion(), String.valueOf(pr.getPrecio()), String.valueOf(pr.getCantidad()),String.valueOf(pr.getCantidadVendida()),
                    String.valueOf(pr.getCalificacion()),new JLabel(new ImageIcon(pr.getImagen()))
                });
            }
        }
    }//GEN-LAST:event_cbCategoria_cpActionPerformed

    private void btnQuitarCarritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarCarritoActionPerformed
        int fila = jTable2.getSelectedRow();
        pr = new productos();
        
        pr = lst.BuscarPr((String) jTable2.getValueAt(fila, 0));
        
        if(pr == null){
            return;
        }
        
        int posicion = lst.getPosicionPr();
        
        lst.RemoverPr(posicion);
        
        lst.ActualizarPr();
        
        modelo2.removeRow(jTable2.getSelectedRow());
        
    }//GEN-LAST:event_btnQuitarCarritoActionPerformed

    private void btnPonerCarritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPonerCarritoActionPerformed
        conn = new conexion();
        int fila = jTable1.getSelectedRow();
        int fila2 = jTable2.getRowCount();
        boolean yes=false;
        int ind = fila2-1;
        
        pr = new productos();
        
        try {
            pr = conn.buscarProducto(String.valueOf(jTable1.getValueAt(fila, 0)));
        } catch (SQLException ex) {
            Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        if(!"".equals(txtCantidad_cp.getText())){
            
            
            int i=0;
                while(i <= ind){
                    if(jTable1.getValueAt(fila, 1).equals(jTable2.getValueAt(i, 1))){
                        yes = true;
                        break;
                    }
                    i++;
                }
            
            if(Integer.parseInt(txtCantidad_cp.getText()) <= pr.getCantidad()){
                if(fila >= 0 && yes == false){
                    modelo2.addRow(new Object[]{
                        jTable1.getValueAt(fila, 0), jTable1.getValueAt(fila, 1), jTable1.getValueAt(fila, 2), txtCantidad_cp.getText(), new JLabel(new ImageIcon(pr.getImagen()))
                    });
                    lst.AgregarPr(pr);
                    lst.ActualizarPr();
                }
            }else{
                JOptionPane.showMessageDialog(null, "No hay suficientes articulos");
                return;
            }
            
            if(ind >= 0 && yes == true){
                
                if(jTable1.getValueAt(fila, 1).equals(jTable2.getValueAt(i, 1))){       
                    
                    int cantidadNueva = Integer.parseInt(txtCantidad_cp.getText()) + Integer.parseInt((String) jTable2.getValueAt(i, 3));

                    if(cantidadNueva <= pr.getCantidad()){
                        modelo2.setValueAt(String.valueOf(cantidadNueva), i, 3);
                        txtCantidad_cp.setText("");
                        return;
                    }else{
                        JOptionPane.showMessageDialog(null, "No hay suficientes articulos");
                        return;
                    }
                } 
                
            } 
            
        }else{
            JOptionPane.showMessageDialog(null, "Los espacios de informacion no estan llenos en su totalidad");
            return;
        }
        
        txtCantidad_cp.setText("");
        
    }//GEN-LAST:event_btnPonerCarritoActionPerformed

    private void btnComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprarActionPerformed
        jTabbedPane4.setEnabledAt(jTabbedPane4.indexOfComponent(jPanel9),true);
        txtPropietario.setEnabled(true);
        txtTarjeta.setEnabled(true);
        cbBanco.setEnabled(true);
        dateCaducidad.setEnabled(true);
        txtCVV.setEnabled(true);
        btnConfirmarCompra.setEnabled(true);
    }//GEN-LAST:event_btnComprarActionPerformed

    private void btnConfirmarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarCompraActionPerformed
        try{
            int size = 1000;
            QRCodeWriter codigoQR = new QRCodeWriter();
            BitMatrix matrix = codigoQR.encode(lst.toString(), BarcodeFormat.QR_CODE, size, size);
            int matrixWidth = matrix.getWidth();
            BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();
            Graphics2D gd = (Graphics2D)image.getGraphics();
            gd.setColor(Color.WHITE);
            gd.fillRect(0, 0, size, size);
            gd.setColor(Color.black);
            for(int b = 0; b < matrixWidth; b++){
                for(int j = 0; j < matrixWidth; j++){
                    if(matrix.get(b, j)){
                        gd.fillRect(b, j, 1, 1);
                    }
                }
            }
            File f = new File("qr.png");
            ImageIO.write(image, "png", f);
            Image mImagen = new ImageIcon("qr.png").getImage();
            ImageIcon imIcon = new ImageIcon(mImagen.getScaledInstance(300, 300, 0));
            
            conn = new conexion();
            int fila =jTable2.getRowCount();
            int i = fila-1;
            
            int j=0;
            pr = new productos();
            int tamaño = lst.getTamaño_pr();
            
            while(j < tamaño){
                pr = lst.getProducto(j);
                if(pr.getDescripcion().equals(jTable2.getValueAt(j, 1))){
                    if(!pr.quitarExistencia(Integer.parseInt((String) jTable2.getValueAt(j, 3)))){
                        return;
                    }
                    
                    pr.añadirCantidad(Integer.parseInt((String) jTable2.getValueAt(j, 3)));
                    conn.editarProducto(pr);
                }
                j++;
            }
            
            while(i>=0){
                
                modelo2.removeRow(i);
                
                i--;
            }
            
            PDF pdf = new PDF();
            pdf.setName(txtPropietario.getText());
            pdf.setNumberCard(Long.parseLong(txtTarjeta.getText()));
            pdf.setBanco(cbBanco.getSelectedItem().toString());
            pdf.setCaducidad(dateCaducidad.getDate());
            pdf.setCvv(Integer.parseInt(txtCVV.getText()));
            pdf.generar(txtPropietario.getText(), "qr.png", 300, 300);
            
            String correo = "hosttiendita2208@gmail.com";
                String contraseña = "";
                String emailDest = "compradortiendita2208@gmail.com";
                
                Properties p = new Properties();
                
                p.put("mail.smtp.host","smtp.gmail.com");
                p.setProperty("mail.smtp.starttls.enable", "true");
                p.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                p.setProperty("mail.smtp.port", "587");
                p.setProperty("mail.smtp.user", correo);
                p.setProperty("mail.smtp.auth", "true");
                
                Session s = Session.getDefaultInstance(p);
                
                BodyPart texto = new MimeBodyPart();
                texto.setText("Factura de compra: ");
                BodyPart adjunto = new MimeBodyPart();
                adjunto.setDataHandler(new DataHandler(new FileDataSource(txtPropietario.getText() + ".pdf")));
                adjunto.setFileName(txtPropietario.getText() + ".pdf");
                MimeMultipart m = new MimeMultipart();
                m.addBodyPart(texto);
                m.addBodyPart(adjunto);
                
                
                MimeMessage mensaje = new MimeMessage(s);
                mensaje.setFrom(new InternetAddress(correo));
                mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDest));
                mensaje.setSubject("Factura de compra");
                mensaje.setContent(m);
                
                Transport t = s.getTransport("smtp");
                t.connect(correo,contraseña);
                t.sendMessage(mensaje, mensaje.getAllRecipients());
                t.close();
            
            JOptionPane.showMessageDialog(null, "Compra realizada" + "\n\n" + "Gracias por su compra!!!");
            jTabbedPane4.setEnabledAt(jTabbedPane4.indexOfComponent(jPanel9),false);
            txtPropietario.setText("");
            txtTarjeta.setText("");
            cbBanco.setSelectedItem("...");
            txtCVV.setText("");
            txtPropietario.setEnabled(false);
            txtTarjeta.setEnabled(false);
            cbBanco.setEnabled(false);
            dateCaducidad.setEnabled(false);
            txtCVV.setEnabled(false);
            btnConfirmarCompra.setEnabled(false);
            f.delete();
        } catch (Exception ex) {
            Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnConfirmarCompraActionPerformed

    private void btnListoSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListoSalirActionPerformed
        lblListoSalir.setBackground(Color.GREEN);
        btnEnviado.setEnabled(true);
                
    }//GEN-LAST:event_btnListoSalirActionPerformed

    private void btnEnviadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviadoActionPerformed
        lblEnviado.setBackground(Color.GREEN);
        btnCamino.setEnabled(true);
    }//GEN-LAST:event_btnEnviadoActionPerformed

    private void btnCaminoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaminoActionPerformed
        lblEnCamino.setBackground(Color.GREEN);
        btnEntregado.setEnabled(true);
    }//GEN-LAST:event_btnCaminoActionPerformed

    private void btnEntregadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntregadoActionPerformed
        lblEntregado.setBackground(Color.GREEN);
        JOptionPane.showMessageDialog(null, "Venta finalizada");
        jInternalFrame1.setVisible(true);
    }//GEN-LAST:event_btnEntregadoActionPerformed

    private void btnConfirnCalfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirnCalfActionPerformed
        int i=0;
        conn = new conexion();
        pr = new productos();
        int tamaño = lst.getTamaño_pr();
        while(i < tamaño){
            pr = lst.getProducto(i);
            pr.actualizarCalificacion(Integer.parseInt(cbCalf.getSelectedItem().toString()));
            try {
                conn.editarProducto(pr);
            } catch (SQLException ex) {
                Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            i++;
        }
        
        conn = new conexion();
        
        u = new usuarios();
        
        try {
            u = conn.buscarUsuario(String.valueOf(pr.getVendedorID()));
        } catch (SQLException ex) {
            Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
            
        if(u.getID() == pr.getVendedorID()){
            u.actualizarCalificacion(Integer.parseInt(cbCalf.getSelectedItem().toString()));
                
            try {
                conn.editarUsuario(u);
            } catch (SQLException ex) {
                Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        }
            
            jInternalFrame1.setVisible(false);
            
            btnEnviado.setEnabled(false);
            btnCamino.setEnabled(false);
            btnEntregado.setEnabled(false);
            lblListoSalir.setBackground(Color.WHITE);
            lblEnviado.setBackground(Color.WHITE);
            lblEnCamino.setBackground(Color.WHITE);
            lblEntregado.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnConfirnCalfActionPerformed

    private void cbPerfil_usActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPerfil_usActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPerfil_usActionPerformed

    private void cbCalfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCalfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCalfActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(tienda_amazon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tienda_amazon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tienda_amazon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tienda_amazon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new tienda_amazon().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(tienda_amazon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarFoto;
    private javax.swing.JButton btnBuscar_pr;
    private javax.swing.JButton btnBuscar_us;
    private javax.swing.JButton btnCamino;
    private javax.swing.JButton btnCancelar_pr;
    private javax.swing.JButton btnCancelar_us;
    private javax.swing.JButton btnComprar;
    private javax.swing.JButton btnConfirmarCompra;
    private javax.swing.JButton btnConfirnCalf;
    private javax.swing.JButton btnEditar_pr;
    private javax.swing.JButton btnEditar_us;
    private javax.swing.JButton btnEliminar_pr;
    private javax.swing.JButton btnEliminar_us;
    private javax.swing.JButton btnEntregado;
    private javax.swing.JButton btnEnviado;
    private javax.swing.JButton btnGuardarCambios_pr;
    private javax.swing.JButton btnGuardarCambios_us;
    private javax.swing.JButton btnListoSalir;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnNuevo_pr;
    private javax.swing.JButton btnNuevo_us;
    private javax.swing.JButton btnPonerCarrito;
    private javax.swing.JButton btnQuitarCarrito;
    private javax.swing.JButton btnQuitarFoto;
    private javax.swing.JButton btnRecupera;
    private javax.swing.JButton btnRecuperarContraseña;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSalir_ps;
    private javax.swing.JButton btnSalvar_pr;
    private javax.swing.JButton btnSalvar_us;
    private javax.swing.JComboBox cbBanco;
    private javax.swing.JComboBox cbCalf;
    private javax.swing.JComboBox cbCategoria_cp;
    private javax.swing.JComboBox cbCategoria_pr;
    private javax.swing.JComboBox cbPerfil_us;
    private javax.swing.JComboBox cbVendedorID;
    private com.toedter.calendar.JDateChooser dateCaducidad;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblEnCamino;
    private javax.swing.JLabel lblEntregado;
    private javax.swing.JLabel lblEnviado;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblListoSalir;
    private javax.swing.JTextField txtAm_us;
    private javax.swing.JTextField txtAp_us;
    private javax.swing.JSpinner txtBuscar_pr;
    private javax.swing.JTextField txtBuscar_us;
    private javax.swing.JTextField txtCVV;
    private javax.swing.JTextField txtCalificacion;
    private javax.swing.JTextField txtCalificacion_us;
    private javax.swing.JTextField txtCantidad_cp;
    private javax.swing.JTextField txtCantidad_pr;
    private javax.swing.JTextField txtContraseña_us;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtEmail_ps;
    private javax.swing.JTextField txtID_pr;
    private javax.swing.JTextField txtID_us;
    private javax.swing.JTextField txtNombreUsuario_us;
    private javax.swing.JTextField txtNombre_us;
    private javax.swing.JPasswordField txtPassword_lg;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtPropietario;
    private javax.swing.JTextField txtTarjeta;
    private javax.swing.JTextField txtUsuario_ps;
    private javax.swing.JTextField txtUsuarios_lg;
    private javax.swing.JTextField txtVendidos;
    // End of variables declaration//GEN-END:variables
}
