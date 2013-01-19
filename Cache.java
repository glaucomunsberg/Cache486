import java.util.Random;
public class Cache {

    Celula celulas[];
    Cache nivelInferior;
    private int mBits_offset;
    private int mBits_indice;
    private int mBits_tag;
    private int indice;
    private int tag;
    private int endereco;
    private String politicaAplicada;
    private Random gerar;
    /**
     *  Construtor de uma cache
     * @param int nsets númerode celulas de memória
     * @param int bsize tamanho de cada célula
     * @param assoc nível de associatividade
     * @param politica política usada: {'random','LRU'}
     */
    public Cache (int nsets, int bsize, int assoc, String politica){
        
        try{
            celulas = new Celula[nsets];
            for(int a=0; a < nsets; a++){
                celulas[a] = new Celula(assoc);
            }
        }catch(Exception e){
            System.out.println("Error 5.100! Leia o 'README'"+e.toString());
        }
        mBits_offset = (int)(Math.log(bsize) / Math.log(2));
        mBits_indice = (int)(Math.log(nsets) / Math.log(2));
        mBits_tag = 32 -  mBits_offset - mBits_indice;
        politicaAplicada = politica.toLowerCase();
        gerar = new Random();
        System.out.println("mBits_offset: "+mBits_offset+" mBits_indice: "+mBits_indice+" mBits_tag: "+mBits_tag+" Política Usada:"+politicaAplicada);
    }
    
    /**
     * Construtor de uma cache
     * @param int nsets númerode celulas de memória
     * @param int bsize tamanho de cada célula
     * @param assoc nível de associatividade
     * @param politica política usada: {'random','LRU'}
     * @param Cache l2 cache atuante como secundária
     */
    public Cache (int nsets, int bsize, int assoc, String politica, Cache l2){
        this(nsets,bsize,assoc,politica);
        this.nivelInferior = l2;
    }
    
    /**
     * Retorna o nómerode bits usados como offiset
     * @return
     */
    public int getmBits_offset(){
        return mBits_offset;
    }
    
    /**
     * Retorna o número de bits usados
     *  para o índice
     * @return 
     */
    public int getmBits_indice(){
        return mBits_indice;
    }
    
    /**
     * Retorna o número de bits usados
     *  para serem tag
     * @return 
     */
    public int getmBits_tag(){
        return mBits_tag;
    }
    
    /**
     * Método que retorna o resultado da procura do endereço
     *  boolean{true - Hit | false - Miss}
     * @param enderecoRecebido
     * @return boolean resultadoDaProcura
     */
    public boolean manipula(int enderecoRecebido){
       
        this.prepararEndereco(enderecoRecebido);
        boolean resultadoDaProcura = acharEnderecoNaCache();
        
        
        /**
         * Se o valor não está presente, então aplica a política
         *  adeguada
         */
        if(!resultadoDaProcura){
            if( "random".equals(politicaAplicada) ){
                this.politicaRandomica();
            }else{
            if( "lru".equals(politicaAplicada) ){
                    this.politicaLRU();
                }else{
                this.politicaRandomica();
            }
            }
        }
        
        
        
       return resultadoDaProcura;
        
    }
    
    /**
     * Método que procura nas celula (linha da cache) igual a this.indice
     *  se o valor da this.tag está presente
     * @return 
     */
    private boolean acharEnderecoNaCache(){
        boolean achou = false;
        
        for(int n_assoc=0; n_assoc < celulas[0].getAssociatividadeLength(); n_assoc++ ){
            if( celulas[this.indice].getValidade(n_assoc)){
                if(celulas[this.indice].getTag(n_assoc) == this.tag){
                    achou = true;
                    break;
                }
            }
        }
        
        if(achou){
            return achou;
        }else{
            if(this.nivelInferior != null){
                achou = this.nivelInferior.manipula(endereco);
            }
            
        }
        return achou;
    }
    
    /**
     * Método que calcula o indice e tag para o endereço manipulado
     * @param int enderecoRecebido 
     */
   private void prepararEndereco(int enderecoRecebido){
       try{
           this.endereco = enderecoRecebido;
            Double aa = Math.pow(2,this.getmBits_indice())-1;
            this.indice = ( endereco >>> this.getmBits_offset()  );
            this.indice = ( this.indice & aa.byteValue() );
            this.tag = ( this.endereco >>> (byte)(this.mBits_offset + this.mBits_indice) );
                    System.out.println("Endereço:"+Integer.toBinaryString(enderecoRecebido) +"\n  Indice:"+Integer.toBinaryString(indice) +"\n  Tag:"+Integer.toBinaryString(this.tag) +"\n");
       }catch(Exception e){
           System.out.println("alguma coisa");
        }
        
        
   }
   
     /**
     * Aplica a politica Radomica da inserção na linha manipulada ( this.indice). 
     * a celula (linha da cache) manipulada receberá randomicamente em um
     *  das suas posições associativas
     */
    private void politicaRandomica(){
        int n_random_assoc = celulas[0].getAssociatividadeLength();
        if( n_random_assoc != 1){
            n_random_assoc = gerar.nextInt( celulas[0].getAssociatividadeLength()-1 );
        }else{
            n_random_assoc =0;
        }
        
        celulas[this.indice].setValido(n_random_assoc);
        celulas[this.indice].setTag(n_random_assoc, this.tag);
    }
    
    /**
     * Aplica a politica LRU da inserção na linha manipulada ( this.indice). 
     *  a celula (linha da cache) manipulada receberá o dado na mais antiga das inserções
     */
   private void politicaLRU(){
       System.out.println("\nMiss\nPolitica não implementada");

   }
}
