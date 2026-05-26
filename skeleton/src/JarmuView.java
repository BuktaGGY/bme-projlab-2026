import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public abstract class JarmuView implements IRajzolhato {
    protected Jarmu modell;

    public JarmuView(Jarmu modell) {
        this.modell = modell;
    }

    public abstract Color getJarmuSzin();

    public abstract String getJarmuJel();

    @Override
    public void rajzol(Graphics g, GrafikusElrendezes elrendezes) {
        Sav sav = modell.getAktualisSav();
        int poz = modell.getPozicioASavon();
        if (sav == null) {
            sav = modell.getDisplaySav();
            poz = modell.getDisplayPozicio();
        }
        if (sav == null || sav.getSzuloUtszakasz() == null) return;
        Utszakasz ut = sav.getSzuloUtszakasz();
        CsomopontPozicio p1 = elrendezes.getPozicio(ut.getEleje());
        CsomopontPozicio p2 = elrendezes.getPozicio(ut.getVege());
        if (p1 == null || p2 == null) return;
        int savIndex = ut.getSavok().indexOf(sav);
        int savDb = Math.max(1, ut.getSavok().size());
        double arany = Math.max(0.0, Math.min(1.0, poz / (double) sav.getHossz()));
        if (modell.isAktualisIranyForditott()) {
            arany = 1.0 - arany;
        }
        Point p = savPont(p1, p2, savIndex, savDb, arany);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(getJarmuSzin());
        g2.fillRect(p.x - 12, p.y - 12, 24, 24);
        g2.setColor(Color.BLACK);
        g2.drawRect(p.x - 12, p.y - 12, 24, 24);
        g2.setColor(Color.WHITE);
        String jel = getJarmuJel();
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(jel, p.x - fm.stringWidth(jel) / 2, p.y + 4);

        if (modell.getAllapot() == JarmuAllapot.MEGCSUSZOTT) {
            g2.setStroke(new BasicStroke(3));
            g2.setColor(new Color(255, 210, 40));
            g2.drawOval(p.x - 19, p.y - 19, 38, 38);
            g2.setColor(new Color(30, 30, 30));
            g2.fillOval(p.x + 10, p.y - 22, 16, 16);
            g2.setColor(Color.WHITE);
            g2.drawString("!", p.x + 14, p.y - 10);
            g2.setStroke(new BasicStroke(1));
        }
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
