package exportar;

import Consulta.ConsultasProductos;
import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ExportarExcel {

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        try {
            // TODO code application logic here
            UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel()); //
            ConsultasProductos inicio = new ConsultasProductos();
            //inicio.setLocationRelativeTo(null);
            inicio.setLocation(null);
            inicio.setVisible(true);
        } catch (ParseException ex) {
            Logger.getLogger(ExportarExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
