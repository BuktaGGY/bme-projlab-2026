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
     * Jelzi, hogy az aktuális útszakaszon fordított irányban (vege→eleje) halad-e a jármű.
     * Ha false: eleje→vege irány (normál). Ha true: vege→eleje irány (fordított).
     */
    protected boolean aktualisIranyForditott = false;

    /**
     * Vizuális megjelenítéshez használt tartalék sáv és pozíció.
     * Akkor töltjük ki, ha aktualisSav null-ra kerül (pl. célba ért autó vár),
     * hogy a View ne tüntesse el a járművet a várakozás alatt.
     */
    protected Sav displaySav = null;
    protected int displayPozicio = 0;

    /**
     * Alapértelmezett konstruktor.
     * (A regisztrációt a Skeletonban a leszármazottak végzik el).
     */
    public Jarmu(String id) {
        this.id = id;
        Utvonal = new Utszakasz[1];
        this.allapot = JarmuAllapot.HALAD; // JAVÍTÁS: Alapértelmezett állapot beállítása!
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
     */
    public void mozog() {
        if (allapot != JarmuAllapot.HALAD) return;

        pozicioASavon += sebesseg;
        int aktualisHossz = aktualisSav.getHossz();

        if (pozicioASavon >= aktualisHossz) {
            int maradek = pozicioASavon - aktualisHossz;

            if (Utvonal != null && utvonalIndex + 1 < Utvonal.length && Utvonal[utvonalIndex + 1] != null) {
                // Meghatarozza, melyik csomopontra ert a jarmu (az aktualis ut vegpontja)
                Utszakasz regiUt = aktualisSav.getSzuloUtszakasz();
                Csomopont erkezesiCsomopont = null;
                if (regiUt != null) {
                    erkezesiCsomopont = aktualisIranyForditott
                            ? regiUt.getEleje()
                            : regiUt.getVege();
                }

                utvonalIndex++;
                Utszakasz kovetkezoUt = Utvonal[utvonalIndex];
                Sav regiSav = aktualisSav;

                aktualisSav = kovetkezoUt.getSavok().get(0);
                pozicioASavon = maradek;

                // Az uj ut iranyt az erkezesi csomopont hatarozza meg
                if (erkezesiCsomopont != null) {
                    aktualisIranyForditott = (erkezesiCsomopont == kovetkezoUt.getVege());
                }

                ujSzakaszLog(regiSav);
            } else {
                pozicioASavon = aktualisHossz;
                celbaErt();
            }
        } else {
            mozgasLog();
        }
    }

    /**
     * Hook metódus a mozgás naplózására.
     * Leszármazottak felülírhatják (override), ha el akarják némítani, 
     * vagy máshogy szeretnék kiírni a mozgást.
     */
    protected void mozgasLog() {
        System.out.println("[ESEMENY] " + id + " | MOZGOTT | " + aktualisSav.getId() + " savban (uj pozicio: " + pozicioASavon + ")");
    }

    public String getId() {
        return this.id;
    }

    public int getPozicioASavon() {
        return pozicioASavon;
    }

    public int getSebesseg() {
        return sebesseg;
    }

    public Sav getAktualisSav() {
        return aktualisSav;
    }

    public Sav getDisplaySav() {
        return displaySav;
    }

    public int getDisplayPozicio() {
        return displayPozicio;
    }

    public JarmuAllapot getAllapot() {
        return allapot;
    }

    /**
     * Visszaadja, hogy a jarmu fordított irányban halad-e az aktuális útszakaszon.
     */
    public boolean isAktualisIranyForditott() {
        return aktualisIranyForditott;
    }

    /**
     * Beállítja az aktuális útszakasz kezdeti haladási irányát a startCsomopont alapján.
     * Akkor kell hívni, miután az Utvonal[] tömb be van állítva és utvonalIndex == 0.
     * @param startCsomopont Az a csomópont, ahonnan a jármű az Utvonal[0] útszakaszon indul.
     */
    public void beallitKezdoIrany(Csomopont startCsomopont) {
        if (Utvonal != null && Utvonal.length > 0 && Utvonal[0] != null && startCsomopont != null) {
            aktualisIranyForditott = (startCsomopont == Utvonal[0].getVege());
        }
    }
    
    /**
     * Szintén Hook metódus a sávváltás logolására.
     */
    protected void ujSzakaszLog(Sav regiSav) {
        System.out.println("[ESEMENY] " + id + " | UJ SZAKASZRA LEPETT | " + regiSav.getId() + " -> " + aktualisSav.getId() + " savra (uj poz: " + pozicioASavon + ")");
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

    public void setPozicioASavon(int pozicio) {
        this.pozicioASavon = pozicio;
    }

    public void setAktualisIranyForditott(boolean forditott) {
        this.aktualisIranyForditott = forditott;
    }

    public void statKiir(){
    }

    public boolean isRoncs(){
        return this.allapot == JarmuAllapot.RONCS;
    }

    public  boolean isHokotro(){
        return false;
    }

    public boolean isBusz() {
        return false;
    }

    public Auto asAuto() {
        return null;
    }

    public Busz asBusz() {
        return null;
    }

    public Hokotro asHokotro() {
        return null;
    }

    public String getTerkepJel() {
        return "A";
    }

    @Override
    public String toString() {
        return id;
    }
    public void megcsuszik() {}
    public void balesetezik() {}
    public void megsemmisites() {}
    public void ujraTervezes(UtvonalTervezo ut) {}
}
