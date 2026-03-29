import java.util.List;

public class Munkahely extends PointOfInterest {
    private List<Auto> dolgozok;
   
    /**
     * Konstruktor a Szkeleton teszteléshez.
     * @param name A példány azonosító neve a naplózáshoz.
     */
    public Munkahely(String name) {
        super(name);
        Skeleton.ctor(this, name);
    }
}
