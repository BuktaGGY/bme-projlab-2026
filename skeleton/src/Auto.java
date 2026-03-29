public class Auto extends SerulekenyJarmu {
    public Auto(String name) {
        super();
        Skeleton.ctor(this, name);
    }

    @Override
    public void frissitAllapot() {
        Skeleton.call(this, "frissitAllapot");

        switch (aktualisSav.getAllapot()) {
            case HAVAS, BLOKKOLT: setAllapot(JarmuAllapot.ELAKADT); break;
            default: setAllapot(JarmuAllapot.HALAD); break;
        }

        Skeleton.ret();
    }

    @Override
    public void balesetezik() {
        Skeleton.call(this, "balesetezik");

        setAllapot(JarmuAllapot.RONCS);
        aktualisSav.setSavAllapot(SavAllapot.BLOKKOLT);

        Skeleton.ret();
    }

    public void ujraTervezes(UtvonalTervezo ut) {
        Skeleton.call(this, "ujraTervezes", "ut");
        
        ut.utvonalKeres();
        this.utvonalFrissit();
        
        Skeleton.ret();
    }

    public void megsemmisites() {
        Skeleton.call(this, "megsemmisites");
        
        if (aktualisSav != null) {
            // A roncs eltakarítása után a sáv újra járható lesz
            aktualisSav.setSavAllapot(SavAllapot.TISZTA); 
        }
        
        Skeleton.ret();
    }
}