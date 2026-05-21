import java.awt.Color;

public class HokotroView extends JarmuView {
    public HokotroView(Jarmu modell) {
        super(modell);
    }

    @Override
    public Color getJarmuSzin() {
        return new Color(220, 130, 30);
    }

    @Override
    public String getJarmuJel() {
        return "H";
    }
}
