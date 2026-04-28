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

    /** A hókotró rendelkezésre álló zúzalék készlete. */
    private int zuzalek = 0;

	//ismeri cd alapjan
	private ForgalomIranyito fi;

    public void setForgalomIranyito(ForgalomIranyito fi) {
        this.fi = fi;
    }

    /**
     * Konstruktor a Szkeleton teszteléshez.
     * @param name A példány azonosító neve a naplózáshoz.
     */
    public Hokotro(String name) {
        super();
    }

    /**
     * Frissíti a hókotró állapotát.
     */
    @Override
    public void frissitAllapot() {

    }

    /**
     * Kijelöli a hókotró új útvonalát.
     * @param ujUtvonal A kijelölt útszakaszok tömbje.
     */
    @Override
    public void UtvonalatKijelol(Utszakasz[] ujUtvonal) {
        this.Utvonal = ujUtvonal;
    }

    /**
     * Eltakarítja a balesetet szenvedett (roncs) autókat az aktuális sávról.
     */
    public void roncsotTakarit(Jarmu jarmu) {
        if (fi != null) {
            fi.roncsEltakarit(jarmu);
        }
    }

    public int getSo() {
        return so;
    }

    public void setSo(int so) {
        this.so = so;
    }

    public int getBiokerozin() {
        return biokerozin;
    }

    public void setBiokerozin(int biokerozin) {
        this.biokerozin = biokerozin;
    }

    public void setKotrofej(KotroFej kotrofej) {
        this.aktualisKotrofej = kotrofej;
    }

    public void setZuzalek(int mennyiseg){
        this.zuzalek = mennyiseg;
    }

    public int getZuzalek(){
        return zuzalek;
    }
}