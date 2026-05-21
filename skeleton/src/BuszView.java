import java.awt.Color;

public class BuszView extends JarmuView {
    public BuszView(Jarmu modell) {
        super(modell);
    }

    @Override
    public Color getJarmuSzin() {
        if (modell.getAllapot() == JarmuAllapot.RONCS) return new Color(30, 60, 130);
        return new Color(50, 110, 190);
    }

    @Override
    public String getJarmuJel() {
        return "B";
    }
}
