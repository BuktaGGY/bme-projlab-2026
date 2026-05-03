/**
 * A hókotró járművet reprezentáló osztály.
 * Kötött (játékos által irányított) útvonalon halad, feladata az utak 
 * megtisztítása a hótól, jégtől, valamint a roncsok eltakarítása.
 * Nem sérülékeny, így nem tud megcsúszni vagy elakadni.
 */
public class Hokotro extends Jarmu implements IranyitottJarmu {
    

    private boolean befejezte = false;
    /** A hókotróra jelenleg felszerelt kotrófej (Strategy minta). */
    private KotroFej aktualisKotrofej;
    
    /** A hókotró rendelkezésre álló sókészlete. */
    private int so = 0;
    
    /** A hókotró rendelkezésre álló biokerozin készlete. */
    private int biokerozin = 0;

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
        this.sebesseg = 50; // JAVÍTVA: A hókotró sebessége 50!
    }

    /**
     * Frissíti a hókotró állapotát.
     */
    @Override
    public void frissitAllapot() {
        if (befejezte) return; // Ha célba ért, már nem dolgozik!

        if (aktualisKotrofej != null && aktualisSav != null) {
            aktualisKotrofej.takarit(aktualisSav, this);
            
            // Csak akkor írjuk ki az alap takarítás logot, ha NEM sószóró vagy sárkány (mert ők megoldják maguknak)
            String tipus = aktualisKotrofej.getFejTipus();
            if (!tipus.equals("soszoro") && !tipus.equals("sarkany")) {
                System.out.println("[ESEMENY] " + this.id + " | TAKARITOTT | " + aktualisSav.getId() + " sav");
            }
        }
    }

    @Override
    public void mozog(Object utszakasz) {
        super.mozog(utszakasz);
    }

    @Override
    protected void celbaErt() {
        this.befejezte = true; // Jelezzük, hogy végzett!
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