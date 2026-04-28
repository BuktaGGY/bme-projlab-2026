public class JegToro extends KotroFej {

    // Konstruktor a Szkeleton névvel
    public JegToro(String name) {
        super(300);
    }


	/**
     * Feltöri a jégpáncélt az aktuális sávon, fizikai erővel járhatóvá téve azt a forgalom számára.
     * * @param sav           Az a sáv, amelyen a jégtörés történik.
     * @param szomszedosSav Az ősosztály miatt átvett paraméter, de ez a fej nem használja.
     * @param kotro         A műveletet végző hókotró referenciája.
     */
    @Override
    public void takarit(Sav sav, Hokotro kotro) {
        sav.jegTores();
    }
}