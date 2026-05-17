package claseConectar;

import java.sql.*;
import javax.swing.*;

public class conectar {
Connection conect = null;
   public Connection conexion()
    {
      try {
             
           //Cargamos el Driver MySQL
          // Class.forName("com.mysql.jdbc.Driver");
           Class.forName("com.mysql.cj.jdbc.Driver");

           //RUTA DE MI BD
           conect = DriverManager.getConnection("jdbc:mysql://192.168.1.13/db_tienda","root","root");
           
           //MENSAJE DE PRUEBA PARA SABER QUE NUESTRA BD ESTA CONECTANDOSE 
           //JOptionPane.showMessageDialog(null, "conectado");
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error "+e);
        }
        return conect;
     
}}
