public class HanyoFej extends KotroFej {

    public HanyoFej(String name) {
        super(200); 
		Skeleton.ctor(this, name);
    }

	/**
     * Véglegesen eltünteti (megsemmisíti) a havat az aktuális sávról. 
     * A szomszédos sávot nem használja és nem módosítja.
     * * @param sav           Az a sáv, amelyen a hókotró éppen tartózkodik és takarít.
     * @param szomszedosSav Az ősosztály miatt átvett paraméter, de ez a fej nem használja.
     * @param kotro         A műveletet végző hókotró referenciája.
     */
    @Override
    public void takarit(Sav sav, Sav szomszedosSav, Hokotro kotro) {
		Skeleton.call(this, "takarit");
		
        // Csak az aktuális sávról tünteti el a havat, véglegesen
        sav.hoEltuntet();
		
		Skeleton.ret();
    }
}