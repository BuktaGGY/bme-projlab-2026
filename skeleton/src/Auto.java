/**
 * Az osztály felelőssége, hogy meghatározza a játékban szereplő autók működését.
 * Megvalósítja az autó mozgását, frissíti az állapotát.
 */
public class Auto extends SerulekenyJarmu {
    public Auto(String name) {
        super();
        Skeleton.ctor(this, name);
    }

    /**
     * Frissíti az autó állapotát, ha az útszakasz amin halad nem járható.
     */
    @Override
    public void frissitAllapot() {
        Skeleton.call(this, "frissitAllapot");

        switch (aktualisSav.getAllapot()) {
            case HAVAS, BLOKKOLT: setAllapot(JarmuAllapot.ELAKADT); break;
            default: setAllapot(JarmuAllapot.HALAD); break;
        }

        Skeleton.ret();
    }

    /**
     * Baleset esetén az autó ronccsá válik, a sáv amin halad pedig blokkolt lesz.
     */
    @Override
    public void balesetezik() {
        Skeleton.call(this, "balesetezik");

        setAllapot(JarmuAllapot.RONCS);
        aktualisSav.setSavAllapot(SavAllapot.BLOKKOLT);

        Skeleton.ret();
    }

    /**
     * Ujratervezi az autó útvonalát, amin az autó haladni fog.
     * @param ut út
     */
    public void ujraTervezes(UtvonalTervezo ut) {
        Skeleton.call(this, "ujraTervezes", "ut");
        
        ut.utvonalKeres();
        this.utvonalFrissit();
        
        Skeleton.ret();
    }

    /**
     * A roncs eltakarítása után törli az autó objektumot.
     */
    public void megsemmisites() {
        Skeleton.call(this, "megsemmisites");
        
        if (aktualisSav != null) {
            // A roncs eltakarítása után a sáv újra járható lesz
            aktualisSav.setSavAllapot(SavAllapot.TISZTA); 
        }
        
        Skeleton.ret();
    }
}