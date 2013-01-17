import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Simulador {

    Cache l1, l2;
    public static void main(String[] args) {

        if(args.length == 3){

            try {
                Simulador main = new Simulador();
                main.iniciar(args[0] , args[1], args[2]);
                main.finalizar();
            
            } catch (Exception e){
                System.out.println("Erro 5.000! Leia o 'README'"+e.toString());
            }

        } else {
            System.out.println("Erro 5.001! Leia o 'README'");
            System.exit(0);
        }

    }

    public void iniciar(String cache1, String cache2, String nomeArquivoBin){
        
        int nSetsCache1, bSizeCache1, associacaoCache1;
        int nSetsCache2, bSizeCache2, associacaoCache2;
        String[] configCache1, configCache2;
        
        nSetsCache1 = nSetsCache2 = 1;
        bSizeCache1 = bSizeCache2 =32;
        associacaoCache1 = associacaoCache2 = 1024;
        
        
        /**
         * Faz a conversão de String para int
         */
        try{
            
            /**
             * Recebe os parametros juntos na string cache1 = "<nsets_L1>:<bsize_L1>:<assoc_L1>"
             */
            configCache1 = cache1.split(":");
            configCache2 = cache2.split(":");
            
            nSetsCache1 = Integer.parseInt(configCache1[0]);
            if(nSetsCache1 < 1){
                nSetsCache1 = 1;  
            }
                
            bSizeCache1 = Integer.parseInt(configCache1[1]);
            if(bSizeCache1 < 1){
                bSizeCache1 = 4;  
            }
                
            associacaoCache1 = Integer.parseInt(configCache1[2]);
            if(associacaoCache1 < 1 || associacaoCache1 > nSetsCache1){
                associacaoCache1 = 1024;  
            }
            
            
            nSetsCache2 = Integer.parseInt(configCache2[0]);
            if(nSetsCache2 < 1){
                nSetsCache2 = 1;  
            }
            
            bSizeCache2 = Integer.parseInt(configCache2[1]);
            if(bSizeCache2 < 1){
                bSizeCache2 = 4;  
            }
            
            associacaoCache2 = Integer.parseInt(configCache2[2]);
            if(associacaoCache2 < 1 || associacaoCache2 > nSetsCache2){
                associacaoCache2 = 1024;  
            }
            
            
        }catch(Exception e){
            System.out.println("Error 5.002! Leia o 'README'");
        }
        
        /**
         * Cria as caches
         */
        
        l2 = new Cache(nSetsCache2,bSizeCache2,associacaoCache2,null);
        l1 = new Cache(nSetsCache1,bSizeCache1,associacaoCache1, l2);
        
        System.out.println("Cache 1:\n mBits_offset: "+l1.getmBits_offset()+"mBits_indice: "+l1.getmBits_indice()+"mBits_tag: "+l1.getmBits_tag()+"\nCache 2:\n mBits_offset: "+l2.getmBits_offset()+"mBits_indice: "+l2.getmBits_indice()+"mBits_tag: "+l2.getmBits_tag());
        
        
        int enderecoEmInteger;
        
        
        ArquivoBinario arquivo = new ArquivoBinario(nomeArquivoBin);
        while(arquivo.temProximaPalavra32()){
            System.out.println("Endereço de Memória: "+arquivo.getProximaPalavra32String());
            enderecoEmInteger = arquivo.getProximaPalavra32Bits();
            l1.manipula(enderecoEmInteger);
            
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
            boolean voltaOQue=false;
            for(int a=0; a <4;a++){
                if( (conteudoLido = (byte) arquivoStream.read() ) != -1 ){
                    voltaOQue = true;
                }else{
                    terminouArquivo = true;
                    voltaOQue = false;
                }
            }
            return voltaOQue;
            
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
