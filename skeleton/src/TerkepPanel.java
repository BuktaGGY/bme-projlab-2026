import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class TerkepPanel extends JPanel implements ModellMegfigyelo {
    private final GrafikusVezerlo vezerlo;
    private UtvonalKattintasKezelo kattintasKezelo;
    private final List<IRajzolhato> utszakaszViewk = new ArrayList<>();
    private final List<IRajzolhato> jarmuViewk = new ArrayList<>();

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

    @Override
    public void modellValtozott() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GrafikusElrendezes elrendezes = vezerlo.getElrendezes();
        for (IRajzolhato elem : utszakaszViewk) {
            elem.rajzol(g2, elrendezes);
        }
        rajzolKijeloltUtvonal(g2);
        rajzolPoiIkonok(g2);
        for (IRajzolhato elem : jarmuViewk) {
            elem.rajzol(g2, elrendezes);
        }
        g2.dispose();
    }

    public void setKattintasKezelo(UtvonalKattintasKezelo kattintasKezelo) {
        this.kattintasKezelo = kattintasKezelo;
    }

    public void addJarmuView(IRajzolhato jv) {
        if (jv != null) jarmuViewk.add(jv);
    }

    public void setUtszakaszViewk(List<IRajzolhato> viewk) {
        utszakaszViewk.clear();
        if (viewk != null) utszakaszViewk.addAll(viewk);
    }

    public void clearJarmuViewk() {
        jarmuViewk.clear();
    }

    private void rajzolKijeloltUtvonal(Graphics2D g2) {
        List<Csomopont> utvonal = vezerlo.getKijeloltUtvonal();
        if (utvonal.isEmpty()) return;
        g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(30, 130, 95));
        for (int i = 0; i < utvonal.size(); i++) {
            CsomopontPozicio p = vezerlo.getElrendezes().getPozicio(utvonal.get(i));
            if (p == null) continue;
            g2.fillOval(p.getX() - 13, p.getY() - 13, 26, 26);
            if (i + 1 < utvonal.size()) {
                CsomopontPozicio kov = vezerlo.getElrendezes().getPozicio(utvonal.get(i + 1));
                if (kov != null) {
                    g2.drawLine(p.getX(), p.getY(), kov.getX(), kov.getY());
                }
            }
        }
    }

    private void rajzolPoiIkonok(Graphics2D g2) {
        for (PointOfInterest poi : vezerlo.getPoik()) {
            Csomopont cs = poi.getCsomopont();
            CsomopontPozicio p = vezerlo.getElrendezes().getPozicio(cs);
            if (p == null) continue;
            String jel = poi.getTerkepJel();
            g2.setColor(new Color(255, 255, 255));
            g2.fillRect(p.getX() - 17, p.getY() - 38, 34, 22);
            g2.setColor(new Color(50, 60, 70));
            g2.drawRect(p.getX() - 17, p.getY() - 38, 34, 22);
            kozepreIr(g2, jel, p.getX(), p.getY() - 22);
        }
    }

    private void kozepreIr(Graphics2D g2, String text, int x, int y) {
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(text, x - fm.stringWidth(text) / 2, y);
    }
}
