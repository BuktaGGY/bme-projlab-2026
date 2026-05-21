import java.awt.Color;

public class AutoView extends JarmuView {
    public AutoView(Jarmu modell) {
        super(modell);
    }

    @Override
    public Color getJarmuSzin() {
        if (modell.getAllapot() == JarmuAllapot.RONCS) return new Color(120, 30, 30);
        return new Color(190, 50, 55);
    }

    @Override
    public String getJarmuJel() {
        return "A";
    }
}
