public class Soszoro extends KotroFej {

    public Soszoro() {
        super(150);
    }

    /**
     * Sót szór az aktuális sávra a hókotró saját sókészletéből, ami megolvasztja 
     * a jeget és megakadályozza a további fagyást. A működéshez sóra van szükség.
     * @param sav           Az a sáv, amelyre a sót szórja.
     * @param hk            A műveletet végző hókotró referenciája, ahonnan a sót fogyasztja.
     */
    @Override
    public void takarit(Sav sav, Hokotro hk) {
        // A hókotró 50 egységet halad, így 50 sót kell elszórnia
        if (hk.getSo() >= 50) {
            hk.setSo(hk.getSo() - 50); // Só csökkentése 50-nel
            sav.sotSzor(); // Ez beállítja a so_ido-t 5-re a sávon
        }
    }

    @Override
    public String getFejTipus() {
        return "soszoro";
    }
}