
package Compras;

import claseConectar.conectar;
import java.sql.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.table.*;
import Compras.Compra;


public class ListarProveedores extends javax.swing.JInternalFrame {
    
    DefaultTableModel tabla;
    ResultSet rs = null;
    PreparedStatement ps = null;
    DefaultTableModel modelo = new DefaultTableModel();

    public ListarProveedores() {
        initComponents();
        ActualizarTabla("");
    }

       void ActualizarTabla(String valor) {

        String[]titulos={"COD","RUC","PROVEEDOR","DIRECCION"} ;  
        String []Registros= new String[4];
        modelo=new DefaultTableModel(null,titulos);
        String Sql="SELECT id_proveedor,ruc,razon_social,direccion FROM proveedor WHERE estado ='1' and razon_social LIKE '%"+valor+"%'";
       
        try {
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(Sql);
             while(rs.next())
             {
                 Registros[0]=rs.getString("id_proveedor");  
                 Registros[1]=rs.getString("ruc");  
                 Registros[2]=rs.getString("razon_social");  
                 Registros[3]=rs.getString("direccion");  

                 modelo.addRow(Registros);
             } 
             jtproveedor.setModel(modelo);
             
        jtproveedor.getColumnModel().getColumn(0).setPreferredWidth(5);
        jtproveedor.getColumnModel().getColumn(1).setPreferredWidth(30);
        jtproveedor.getColumnModel().getColumn(2).setPreferredWidth(80);
        jtproveedor.getColumnModel().getColumn(3).setPreferredWidth(50);
        
        } catch (SQLException ex) {
            Logger.getLogger(ListarProveedores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        mnenviar = new javax.swing.JMenuItem();
        txtbuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtproveedor = new javax.swing.JTable();

        mnenviar.setText("Enviar a Compra");
        mnenviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnenviarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mnenviar);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("PROVEEDORES");

        txtbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscarActionPerformed(evt);
            }
        });
        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        jLabel1.setText("Buscar:");

        jtproveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtproveedor.setToolTipText("");
        jtproveedor.setComponentPopupMenu(jPopupMenu1);
        jtproveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jtproveedor.setDoubleBuffered(true);
        jtproveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtproveedorMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jtproveedorMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jtproveedor);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void mnenviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnenviarActionPerformed

         String cod="", nom="", dir="", ruc="";
    int fila = jtproveedor.getSelectedRow();
    try {
        if(fila==-1)
        {
            JOptionPane.showMessageDialog(null, "No ha seleccionado ningun dato");
                  
        }
        else
        {
         cod =  (String)jtproveedor.getValueAt(fila, 0);
         ruc =  (String)jtproveedor.getValueAt(fila, 1);
         nom =  (String)jtproveedor.getValueAt(fila, 2);
         dir =  (String)jtproveedor.getValueAt(fila, 3);

         Compra.txtcod.setText(cod);
         Compra.txtruc.setText(ruc);
         Compra.txtproveedor.setText(nom);
         Compra.txtdireccion.setText(dir);
        
         this.dispose();
         
        }
    } catch (Exception e) {
    }
}//GEN-LAST:event_mnenviarActionPerformed

    private void txtbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscarActionPerformed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
                ActualizarTabla(txtbuscar.getText());

    }//GEN-LAST:event_txtbuscarKeyReleased

    private void jtproveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtproveedorMouseClicked

    }//GEN-LAST:event_jtproveedorMouseClicked

    private void jtproveedorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtproveedorMousePressed

    }//GEN-LAST:event_jtproveedorMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtproveedor;
    private javax.swing.JMenuItem mnenviar;
    public static javax.swing.JTextField txtbuscar;
    // End of variables declaration//GEN-END:variables
conectar cc = new conectar();
Connection cn = cc.conexion();
}
