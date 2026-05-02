public class HanyoFej extends KotroFej {

    public HanyoFej() {
        super(200); 
    }

	/**
     * Véglegesen eltünteti (megsemmisíti) a havat az aktuális sávról. 
     * A szomszédos sávot nem használja és nem módosítja.
     * @param sav           Az a sáv, amelyen a hókotró éppen tartózkodik és takarít.
     * @param kotro         A műveletet végző hókotró referenciája.
     */
    @Override
    public void takarit(Sav sav, Hokotro kotro) {
        sav.hoEltuntet();
        sav.zuzalekotEltuntet();
    }

    @Override
    public String getFejTipus() {
        return "hanyo";
    }
}