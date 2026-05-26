import java.awt.Color;

/**
 * A szimulációban szereplő buszok grafikus megjelenítéséért felelős nézet osztály.
 * Az ősosztály (JarmuView) által meghatározott rajzolási logikát
 * a buszokra jellemző színnel és betűjellel látja el.
 */
public class BuszView extends JarmuView {
    /**
     * Létrehozza a nézetet egy adott buszhoz.
     *
     * @param modell A kirajzolandó buszt reprezentáló modell objektum.
     */
    public BuszView(Jarmu modell) {
        super(modell);
    }

    /**
     * Meghatározza a busz aktuális állapotának megfelelő kitöltési színt.
     *
     * @return Sötétkék szín roncs állapot esetén, egyébként a buszok alapértelmezett kék színe.
     */
    @Override
    public Color getJarmuSzin() {
        if (modell.getAllapot() == JarmuAllapot.RONCS) return new Color(30, 60, 130);
        return new Color(50, 110, 190);
    }

    /**
     * Meghatározza a jármű grafikai dobozában megjelenő szöveges jelet.
     *
     * @return A buszt szimbolizáló "B" karakter.
     */
    @Override
    public String getJarmuJel() {
        return "B";
    }
}
