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
     * Konstruktor a Szkeleton teszteléshez.
     * @param name A példány azonosító neve a naplózáshoz.
     */
    public JatekKezelo(String name) {
        Skeleton.ctor(this, name);
    }

    public void start() {
        Skeleton.call(this, "start");
        isRunning = true;
        Skeleton.ret();
    }

    public void stop() {
        Skeleton.call(this, "stop");
        isRunning = false;
        Skeleton.ret();
    }

    public void tick() {
        Skeleton.call(this, "tick");
        idojaraskezelo.Havaz();
        idojaraskezelo.olvasztasKezeles();
        Skeleton.ret();
    }

    /**
     * Növeli a játékos által megszerzett pontok számát,
     * amikor egy busz sikeresen teljesít egy kört (érinti a végállomást).
     */
    public void buszPontszamNovel() {
        Skeleton.call(this, "buszPontszamNovel");
        buszPontszamok++;
        Skeleton.ret();
    }
}