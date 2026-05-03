import java.util.ArrayList;

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
    
	public static boolean veletlenBe = false;
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
        forgalomIranyito = new ForgalomIranyito();
        utvonalTervezo = new UtvonalTervezo(null);
        gazdasagKezelo = new GazdasagKezelo(null);
        forgalomIranyito.setGazdasagKezelo(gazdasagKezelo);
        forgalomIranyito.setUtvonalTervezo(utvonalTervezo);
        utvonalTervezo.setForgalomIranyito(forgalomIranyito);
        idojaraskezelo = new Idojaraskezelo(10, new ArrayList<>());
    }
    public UtvonalTervezo getUtvonalTervezo() {
        return utvonalTervezo;
    }

    public void start() {
        isRunning = true;
    }

    public void stop() {
        isRunning = false;
    }

    public void tick() {
        if (!isRunning){
            return;
        }
        // 1. ELŐSZÖR az időjárás (hogy az épp lerakott só csak a KÖVETKEZŐ körben olvasszon)
        if (idojaraskezelo != null) {
            idojaraskezelo.olvasztasKezeles();
        }
        
        // 2. UTÁNA mozognak és takarítanak a járművek
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