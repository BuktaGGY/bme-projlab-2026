import java.util.ArrayList;
import java.util.List;

/**
 * A közlekedési hálózat egy csomópontját reprezentáló osztály.
 * A csomópontok kötik össze az egyes útszakaszokat, és ezek adják
 * a járművek útvonaltervezésének sarokpontjait.
 */
public class Csomopont {

    /**
     * A csomópont egyedi azonosítója.
     */
    private String id;

    /**
     * A csomóponthoz csatlakozó útszakaszok tömbje.
     */
    private List<Utszakasz> utszakaszok;

    /**
     * @param id A példány azonosító neve a naplózáshoz.
     */
    public Csomopont(String id) {
        this.id = id;
        this.utszakaszok = new ArrayList<>();
    }

    /**
     * Visszaadja a csomópont azonosítóját.
     * @return A csomópont egyedi azonosítója.
     */
    public String getID(){

        return id;
    }

    /**
     * Visszaadja a csomóponthoz csatlakozó útszakaszokat.
     * @return Az útszakaszokat tartalmazó tömb.
     */
    public List<Utszakasz> getUtszakaszok(){
        return utszakaszok;
    }

    public void addUtszakasz(Utszakasz u){
        this.utszakaszok.add(u);
    }
}
