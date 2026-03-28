import java.util.Map;

/**
 * A közös költségvetés kezelését végző osztály.
 * A hókotrók munkavégzése utáni pénzt írja jóvá, illetve ellenőrzi a játékosok vásárlási kísérleteit a garázsokban.
 */
public class GazdasagKezelo {
    /**
     * A játék során összegyűjtött pénz nyilvántartása
     */
    private int kozosKassza;

    /**
     * A különböző fejtípusok név-ár párjait tartalmazó gyűjtemény
     */
    private Map<String, Integer> fejArak;

    /**
     * Pénz hozzáadását végzi a közös kasszához
     * @param bevetel bevétel
     */
    public void bevetelHozzaad(int bevetel){
        Skeleton.call(this, "bevetelHozzaad");

        kozosKassza += bevetel;

        Skeleton.ret();
    }

    /**
     * Ellenőrzi, hogy van-e elegendő fedezet a vásárláshoz
     * @param osszeg Fizetendő összeg
     * @return "Igaz" értékkel tér vissza ha van elég fedezet, különben "hamis"
     */
    public boolean vasarlasValidacio(int osszeg){
        Skeleton.call(this, "vasarlasValidacio");

        if(osszeg >= kozosKassza){
            Skeleton.ret();
            return false;
        }

        Skeleton.ret();
        return true;
    }

    /**
     * Végrehajtja a fizetési tranzakciót, ha van elegendő fedezet
     * @param osszeg Vásárlás összege
     * @return "Igaz" értékkel tér vissza ha sikeres a vásárlás művelet, különben "hamis"
     */
    public boolean fizetes(int osszeg){
        Skeleton.call(this, "fizetes");

        if(vasarlasValidacio(osszeg)){
            kozosKassza -= osszeg;
            Skeleton.ret();
        }
        else{
            Skeleton.ret();
            return false;
        }

        Skeleton.ret();
        return true;
    }
}
