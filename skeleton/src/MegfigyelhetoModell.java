import java.util.ArrayList;
import java.util.List;

/**
 * Megfigyelheto modell alaposztaly.
 * Felelossege a modellvaltozasokra feliratkozott megfigyelok nyilvantartasa
 * es ertesitese.
 */
public class MegfigyelhetoModell {
    /** A modell valtozasaira feliratkozott megfigyelok listaja. */
    private final List<ModellMegfigyelo> megfigyelok = new ArrayList<>();

    /**
     * Hozzaad egy uj megfigyelot.
     * @param megfigyelo A feliratkozo objektum.
     */
    public void addMegfigyelo(ModellMegfigyelo megfigyelo) {
        if (megfigyelo != null && !megfigyelok.contains(megfigyelo)) {
            megfigyelok.add(megfigyelo);
        }
    }

    /**
     * Eltavolit egy korabban felvett megfigyelot.
     * @param megfigyelo Az eltavolitando objektum.
     */
    public void removeMegfigyelo(ModellMegfigyelo megfigyelo) {
        megfigyelok.remove(megfigyelo);
    }

    /**
     * Ertesiti az osszes feliratkozott megfigyelot a modell valtozasarol.
     */
    protected void ertesitMegfigyeloket() {
        List<ModellMegfigyelo> snapshot = new ArrayList<>(megfigyelok);
        for (ModellMegfigyelo megfigyelo : snapshot) {
            megfigyelo.modellValtozott();
        }
    }
}
