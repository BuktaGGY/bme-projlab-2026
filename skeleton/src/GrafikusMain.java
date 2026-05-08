import javax.swing.SwingUtilities;

/**
 * A grafikus felulet belepesi pontja.
 * Felelossege, hogy a Swing esemenykezelo szalan letrehozza a vezerlot
 * es megjelenitse a jatek foablakat.
 */
public class GrafikusMain {
    /**
     * Elinditja a grafikus alkalmazast.
     * @param args Parancssori argumentumok, jelenleg nincsenek hasznalva.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GrafikusVezerlo vezerlo = new GrafikusVezerlo();
            JatekAblak ablak = new JatekAblak(vezerlo);
            ablak.setVisible(true);
        });
    }
}
