import java.util.ArrayList;
import java.util.List;

public class Munkahely extends PointOfInterest {
    private List<Auto> dolgozok;

    public Munkahely(String id, Csomopont hely) {
        super(id, hely);
        this.dolgozok = new ArrayList<>();
    }

    @Override
    public String getTerkepJel() {
        return "M";
    }

    /**
     * Hozzáad egy dolgozót a munkahelyhez.
     * @param a     Az Auto példány, amely dolgozóként kerül hozzáadásra.
     */
    public void addDolgozo(Auto a) {
        dolgozok.add(a);
    }


    @Override
    public void StatKiir(){
        System.out.println("[STAT] MUNKAHELY:" + this.id + " | dolgozok szama: " + dolgozok.size());
    }
}
