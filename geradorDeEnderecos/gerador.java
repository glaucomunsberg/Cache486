/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geradorDeEnderecos;

import java.io.*;

/**
 *
 * @author glaucoroberto
 */
public class gerador {
    private static byte[] b = new byte[1];
    
    
    
    public static void main(String[] args){
        File arquivo = new File("teste.bin");
        try( OutputStream os = new FileOutputStream(arquivo) ){
            //byte[] b = {50,51,52,53};
           // String string = "Glauco";
            
            
            
            
            System.out.println("Gravou:");
            int numero = 10;
            for(int a=0; a < numero; a++ ){
                os.write( a );
                System.out.println( Integer.toBinaryString(a) );
            }
            os.write( numero );
            
            //os.write( b );
            //os.write( string.getBytes() );
            os.flush();
        }catch(IOException ex){
            ex.printStackTrace();
        }
 
        try( InputStream is = new FileInputStream(arquivo) ){
            System.out.println("\n\nLeu:");
            int content;
            while ( (content = (byte) is.read() ) != -1) {

                System.out.println( Integer.toBinaryString(content) );
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
}
