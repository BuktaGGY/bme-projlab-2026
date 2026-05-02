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
     * @param id A példány azonosító neve a naplózáshoz.
     */
    public Hokotro(String id, KotroFej fej, Sav kezdoSav, int pozSavon) {
        super(id);
        aktualisKotrofej = fej;
        aktualisSav = kezdoSav;
        this.pozicioASavon = pozSavon;
    }

    /**
     * Frissíti a hókotró állapotát.
     */
	@Override
    public void frissitAllapot() {
        // 1. TAKARÍTÁS FÁZISA
        if (aktualisKotrofej != null && aktualisSav != null) {
            aktualisKotrofej.takarit(aktualisSav, this);
            System.out.println("[ESEMENY] " + this.id + " | TAKARITOT | " + aktualisSav.getId() + " sav");
        }

        // 2. MOZGÁS FÁZISA
        if (Utvonal != null && Utvonal.length > 0) {
            int sebesseg = 10; 
            this.pozicioASavon += sebesseg;

            int aktualisUtszakaszIndex = -1;
            for (int i = 0; i < Utvonal.length; i++) {
                // ÚJÍTÁS: NULL-SAFE ELLENŐRZÉS! 
                // Csak akkor vizsgáljuk a sávokat, ha a tömb eleme ténylegesen létezik
                if (Utvonal[i] != null && Utvonal[i].getSavok() != null && Utvonal[i].getSavok().contains(aktualisSav)) {
                    aktualisUtszakaszIndex = i;
                    break;
                }
            }

            int utHossz = 100; 

            // Ha elértük vagy túlléptük a jelenlegi útszakasz végét
            if (aktualisUtszakaszIndex != -1 && this.pozicioASavon >= utHossz) {
                // Szintén null-safe ellenőrzés a következő útszakaszra
                if (aktualisUtszakaszIndex + 1 < Utvonal.length && Utvonal[aktualisUtszakaszIndex + 1] != null) {
                    Sav regiSav = aktualisSav;
                    aktualisSav = Utvonal[aktualisUtszakaszIndex + 1].getSavok().get(0);
                    pozicioASavon = pozicioASavon - utHossz;
                    System.out.println("[ESEMENY] " + this.id + " | SAVOT_VALTOTT | " + regiSav.getId() + " -> " + aktualisSav.getId() + " savra");
                } else {
                    this.pozicioASavon = utHossz; 
                    this.Utvonal = null;          
                    System.out.println("[ESEMENY] " + this.id + " | CELBA_ERT | Befejezte a kijelolt utvonalat");
                }
            }
        }
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

    public void statKiir(){
        System.out.println("[STAT] HOKOTRO "+ this.id + " | sav: " + aktualisSav.getId() + " | poz: " + pozicioASavon
                + " | fej: "+ aktualisKotrofej.getFejTipus() + " | so: " + so + " | kerozin: " + biokerozin);
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