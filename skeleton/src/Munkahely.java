import java.util.List;

public class Munkahely extends PointOfInterest {
    private List<Auto> dolgozok;

    private String id;
   
    /**
     * Konstruktor
     * @param id A példány azonosítója a naplózáshoz.
     */
    public Munkahely(String id)
    {
        super(id);
    }

    public void StatKiir(){
        System.out.println("[STAT] MUNKAHELY " + this.id); //TODO listaelemek kiirasa
    }
}
