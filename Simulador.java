import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.math.*;

public class Simulador {

    Cache l1;
    Scanner leitor;
    public static void main(String[] args) {

        if(args.length == 4){

            try {
                Simulador main = new Simulador();
                main.iniciar(args[0] , args[1], args[2], args[3]);
                main.finalizar();
            
            } catch (Exception e){
                System.out.println("Erro 5.000! Leia o 'README'"+e.toString());
            }

        } else {
            System.out.println("Erro 5.001! Leia o 'README'");
            System.exit(0);
        }

    }

    public void iniciar(String nsets, String bsize, String assoc, String nomeArquivoBin){
        int paramNSets, paramBSize, paramAssoc;
        paramNSets = 1;
        paramBSize = 32;
        paramAssoc = 1;
        
        /**
         * Faz a conversão de String para int
         */
        try{
            paramNSets = Integer.parseInt(nsets);
            if(paramNSets < 1){
                paramNSets = 1;  
            }
                
            paramBSize = Integer.parseInt(bsize);
            if(paramBSize < 1){
                paramBSize = 32;  
            }
                
            paramAssoc = Integer.parseInt(assoc);
            if(paramAssoc < 1){
                paramAssoc = 1;  
            }
            
            
        }catch(Exception e){
            System.out.println("Error 5.002! Leia o 'README'");
        }
        
        /**
         * Cria as caches
         */
        l1 = new Cache(paramNSets,paramBSize,paramAssoc, null);
        System.out.println("mBits_offset: "+l1.getmBits_offset()+"mBits_indice: "+l1.mBits_indice+"mBits_tag: "+l1.getmBits_tag());
        
        int indice, tag, endereco;
        
        
        ArquivoBinario arquivo = new ArquivoBinario(nomeArquivoBin);
        while(arquivo.temProximaPalavra32()){
            System.out.println("Endereço de Memória: "+arquivo.getProximaPalavra32String());
            endereco = arquivo.getProximaPalavra32Bits();
            System.out.println(endereco);
            
            //indice = ( l1.mBits_offset >> endereco );
            //System.out.println(Integer.toBinaryString(indice));
            
        }

    }
    public void finalizar(){
        System.out.println("Execução finalizada com sucesso!");
    }

}

/**
 * Classe que manipula o arquivo
 * @author glaucoroberto
 */
class ArquivoBinario{
    File arquivo;
    InputStream arquivoStream;
    int conteudoLido;
    boolean terminouArquivo = false;
    
    private ArquivoBinario(){
        
    }
    
    /**
     * Abre o arquivo binário enviado como paramentro
     * 
     * @param nomeArquivo 
     */
    public ArquivoBinario(String nomeArquivo){
        arquivo = new File(nomeArquivo);

        try{
            arquivoStream = new FileInputStream(arquivo);
        }catch(IOException ex){
            System.out.printf("Error 5.900! Leia o 'README'");
        }
    }
    
    /**
     * Verifica se possui uma próxima palavra de 32bits
     *  ou equivalente a um int
     * @return true - se possúi | false - se não possúi
     */
    public boolean temProximaPalavra32(){
        if(terminouArquivo){
            return false;
        }
        try{
            if( (conteudoLido = (byte) arquivoStream.read() ) != -1 ){
                return true;
           }else{
                terminouArquivo = true;
                return false;
            }
        }catch(IOException ex){
            System.out.println("Error 5.901! Leia o 'README'"+"/nVeja o erro: "+ex.getMessage() );
            return false;
        }
    }
    
    /**
     * Retorna um integer com os 32bits
     * @return int
     */
    public int getProximaPalavra32Bits(){
        if(terminouArquivo){
            return -1;
        }
        return conteudoLido;
    }
    
    /**
     * Retorna os 32bits lidos na forma de String contendo 32bits em forma de caract.
     * @return String
     */
    public String getProximaPalavra32String(){
        if(terminouArquivo){
            return null;
        }
        return Integer.toBinaryString(conteudoLido);
    }
}
