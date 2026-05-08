/**
 * Lakás osztály, az autó úticéljaként funkcionál.
 */
public class Lakas extends PointOfInterest {
    private Munkahely par;

    /**
     * Konstruktor a Szkeleton teszteléshez.
     * @param name A példány azonosító neve a naplózáshoz.
     */
    public Lakas(String id, Csomopont hely, Munkahely par) {
        super(id, hely);
        this.par = par;
    }

    @Override
    public String getTerkepJel() {
        return "L";
    }

    /**
     * Beállítja a par referenciáját a teszteléshez.
     * @param p A munkahely példány.
     */
    public void setPar(Munkahely p) {
        this.par = p;
    }

    public void StatKiir(){
        System.out.println("[STAT] LAKAS " + this.id + " | par: "+ par.getId());
    }
}
