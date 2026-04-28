public class SarkanyFej extends KotroFej {

    // Konstruktor a Szkeleton névvel
    public SarkanyFej(String name) {
        super(800);
    }


	/**
     * Biokerozin felhasználásával azonnal felolvaszt és eltüntet minden havat és jeget 
     * az aktuális sávról. A működéshez a hókotrónak rendelkeznie kell kerozinnal.
     * * @param sav           Az a sáv, amelyen az olvasztás történik.
     * @param szomszedosSav Az ősosztály miatt átvett paraméter, de ez a fej nem használja.
     * @param kotro         A műveletet végző hókotró referenciája, ahonnan a kerozint fogyasztja.
     */
    @Override
    public void takarit(Sav sav, Hokotro kotro) {
        if (kotro.getBiokerozin() > 0) {
            sav.mindentEltuntet();
            kotro.setBiokerozin(kotro.getBiokerozin() - 1); 
        }
    }
}