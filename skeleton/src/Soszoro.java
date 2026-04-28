public class Soszoro extends KotroFej {

    // Konstruktor a Szkeleton névvel
    public Soszoro(String name) {
        super(150);
    }


	/**
     * Sót szór az aktuális sávra a hókotró saját sókészletéből, ami megolvasztja 
     * a jeget és megakadályozza a további fagyást. A működéshez sóra van szükség.
     * * @param sav           Az a sáv, amelyre a sót szórja.
     * @param szomszedosSav Az ősosztály miatt átvett paraméter, de ez a fej nem használja.
     * @param kotro         A műveletet végző hókotró referenciája, ahonnan a sót fogyasztja.
     */
    @Override
    public void takarit(Sav sav, Hokotro kotro) {
        if (kotro.getSo() > 0) {
            sav.sotSzor();
            kotro.setSo(kotro.getSo() - 1);
        }

    }
}