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
        Skeleton.ctor(this, name);
    }

    /**
     * Visszaadja a csomópont azonosítóját.
     * @return A csomópont egyedi azonosítója.
     */
    public int getID(){
        Skeleton.call(this, "getID");
        Skeleton.ret(Skeleton.getName(id));
        return id;
    }

    /**
     * Visszaadja a csomóponthoz csatlakozó útszakaszokat.
     * @return Az útszakaszokat tartalmazó tömb.
     */
    public Utszakasz[] getUtszakaszok(){
        Skeleton.call(this, "getUtszakaszok");
        // Ha az utszakaszok null, akkor "null"-t ír ki a Skeleton
        Skeleton.ret(utszakaszok != null ? "Utszakasz[]" : "null");
        return utszakaszok;
    }
}
