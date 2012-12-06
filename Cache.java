public class Cache {

    Celula celulas[];
    Cache l2;


    public Cache (int nsets, int bsize, int assoc){
        
        celulas[nsets] = new Celula(assoc);

    }

    public Cache (int nsets, int bsize, int assoc, Cache l2){

        celulas[nsets] = new Celula(assoc);
        this.l2 = l2;
    }

}
