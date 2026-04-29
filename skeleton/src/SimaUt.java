
/**
 * A SimaÚt osztály egyetlen felelősége az, hogy meg tudjuk különböztetni, hogy milyen típusú útszakaszról van szó.
 */
public class SimaUt extends Utszakasz{

    /**
     * Egyszerű konstruktor
     * @param name Skeleton class miatt
     */
    public SimaUt(String id, Csomopont eleje, Csomopont vege, int hossz) {
        super(id, eleje, vege, hossz);
    }
}
