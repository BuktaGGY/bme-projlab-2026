public class SarkanyFej extends KotroFej {

    public SarkanyFej() {
        super(800);
    }


	/**
     * Biokerozin felhasználásával azonnal felolvaszt és eltüntet minden havat és jeget 
     * az aktuális sávról. A működéshez a hókotrónak rendelkeznie kell kerozinnal.
     * * @param sav           Az a sáv, amelyen az olvasztás történik.
     * @param kotro         A műveletet végző hókotró referenciája, ahonnan a kerozint fogyasztja.
     */
    @Override
    public void takarit(Sav sav, Hokotro kotro) {
        if (kotro.getBiokerozin() > 0) {
            sav.mindentEltuntet();
            kotro.setBiokerozin(kotro.getBiokerozin() - 1); 
        }
    }
    @Override
    public String getFejTipus() {
        return "sarkany";
    }
}