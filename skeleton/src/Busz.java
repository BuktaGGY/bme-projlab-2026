/**
 * A Busz járműtípust megvalósító osztály.
 * A járművek közül ez az, amelyik kötött útvonalon, menetrendszerűen 
 * közlekedik a két végállomása között, és menet közben pontokat szerez.
 */
public class Busz extends SerulekenyJarmu implements IranyitottJarmu {

	private JatekKezelo jk;

	// Egy új metódus a beállításhoz:
	public void setJatekKezelo(JatekKezelo jk) {
    this.jk = jk;
}


    /**

     *A busz által bejárt útvonal indulási állomása.
     */
    private Vegallomas kezdoAllomas;

    /**
     *A busz által bejárt útvonal célállomása.
     */
    private Vegallomas vegAllomas;

    /**
     *A busz balesete után nem tud mozogni egy meghatározott ideig,
     * ennek nyilvántartására használatos számláló.
     */
    private int blokkoltSzamlalo;

    /**
     * Konstruktor
     * @param id A példány azonosító neve a naplózáshoz.
     * @param pozicioSavban A kezdő pozíció a sávon.
     * @param startSav A sáv, amelyen a busz elindul.
     * @param celAllomas A végállomás, ahová tart.
     */
    public Busz(String id, int pozicioSavban, Sav startSav, Vegallomas celAllomas) {
        super(id);
        this.blokkoltSzamlalo = 0;
        this.allapot = JarmuAllapot.HALAD;
        this.pozicioASavon = pozicioSavban;
        this.setStartSav(startSav);
        this.vegAllomas = celAllomas; 
        this.sebesseg = 15;
    }

    /**
     * Beállítja a busz kezdő- és végállomását a szimulációban.
     * @param kezdo A kiindulási állomás.
     * @param veg A célállomás.
     */
    public void setAllomasok(Vegallomas kezdo, Vegallomas veg) {
        this.kezdoAllomas = kezdo;
        this.vegAllomas = veg;
    }


    @Override
    public void megcsuszik() {
        if (allapot != JarmuAllapot.MOZGASKEPTELEN) {
            setAllapot(JarmuAllapot.MOZGASKEPTELEN);
            setBlokk(5);
            System.out.println("[ESEMENY] " + this.id + " | MEGCSUSZOTT | " + aktualisSav.getId() + " savban");
            System.out.println("[ESEMENY] " + this.id + " | MOZGASKEPTELEN | 5 tick buntetes");
        }
    }

    /**
     * Baleset esetén ideiglenes mozgásképtelenné teszi a buszt.
     */
    @Override
    public void balesetezik() {
        setAllapot(JarmuAllapot.MOZGASKEPTELEN);
        setBlokk(5);
        System.out.println("[ESEMENY] " + this.id + " | BALESET | " + aktualisSav.getId() + " savban");
    }

    /**
     * Lépteti a blokkolt számlálót és frissíti a jármű állapotát.
     * Ellenőrzi, hogy a sáv blokkolva van-e a hó miatt.
     */
    @Override
    public void frissitAllapot() {
        if (aktualisSav != null && aktualisSav.getAllapot() == SavAllapot.BLOKKOLT) {
            setAllapot(JarmuAllapot.MOZGASKEPTELEN);
            System.out.println("[ESEMENY] " + this.id + " | ELAKADT | " + aktualisSav.getId() + " savban");
            return;
        }

        if (blokkoltSzamlalo > 0) {
            blokkoltSzamlalo--;
            if (blokkoltSzamlalo == 0 && allapot == JarmuAllapot.MOZGASKEPTELEN) {
                setAllapot(JarmuAllapot.HALAD);
                System.out.println("[ESEMENY] " + this.id + " | UJRA_INDULT | buntetes lejart");
            }
        } else {
            setAllapot(JarmuAllapot.HALAD);
        }
    }

    /**
     * Beállítja a mozgásképtelenség időtartamát.
     * @param b A mozgásképtelenség időtartama (tickekben).
     */
    public void setBlokk(int b) {
        blokkoltSzamlalo = b;
    }

    /**
     * Felcseréli a kezdő- és végállomást, amikor a busz eléri a célját,
     * biztosítva a folyamatos oda-vissza ingázást.
     */
    public void megfordul() {
        Vegallomas temp = kezdoAllomas;
        kezdoAllomas = vegAllomas;
        vegAllomas = temp;
    }

    /**
     * Megvalósítja az IrányítottJármű interfész metódusát.
     * A játékos által kijelölt célhoz vezető utat adja át a busz új útvonalának.
     * @param ujUtvonal A kijelölt útszakaszokat tartalmazó tömb.
     */
    @Override
    public void UtvonalatKijelol(Utszakasz[] ujUtvonal) {
        this.Utvonal = ujUtvonal;
    }

    /**
     * A jármű mozgatása (a Jármű ősosztályból felülírva). 
     * A szkeleton tesztben ez szimulálja a végállomásra való érkezést és az érintést.
     */
    @Override
    public void mozog() {
        if (allapot == JarmuAllapot.HALAD) {
            aktualisSav.letapos(this);
            super.mozog();
        }
    }

    /* 
	@Override
    protected void mozgasLog() {}

    @Override
    protected void ujSzakaszLog(Sav regiSav) {}
    */
	
    @Override
    protected void celbaErt() {
        megfordul();
        if (vegAllomas != null) {
            vegAllomas.addErintes();
        }

        if (jk != null) {
            jk.buszPontszamNovel();
        }

        utvonalIndex = 0;
        pozicioASavon = 0;
        
        String celNev = (kezdoAllomas != null) ? kezdoAllomas.getId() : "ismeretlen";
        System.out.println("[ESEMENY] " + this.id + " | KORT_TELJESITETT | vegallomas: " + celNev + " (Pontszam novelve)");
    }

    /**
     * Statisztika kiírása a konzolra.
     */
    @Override
    public void statKiir(){
        String allapotStr = (aktualisSav != null && aktualisSav.getAllapot() == SavAllapot.BLOKKOLT) ? "ELAKADT" : allapot.toString();
    
        if (allapot == JarmuAllapot.MOZGASKEPTELEN) {
            System.out.println("[STAT] BUSZ "+ this.id + " | sav: " + aktualisSav.getId() + " | poz: " + pozicioASavon
                +" | allapot: "+ allapotStr + " | blokkolt_ido: " + blokkoltSzamlalo);
        } else {
            String celStr = (vegAllomas != null) ? vegAllomas.getId() : "nincs";
            System.out.println("[STAT] BUSZ "+ this.id + " | sav: " + aktualisSav.getId() + " | poz: " + pozicioASavon
                +" | allapot: "+ allapotStr + " | cel: " + celStr);
        }
    }
}