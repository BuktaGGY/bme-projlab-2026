import java.util.HashMap;
import java.util.Map;

/**
 * A grafikus palya elrendezeset tarolo osztaly.
 * Felelossege, hogy a modellbeli csomopontokhoz kepernyobeli poziciot rendeljen.
 */
public class GrafikusElrendezes {
    /** A csomopontokhoz tartozo kepernyobeli poziciok. */
    private final Map<Csomopont, CsomopontPozicio> csomopontPoziciok = new HashMap<>();

    /**
     * Beallitja egy csomopont kepernyobeli poziciojat.
     * @param csomopont A pozicionalando csomopont.
     * @param x Az x koordinata.
     * @param y Az y koordinata.
     */
    public void setPozicio(Csomopont csomopont, int x, int y) {
        csomopontPoziciok.put(csomopont, new CsomopontPozicio(x, y));
    }

    /**
     * Visszaadja egy csomopont kepernyobeli poziciojat.
     * @param csomopont A keresett csomopont.
     * @return A csomopont pozicioja, vagy null, ha nincs beallitva.
     */
    public CsomopontPozicio getPozicio(Csomopont csomopont) {
        return csomopontPoziciok.get(csomopont);
    }
}
