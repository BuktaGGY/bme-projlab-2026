import java.awt.Graphics;

/**
 * Közös interfész minden olyan objektum számára, amely megjeleníthető a grafikus felületen.
 * Az interfész megvalósítása lehetővé teszi, hogy a TerkepPanel egységesen,
 * a konkrét típus ismerete nélkül kezelje és rajzolja ki a különböző modell-elemeket
 * (utakat, csomópontokat, járműveket).
 */
public interface IRajzolhato {
    /**
     * Kirajzolja az adott objektumot a grafikus környezetbe.
     * @param g A Swing által biztosított grafikus környezet
     * @param elrendezes Az objektum, amely tárolja a logikai csomópontok és a
     *                   pixel-alapú képernyőpozíciók közötti összerendeléseket.
     */
    void rajzol(Graphics g, GrafikusElrendezes elrendezes);
}
