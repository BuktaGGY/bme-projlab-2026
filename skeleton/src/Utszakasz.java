
/**
 * Egy útszakasz egy absztrakt osztály, amelynek legfőbb felelőssége, hogy egy alapsablonként szolgáljon a különböző út típusoknak.
 * Tárolja a hálózatban tárolt helyét egy útszakasznak, tárolja a kezdő- és a végpontot, továbbá az útszakasz hosszát.
 * Emellett tárolóként is szolgál, birtokolja a rajta futó sávokat és kezeli az életciklusukat.
 */
public abstract class Utszakasz {
    
    /**
     * Egy egész szám, amely az útszakasz fizikai hosszát tárolja.
     */
    protected int hossz;

    /**
     * Egy referencia, amely az útszakasz kezdő pontjára mutat.
     */
    protected Csomopont eleje;

    /**
     * Egy referencia, amely az útszakasz végpontjára mutat.
     */
    protected Csomopont vege;

}
