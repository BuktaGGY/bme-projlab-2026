import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class CsomopontView implements IRajzolhato {
    private final Csomopont modell;

    public CsomopontView(Csomopont modell) {
        this.modell = modell;
    }

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
