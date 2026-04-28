/**
 * A közlekedési hálózat egy csomópontját reprezentáló osztály.
 * A csomópontok kötik össze az egyes útszakaszokat, és ezek adják
 * a járművek útvonaltervezésének sarokpontjait.
 */
public class Csomopont {

    /**
     * A csomópont egyedi azonosítója.
     */
    private int id;

    /**
     * A csomóponthoz csatlakozó útszakaszok tömbje.
     */
    private Utszakasz[] utszakaszok;

    /**
     * Konstruktor a Szkeleton teszteléshez.
     * @param name A példány azonosító neve a naplózáshoz.
     */
    public Csomopont(String name) {

    }

    /**
     * Visszaadja a csomópont azonosítóját.
     * @return A csomópont egyedi azonosítója.
     */
    public int getID(){

        return id;
    }

    /**
     * Visszaadja a csomóponthoz csatlakozó útszakaszokat.
     * @return Az útszakaszokat tartalmazó tömb.
     */
    public Utszakasz[] getUtszakaszok(){
        return utszakaszok;
    }
}
