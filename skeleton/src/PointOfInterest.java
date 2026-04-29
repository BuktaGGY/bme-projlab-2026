public abstract class PointOfInterest {
    protected Csomopont hely;
    protected String id;

    /**
     * Konstruktor
     * @param id A példány azonosító neve a naplózáshoz.
     */
    public PointOfInterest(String id) {
        this.id = id;
    }

    /**
     * Beállítja a Csomopont referenciáját a teszteléshez.
     * @param csp A Csomopont példány.
     */
    public void setCsomopont(Csomopont csp) {
        this.hely = csp;
    }

    public void StatKiir(){}

    public String getId() {
        return id;
    }
}