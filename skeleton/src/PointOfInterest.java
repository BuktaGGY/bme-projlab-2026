public abstract class PointOfInterest {
    protected Csomopont hely;
    protected int id;

    /**
     * Konstruktor a Szkeleton teszteléshez.
     * @param name A példány azonosító neve a naplózáshoz.
     */
    public PointOfInterest(String name) { 
        Skeleton.ctor(this, name); 
    }

    /**
     * Beállítja a Csomopont referenciáját a teszteléshez.
     * @param csp A Csomopont példány.
     */
    public void setCsomopont(Csomopont csp) {
        this.hely = csp;
    }

}