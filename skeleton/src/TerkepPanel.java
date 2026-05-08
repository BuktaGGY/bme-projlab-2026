import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.List;
import javax.swing.JPanel;

/**
 * A palya kirajzolasat vegzo Swing panel.
 * Megjeleniti az utakat, savokat, csomopontokat, POI objektumokat,
 * jarmuveket es a felhasznalo altal kijelolt utvonalat.
 */
public class TerkepPanel extends JPanel implements ModellMegfigyelo {
    /** A modelladatokat szolgaltato grafikus vezerlo. */
    private final GrafikusVezerlo vezerlo;
    /** A csomopontokra torteno kattintast feldolgozo objektum. */
    private UtvonalKattintasKezelo kattintasKezelo;

    /**
     * Letrehozza a terkep panelt es beallitja az egerkattintasok kezeleset.
     * @param vezerlo A grafikus vezerlo, amelybol a palya adatai lekerhetok.
     */
    public TerkepPanel(GrafikusVezerlo vezerlo) {
        this.vezerlo = vezerlo;
        setPreferredSize(new Dimension(880, 680));
        setBackground(new Color(245, 247, 250));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Csomopont csomopont = vezerlo.csomopontKoordinatanal(e.getX(), e.getY());
                if (csomopont != null && kattintasKezelo != null) {
                    kattintasKezelo.csomopontKivalasztva(csomopont);
                    return;
                }
                Jarmu jarmu = vezerlo.jarmuKoordinatanal(e.getX(), e.getY());
                if (jarmu != null && kattintasKezelo != null) {
                    kattintasKezelo.jarmuKivalasztva(jarmu);
                    return;
                }
                if (kattintasKezelo != null) {
                    kattintasKezelo.uresTeruletKivalasztva();
                }
            }
        });
    }

    /**
     * Modellvaltozas eseten ujrarajzolja a terkepet.
     */
    @Override
    public void modellValtozott() {
        repaint();
    }

    /**
     * Kirajzolja a teljes palyat es a rajta levo elemeket.
     * @param g A Swing altal atadott grafikus objektum.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rajzolUtak(g2);
        rajzolKijeloltUtvonal(g2);
        rajzolCsomopontokEsPoi(g2);
        rajzolJarmuvek(g2);
        g2.dispose();
    }

    /**
     * Beallitja, ki kapjon ertesitest, ha a felhasznalo csomopontra kattint.
     * @param kattintasKezelo A kattintasokat feldolgozo objektum.
     */
    public void setKattintasKezelo(UtvonalKattintasKezelo kattintasKezelo) {
        this.kattintasKezelo = kattintasKezelo;
    }

    /**
     * Kirajzolja az uthalozat savjait.
     * @param g2 A rajzolashoz hasznalt grafikus objektum.
     */
    private void rajzolUtak(Graphics2D g2) {
        for (Utszakasz ut : vezerlo.getUtak()) {
            CsomopontPozicio p1 = vezerlo.getElrendezes().getPozicio(ut.getEleje());
            CsomopontPozicio p2 = vezerlo.getElrendezes().getPozicio(ut.getVege());
            if (p1 == null || p2 == null) {
                continue;
            }

            int savDb = Math.max(1, ut.getSavok().size());
            for (int i = 0; i < ut.getSavok().size(); i++) {
                Sav sav = ut.getSavok().get(i);
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
    }

    /**
     * Kirajzolja a csomopontokat es a hozzajuk tartozo POI jeleket.
     * @param g2 A rajzolashoz hasznalt grafikus objektum.
     */
    private void rajzolCsomopontokEsPoi(Graphics2D g2) {
        for (PointOfInterest poi : vezerlo.getPoik()) {
            Csomopont cs = poi.getCsomopont();
            CsomopontPozicio p = vezerlo.getElrendezes().getPozicio(cs);
            if (p == null) {
                continue;
            }
            String jel = poiJel(poi);
            g2.setColor(new Color(255, 255, 255));
            g2.fillRect(p.getX() - 17, p.getY() - 38, 34, 22);
            g2.setColor(new Color(50, 60, 70));
            g2.drawRect(p.getX() - 17, p.getY() - 38, 34, 22);
            kozepreIr(g2, jel, p.getX(), p.getY() - 22);
        }

        for (Csomopont cs : vezerlo.getCsomopontok()) {
            CsomopontPozicio p = vezerlo.getElrendezes().getPozicio(cs);
            if (p == null) {
                continue;
            }
            g2.setColor(new Color(35, 45, 55));
            g2.fillOval(p.getX() - 8, p.getY() - 8, 16, 16);
            g2.setColor(Color.WHITE);
            kozepreIr(g2, cs.getID(), p.getX(), p.getY() + 4);
        }
    }

    /**
     * Kirajzolja a felhasznalo altal eppen kijelolt utvonalat.
     * @param g2 A rajzolashoz hasznalt grafikus objektum.
     */
    private void rajzolKijeloltUtvonal(Graphics2D g2) {
        List<Csomopont> utvonal = vezerlo.getKijeloltUtvonal();
        if (utvonal.isEmpty()) {
            return;
        }
        g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(30, 130, 95));
        for (int i = 0; i < utvonal.size(); i++) {
            CsomopontPozicio p = vezerlo.getElrendezes().getPozicio(utvonal.get(i));
            if (p == null) {
                continue;
            }
            g2.fillOval(p.getX() - 13, p.getY() - 13, 26, 26);
            if (i + 1 < utvonal.size()) {
                CsomopontPozicio kov = vezerlo.getElrendezes().getPozicio(utvonal.get(i + 1));
                if (kov != null) {
                    g2.drawLine(p.getX(), p.getY(), kov.getX(), kov.getY());
                }
            }
        }
    }

    /**
     * Kirajzolja a palyan levo jarmuveket.
     * @param g2 A rajzolashoz hasznalt grafikus objektum.
     */
    private void rajzolJarmuvek(Graphics2D g2) {
        for (Jarmu jarmu : vezerlo.getJarmuvek()) {
            Sav sav = jarmu.getAktualisSav();
            if (sav == null || sav.getSzuloUtszakasz() == null) {
                continue;
            }
            Utszakasz ut = sav.getSzuloUtszakasz();
            CsomopontPozicio p1 = vezerlo.getElrendezes().getPozicio(ut.getEleje());
            CsomopontPozicio p2 = vezerlo.getElrendezes().getPozicio(ut.getVege());
            if (p1 == null || p2 == null) {
                continue;
            }
            int savIndex = ut.getSavok().indexOf(sav);
            int savDb = Math.max(1, ut.getSavok().size());
            double arany = Math.max(0.0, Math.min(1.0, jarmu.getPozicioASavon() / (double) sav.getHossz()));
            Point p = savPont(p1, p2, savIndex, savDb, arany);

            g2.setColor(jarmuSzin(jarmu));
            g2.fillRect(p.x - 12, p.y - 12, 24, 24);
            g2.setColor(Color.BLACK);
            g2.drawRect(p.x - 12, p.y - 12, 24, 24);
            g2.setColor(Color.WHITE);
            kozepreIr(g2, jarmuJel(jarmu), p.x, p.y + 4);
            if (jarmu.getAllapot() == JarmuAllapot.MEGCSUSZOTT) {
                g2.setStroke(new BasicStroke(3));
                g2.setColor(new Color(255, 210, 40));
                g2.drawOval(p.x - 19, p.y - 19, 38, 38);
                g2.setColor(new Color(30, 30, 30));
                g2.fillOval(p.x + 10, p.y - 22, 16, 16);
                g2.setColor(Color.WHITE);
                kozepreIr(g2, "!", p.x + 18, p.y - 10);
                g2.setStroke(new BasicStroke(1));
            }
        }
    }

    /**
     * Kiszamolja egy sav adott aranyban levo kepernyopontjat.
     * @param p1 Az utszakasz egyik vegpontjanak pozicioja.
     * @param p2 Az utszakasz masik vegpontjanak pozicioja.
     * @param savIndex A sav indexe az utszakaszon.
     * @param savDb Az utszakasz savjainak szama.
     * @param arany A pont helye az utszakaszon 0 es 1 kozott.
     * @return A kiszamolt kepernyopont.
     */
    private Point savPont(CsomopontPozicio p1, CsomopontPozicio p2, int savIndex, int savDb, double arany) {
        double x1 = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();
        double dx = x2 - x1;
        double dy = y2 - y1;
        double hossz = Math.max(1.0, Math.sqrt(dx * dx + dy * dy));
        double nx = -dy / hossz;
        double ny = dx / hossz;
        double offset = (savIndex - (savDb - 1) / 2.0) * 16.0;
        int x = (int) Math.round(x1 + dx * arany + nx * offset);
        int y = (int) Math.round(y1 + dy * arany + ny * offset);
        return new Point(x, y);
    }

    /**
     * Meghatarozza egy sav kirajzolasi szinet az allapota alapjan.
     * @param sav A vizsgalt sav.
     * @return A savhoz tartozo szin.
     */
    private Color savSzin(Sav sav) {
        if (sav.getAllapot() == SavAllapot.BLOKKOLT) return new Color(80, 80, 80);
        if (sav.getAllapot() == SavAllapot.JEGPANCEL) return new Color(120, 190, 230);
        if (sav.getAllapot() == SavAllapot.SOZOTT) return new Color(230, 220, 145);
        if (sav.getAllapot() == SavAllapot.HAVAS) return new Color(230, 235, 240);
        return new Color(160, 170, 180);
    }

    /**
     * Meghatarozza egy jarmu kirajzolasi szinet a tipusa es allapota alapjan.
     * @param jarmu A kirajzolando jarmu.
     * @return A jarmu szine.
     */
    private Color jarmuSzin(Jarmu jarmu) {
        if (jarmu.isHokotro()) return new Color(220, 130, 30);
        if (jarmu.isBusz()) return new Color(50, 110, 190);
        if (jarmu.getAllapot() == JarmuAllapot.RONCS) return new Color(120, 30, 30);
        return new Color(190, 50, 55);
    }

    /**
     * Meghatarozza a jarmu ikonjan megjeleno betut.
     * @param jarmu A kirajzolando jarmu.
     * @return A jarmu rovid jele.
     */
    private String jarmuJel(Jarmu jarmu) {
        return jarmu.getTerkepJel();
    }

    /**
     * Meghatarozza a POI ikonjan megjeleno betut.
     * @param poi A kirajzolando POI.
     * @return A POI rovid jele.
     */
    private String poiJel(PointOfInterest poi) {
        return poi.getTerkepJel();
    }

    /**
     * A megadott szoveget vizszintesen kozepre igazitva rajzolja ki.
     * @param g2 A rajzolashoz hasznalt grafikus objektum.
     * @param text A kirajzolando szoveg.
     * @param x A szoveg kozep x koordinataja.
     * @param y A szoveg alapvonalanak y koordinataja.
     */
    private void kozepreIr(Graphics2D g2, String text, int x, int y) {
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(text, x - fm.stringWidth(text) / 2, y);
    }
}
