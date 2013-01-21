import java.util.Random;
import java.util.LinkedList;
public class Cache {

    LinkedList<Integer> historicoCache = new LinkedList<Integer>();
    Celula celulas[];
    Cache nivelInferior;
    private int mBits_offset;
    private int mBits_indice;
    private int mBits_tag;
    private int indice;
    private int tag;
    private int endereco;
    private int num_enderecos_processados;
    private int num_hits;
    private int numMissCompulsorio;
    private int numMissCapacidade;
    private int numMissConflito;
    private int num_misses;
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
        num_enderecos_processados = num_hits = num_misses = 0;
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
        
        historicoCache.add(enderecoRecebido);

        this.prepararEndereco(enderecoRecebido);
        this.num_enderecos_processados++;
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
        
        if(resultadoDaProcura){
            this.num_hits+=1;
        }else{
            this.num_misses+=1;
        }
        
        
       return resultadoDaProcura;
        
    }
    
    /**
     * Método que procura nas celula (linha da cache) igual a this.indice
     *  se o valor da this.tag está presente
     * @return 
     */
    private boolean acharEnderecoNaCache(){
        boolean achouEndereco = false;
        
        for(int n_assoc=0; n_assoc < celulas[0].getAssociatividadeLength(); n_assoc++ ){
            if( celulas[this.indice].getValidade(n_assoc)){
                if(celulas[this.indice].getTag(n_assoc) == this.tag){
                    achouEndereco = true;
                    break;
                }
            }
        }
        
        if(achouEndereco){
            return achouEndereco;
        }else{
            if(this.nivelInferior != null){
                achouEndereco = this.nivelInferior.manipula(endereco);
            }
            
        }
        return achouEndereco;
    }
    
    /**
     * Método que calcula o indice e tag para o endereço manipulado
     * @param int enderecoRecebido 
     */
   private void prepararEndereco(int enderecoRecebido){
       try{
            if (enderecoRecebido < 0){
                this.endereco = ~enderecoRecebido;
                this.endereco-=this.endereco;
                System.out.println(this.endereco);
            } else{
                this.endereco = enderecoRecebido;
            }
            
            Double aa = Math.pow(2,this.getmBits_indice())-1;
            this.indice = ( endereco >> this.getmBits_offset());
            this.indice = ( this.indice & aa.byteValue() );
            this.tag = ( this.endereco >> (byte)(this.mBits_offset + this.mBits_indice) );
            System.out.println("Endereço:"+Integer.toBinaryString(enderecoRecebido) +"\n  Indice:"+Integer.toBinaryString(indice) +"\n  Tag:"+Integer.toBinaryString(this.tag) +"\n");
        }catch(Exception e){
            System.out.println("Erro 5.101! Leia o 'README'");
        }
        
        
   }
   
     /**
     * Aplica a politica Radomica da inserção na linha manipulada ( this.indice). 
     * a celula (linha da cache) manipulada receberá randomicamente em um
     *  das suas posições associativas
     */
    private void politicaRandomica(){
        int num_random_assoc = celulas[0].getAssociatividadeLength();
        if( num_random_assoc != 1){
            num_random_assoc = gerar.nextInt( celulas[0].getAssociatividadeLength()-1 );
        }else{
            num_random_assoc = 0;
        }
        
        try{
            System.out.println(this.celulas.length);
            /*for(int i = 0; i < this.celulas.length; i++){
                System.out.println(i +" "+celulas[i].getTag(0)+" "+celulas[i].getValidade(0));
            
            }*/
            
            
            //if (celulas[this.indice])
            
            celulas[this.indice].setValido(num_random_assoc);
            celulas[this.indice].setTag(num_random_assoc, this.tag);            
        }catch(Exception e){
            System.out.println("Erro 5.102! Leia o 'README'");
        }

    }
    
    
    /**
     * Aplica a politica LRU da inserção na linha manipulada ( this.indice). 
     *  a celula (linha da cache) manipulada receberá o dado na mais antiga das inserções
     */
   private void politicaLRU(){
       System.out.println("\nMiss\nPolitica não implementada");

   }
   
   public String gerarRelatorio(){
       String retorno = "";
        if(this.nivelInferior != null){
            retorno = "\nL2\n"+this.nivelInferior.gerarRelatorio();
        }
        
        double missRatio, hitRatio;
        try{
            missRatio = (num_misses * 100) / this.num_enderecos_processados;
            hitRatio  = (num_hits * 100) / this.num_enderecos_processados;
       }catch(Exception e){
            missRatio = 0.0;
            hitRatio  = 0.0;
       }
       return String.format("Total Endereços: %d\nTotal Hits: %d\nTotal Misses: %d\nHit Ratio: %.1f \nMiss Ratio: %.1f\n============"+retorno, this.num_enderecos_processados, this.num_hits, this.num_misses, hitRatio, missRatio);
   }
   
   
}