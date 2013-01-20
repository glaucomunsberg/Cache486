public class Celula {

    private boolean val[];
    private int tag[];

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
        for(int a=0; a < val.length;a++){
            val[a] = false;
        }
    }
    
    /**
     * Retorna o tamanho da associatividade
     *      da célula
     * @return int assoc
     */
    public int getAssociatividadeLength(){
        return val.length;
    }
    
    /**
     * Método que retorna a validade da celula
     * @param int indice na celula
     * @return boolean true | false
     */
    public boolean getValidade(int indice){
        return val[indice];
    }
    
    /**
     * Método que seta como válido o inidice,
     *  ou seja, o campo está válido e não está
     *  mais vazio.
     * @param indice 
     */
    public void setValido(int indice){
        this.val[indice] = true;
    }
    
    /**
     * Método que retorna a tag armazenada
     * @param int indice
     * @return int tag
     */
    public int getTag(int indice){
        return tag[indice] ;
    }
    
    /**
     * insere a tag no indice descriminado
     * @param int n_assoc
     * @param int tag 
     */
    public void setTag(int n_assoc, int tag){
        this.tag[n_assoc] = tag;
    }
    

}
