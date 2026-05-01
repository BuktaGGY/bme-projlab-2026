/**
 * A szimulációban szereplő összes jármű (Autó, Busz, Hókotró) absztrakt ősosztálya.
 * Tartalmazza a mozgáshoz és az útvonalkövetéshez szükséges alapvető tulajdonságokat
 * és metódusokat.
 */
public abstract class Jarmu {
    /**
     * Jarmu jelenlegi állapota
     */
    protected JarmuAllapot allapot;

    /** A jármű egyedi azonosítója. */
    protected final String id;
    
    /** A jármű aktuális pozíciója az adott sávon belül. */
    protected int pozicioASavon;
    
    /** A jármű aktuális sebessége. */
    protected int sebesseg;
    
    /** Az a sáv, amelyen a jármű éppen tartózkodik. */
    protected Sav aktualisSav;
    
    /** A jármű által követett útvonal, útszakaszok sorozataként. */
    protected Utszakasz[] Utvonal;

    /**
     * Alapértelmezett konstruktor.
     * (A regisztrációt a Skeletonban a leszármazottak végzik el).
     */
    public Jarmu(String id) {
        this.id = id;
        Utvonal = new Utszakasz[1];
    }

    /**
     * Absztrakt metódus a jármű állapotának (pl. mozgásképtelenség, 
     * elakadás) körönkénti frissítésére.
     */
    public abstract void frissitAllapot();

    /**
     * Frissíti a jármű útvonalát (pl. újratervezés után).
     */
    public void utvonalFrissit() {

    }

    /**
     * Lépteti a járművet a megadott útszakaszon, ellenőrzi, hogy ki tud-e szabadulni
     * az elakadt auto.
     * @param utszakasz Az útszakasz (vagy annak egy része), amin a jármű mozog.
     */
    public void mozog(Object utszakasz) {

    }

    /**
     * Áthelyezi a járművet egy megadott szomszédos sávba.
     * @param szomszedosSav Szomszéd sáv
     */
    public void savValtas(Sav szomszedosSav) {

        aktualisSav = szomszedosSav;
    }

    /**
     * Beállítja a jármű új állapotát (pl. HALAD, RONCS).
     * @param allapot Az új állapot.
     */
    public void setAllapot(JarmuAllapot allapot) {
        this.allapot = allapot;
    }

    /**
     * Beállítja melyik sávon tartózkodik jelenleg a jármű
     * @param sav Aktuális sáv
     */
    public void setStartSav(Sav sav) {
        aktualisSav = sav;
    }


    public void statKiir(){

    }

    // instanceof elkerulese erdekeben
    public void megcsuszik() {}
    public void balesetezik() {}
    public void megsemmisites() {}
}