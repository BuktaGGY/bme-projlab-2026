public class SoproFej {
    //a kotrofej ara
	int ar;
	
    // A szkeleton a inicializáláskor adja meg a nevet (pl. "sf")
    public SoproFej(String name) {
        ar = 100; 
        // 1. Lépés: Regisztráljuk magunkat a Skeletonban!
        Skeleton.ctor(this, name); 
    }


	/**
     * Eltakarítja a havat az aktuális sávról, és áttolja azt a megadott szomszédos sávra.
     * * @param sav           Az a sáv, amelyen a hókotró éppen tartózkodik és takarít.
     * @param szomszedosSav A szomszédos sáv, ahová a letakarított havat áthelyezi (ha nem null).
     * @param kotro         A műveletet végző hókotró referenciája.
     */
    
    public void takarit(Sav sav, Hokotro kotro) {
        // 2. Lépés: Jelezzük a hívást
        Skeleton.call(this, "takarit", Skeleton.getName(sav), Skeleton.getName(kotro)); 

        int eltuntetettHo = sav.hoEltuntet();
		
		Sav szomszedosSav = sav.getSzomszedosSav();
		
        if (szomszedosSav != null && eltuntetettHo > 0) {
            szomszedosSav.hoNovel(eltuntetettHo);
        }
        
        // 3. Lépés: Jelezzük a visszatérést
        Skeleton.ret(); 
    }
	
	//getter, setter
	public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }
}