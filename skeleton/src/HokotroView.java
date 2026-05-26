import java.awt.Color;

/**
 * A szimulációban szereplő hókotrók grafikus megjelenítéséért felelős nézet osztály.
 * Az ősosztály (JarmuView) által meghatározott rajzolási logikát
 * a hókotrókra jellemző egyedi színnel és betűjellel paraméterezi.
 */
public class HokotroView extends JarmuView {
    /**
     * Létrehozza a nézetet egy adott hókotróhoz.
     *
     * @param modell A kirajzolandó hókotrót reprezentáló modell objektum.
     */
    public HokotroView(Jarmu modell) {
        super(modell);
    }

    /**
     * Meghatározza a hókotró kitöltési színét.
     *
     * @return A hókotrókat azonosító narancssárga szín.
     */
    @Override
    public Color getJarmuSzin() {
        return new Color(220, 130, 30);
    }


    /**
     * Meghatározza a jármű grafikai dobozában megjelenő szöveges jelet.
     *
     * @return A hókotrót szimbolizáló "H" karakter.
     */
    @Override
    public String getJarmuJel() {
        return "H";
    }
}
