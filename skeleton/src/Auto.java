/**
 * Az osztály felelőssége, hogy meghatározza a játékban szereplő autók működését.
 * Megvalósítja az autó mozgását, frissíti az állapotát.
 */
public class Auto extends SerulekenyJarmu {
    private PointOfInterest cel;

    public Auto(String id) {
        super(id);
    }



    /**
     * Frissíti az autó állapotát, ha az útszakasz amin halad nem járható.
     */
    @Override
    public void frissitAllapot() {

        switch (aktualisSav.getAllapot()) {
            case HAVAS:
            case BLOKKOLT:
                setAllapot(JarmuAllapot.ELAKADT);
                break;
            default: setAllapot(JarmuAllapot.HALAD); break;
        }

    }

    /**
     * Baleset esetén az autó ronccsá válik, a sáv amin halad pedig blokkolt lesz.
     */
    @Override
    public void balesetezik() {

        setAllapot(JarmuAllapot.RONCS);
        aktualisSav.setSavAllapot(SavAllapot.BLOKKOLT);

    }

    /**
     * Ujratervezi az autó útvonalát, amin az autó haladni fog.
     * @param ut út
     */
    public void ujraTervezes(UtvonalTervezo ut) {
        
        ut.utvonalKeres();
        this.utvonalFrissit();
        
    }

    /**
     * A roncs eltakarítása után törli az autó objektumot.
     */
    public void megsemmisites() {
        
        if (aktualisSav != null) {
            // A roncs eltakarítása után a sáv újra járható lesz
            aktualisSav.setSavAllapot(SavAllapot.TISZTA); 
        }
        
    }

    public void setCel(PointOfInterest cel) {
        this.cel = cel;
    }

    public void statKiir(){
        System.out.println("[STAT] AUTO "+ this.id + " | sav: " + aktualisSav + " | poz: " + pozicioASavon
        +" | allapot: "+ allapot+ " | cel: " + cel);
    }
}