public class Celula {

    boolean val[];
    int tag[];

    /**
     * Construtor que começa apenas com o
     *  nível 1 de associatividade
     */
    public Celula(){
        val = new boolean[1];
        tag = new int[1];
    }
    
    /**
     * Construtor segundo a associatividade
     * @param int assoc 
     */
    public Celula(int assoc){
        val = new boolean[assoc];
        tag = new int[assoc];
    }
    
    /**
     * Retorna o tamanho da associatividade
     *      da célula
     * @return int assoc
     */
    public int getAssociatividadeLength(){
        return val.length;
    }

}
