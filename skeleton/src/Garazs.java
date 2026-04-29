/**
 * Hókotró garázst megvalósító osztály
 */
public class Garazs extends PointOfInterest {
    private boolean isOccupied;

    /**
     * Konstruktor a Szkeleton teszteléshez.
     * @param name A példány azonosító neve a naplózáshoz.
     */
    public Garazs(String name) { 
        super(name);
    }
    
    /**
     * A fejcsere metodus lecsereli az eppen hasznalt kotrofejet egy masik kotrofejre. 
     * Ha a garazs mar foglalt, akkor a muveletet nem lehet elvegezni.
     * @param fej - a kotrofej, amit le akarunk cserelni
     */
    public void fejCsere(KotroFej fej) {
    }

    /**
     * A tankol metodus feltolti az eppen hasznalt kotrofejet nyersanyaggal. 
     * Ha a garazs mar foglalt, akkor a muveletet nem lehet elvegezni.
     * @param fej - a kotro fej, amit fel akarunk tankolni
     */
    public void tankol(KotroFej fej) {
    }
}
