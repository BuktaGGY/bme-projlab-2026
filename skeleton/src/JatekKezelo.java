/**
 * A teljes szimulációt és játékmenetet vezérlő központi osztály.
 * Nyilvántartja a játék állapotát (fut-e), a tickeket (idő múlását), 
 * és a játékos által megszerzett pontokat.
 */
public class JatekKezelo {
    Idojaraskezelo idojaraskezelo;
    UtvonalTervezo utvonalTervezo;
    ForgalomIranyito forgalomIranyito;
    GazdasagKezelo gazdasagKezelo;
    
    public boolean isRunning;
    public int aktualisTick;
    public int buszPontszamok;

    /**
     * Konstruktor
     */
    public JatekKezelo() {
        aktualisTick = 0;
        buszPontszamok = 0;
        isRunning = false;
        forgalomIranyito = new  ForgalomIranyito();
        //idojaraskezelo = new Idojaraskezelo(20,);
    }

    public void start() {
        isRunning = true;
    }

    public void stop() {
        isRunning = false;
    }

    public void tick() {
        // Kivettem ezt mert a szekvencia diagramok szerint ezt nem o a tick hivja meg (Ho olvad az uton + kornyezeti havazas)
        // Lehethogy nem ez a jo megoldas de a szekvencia diagramon nem latom hogy a havaz fuggvenyt meghivnank a tick() utan
        //idojaraskezelo.Havaz();

        if (!isRunning) {return;}

       // idojaraskezelo.olvasztasKezeles();
        forgalomIranyito.mozgatJarmuvek();

        aktualisTick++;

    }

    /**
     * Növeli a játékos által megszerzett pontok számát,
     * amikor egy busz sikeresen teljesít egy kört (érinti a végállomást).
     */
    public void buszPontszamNovel() {
        buszPontszamok++;
    }

    public ForgalomIranyito getForgalomIranyito() {
        return forgalomIranyito;
    }
}