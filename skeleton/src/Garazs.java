/**
 * Hókotró garázst megvalósító osztály
 */
public class Garazs extends PointOfInterest {
    private boolean isOccupied;

    public Garazs(String id, Csomopont hely) { 
        super(id, hely);
        this.isOccupied = false;
    }
    
    /**
     * A fejcsere metodus lecsereli az eppen hasznalt kotrofejet egy masik kotrofejre. 
     * Ha a garazs mar foglalt, akkor a muveletet nem lehet elvegezni.
     * @param fej - a kotrofej, amit le akarunk cserelni
     * @param hk1 - a hokotro, amelyik a fej cseret keri
     * @param gk - a gazdasagkezelo, amelyik kezeli a koltsegvetest
     */
    public void fejCsere(KotroFej fej, Hokotro hk1, GazdasagKezelo gk) {
        if (!isOccupied) {
            gk.fizetes(fej.getAr());
            hk1.setKotrofej(fej);
            System.out.println("[OK] " + this.id + " fejcsere sikeres (uj fej: " + fej.getFejTipus()+")");
        } else {
            System.out.println("[HIBA] " + this.id + " fejcsere sikertelen (mar foglalt)");
        }
    }

    /**
     * A tankol metodus feltolti az eppen hasznalt kotrofejet nyersanyaggal. 
     * Ha a garazs mar foglalt, akkor a muveletet nem lehet elvegezni.
     * @param fej - a kotro fej, amit fel akarunk tankolni
     * @param hk1 - a hokotro, amelyik a tankolast keri
     * @param gk - a gazdasagkezelo, amelyik kezeli a koltsegvetest
     */
    public boolean tankol(KotroFej fej, Hokotro hk1, GazdasagKezelo gk, String anyag) {
        if (!isOccupied) {
            switch (anyag) {
                case "so":
                    gk.fizetes(50-hk1.getSo());
                    hk1.setSo(50);
                    break;
                case "biokerozin":
                    gk.fizetes(100-hk1.getBiokerozin());
                    hk1.setBiokerozin(100);
                    break;
                case "zuzalek":
                    gk.fizetes(50);
                    hk1.setZuzalek(50);
                    break;
                default:
                    System.out.println("[HIBA] " + this.id + " tankolás sikertelen (ismeretlen anyag)");
                    return false;
            }
            
            System.out.println("[OK] " + this.id + " tankolas sikeres (uj" + anyag + ")");
        } else {
            System.out.println("[HIBA] " + this.id + " tankolás sikertelen (mar foglalt)");
            return false;
        }
        return true;
    }

    @Override
    public void StatKiir(){
        String foglalt =  isOccupied ? "igen" : "nem";
        System.out.println("[STAT] GARAZS " + this.id + " | foglalt: "+ foglalt);
    }
}
