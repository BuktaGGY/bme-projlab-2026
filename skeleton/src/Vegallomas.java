import java.util.List;

public class Vegallomas extends PointOfInterest {
    private List<Busz> erintoBuszok;
    private int erintesekSzama;

    public Vegallomas(String name) {
        super(name);
        Skeleton.ctor(this, name);
    }

    /**
     * Növeli a buszok általi érintések számát eggyel.
     */
    public void addErintes() { erintesekSzama++; }
}
