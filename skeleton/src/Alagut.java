
/**
 * Az Alagút osztály egyetlen felelősége az, hogy meg tudjuk különböztetni, hogy milyen típusú útszakaszról van szó.
 */
public class Alagut {

    /**
     * Egy egész szám, amely az útszakasz fizikai hosszát tárolja.
     */
    private int hossz;

    /**
     * Egy referencia, amely az útszakasz kezdő pontjára mutat.
     */
    private Csomopont eleje;

    /**
     * Egy referencia, amely az útszakasz végpontjára mutat.
     */
    private Csomopont vege;

    /**
     * Egyszerű konstruktor
     */

    public Alagut(String name){
        Skeleton.ctor(this, name);
    }
}
