package Compras;

import Formulario.ListarProductos;
import Formulario.ventanaadmin;
import GeneracionCodigo.GenerarCodigosMovimiento;
import claseConectar.conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Compra extends javax.swing.JInternalFrame {

public Compra(){
    initComponents();
    ConfigurarTabla();
    this.setLocation(15, 15);
    txtfecha.setText(fechaact());
    
    numeros_mov();
    
}




    private void limpiarCajas() {

        txtguia.setText(null);
        txtproveedor.setText(null);
        txtruc.setText(null);
        txtdireccion.setText(null);

        txtguia.setEnabled(true);
        txtproveedor.setEnabled(true);
        txtruc.setEnabled(true);
        txtdireccion.setEnabled(true);
        txtfecha.setEnabled(true);
        btnprov.setEnabled(true);
        btnproducto.setEnabled(true);
        btneliminar.setEnabled(true);
        btnguardar.setEnabled(true);
        btncalcular.setEnabled(true);

    }
    
    private void ConfigurarTabla() {
    // Índices: 0:CODIGO, 1:DESCRIPCION, 2:MARCA, 3:CANT.RECIBIDA, 4:PRECIO UNITARIO, 5:SUB TOTAL
    String[] titulos = {"CODIGO", "DESCRIPCION", "MARCA", "CANT.RECIBIDA", "PRECIO UNITARIO", "SUB TOTAL"};
    
    DefaultTableModel modelo = new DefaultTableModel(null, titulos) {
        @Override
        public boolean isCellEditable(int row, int column) {
            // Ahora la columna 4 (PRECIO UNITARIO) es la única editable
            return column == 4; 
        }
        
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            // CANT.RECIBIDA (3) es entero, pero PRECIO UNITARIO (4) y SUB TOTAL (5) deben ser Double
            if (columnIndex == 3) {
                return Integer.class;
            }
            if (columnIndex == 4 || columnIndex == 5) {
                return Double.class;
            }
            return Object.class;
        }
    };
    
    jtdetalleent.setModel(modelo);
}

    void numeros_mov() {
        int j;
        int cont = 1;
        String num = "";
        String c = "";
         String SQL="SELECT IFNULL(MAX(iNroMov), 0)  AS siguiente_nro FROM mov_cab";
      
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                c = rs.getString(1);
            }
            j = Integer.parseInt(c);
            GenerarCodigosMovimiento gen = new GenerarCodigosMovimiento();
            gen.generar(j);
            txtnumero.setText(gen.serie());
        } catch (SQLException ex) {
            Logger.getLogger(Compra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
void calcular() {
    String pre;
    String can;
    double igv = 0;
    double total = 0;
    double subtotal = 0;
    double precio;
    int cantidad;
    double imp = 0.0;

    for (int i = 0; i < jtdetalleent.getRowCount(); i++) {
        // Obtenemos los valores de la tabla
        Object objCan = jtdetalleent.getValueAt(i, 3);
        Object objPre = jtdetalleent.getValueAt(i, 4);

        // VALIDACIÓN: Verificamos si la celda de precio o cantidad están vacías
        if (objPre == null || objPre.toString().trim().isEmpty() || 
            objCan == null || objCan.toString().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "Error en la fila " + (i + 1) + ":\n"
                    + "Debe ingresar un precio y cantidad antes de calcular.");
            return; // Detenemos el proceso para que el usuario corrija
        }

        try {
            can = objCan.toString();
            pre = objPre.toString();
            
            cantidad = Integer.parseInt(can);
            precio = Double.parseDouble(pre);
            
            // Calculamos el importe de la fila
            imp = cantidad * precio;
            subtotal = subtotal + imp;
            
            // Colocamos el importe en la columna 5 (Subtotal por fila)
            jtdetalleent.setValueAt(Math.rint(imp * 100) / 100, i, 5);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dato no válido en la fila " + (i + 1) + ".\n"
                    + "Asegúrese de usar solo números.");
            return;
        }
    }
    
    // Cálculo de impuestos (Fuera del bucle for para no repetir cálculos innecesarios)
    igv = subtotal * 0.18;
    total = subtotal + igv;

    // Mostramos resultados en los campos de texto
    txtSubtotal.setText(String.format("%.2f", subtotal));
    txtIgv.setText(String.format("%.2f", igv));
    txtTotal.setText(String.format("%.2f", total));
}
    
    void AumentarStock(String codi, String can) {
        int des = Integer.parseInt(can);
        String cap = "";
        int desfinal;
        String consul = "SELECT * FROM producto WHERE  cod_pro='" + codi + "'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(consul);
            while (rs.next()) {
                cap = rs.getString(5);
            }

        } catch (Exception e) {
        }
        desfinal = Integer.parseInt(cap) + des;
        String modi = "UPDATE producto SET Stock='" + desfinal + "' WHERE cod_pro = '" + codi + "'";
        try {
            PreparedStatement pst = cn.prepareStatement(modi);
            pst.executeUpdate();
        } catch (Exception e) {
        }
    }
    
    
    // TENEMOS CABECERA Y DETALLE DE MOVIMIENTO PARA QUE TODO NUESTRO INGRESO SE GUARDEN EN 2 TABLAS
    void Movimientos_cab() {

        String InsertarSQL = "INSERT INTO mov_cab(fec_mov,guia_prove,tip_mov,cod_motivo,cod_prov,proveedor,sub_Total,igv,total,usuarioReg) VALUES (?,?,'E','1',?,?,?,?,?,?)";
        String fecha = txtfecha.getText();
        String guia = txtguia.getText();
        String codprov = txtcod.getText();
        String prov = txtproveedor.getText();
        String subt = txtSubtotal.getText();
        String igv = txtIgv.getText();
        String total = txtTotal.getText();
        String codper = txtcodpersonal.getText();

        try {
            PreparedStatement pst = cn.prepareStatement(InsertarSQL);
            pst.setString(1, fecha);
            pst.setString(2, guia);
            pst.setString(3, codprov);
            pst.setString(4, prov);
            pst.setString(5, subt);
            pst.setString(6, igv);
            pst.setString(7, total);
            pst.setString(8, codper);

            int n = pst.executeUpdate();
            if (n > 0) {
               JOptionPane.showMessageDialog(null, "Los datos se guardaron correctamente");
           }

        } catch (SQLException ex) {
            Logger.getLogger(Compra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
    void Movimientos_det() {
        for (int i = 0; i < jtdetalleent.getRowCount(); i++) {
            String InsertarSQL = "INSERT INTO mov_det(cod_mov,CodProdu,Descrip,Marca,CantMov,precio_compra) VALUES (?,?,?,?,?,?)";
            String nummov = txtnumero.getText();
            String codpro = jtdetalleent.getValueAt(i, 0).toString();
            String despro = jtdetalleent.getValueAt(i, 1).toString();
            String marpro = jtdetalleent.getValueAt(i, 2).toString();
            String cantpro = jtdetalleent.getValueAt(i, 3).toString();
            String precompra = jtdetalleent.getValueAt(i, 4).toString();

            try {
                PreparedStatement pst = cn.prepareStatement(InsertarSQL);
                pst.setString(1, nummov);
                pst.setString(2, codpro);
                pst.setString(3, despro);
                pst.setString(4, marpro);
                pst.setString(5, cantpro);
                pst.setString(6, precompra);

                pst.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(Compra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public static String fechaact() {
        Date fecha = new Date();
        SimpleDateFormat formatofecha = new SimpleDateFormat("dd/MM/YYYY");
        return formatofecha.format(fecha);

    }
    
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtdetalleent = new javax.swing.JTable();
        txtcodpersonal = new javax.swing.JLabel();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtnumero = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtguia = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtproveedor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtdireccion = new javax.swing.JTextField();
        btnprov = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtruc = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtfecha = new javax.swing.JTextField();
        txtcod = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btneliminar = new javax.swing.JButton();
        btnproducto = new javax.swing.JButton();
        btncalcular = new javax.swing.JButton();
        txtSubtotal = new javax.swing.JTextField();
        txtIgv = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

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

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("COMPRA");
        setToolTipText("");

        jtdetalleent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DESCRIPCION", "MARCA", "CANT.RECIBIDO", "PRECIO UNITARIO", "SUB TOTAL"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jtdetalleent);

        txtcodpersonal.setText("1");
        txtcodpersonal.setToolTipText("");

        btnnuevo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });

        btnguardar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setEnabled(false);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("COD:");

        txtnumero.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtnumero.setForeground(new java.awt.Color(255, 0, 51));
        txtnumero.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("GUIA:");

        txtguia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtguia.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("PROVEEDOR:");

        txtproveedor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtproveedor.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("DIRECCION:");

        txtdireccion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtdireccion.setEnabled(false);

        btnprov.setText("...");
        btnprov.setEnabled(false);
        btnprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprovActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("R.U.C.:");

        txtruc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtruc.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("FECHA INTERNAMIENTO:");

        txtfecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha.setEnabled(false);
        txtfecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfechaActionPerformed(evt);
            }
        });

        txtcod.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcod.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtguia, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtnumero, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 2, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnprov)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtcod, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtnumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtguia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(btnprov, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcod, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btneliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/remove_carro.png"))); // NOI18N
        btneliminar.setEnabled(false);
        btneliminar.setMaximumSize(new java.awt.Dimension(41, 41));
        btneliminar.setMinimumSize(new java.awt.Dimension(41, 41));
        btneliminar.setPreferredSize(new java.awt.Dimension(40, 40));
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        btnproducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/AgregarProducto.png"))); // NOI18N
        btnproducto.setEnabled(false);
        btnproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnproductoActionPerformed(evt);
            }
        });

        btncalcular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/calc.png"))); // NOI18N
        btncalcular.setEnabled(false);
        btncalcular.setPreferredSize(new java.awt.Dimension(125, 41));
        btncalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncalcularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btncalcular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtIgv.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Subtotal S/");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("IGV (18%)");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Total S/");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(txtcodpersonal)
                                        .addGap(129, 129, 129)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtIgv, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtcodpersonal)
                                .addGap(34, 34, 34))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(txtIgv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)))
                .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprovActionPerformed

    try {

        ListarProveedores VerListadoProve = new ListarProveedores();
        ventanaadmin.jdpescritorio.add(VerListadoProve);

        VerListadoProve.toFront();
        VerListadoProve.setVisible(true);

    } catch (Exception e) {
    }
    
    }//GEN-LAST:event_btnprovActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        DefaultTableModel model = (DefaultTableModel) jtdetalleent.getModel();
        int fila = jtdetalleent.getSelectedRow();
        if (fila >= 0) {
            model.removeRow(fila);
        } else {
            JOptionPane.showMessageDialog(null, "Tabla vacia o no seleccione ninguna fila");
        }
    }//GEN-LAST:event_btneliminarActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed

        // 1. PRIMERO VALIDAMOS (No llamamos a los métodos de guardado todavía)
    if (txtguia.getText().isEmpty() || txtruc.getText().isEmpty() || 
    txtTotal.getText().isEmpty() || txtcod.getText().isEmpty()) {
    
    JOptionPane.showMessageDialog(this, "Debe completar: Proveedor, Nro Guía y realizar el cálculo del Total.");

    } else if (jtdetalleent.getRowCount() == 0) {
    // Validación extra: Que la tabla no esté vacía
    JOptionPane.showMessageDialog(this, "No hay productos en el detalle de la compra.");

    } else {
    // SI TODO ESTÁ BIEN, RECIÉN GUARDAMOS
    try {
        // Ejecutamos la inserción en la Base de Datos
        Movimientos_cab(); 
        Movimientos_det(); 

        // 3. Aumentamos el stock en la base de datos
        String capcod = "", capcan = "";
        for (int i = 0; i < Compra.jtdetalleent.getRowCount(); i++) {
            capcod = Compra.jtdetalleent.getValueAt(i, 0).toString();
            capcan = Compra.jtdetalleent.getValueAt(i, 3).toString();
            AumentarStock(capcod, capcan);
        }

        JOptionPane.showMessageDialog(this, "Compra registrada con éxito y stock actualizado.");

        // 4. LIMPIAMOS LOS CAMPOS
        txtguia.setText("");
        txtruc.setText("");
        txtdireccion.setText("");
        txtproveedor.setText("");
        txtcod.setText(""); 

        txtSubtotal.setText("");
        txtIgv.setText("");
        txtTotal.setText("");

        // Limpiar la tabla de forma eficiente
        DefaultTableModel modelo = (DefaultTableModel) jtdetalleent.getModel();
        modelo.setRowCount(0); 

        // Generar nuevo número de movimiento
        numeros_mov();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error crítico al guardar: " + e.getMessage());
    }
}
        
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btnproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnproductoActionPerformed

        
    try {

        ListarProductosCom ListadoProd = new ListarProductosCom();
        ventanaadmin.jdpescritorio.add(ListadoProd);

        ListadoProd.toFront();
        ListadoProd.setVisible(true);

    } catch (Exception e) {
    }
        
    }//GEN-LAST:event_btnproductoActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        limpiarCajas();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void btncalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncalcularActionPerformed
        if (jtdetalleent.getRowCount() < 1) {
            JOptionPane.showMessageDialog(this, "ingrese algun producto");
        } else {
            calcular();
           // sumarcolumna();
        }
    }//GEN-LAST:event_btncalcularActionPerformed

    private void txtfechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfechaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncalcular;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnproducto;
    private javax.swing.JButton btnprov;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    public static javax.swing.JTable jtdetalleent;
    private javax.swing.JTextField txtIgv;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtTotal;
    public static javax.swing.JTextField txtcod;
    public static javax.swing.JLabel txtcodpersonal;
    public static javax.swing.JTextField txtdireccion;
    public static javax.swing.JTextField txtfecha;
    public static javax.swing.JTextField txtguia;
    public static javax.swing.JTextField txtnumero;
    public static javax.swing.JTextField txtproveedor;
    public static javax.swing.JTextField txtruc;
    // End of variables declaration//GEN-END:variables
    conectar cc = new conectar();
    Connection cn = cc.conexion();
}
