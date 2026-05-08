/**
 * Egy csomopont kepernyobeli poziciojat tarolo egyszeru adatosztaly.
 * A modellbeli csomopont nem tartalmaz grafikus koordinatat, ezt az osztaly
 * kulon, a megjelenitesi retegben tarolja.
 */
public class CsomopontPozicio {
    /** A csomopont x koordinataja a terkepen. */
    private final int x;
    /** A csomopont y koordinataja a terkepen. */
    private final int y;

    /**
     * Letrehoz egy uj kepernyobeli poziciot.
     * @param x Az x koordinata.
     * @param y Az y koordinata.
     */
    public CsomopontPozicio(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Visszaadja az x koordinatat.
     * @return Az x koordinata.
     */
    public int getX() {
        return x;
    }

    /**
     * Visszaadja az y koordinatat.
     * @return Az y koordinata.
     */
    public int getY() {
        return y;
    }
}
