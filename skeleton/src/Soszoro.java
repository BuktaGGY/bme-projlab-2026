public class Soszoro extends KotroFej {

    public Soszoro() {
        super(800);
    }

    /**
     * Sót szór az aktuális sávra a hókotró saját sókészletéből, ami megolvasztja 
     * a jeget és megakadályozza a további fagyást. A működéshez sóra van szükség.
     * @param sav           Az a sáv, amelyre a sót szórja.
     * @param hk            A műveletet végző hókotró referenciája, ahonnan a sót fogyasztja.
     */
    @Override
    public void takarit(Sav sav, Hokotro hk) {
        if (hk.getSo() >= 50) {
            hk.setSo(hk.getSo() - 50);
            sav.sotSzor();
        }
    }

    @Override
    public String getFejTipus() {
        return "soszoro";
    }
}