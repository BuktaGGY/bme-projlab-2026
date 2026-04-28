import java.util.List;

/**
 * A környezeti hatások szimulálását végző osztály.
 * Tickenként havat ad hozzá az utakhoz, és kezeli a sózott sávok olvadási folyamatait.
 */
public class Idojaraskezelo {
    /**
     * A Tickenként eső hó mennyiségét adja meg.
     */
    private int havazasIntenzitas;

    /**
     * Referencia az összes sávra
     */
    private List<Sav> osszesSav;

    /**
     * Konstruktor
     * @param name Név
     * @param havazasIntenzitas Tickenként leeső hó
     * @param osszesSav Referencia sávokra
     */
    public Idojaraskezelo(String name, int havazasIntenzitas, List<Sav> osszesSav) {
        this.havazasIntenzitas = havazasIntenzitas;
        this.osszesSav = osszesSav;
    }

    /**
     * Növeli a hó mennyiségét az összes sávon
     */
    public void Havaz(){

        for(Sav sav : osszesSav){
            // Hozzaadtam egy checket a sav allapotara, mert kell az egyik teszthez
            if (sav.getAllapot() != SavAllapot.SOZOTT){
                sav.hoNovel(havazasIntenzitas);
            } 
        }

    }

    /**
     * Kezeli a sávokon az olvadási folyamatokat.
     */
    public void olvasztasKezeles(){

        for(Sav sav : osszesSav){
            // Hozzaadtam egy checket a sav allapotara, mert kell az egyik teszthez
            if (sav.getAllapot() == SavAllapot.SOZOTT && sav.getSozottIdo() == 0){
                sav.hoEltuntet();
            }
        }

    }
}
