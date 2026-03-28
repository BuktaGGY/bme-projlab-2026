import java.util.List;

public class Munkahely extends PointOfInterest {
    private List<Auto> dolgozok;
   
    public Munkahely(String name) {
        super(name);
        Skeleton.ctor(this, name);
    }
}
