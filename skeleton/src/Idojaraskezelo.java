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
     * @param havazasIntenzitas Tickenként leeső hó
     * @param osszesSav Referencia sávokra
     */
    public Idojaraskezelo(int havazasIntenzitas, List<Sav> osszesSav) {
        this.havazasIntenzitas = havazasIntenzitas;
        this.osszesSav = osszesSav;
    }

    /**
     * Növeli a hó mennyiségét az összes sávon
     */
    public void Havaz(){
        for(Sav sav : osszesSav){
            if (sav.getAllapot() != SavAllapot.SOZOTT){
                sav.hoNovel(havazasIntenzitas);
            } 
        }
    }

    /**
     * Kezeli a sávokon az olvadási folyamatokat.
     */
    public void olvasztasKezeles(){
        for (Sav s : osszesSav){
            if (s.getSozottIdo() > 0){
                s.setSozottIdo(s.getSozottIdo() - 1);
            }
            if (s.getAllapot() == SavAllapot.SOZOTT && s.getSozottIdo() == 0){
                String miOlvadt = (s.getHoVastagsag() > 0) ? "ho" : "jeg";
                s.hoEltuntet();
                System.out.println("[ESEMENY] IDOJARAS | OLVADAS | " + s.getId() + " savrol elolvadt a " + miOlvadt);
            }
        }
    }


    public void setOsszesSav(List<Sav> savok){
        osszesSav = savok;
    }
}
