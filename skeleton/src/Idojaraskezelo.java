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
            
            // Csak akkor csinálunk valamit, ha a sáv le van sózva
            if (s.getSozottIdo() > 0) {
                
                // 1. Hó azonnali olvadása
                if (s.getHoVastagsag() > 0) {
                    s.hoEltuntet(); // Ez a Sav-ban kinullázza a havat és TISZTA-ra állítja az állapotot
                    System.out.println("[ESEMENY] IDOJARAS | OLVADAS | " + s.getId() + " savrol elolvadt a ho");
                }
                // 2. Jég azonnali olvadása (ha nincs hó, de jégpáncél van)
                else if (s.getAllapot() == SavAllapot.JEGPANCEL) {
                    s.setSavAllapot(SavAllapot.TISZTA);
                    System.out.println("[ESEMENY] IDOJARAS | OLVADAS | " + s.getId() + " savrol elolvadt a jeg");
                }

                // Körönként csökkentjük a só hatóidejét
                s.setSozottIdo(s.getSozottIdo() - 1);
            }
        }
    }


    public void setOsszesSav(List<Sav> savok){
        osszesSav = savok;
    }
}
