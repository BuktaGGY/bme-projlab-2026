/**
 * A szimulációban szereplő összes jármű (Autó, Busz, Hókotró) absztrakt ősosztálya.
 * Tartalmazza a mozgáshoz és az útvonalkövetéshez szükséges alapvető tulajdonságokat
 * és metódusokat.
 */
public abstract class Jarmu {
    
    /** A jármű egyedi azonosítója. */
    protected int id;
    
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
    public Jarmu() {
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
        Skeleton.call(this, "utvonalFrissit");
        Skeleton.ret();
    }

    /**
     * Lépteti a járművet a megadott útszakaszon.
     * @param utszakasz Az útszakasz (vagy annak egy része), amin a jármű mozog.
     */
    public void mozog(Object utszakasz) {
        Skeleton.call(this, "mozog", "utszakasz");

        if(this.getClass() == Auto.class) {

            Sav s =this.aktualisSav;
            if(s.getJobbSavAllapot() != SavAllapot.BLOKKOLT) {
                savValtas(s.getSzomszedosSav());
                setAllapot(JarmuAllapot.HALAD);
                Skeleton.ret();
                return;
            }
            if(s.getBalSavAllapot() != SavAllapot.BLOKKOLT){
                savValtas(s.getBalSav());
                setAllapot(JarmuAllapot.HALAD);
                Skeleton.ret();
                return;
            }
        }
    }

    /**
     * Áthelyezi a járművet egy megadott szomszédos sávba.
     * @param szomszedosSav Szomszéd sáv
     */
    public void savValtas(Sav szomszedosSav) {
        Skeleton.call(this, "savValtas", "szomszedosSav");

        aktualisSav = szomszedosSav;
        Skeleton.ret("void");
    }

    /**
     * Beállítja a jármű új állapotát (pl. HALAD, RONCS).
     * @param allapot Az új állapot.
     */
    public void setAllapot(JarmuAllapot allapot) {
        // Itt a Skeleton.getName()-et használjuk a kérésednek megfelelően!
        Skeleton.call(this, "setAllapot", Skeleton.getName(allapot));
        Skeleton.ret();
    }

    /**
     * Beállítja melyik sávon tartózkodik jelenleg a jármű
     * @param sav Aktuális sáv
     */
    public void setStartSav(Sav sav) {
        Skeleton.call(this, "setStartSav", Skeleton.getName(sav));

        aktualisSav = sav;

        Skeleton.ret();
    }
}