
package Consulta;

import Formulario.ventanaadmin;
import claseConectar.conectar;
import exportar.clsExportarExcel;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class VerProducto extends javax.swing.JInternalFrame {

    DefaultTableModel model;
    clsExportarExcel obj;


    public VerProducto() {
        initComponents();
        cargar("");

    }
    
        void cargar(String valor) {
        String mostrar = "SELECT * FROM producto WHERE CONCAT(cod_pro,descripcion,marca) LIKE '%" + valor + "%'";
        String[] titulos = {"CODIGO","DESCRIPCION","MARCA","STOCK","PRECIO"};
        String[] Registros = new String[5];
        model = new DefaultTableModel(null, titulos);

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(mostrar);
            while (rs.next()) {
                Registros[0] = rs.getString("cod_pro");
                Registros[1] = rs.getString("descripcion");
                Registros[2] = rs.getString("marca");
                Registros[3] = rs.getString("Stock");
                Registros[4] = rs.getString("precio");

                model.addRow(Registros);
            }
            tbProducto.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(VerProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        mnver = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbProducto = new javax.swing.JTable();
        BtnExpor = new javax.swing.JButton();

        mnver.setText("Ver Kardex");
        mnver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnverActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mnver);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("PRODUCTOS");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtBuscar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel1.setText("BUSCA TU PRODUCTO:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tbProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbProducto.setColumnSelectionAllowed(true);
        tbProducto.setComponentPopupMenu(jPopupMenu1);
        tbProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(tbProducto);

        BtnExpor.setIcon(new javax.swing.ImageIcon("D:\\tienda\\build\\classes\\img\\logo_excel.png")); // NOI18N
        BtnExpor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnExporActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(BtnExpor)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtnExpor)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void mnverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnverActionPerformed
// TODO add your handling code here:
    int filasele = tbProducto.getSelectedRow();
    if (filasele == -1) {
        JOptionPane.showMessageDialog(null, "No Seleciono ninguna fila");
    } else {
        DetalleProducto detalle = new DetalleProducto();
        ventanaadmin.jdpescritorio.add(detalle);
        detalle.toFront();
        detalle.setVisible(true);
        String cod_prod = tbProducto.getValueAt(filasele, 0).toString();
        String des = tbProducto.getValueAt(filasele, 1).toString();
        String marca = tbProducto.getValueAt(filasele, 2).toString();
        String stock = tbProducto.getValueAt(filasele, 3).toString();
        
        // Concatenación de descripción y marca con un espacio intermedio
        String productoCompleto = des + " - " + marca;
        
        DetalleProducto.txtcodprod.setText(cod_prod);
        DetalleProducto.txtprod.setText(productoCompleto); // devuelve concatenado
        DetalleProducto.txtcant.setText(stock);

        //DetalleBoleta.txtcod.setText(cod);
       // DetalleBoleta.txttot.setText(total);
       //DetalleBoleta.txtfecha.setText(fecha);
       
        DefaultTableModel model = (DefaultTableModel) DetalleProducto.tbdetalle.getModel();
        //String ver = "SELECT * FROM producto WHERE cod_pro='" + cod_prod + "'";
        String ver = "SELECT fec_mov, CONCAT(cliente.nom_cli,' ',ape_cli) AS cliente, nro_doc_salida, guia_prove,IF(tip_mov='S','Salida','Entrada') AS tip_mov, proveedor,Descrip, Marca, CantMov, Saldo FROM mov_cab\n" +
        "LEFT JOIN mov_det ON mov_cab.iNroMov = mov_det.cod_mov\n" +
        "LEFT JOIN cliente ON cliente.cod_cli = mov_cab.cod_clie\n" +
        "WHERE \n" +
        "mov_det.CodProdu ='" + cod_prod + "'  ORDER BY iNroMov";

        
        String[] datos = new String[10];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ver);
            while (rs.next()) {
                datos[0] = rs.getString("fec_mov");
                datos[1] = rs.getString("cliente");
                datos[2] = rs.getString("nro_doc_salida");
                datos[3] = rs.getString("guia_prove");
                datos[4] = rs.getString("tip_mov");
                datos[5] = rs.getString("proveedor");
                datos[6] = rs.getString("Descrip");
                datos[7] = rs.getString("Marca");
                datos[8] = rs.getString("CantMov");
                datos[9] = rs.getString("Saldo");

                model.addRow(datos);

            }
            DetalleProducto.tbdetalle.getModel();
            
        } catch (SQLException ex) {
            Logger.getLogger(VerProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}//GEN-LAST:event_mnverActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased

        cargar(txtBuscar.getText());

    }//GEN-LAST:event_txtBuscarKeyReleased

    private void BtnExporActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnExporActionPerformed
        
        try {
            obj = new clsExportarExcel();
            obj.exportarExcel(tbProducto);
        } catch (IOException ex) {
            Logger.getLogger(VerProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_BtnExporActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnExpor;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem mnver;
    public static javax.swing.JTable tbProducto;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
conectar cc = new conectar();
    Connection cn = cc.conexion();
}
