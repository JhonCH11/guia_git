
package Compras;

import Salidas.Productos;
import Formulario.*;
import claseConectar.conectar;
import java.sql.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.table.*;


public class ListarProductosCom extends javax.swing.JInternalFrame {

    DefaultTableModel tabla;


    public ListarProductosCom() {
        initComponents();
        cargarlistaproductos("");
    }

    String comparar(String cod) {
        String cant = "";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM producto WHERE cod_pro='" + cod + "'");
            while (rs.next()) {
                cant = rs.getString(5);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ListarProductosCom.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cant;

    }

    void cargarlistaproductos(String dato) {
        String[] Titulo = {"Codigo", "Descripcion", "Marca", "Precio", "Stock"};
        tabla = new DefaultTableModel(null, Titulo);
        String[] Registro = new String[5];
        String mostrar = "SELECT * FROM producto WHERE CONCAT (cod_pro,'',descripcion) LIKE '%" + dato + "%'";
        Statement st;
        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(mostrar);
            while (rs.next()) {
                Registro[0] = rs.getString("cod_pro");
                Registro[1] = rs.getString("Descripcion");
                Registro[2] = rs.getString("marca");
                Registro[3] = rs.getString("precio");
                Registro[4] = rs.getString("Stock");
                tabla.addRow(Registro);
            }
            tbprod.setModel(tabla);
        } catch (SQLException ex) {
            Logger.getLogger(ListarProductosCom.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        mnenviarProd = new javax.swing.JMenuItem();
        txtprod = new javax.swing.JTextField();
        btnmostrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbprod = new javax.swing.JTable();

        mnenviarProd.setText("Enviar Producto");
        mnenviarProd.setToolTipText("");
        mnenviarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnenviarProdActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mnenviarProd);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("PRODUCTOS");

        txtprod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprodKeyReleased(evt);
            }
        });

        btnmostrar.setText("Mostrar todo");
        btnmostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmostrarActionPerformed(evt);
            }
        });

        jLabel1.setText("Buscar Productos");

        tbprod.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbprod.setComponentPopupMenu(jPopupMenu1);
        jScrollPane2.setViewportView(tbprod);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtprod, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnmostrar)
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnmostrar)
                    .addComponent(txtprod, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void txtprodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprodKeyReleased
// TODO add your handling code here:
    cargarlistaproductos(txtprod.getText());
}//GEN-LAST:event_txtprodKeyReleased

private void btnmostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmostrarActionPerformed
// TODO add your handling code here:
    cargarlistaproductos("");
}//GEN-LAST:event_btnmostrarActionPerformed

private void mnenviarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnenviarProdActionPerformed

try {
    DefaultTableModel tabladet = (DefaultTableModel) Compra.jtdetalleent.getModel();
    int fila = tbprod.getSelectedRow();

    if (fila == -1) {
        JOptionPane.showMessageDialog(null, "No ha seleccionado ningún registro");
        return;
    }

    // Datos del producto seleccionado
    String codins = tbprod.getValueAt(fila, 0).toString();
    String desins = tbprod.getValueAt(fila, 1).toString();
    String marins = tbprod.getValueAt(fila, 2).toString();
    
    String cant = JOptionPane.showInputDialog("Ingrese cantidad a internar:");

    // 1. Validar que no se cierre la ventana sin escribir nada
    if (cant == null || cant.trim().isEmpty()) {
        return; 
    }

    // 2. Validar que sea un número entero y POSITIVO
    int cantIngresada;
    try {
        cantIngresada = Integer.parseInt(cant);
        if (cantIngresada <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0 para el ingreso.");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error: Ingrese un número entero válido.");
        return;
    }
    
    

    // 3. Verificar si el producto ya está en la lista de abajo para sumar cantidades
    boolean existeEnTabla = false;
    for (int i = 0; i < tabladet.getRowCount(); i++) {
        if (tabladet.getValueAt(i, 0).toString().equals(codins)) {
            // Si ya existe, sumamos la cantidad nueva a la que ya estaba anotada
            int cantActualEnTabla = Integer.parseInt(tabladet.getValueAt(i, 3).toString());
            int nuevaSuma = cantActualEnTabla + cantIngresada;
            
            tabladet.setValueAt(String.valueOf(nuevaSuma), i, 3);
            existeEnTabla = true;
            break;
        }
    }

    // 4. Si es un producto nuevo en la lista, agregamos la fila
    if (!existeEnTabla) {
        String[] dato = new String[5];
        dato[0] = codins;
        dato[1] = desins;
        dato[2] = marins;
        dato[3] = String.valueOf(cantIngresada);
        // dato[4] = precio; // Si tienes el precio, agrégalo aquí
        
        tabladet.addRow(dato);
    }

} catch (Exception e) {
    JOptionPane.showMessageDialog(this, "Error al procesar el ingreso: " + e.getMessage());
}
    
}//GEN-LAST:event_mnenviarProdActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnmostrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem mnenviarProd;
    private javax.swing.JTable tbprod;
    private javax.swing.JTextField txtprod;
    // End of variables declaration//GEN-END:variables
conectar cc = new conectar();
Connection cn = cc.conexion();
}
