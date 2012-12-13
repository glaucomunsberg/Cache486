public class Cache {

    Celula celulas[];
    Cache l2;

    /**
     *  Construtor de uma cache
     * @param int nsets númerode celulas de memória
     * @param int bsize tamanho de cada célula
     * @param assoc nível de associatividade
     */
    public Cache (int nsets, int bsize, int assoc){
        
        celulas[nsets] = new Celula(assoc);

    }
    
    /**
     * Construtor de uma cache
     * @param int nsets númerode celulas de memória
     * @param int bsize tamanho de cada célula
     * @param assoc nível de associatividade
     * @param Cache l2 cache atuante como secundária
     */
    public Cache (int nsets, int bsize, int assoc, Cache l2){

        celulas[nsets] = new Celula(assoc);
        this.l2 = l2;
    }

}
