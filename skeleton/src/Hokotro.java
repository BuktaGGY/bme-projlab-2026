/**
 * A hókotró járművet reprezentáló osztály.
 * Kötött (játékos által irányított) útvonalon halad, feladata az utak 
 * megtisztítása a hótól, jégtől, valamint a roncsok eltakarítása.
 * Nem sérülékeny, így nem tud megcsúszni vagy elakadni.
 */
public class Hokotro extends Jarmu implements IranyitottJarmu {
    

    private boolean roncsotTakarit = false;

    private boolean befejezte = false;
    /** A hókotróra jelenleg felszerelt kotrófej (Strategy minta). */
    private KotroFej aktualisKotrofej;
    
    /** A hókotró rendelkezésre álló sókészlete. */
    private int so = 0;
    
    /** A hókotró rendelkezésre álló biokerozin készlete. */
    private int biokerozin = 0;

    /** A hókotró rendelkezésre álló zúzalék készlete. */
    private int zuzalek = 0;

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
        this.sebesseg = SzimulacioBeallitasok.finomitottSzimulacio ? 10 : 50;
    }

    /**
     * Frissíti a hókotró állapotát.
     */
    @Override
public void frissitAllapot() {
    roncsotTakarit = false;

    if(fi != null){
        roncsotTakarit = fi.roncsEltakarit(this);
    }
    if (befejezte) return;

    if (!roncsotTakarit && aktualisKotrofej != null && aktualisSav != null) {
        // Takarítunk, ha a sáv nem teljesen tiszta (nem kell azonossági ellenőrzés)
        if (aktualisSav.getAllapot() != SavAllapot.TISZTA) {
            aktualisKotrofej.takarit(aktualisSav, this);

            String tipus = aktualisKotrofej.getFejTipus();
            if (!tipus.equals("soszoro") && !tipus.equals("sarkany")) {
                String extra;
                if (aktualisSav.getAllapot() == SavAllapot.JEGPANCEL) {
                    extra = " (zuzalek eltavolitva, jegpancel maradt)";
                } else {
                    extra = " (maradek ho: " + aktualisSav.getHoVastagsag() + "cm)";
                }
                System.out.println("[ESEMENY] " + this.id + " | TAKARITOTT | " + aktualisSav.getId() + " sav" + extra);
            }
        }
    }
}

    @Override
    public void mozog() {
        if(roncsotTakarit){
            return;
        }
        super.mozog();
    }

    @Override
    public boolean isHokotro() {
        return true;
    }

    @Override
    public Hokotro asHokotro() {
        return this;
    }

    @Override
    public String getTerkepJel() {
        return "H";
    }


    @Override
    protected void celbaErt() {
        this.befejezte = true;
    }

    /**
     * Kijelöli a hókotró új útvonalát.
     * @param ujUtvonal A kijelölt útszakaszok tömbje.
     */
    @Override
    public void UtvonalatKijelol(Utszakasz[] ujUtvonal) {
        this.Utvonal = ujUtvonal;
        this.utvonalIndex = 0;
        this.befejezte = false;
    }

    /**
     * Eltakarítja a balesetet szenvedett (roncs) autókat az aktuális sávról.
     */
    public void roncsotTakarit(Jarmu roncs) {
        if (fi != null) {
            fi.roncsEltakarit(this);
        }
    }

    @Override
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
