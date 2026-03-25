public class SoproFej extends KotroFej {
    
    // A szkeleton a inicializáláskor adja meg a nevet (pl. "sf")
    public SoproFej(String name) {
        super(100); 
        // 1. Lépés: Regisztráljuk magunkat a Skeletonban!
        Skeleton.ctor(this, name); 
    }


	/**
     * Eltakarítja a havat az aktuális sávról, és áttolja azt a megadott szomszédos sávra.
     * * @param sav           Az a sáv, amelyen a hókotró éppen tartózkodik és takarít.
     * @param szomszedosSav A szomszédos sáv, ahová a letakarított havat áthelyezi (ha nem null).
     * @param kotro         A műveletet végző hókotró referenciája.
     */
    @Override
    public void takarit(Sav sav, Sav szomszedosSav, Hokotro kotro) {
        // 2. Lépés: Jelezzük a hívást!
        Skeleton.call(this, "takarit"); 

        int eltuntetettHo = sav.hoEltuntet();
        if (szomszedosSav != null && eltuntetettHo > 0) {
            szomszedosSav.addHo(eltuntetettHo);
        }
        
        // 3. Lépés: Jelezzük a visszatérést!
        Skeleton.ret(); 
    }
}