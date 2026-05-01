import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Prototipus proto = new Prototipus();
        proto.run();
    }
}

/**
 * Példa bemeneti sorok egyszerubb teszteléshez
 *
 * uj_csomopont c1
 * uj_csomopont c2
 * uj_ut u1 c1 c2 100
 * hozzaad_sav u1 s1
 * allit_sav s1 0 nem 0 nem
 * uj_poi p1 c1 munkahely
 * uj_poi p2 c2 vegallomas
 * lerak_auto a1 s1 50 p1
 * lerak_busz b1 s1 60 c2       //c2 helyett vegallomas kell majd
 * lerak_hokotro h1 s1 70 sarkany
 * havaz_sav s1 123
 * havaz_globalis 456
 * stat b1
 * stat h1
 *
 */