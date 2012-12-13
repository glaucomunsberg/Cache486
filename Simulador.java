import java.util.Scanner;
import java.math.*;

public class Simulador {

    Cache l1;
    Scanner leitor;
    public static void main(String[] args) {

        if(args.length == 3){

            try {
                Simulador main = new Simulador();
                main.iniciar(args[0] , args[1], args[2]);
                main.finalizar();
            
            } catch (Exception e){
                System.out.println("Ooops! Alguma coisa deu errado!");
            }

        } else {
            System.out.println("Ooops! Número de parametros insuficientes. Leia o 'README' ítem 3.1.4;");
            System.exit(0);
        }

    }

    public void iniciar(String nsets, String bsize, String assoc){
        int paramNSets, paramBSize, paramAssoc;
        paramNSets = 1;
        paramBSize = 32;
        paramAssoc = 1;
        
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
            System.out.printf("Ooops! Algum parametro está errado. Leia o 'README' ítem 3.1.3;");
        }
        
        int mBits_offset;
        int mBits_indice;
        int mBits_tag;
        int indice, tag, endereco;
        
        leitor  = new Scanner(System.in);
        l1      = new Cache(paramNSets, paramBSize, paramAssoc);
        
        while(leitor.hasNextByte(paramBSize)){
            endereco = leitor.nextByte(paramBSize);
                System.out.print("Endereço: ");
                System.out.println(Integer.toBinaryString(endereco));

        }

    }
    public void finalizar(){
        System.out.println("Execução finalizada com sucesso!");
    }

}
