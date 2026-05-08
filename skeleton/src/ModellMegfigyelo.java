/**
 * A modell valtozasait figyelo objektumok kozos interfesze.
 * A grafikus nezetek ezen keresztul kapnak ertesitest, hogy frissiteniuk kell magukat.
 */
public interface ModellMegfigyelo {
    /**
     * Akkor hivodik meg, amikor a megfigyelt modell allapota megvaltozott.
     */
    void modellValtozott();
}
