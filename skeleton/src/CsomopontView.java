import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * A logikai csomópontok grafikus megjelenítéséért felelős nézet osztály.
 * Egy egyszerű sötét kört és a csomópont szöveges azonosítóját (ID) rajzolja ki a képernyőre.
 */
public class CsomopontView implements IRajzolhato {
    /** A megjelenítendő csomópont logikai modellje. */
    private final Csomopont modell;

    /**
     * Létrehozza a nézetet a megadott csomóponthoz.
     *
     * @param modell A kirajzolandó Csomopont objektum.
     */
    public CsomopontView(Csomopont modell) {
        this.modell = modell;
    }

    /**
     * Kirajzolja a csomópontot a GrafikusElrendezes által meghatározott koordinátára.
     *
     * @param g A rajzoláshoz használt grafikus kontextus.
     * @param elrendezes A logikai csomópontokat képernyő-koordinátákra fordító objektum.
     */
    @Override
    public void rajzol(Graphics g, GrafikusElrendezes elrendezes) {
        CsomopontPozicio p = elrendezes.getPozicio(modell);
        if (p == null) return;
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(35, 45, 55));
        g2.fillOval(p.getX() - 8, p.getY() - 8, 16, 16);
        g2.setColor(Color.WHITE);
        FontMetrics fm = g2.getFontMetrics();
        String id = modell.getID();
        g2.drawString(id, p.getX() - fm.stringWidth(id) / 2, p.getY() + 4);
    }
}
