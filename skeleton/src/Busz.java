/**
 * A Busz járműtípust megvalósító osztály
 */
public class Busz extends SerulekenyJarmu implements IranyitottJarmu {

    /**
     * A busz által bejárt útvonal egyik végállomása.
     */
    private Csomopont kezdoAllomas;

    /**
     * A busz által bejárt útvonal egyik végállomása.
     */
    private Csomopont vegAllomas;


    /**
     * A busz balesete utan nem tud mozogni egy meghatarozott ideig,
     * ennek nyilvantartasara használatos.
     */
    private int blokkoltSzamlalo;

    /**
     * Konstruktor
     * @param name Név
     */
    public Busz(String name) {
        Skeleton.ctor(this, name);
    }


    /**
     * Baleset esetén ideiglenes mozgaskeptelenne teszi a buszt
     */
    @Override
    public void balesetezik() {
        Skeleton.call(this, "balesetezik");

        setAllapot(JarmuAllapot.MOZGÁSKÉPTELEN);
        setBlokk(5);
        Skeleton.ret();
    }

    /**
     * Lépteti a blokkolt számlálót és frissíti a jarmu allapotat.
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
     * Beállítja a mozgásképtelenség időtartamát
     * @param b mozgásképtelenség időtartama
     */
    public void setBlokk(int b){
        blokkoltSzamlalo=b;
    }

    /**
     * Felcseréli a kezdő- és végállomást, amikor a busz eléri a célját,
     * biztosítva a folyamatos oda-vissza ingázást.
     */
    public void megfordul(){
        Skeleton.call(this, "megfordul");

        Csomopont temp = kezdoAllomas;
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
        Skeleton.call(this, "utvonalatKijelol");

        this.Utvonal = ujUtvonal;

        Skeleton.ret();
    }
}
