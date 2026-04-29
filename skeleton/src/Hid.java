
/**
 * A Híd osztály egyetlen felelősége az, hogy meg tudjuk különböztetni, hogy milyen típusú útszakaszról van szó.
 */
public class Hid extends Utszakasz {

    public Hid(String id, Csomopont eleje, Csomopont vege, int hossz) {
        super(id, eleje, vege, hossz);
    }
}
