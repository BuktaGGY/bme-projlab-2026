/**
 * A Busz járműtípust megvalósító osztály.
 * A járművek közül ez az, amelyik kötött útvonalon, menetrendszerűen 
 * közlekedik a két végállomása között, és menet közben pontokat szerez.
 */
public class Busz extends SerulekenyJarmu implements IranyitottJarmu {

    /**
     * A busz által bejárt útvonal indulási állomása.
     */
    private Vegallomas kezdoAllomas;

    /**
     * A busz által bejárt útvonal célállomása.
     */
    private Vegallomas vegAllomas;

    /**
     * A busz balesete után nem tud mozogni egy meghatározott ideig,
     * ennek nyilvántartására használatos számláló.
     */
    private int blokkoltSzamlalo;

    /**
     * Konstruktor a Szkeleton teszteléshez.
     * @param name A példány azonosító neve a naplózáshoz.
     */
    public Busz(String name) {
        super();
        Skeleton.ctor(this, name);
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

    /**
     * Baleset esetén ideiglenes mozgásképtelenné teszi a buszt.
     */
    @Override
    public void balesetezik() {
        Skeleton.call(this, "balesetezik");

        setAllapot(JarmuAllapot.MOZGÁSKÉPTELEN);
        setBlokk(5);
        
        Skeleton.ret();
    }

    /**
     * Lépteti a blokkolt számlálót és frissíti a jármű állapotát.
     * Ha a büntetési idő lejárt, a busz újra haladó állapotba kerül.
     */
    @Override
    public void frissitAllapot() {
        Skeleton.call(this, "frissitAllapot");

        if(blokkoltSzamlalo == 0){
            setAllapot(JarmuAllapot.HALAD);
        }

        if(blokkoltSzamlalo > 0 ){
            blokkoltSzamlalo--;
        }
        
        Skeleton.ret();
    }

    /**
     * Beállítja a mozgásképtelenség időtartamát.
     * @param b A mozgásképtelenség időtartama (tickekben).
     */
    public void setBlokk(int b) {
        Skeleton.call(this, "setBlokk", String.valueOf(b));
        blokkoltSzamlalo = b;
        Skeleton.ret();
    }

    /**
     * Felcseréli a kezdő- és végállomást, amikor a busz eléri a célját,
     * biztosítva a folyamatos oda-vissza ingázást.
     */
    public void megfordul() {
        Skeleton.call(this, "megfordul");

        // Figyelem: Itt a temp változó is Vegallomas típusú lett!
        Vegallomas temp = kezdoAllomas;
        kezdoAllomas = vegAllomas;
        vegAllomas = temp;

        Skeleton.ret();
    }

    /**
     * Megvalósítja az IrányítottJármű interfész metódusát.
     * A játékos által kijelölt célhoz vezető utat adja át a busz új útvonalának.
     * @param ujUtvonal A kijelölt útszakaszokat tartalmazó tömb.
     */
    @Override
    public void UtvonalatKijelol(Utszakasz[] ujUtvonal) {
        Skeleton.call(this, "UtvonalatKijelol", "ujUtvonal");

        this.Utvonal = ujUtvonal;

        Skeleton.ret();
    }

    /**
     * A jármű mozgatása (a Jármű ősosztályból felülírva). 
     * A szkeleton tesztben ez szimulálja a végállomásra való érkezést és az érintést.
     * * @param utszakasz Az aktuális útszakasz, amin halad (az ősosztály paraméterezése miatt).
     */
    @Override
    public void mozog(Object utszakasz) {
        Skeleton.call(this, "mozog", Skeleton.getName(utszakasz));

        if (vegAllomas != null) {
            vegAllomas.addErintes();
        }
        
        Skeleton.ret();
    }
}