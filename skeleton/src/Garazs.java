/**
 * Hókotró garázst megvalósító osztály
 */
public class Garazs extends PointOfInterest {
    private boolean isOccupied;

    public Garazs(String name) { 
        super(name);
        Skeleton.ctor(this, name);
    }
    
    /**
     * Takarító fej lecserélése a hókotrón
     * @param fej
     * @return
     */
    public boolean fejCsere(KotroFej fej) {
        Skeleton.call(this, "fejCsere", "fej");
        if (isOccupied) {
            Skeleton.ret("false");
            return false; // Nem lehet cserelni, mert a garazs foglalt
        }

        isOccupied = true; // A garazs most foglalt

        Skeleton.ret("true");
        return true; // Garazs nem volt foglalt
    }

    /**
     * Kotrofej megtankolása
     * @param fej Tankolandó fej
     * @return
     */
    public boolean tankol(KotroFej fej) {
        if (isOccupied) {
            Skeleton.ret("false");
            return false; // Nem lehet cserelni, mert a garazs foglalt
        }
        
        isOccupied = true; // A garazs most foglalt

         Skeleton.ret("true");
        return true; // Garazs nem volt foglalt
    }

}
