public class JegToro{
	
	//a kotrofej ara
	int ar;
    // Konstruktor a Szkeleton névvel
    public JegToro(String name) {
        ar = 300;
        Skeleton.ctor(this, name); // Regisztráció
    }


	/**
     * Feltöri a jégpáncélt az aktuális sávon, fizikai erővel járhatóvá téve azt a forgalom számára.
     * * @param sav           Az a sáv, amelyen a jégtörés történik.
     * @param szomszedosSav Az ősosztály miatt átvett paraméter, de ez a fej nem használja.
     * @param kotro         A műveletet végző hókotró referenciája.
     */
    
    public void takarit(Sav sav, Hokotro kotro) {
        Skeleton.call(this, "takarit", Skeleton.getName(sav), Skeleton.getName(kotro)); // Hívás jelzése

        // Utasítja a sávot a jég feltörésére
        sav.jegTores();

        Skeleton.ret(); // Visszatérés jelzése
    }
	
	//getter, setter
	public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }
}