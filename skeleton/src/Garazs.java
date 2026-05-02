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
     */
    public void fejCsere(KotroFej fej) {
        //TODO: ha egy kotro az adott csomoponton tartozkodik, akkor foglaltra allitja a garazst, ehhez kell a 
        //hokotro helyzete
        if (!isOccupied) {
            System.out.println("[ESEMENY] GARAZS " + this.id + " | FEJCSERE | " + fej.getFejTipus() + " fejjel");
        } else {
            System.out.println("[ESEMENY] GARAZS " + this.id + " | FEJCSERE_SIKERTELEN | mar foglalt");
        }
    }

    /**
     * A tankol metodus feltolti az eppen hasznalt kotrofejet nyersanyaggal. 
     * Ha a garazs mar foglalt, akkor a muveletet nem lehet elvegezni.
     * @param fej - a kotro fej, amit fel akarunk tankolni
     */
    public void tankol(KotroFej fej) {
        //TODO: ha egy kotro az adott csomoponton tartozkodik, akkor foglaltra allitja a garazst, ehhez kell a 
        //hokotro helyzete
        if (!isOccupied) {
            System.out.println("[ESEMENY] GARAZS " + this.id + " | TANKOL | " + fej.getFejTipus() + " fejjel");
        } else {
            System.out.println("[ESEMENY] GARAZS " + this.id + " | TANKOLAS_SIKERTELEN | mar foglalt");
        }
    }

    public void StatKiir(){
        String foglalt =  isOccupied ? "igen" : "nem";
        System.out.println("[STAT] GARAZS " + this.id + " | foglalt: "+ foglalt);
    }
}
