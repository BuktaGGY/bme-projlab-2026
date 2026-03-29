public class Lakas extends PointOfInterest {
    private Munkahely par;

    /**
     * Konstruktor a Szkeleton teszteléshez.
     * @param name A példány azonosító neve a naplózáshoz.
     */
    public Lakas(String name) {
        super(name);
        Skeleton.ctor(this, name);
    }

    /**
     * Beállítja a par referenciáját a teszteléshez.
     * @param p A munkahely példány.
     */
    public void setPar(Munkahely p) {
        this.par = p;
    }
}
