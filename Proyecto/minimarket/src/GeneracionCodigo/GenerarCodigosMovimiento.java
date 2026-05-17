
package GeneracionCodigo;

import Formulario.*;


public class GenerarCodigosMovimiento {
    
    private int dato;
    private int cont=1;
    private String num="";

    public void generar(int dato) {
        this.dato = dato;
          
           if((this.dato<10000)) 
           {
               int can=cont+this.dato;
               num = "" + can; 
           }
          
    }

    public String serie()
    {
        return this.num;
    }
    
    
    
    
}