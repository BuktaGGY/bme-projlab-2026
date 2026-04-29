
/**
 * Az Alagút osztály egyetlen felelősége az, hogy meg tudjuk különböztetni, hogy milyen típusú útszakaszról van szó.
 */
public class Alagut extends Utszakasz {

    public Alagut(String id, Csomopont eleje, Csomopont vege, int hossz) {
        super(id, eleje, vege, hossz);
    }
}
