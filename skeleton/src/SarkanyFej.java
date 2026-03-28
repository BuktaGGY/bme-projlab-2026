public class SarkanyFej extends KotroFej {

    // Konstruktor a Szkeleton névvel
    public SarkanyFej(String name) {
        super(800);
        Skeleton.ctor(this, name); // Regisztráció
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
        Skeleton.call(this, "takarit", Skeleton.getName(sav), Skeleton.getName(kotro)); // Hívás jelzése

        // Ellenőrizzük, hogy van-e elég üzemanyag
        if (kotro.getBiokerozin() > 0) {
            sav.mindentEltuntet();
            // Fogyasztjuk a kerozint
            kotro.setBiokerozin(kotro.getBiokerozin() - 1); 
        }

        Skeleton.ret(); // Visszatérés jelzése
    }
}