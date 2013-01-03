public class Cache {

    Celula celulas[];
    Cache next;
    int mBits_offset;
    int mBits_indice;
    int mBits_tag;
    /**
     *  Construtor de uma cache
     * @param int nsets númerode celulas de memória
     * @param int bsize tamanho de cada célula
     * @param assoc nível de associatividade
     */
    public Cache (int nsets, int bsize, int assoc){
        
        celulas[nsets] = new Celula(assoc);
        this.next = null;
        
        mBits_offset = (int)(Math.log(bsize) / Math.log(2));
        mBits_indice = (int)(Math.log(nsets) / Math.log(2));
        mBits_tag = 32 -  mBits_offset - mBits_indice;
        System.out.println("mBits_offset: "+mBits_offset+"mBits_indice: "+mBits_indice+"mBits_tag: "+mBits_tag);
    }
    
    /**
     * Construtor de uma cache
     * @param int nsets númerode celulas de memória
     * @param int bsize tamanho de cada célula
     * @param assoc nível de associatividade
     * @param Cache l2 cache atuante como secundária
     */
    public Cache (int nsets, int bsize, int assoc, Cache l2){
        
        try{
            celulas = new Celula[nsets];
            for(int a=0; a < nsets; a++){
                celulas[a] = new Celula(assoc);
            }
        }catch(Exception e){
            System.out.println("Error 5.100! Leia o 'README'"+e.toString());
        }
        
        this.next = l2;
        
        mBits_offset = (int)(Math.log(bsize) / Math.log(2));
        mBits_indice = (int)(Math.log(nsets) / Math.log(2));
        mBits_tag = 32 -  mBits_offset - mBits_indice;
        
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

}
