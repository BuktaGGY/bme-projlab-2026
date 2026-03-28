/**
 * A hókotró járművet reprezentáló osztály.
 * Kötött (játékos által irányított) útvonalon halad, feladata az utak 
 * megtisztítása a hótól, jégtől, valamint a roncsok eltakarítása.
 * Nem sérülékeny, így nem tud megcsúszni vagy elakadni.
 */
public class Hokotro extends Jarmu implements IranyitottJarmu {
    
    /** A hókotróra jelenleg felszerelt kotrófej (Strategy minta). */
    private KotroFej aktualisKotrofej;
    
    /** A hókotró rendelkezésre álló sókészlete. */
    private int so = 50;
    
    /** A hókotró rendelkezésre álló biokerozin készlete. */
    private int biokerozin = 100;

    /**
     * Konstruktor a Szkeleton teszteléshez.
     * @param name A példány azonosító neve a naplózáshoz.
     */
    public Hokotro(String name) {
        super();
        Skeleton.ctor(this, name);
    }

    /**
     * Frissíti a hókotró állapotát.
     */
    @Override
    public void frissitAllapot() {
        Skeleton.call(this, "frissitAllapot");
        Skeleton.ret();
    }

    /**
     * Kijelöli a hókotró új útvonalát.
     * @param ujUtvonal A kijelölt útszakaszok tömbje.
     */
    @Override
    public void UtvonalatKijelol(Utszakasz[] ujUtvonal) {
        Skeleton.call(this, "UtvonalatKijelol", Skeleton.getName(ujUtvonal));
        this.Utvonal = ujUtvonal;
        Skeleton.ret();
    }

    /**
     * Eltakarítja a balesetet szenvedett (roncs) autókat az aktuális sávról.
     */
    public void roncsotTakarit() {
        Skeleton.call(this, "roncsotTakarit");
        Skeleton.ret();
    }

    public int getSo() {
        Skeleton.call(this, "getSo");
        Skeleton.ret(String.valueOf(so));
        return so;
    }

    public void setSo(int so) {
        Skeleton.call(this, "setSo", String.valueOf(so));
        this.so = so;
        Skeleton.ret();
    }

    public int getBiokerozin() {
        Skeleton.call(this, "getBiokerozin");
        Skeleton.ret(String.valueOf(biokerozin));
        return biokerozin;
    }

    public void setBiokerozin(int biokerozin) {
        Skeleton.call(this, "setBiokerozin", String.valueOf(biokerozin));
        this.biokerozin = biokerozin;
        Skeleton.ret();
    }
}