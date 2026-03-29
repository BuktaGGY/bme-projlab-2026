/**
 * Lakás osztály, az autó úticéljaként funkcionál.
 */
public class Lakas extends PointOfInterest {
    private Munkahely par;

    public Lakas(String name) {
        super(name);
        Skeleton.ctor(this, name);
    }
}
