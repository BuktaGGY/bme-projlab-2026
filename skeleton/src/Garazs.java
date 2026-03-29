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
        Skeleton.ctor(this, name);
    }
    
    /**
     * A fejcsere metodus lecsereli az eppen hasznalt kotrofejet egy masik kotrofejre. 
     * Ha a garazs mar foglalt, akkor a muveletet nem lehet elvegezni.
     * @param fej - a kotrofej, amit le akarunk cserelni
     */
    public void fejCsere(KotroFej fej) {
        Skeleton.call(this, "fejCsere", "fej");
        int foglalt = Skeleton.askQuestion("Foglalt a garazs?", "Igen", "Nem");
        if (foglalt == 1) {
            Skeleton.ret("false"); // Nem lehet cserelni, mert a garazs foglalt
        } else {
        
            Skeleton.call(this, "fizetes");
            int answer = Skeleton.askQuestion("Van eleg fedezet?", "Igen", "Nem");
            Skeleton.call(this, "vasarlasValidacio");
            Skeleton.ret();
            if (answer == 1) {
                Skeleton.ret("true"); // Sikeres fizetes
            } else {
                Skeleton.ret("false"); // Nem sikerult a fizetes
            }

        Skeleton.ret("true"); // Garazs nem volt foglalt
        }
    }

    /**
     * A tankol metodus feltolti az eppen hasznalt kotrofejet nyersanyaggal. 
     * Ha a garazs mar foglalt, akkor a muveletet nem lehet elvegezni.
     * @param fej - a kotro fej, amit fel akarunk tankolni
     */
    public void tankol(KotroFej fej) {
        Skeleton.call(this, "tankol", "fej");

        int foglalt = Skeleton.askQuestion("Foglalt a garazs?", "Igen", "Nem");
        if (foglalt == 1) {
            Skeleton.ret("false"); // Nem lehet cserelni, mert a garazs foglalt
        } else {
        
            Skeleton.call(this, "fizetes");
            int answer = Skeleton.askQuestion("Van eleg fedezet?", "Igen", "Nem");
            Skeleton.call(this, "vasarlasValidacio");
            Skeleton.ret();
            if (answer == 1) {
                Skeleton.ret("true"); // Sikeres fizetes
            } else {
                Skeleton.ret("false"); // Nem sikerult a fizetes
            }

        Skeleton.ret("true"); // Garazs nem volt foglalt
        }
    }





}
