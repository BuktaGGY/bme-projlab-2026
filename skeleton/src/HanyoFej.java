public class HanyoFej {
	//a kotrofej ara
	int ar;
	
    public HanyoFej(String name) {
        ar = 200; 
		Skeleton.ctor(this, name);
    }

	/**
     * Véglegesen eltünteti (megsemmisíti) a havat az aktuális sávról. 
     * A szomszédos sávot nem használja és nem módosítja.
     * * @param sav           Az a sáv, amelyen a hókotró éppen tartózkodik és takarít.
     * @param szomszedosSav Az ősosztály miatt átvett paraméter, de ez a fej nem használja.
     * @param kotro         A műveletet végző hókotró referenciája.
     */
    
    public void takarit(Sav sav, Hokotro kotro) {
		Skeleton.call(this, "takarit", Skeleton.getName(sav), Skeleton.getName(kotro));
		
        // Csak az aktuális sávról tünteti el a havat, véglegesen
        sav.hoEltuntet();
		
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