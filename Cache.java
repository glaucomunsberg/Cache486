
public class Cache {

    Celula celulas[];
    Cache next;
    private int mBits_offset;
    private int mBits_indice;
    private int mBits_tag;
    private int indice;
    private int tag;
    private int endereco;
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
    
    public void manipula(int enderecoRecebido){
        this.endereco = enderecoRecebido;
        System.out.println(this.endereco);
        Double aa = Math.pow(2,this.getmBits_indice())-1;
        this.indice = ( endereco >>> this.getmBits_offset()  );
        this.indice = ( this.indice & aa.byteValue() );
        System.out.println("Indice:"+Integer.toBinaryString(indice) +"\n");
        
        this.tag = ( this.endereco >>> (byte)(this.mBits_offset + this.mBits_indice) );
        
        System.out.println("Tag:"+Integer.toBinaryString(this.tag) +"\n");
    }

}
