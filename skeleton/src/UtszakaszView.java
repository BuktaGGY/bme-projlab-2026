import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

public class UtszakaszView implements IRajzolhato {
    private final Utszakasz modell;

    public UtszakaszView(Utszakasz modell) {
        this.modell = modell;
    }

    @Override
    public void rajzol(Graphics g, GrafikusElrendezes elrendezes) {
        CsomopontPozicio p1 = elrendezes.getPozicio(modell.getEleje());
        CsomopontPozicio p2 = elrendezes.getPozicio(modell.getVege());
        if (p1 == null || p2 == null) return;
        Graphics2D g2 = (Graphics2D) g;
        int savDb = Math.max(1, modell.getSavok().size());
        for (int i = 0; i < modell.getSavok().size(); i++) {
            Sav sav = modell.getSavok().get(i);
            Point a = savPont(p1, p2, i, savDb, 0.0);
            Point b = savPont(p1, p2, i, savDb, 1.0);
            g2.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(savSzin(sav));
            g2.draw(new Line2D.Double(a.x, a.y, b.x, b.y));
            g2.setStroke(new BasicStroke(1));
            g2.setColor(new Color(70, 70, 70));
            g2.drawString(sav.getId(), (a.x + b.x) / 2 + 4, (a.y + b.y) / 2 - 4);
        }
    }

    private Color savSzin(Sav sav) {
        if (sav.getAllapot() == SavAllapot.BLOKKOLT) return new Color(80, 80, 80);
        if (sav.getAllapot() == SavAllapot.JEGPANCEL) return new Color(120, 190, 230);
        if (sav.getAllapot() == SavAllapot.SOZOTT) return new Color(230, 220, 145);
        if (sav.getAllapot() == SavAllapot.HAVAS) return new Color(230, 235, 240);
        return new Color(160, 170, 180);
    }

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
}
