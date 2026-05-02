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

    /** Az aktuális útszakasz indexe a kijelölt Útvonal tömbben. */
    protected int utvonalIndex = 0;

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
     * Lépteti a járművet a megadott útszakaszon. Ha eléri a sáv végét,
     * automatikusan átlép a következő útszakaszra.
     * @param utszakasz Ezt a paramétert az ősosztályból megörököltük, de a belső logikát használjuk.
     */
    public void mozog(Object utszakasz) {
        if (allapot != JarmuAllapot.HALAD) return;

        pozicioASavon += sebesseg;
        int aktualisHossz = aktualisSav.getHossz();

        if (pozicioASavon >= aktualisHossz) {
            int maradek = pozicioASavon - aktualisHossz;

            if (Utvonal != null && utvonalIndex + 1 < Utvonal.length && Utvonal[utvonalIndex + 1] != null) {
                utvonalIndex++;
                Utszakasz kovetkezoUt = Utvonal[utvonalIndex];
                Sav regiSav = aktualisSav;

                aktualisSav = kovetkezoUt.getSavok().get(0);
                pozicioASavon = maradek;

                System.out.println("[ESEMENY] " + id + " | UJ SZAKASZRA LEPETT | " + regiSav.getId() + " -> " + aktualisSav.getId() + " savra (uj poz: " + pozicioASavon + ")");
            } else {
                pozicioASavon = aktualisHossz;
                celbaErt();
            }
        } else {
            System.out.println("[ESEMENY] " + id + " | MOZGOTT | " + aktualisSav.getId() + " savban (uj pozicio: " + pozicioASavon + ")");
        }
    }


    /**
     * Hook metódus, amit a leszármazottak felülírhatnak, hogy egyedi 
     * logikát hajtsanak végre, amikor az útvonal végére érnek.
     */
    protected void celbaErt() {
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