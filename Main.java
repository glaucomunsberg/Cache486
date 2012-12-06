import java.util.Scanner;
import java.math.*;

public class Main {

    Cache l1;
    Scanner leitor;
    public static void main(String[] args) {

        if(args.length == 3){

            try {
                Main main = new Main();
                main.iniciar(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            
            } catch (Exception e){


            }

        } else {

            System.exit(0);

        }

    }

    public void iniciar(int nsets, int bsize, int assoc){
        leitor  = new Scanner(System.in);
        l1      = new Cache(nsets, bsize, assoc);
        int endereco;
        while(leitor.hasNextByte(bsize)){
           endereco = leitor.nextByte(bsize);

           
           



        }




    }

}
