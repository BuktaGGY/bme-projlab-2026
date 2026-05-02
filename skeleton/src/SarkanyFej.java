public class SarkanyFej extends KotroFej {

    public SarkanyFej() {
        super(800);
    }

	/**
     * Biokerozin felhasználásával azonnal felolvaszt és eltüntet minden havat és jeget 
     * az aktuális sávról. A működéshez a hókotrónak rendelkeznie kell kerozinnal.
     * @param sav           Az a sáv, amelyen az olvasztás történik.
     * @param kotro         A műveletet végző hókotró referenciája, ahonnan a kerozint fogyasztja.
     */
    @Override
    public void takarit(Sav sav, Hokotro hk) {
        sav.hoEltuntet(); 
        if (sav.getAllapot() == SavAllapot.JEGPANCEL) {
            sav.setSavAllapot(SavAllapot.TISZTA);
        }
    }
    @Override
    public String getFejTipus() {
        return "sarkany";
    }
}