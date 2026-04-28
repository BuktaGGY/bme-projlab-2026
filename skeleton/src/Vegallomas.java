import java.util.List;

/**
 * A buszok útvonalának végpontjait reprezentáló osztály (PointOfInterest).
 * Számon tartja, hogy hányszor érintették a buszok, és az érintéskor
 * pontszámot ad a JátékKezelőn keresztül a játékosnak.
 */
public class Vegallomas extends PointOfInterest {
    private List<Busz> erintoBuszok;
    private int erintesekSzama;
    private JatekKezelo jatekKezelo;

    /**
     * Konstruktor a Szkeleton teszteléshez.
     * @param name A példány azonosító neve a naplózáshoz.
     */
    public Vegallomas(String name) {
        super(name);
    }

    /**
     * Beállítja a JátékKezelő referenciáját a teszteléshez.
     * @param jk A JátékKezelő példány.
     */
    public void setJatekKezelo(JatekKezelo jk) {
        this.jatekKezelo = jk;
    }

    /**
     * Növeli a buszok általi érintések számát eggyel,
     * és jelzi a JátékKezelőnek, hogy adjon egy pontot.
     */
    public void addErintes() {
        
        erintesekSzama++;
        if (jatekKezelo != null) {
            jatekKezelo.buszPontszamNovel();
        }
        
    }
}