import java.util.ArrayList;

/**
 * A teljes szimulációt és játékmenetet vezérlő központi osztály.
 * Nyilvántartja a játék állapotát (fut-e), a tickeket (idő múlását), 
 * és a játékos által megszerzett pontokat.
 */
public class JatekKezelo extends MegfigyelhetoModell {
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
        if (idojaraskezelo != null) {
            idojaraskezelo.olvasztasKezeles();
            if (SzimulacioBeallitasok.finomitottSzimulacio
                    && aktualisTick > 0
                    && aktualisTick % SzimulacioBeallitasok.havazasPeriodus == 0) {
                idojaraskezelo.havazMennyiseggel(SzimulacioBeallitasok.havazasMennyiseg);
                System.out.println("[ESEMENY] IDOJARAS | GYENGE_HAVAZAS | +" + SzimulacioBeallitasok.havazasMennyiseg + "cm");
            }
        }
        forgalomIranyito.mozgatJarmuvek();
        
        aktualisTick++;
        ertesitMegfigyeloket();
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

    public GazdasagKezelo getGazdasagKezelo() {
        return gazdasagKezelo;
    }

    public int getAktualisTick() {
        return aktualisTick;
    }

    public int getBuszPontszamok() {
        return buszPontszamok;
    }

    public void palyaValtozott() {
        ertesitMegfigyeloket();
    }
}
