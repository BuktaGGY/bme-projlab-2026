import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

/**
 * Az útszakaszok és a rajtuk lévő sávok grafikus megjelenítéséért felelős nézet osztály.
 * Képes megkülönböztetni és egyedi stílusban kirajzolni a normál utakat, a hidakat
 * és az alagutakat
 */
public class UtszakaszView implements IRajzolhato {
    private final Utszakasz modell;

    /**
     * Konstruktor, létrehozza a nézetet a megadott útszakaszhoz
     * @param modell kirajzolandó objektum
     */
    public UtszakaszView(Utszakasz modell) {
        this.modell = modell;
    }

    /**
     * Kirajzolja az útszakaszt a típusának (sima út, híd, alagút)
     * és sávjainak megfelelő vizuális stílusban
     * @param g A rajzoláshoz használt grafikus kontextus.
     * @param elrendezes A logikai csomópontokat képernyő-koordinátákra fordító objektum
     */
    @Override
    public void rajzol(Graphics g, GrafikusElrendezes elrendezes) {
        CsomopontPozicio p1 = elrendezes.getPozicio(modell.getEleje());
        CsomopontPozicio p2 = elrendezes.getPozicio(modell.getVege());
        if (p1 == null || p2 == null) return;
        Graphics2D g2 = (Graphics2D) g;
        boolean isHid    = modell instanceof Hid;
        boolean isAlagut = modell instanceof Alagut;
        int savDb = Math.max(1, modell.getSavok().size());

        if (isHid) {
            Point a0 = savPont(p1, p2, 0, savDb, 0.0);
            Point b0 = savPont(p1, p2, savDb - 1, savDb, 1.0);
            g2.setStroke(new BasicStroke(18, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(new Color(140, 100, 60));
            g2.draw(new Line2D.Double(a0.x, a0.y, b0.x, b0.y));
        }

        if (isAlagut) {
            Point a0 = savPont(p1, p2, 0, savDb, 0.0);
            Point b0 = savPont(p1, p2, savDb - 1, savDb, 1.0);
            g2.setStroke(new BasicStroke(20, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
            g2.setColor(new Color(50, 45, 40));
            g2.draw(new Line2D.Double(a0.x, a0.y, b0.x, b0.y));
        }

        for (int i = 0; i < modell.getSavok().size(); i++) {
            Sav sav = modell.getSavok().get(i);
            Point a = savPont(p1, p2, i, savDb, 0.0);
            Point b = savPont(p1, p2, i, savDb, 1.0);

            if (isAlagut) {
                g2.setStroke(new BasicStroke(8,
                        BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND,
                        1f, new float[]{14f, 6f}, 0f));
            } else {
                g2.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            }
            g2.setColor(savSzin(sav));
            g2.draw(new Line2D.Double(a.x, a.y, b.x, b.y));

            if (isHid) {
                g2.setStroke(new BasicStroke(2));
                g2.setColor(new Color(210, 200, 175));
                Point al = savPontOldal(p1, p2, i, savDb, 0.0,  7);
                Point bl = savPontOldal(p1, p2, i, savDb, 1.0,  7);
                Point ar = savPontOldal(p1, p2, i, savDb, 0.0, -7);
                Point br = savPontOldal(p1, p2, i, savDb, 1.0, -7);
                g2.draw(new Line2D.Double(al.x, al.y, bl.x, bl.y));
                g2.draw(new Line2D.Double(ar.x, ar.y, br.x, br.y));
            }

            g2.setStroke(new BasicStroke(1));
            g2.setColor(new Color(70, 70, 70));
            g2.drawString(sav.getId(), (a.x + b.x) / 2 + 4, (a.y + b.y) / 2 - 4);
        }
    }

    /**
     * Visszaadja a sáv aktuális állapotának megfelelő színt.
     * @param sav A vizsgált sáv objektum
     * @return A sáv megjelenítési színe
     */
    private Color savSzin(Sav sav) {
        if (sav.getAllapot() == SavAllapot.BLOKKOLT) return new Color(80, 80, 80);
        if (sav.getAllapot() == SavAllapot.JEGPANCEL) return new Color(120, 190, 230);
        if (sav.getAllapot() == SavAllapot.SOZOTT) return new Color(230, 220, 145);
        if (sav.getAllapot() == SavAllapot.HAVAS) return new Color(230, 235, 240);
        return new Color(160, 170, 180);
    }

    /**
     * Kiszámítja egy útszakaszon lévő sáv adott arányú pontjának pixelkoordinátáját
     * @param p1 A kezdő csomópont pozíciója
     * @param p2 A vég csomópont pozíciója
     * @param savIndex A sáv sorszáma az útszakaszon belül
     * @param savDb Az útszakaszon lévő sávok összes száma
     * @param arany A szakasz hossza mentén vett arányy
     * @return Kiszámított koordináta
     */
    private Point savPont(CsomopontPozicio p1, CsomopontPozicio p2, int savIndex, int savDb, double arany) {
        double x1 = p1.getX(), y1 = p1.getY();
        double x2 = p2.getX(), y2 = p2.getY();
        double dx = x2 - x1, dy = y2 - y1;
        double hossz = Math.max(1.0, Math.sqrt(dx * dx + dy * dy));
        double nx = -dy / hossz, ny = dx / hossz;
        double offset = (savIndex - (savDb - 1) / 2.0) * 16.0;
        return new Point(
            (int) Math.round(x1 + dx * arany + nx * offset),
            (int) Math.round(y1 + dy * arany + ny * offset)
        );
    }

    /**
     * Sávtól oldalt eltolt pontot számít ki, amelyet híd esetén a sávok két
     * oldalán egy-egy vékonyabb vonal rajzolásához használunk.
     * @param p1 A kezdő csomópont pozíciója
     * @param p2 A vég csomópont pozíciója
     * @param savIndex A sáv sorszáma az útszakaszon belül
     * @param savDb Az útszakaszon lévő sávok összes száma
     * @param arany A szakasz hossza mentén vett arány
     * @param oldalEltolas Az alap sávpozícióhoz képesti extra geometriai eltolás pixelekben
     * @return A kiszámított képernyő-koordináta.
     */
    private Point savPontOldal(CsomopontPozicio p1, CsomopontPozicio p2,
                               int savIndex, int savDb, double arany, double oldalEltolas) {
        double x1 = p1.getX(), y1 = p1.getY();
        double x2 = p2.getX(), y2 = p2.getY();
        double dx = x2 - x1, dy = y2 - y1;
        double hossz = Math.max(1.0, Math.sqrt(dx * dx + dy * dy));
        double nx = -dy / hossz, ny = dx / hossz;
        double offset = (savIndex - (savDb - 1) / 2.0) * 16.0 + oldalEltolas;
        return new Point(
            (int) Math.round(x1 + dx * arany + nx * offset),
            (int) Math.round(y1 + dy * arany + ny * offset)
        );
    }
}
