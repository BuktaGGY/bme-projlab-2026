public abstract class PointOfInterest {
    protected Csomopont hely;
    protected String id;

    public PointOfInterest(String id, Csomopont hely) {
        this.id = id;
        this.hely = hely;
    }

    /**
     * Beállítja a Csomopont referenciáját.
     * @param csp A Csomopont példány.
     */
    public void setCsomopont(Csomopont csp) {
        this.hely = csp;
    }

    /**
     *Kiírja az objektum aktuális állapotát.
     */
    public abstract void StatKiir();

    public String getId() {
        return id;
    }
	
    public Csomopont getCsomopont() {
        return this.hely;
    }

    public Garazs asGarazs() {
        return null;
    }

    public Vegallomas asVegallomas() {
        return null;
    }

    public String getTerkepJel() {
        return "P";
    }
}
