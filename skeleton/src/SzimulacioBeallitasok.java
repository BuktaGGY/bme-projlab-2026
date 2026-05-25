import java.util.Random;

/**
 * A szimulacio finomhangolhato beallitasait tartalmazo osztaly.
 * A grafikus jatekmod finomitott, lassabb es eselyalapu szabalyokat hasznal,
 * mig a konzolos tesztek alapertelmezetten a regi determinisztikus mukodest tartjak meg.
 */
public class SzimulacioBeallitasok {
    /** Jelzi, hogy a finomitott grafikus szabalyok aktivak-e. */
    public static boolean finomitottSzimulacio = false;
    /** Ennyi letaposas utan alakul ki jegpancel finomitott modban. */
    public static int jegpancelLetaposasiKuszob = 40;
    /** Az auto megcsuszasainak valoszinusege jegpancelon. */
    public static double autoMegcsuszasEsely = 0.25;
    /** A busz megcsuszasainak valoszinusege jegpancelon. */
    public static double buszMegcsuszasEsely = 0.18;
    /** Ennyi tickenkent jon gyenge havazas finomitott modban. */
    public static int havazasPeriodus = 6;
    /** A gyenge havazas mennyisege centimterben. */
    public static int havazasMennyiseg = 3;

    /** Determinisztikus veletlengenerator a reprodukalhato grafikus szimulaciohoz. */
    private static final Random random = new Random(7);

    /**
     * Peldanyositas tiltasa, mert ez az osztaly csak statikus beallitasokat tartalmaz.
     */
    private SzimulacioBeallitasok() {
    }

    /**
     * Eldonti, hogy egy valoszinusegi esemeny bekovetkezik-e.
     * Nem finomitott modban mindig igazat ad, hogy a regi tesztek determinisztikusak maradjanak.
     * @param valoszinuseg Az esemeny valoszinusege 0 es 1 kozott.
     * @return Igaz, ha az esemeny bekovetkezik.
     */
    public static boolean esely(double valoszinuseg) {
        if (!finomitottSzimulacio) {
            return true;
        }
        return random.nextDouble() < valoszinuseg;
    }
}
