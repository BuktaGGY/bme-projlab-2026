public class Soszoro {
	//a kotrofej ara
	int ar;

    // Konstruktor a Szkeleton névvel
    public Soszoro(String name) {
        ar = 150;
        Skeleton.ctor(this, name); // Regisztráció
    }


	/**
     * Sót szór az aktuális sávra a hókotró saját sókészletéből, ami megolvasztja 
     * a jeget és megakadályozza a további fagyást. A működéshez sóra van szükség.
     * * @param sav           Az a sáv, amelyre a sót szórja.
     * @param szomszedosSav Az ősosztály miatt átvett paraméter, de ez a fej nem használja.
     * @param kotro         A műveletet végző hókotró referenciája, ahonnan a sót fogyasztja.
     */
    
    public void takarit(Sav sav, Hokotro kotro) {
        Skeleton.call(this, "takarit", Skeleton.getName(sav), Skeleton.getName(kotro)); // Hívás jelzése

        // Ellenőrizzük, hogy van-e só a tartályban
        if (kotro.getSo() > 0) {
            sav.sotSzor();
            // Fogyasztjuk a sót
            kotro.setSo(kotro.getSo() - 1);
        }

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