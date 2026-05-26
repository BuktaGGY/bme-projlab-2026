import java.awt.Color;

/**
 * A szimulációban szereplő személyautók grafikus megjelenítéséért felelős nézet osztály.
 * Az ősosztály által meghatározott általános rajzolási logikát
 * specifikus színnel és azonosító karakterrel látja el.
 */
public class AutoView extends JarmuView {

    /**
     * Létrehozza a nézetet egy adott autóra.
     *
     * @param modell A kirajzolandó autót reprezentáló modell objektum.
     */
    public AutoView(Jarmu modell) {
        super(modell);
    }

    /**
     * Meghatározza az autó aktuális állapotának megfelelő kitöltési színt.
     *
     * @return Sötétvörös szín roncs állapot esetén, egyébként az autók alapértelmezett pirosas színe.
     */
    @Override
    public Color getJarmuSzin() {
        if (modell.getAllapot() == JarmuAllapot.RONCS) return new Color(120, 30, 30);
        return new Color(190, 50, 55);
    }

    /**
     * Meghatározza a jármű grafikai dobozában megjelenő szöveges jelet.
     *
     * @return Az autót szimbolizáló "A" karakter.
     */
    @Override
    public String getJarmuJel() {
        return "A";
    }
}
