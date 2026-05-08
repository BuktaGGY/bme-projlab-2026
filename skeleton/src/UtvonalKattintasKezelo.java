/**
 * A terkepen torteno csomopont-kattintasok kezelesere szolgalo interfesz.
 * Segitsegevel a TerkepPanel nem kozvetlenul a foablak vagy a vezerlo konkret
 * megvalositasatol fugg.
 */
public interface UtvonalKattintasKezelo {
    /**
     * Akkor hivodik meg, amikor a felhasznalo kivalasztott egy csomopontot.
     * @param csomopont A kivalasztott csomopont.
     */
    void csomopontKivalasztva(Csomopont csomopont);

    /**
     * Akkor hivodik meg, amikor a felhasznalo jarmure kattint.
     * @param jarmu A kivalasztott jarmu.
     */
    void jarmuKivalasztva(Jarmu jarmu);

    /**
     * Akkor hivodik meg, amikor a felhasznalo ures teruletre kattint.
     */
    void uresTeruletKivalasztva();
}
