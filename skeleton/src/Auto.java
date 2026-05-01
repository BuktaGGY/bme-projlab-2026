/**
 * Az osztály felelőssége, hogy meghatározza a játékban szereplő autók működését.
 * Megvalósítja az autó mozgását, frissíti az állapotát.
 */
public class Auto extends SerulekenyJarmu {
    private PointOfInterest cel;

    public Auto(String id,  int pozicioSavban, Sav startSav, PointOfInterest cel) {
        super(id);
        this.allapot = JarmuAllapot.HALAD;
        this.pozicioASavon = pozicioSavban;
        this.setStartSav(startSav);
        this.cel = cel;
        sebesseg = 1;
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

    public void mozog(Object utszakasz) {
        if(this.allapot == JarmuAllapot.ELAKADT) {

            //Kiszabadulás logika
            Sav s =this.aktualisSav;
            if(s.getJobbSavAllapot() != SavAllapot.BLOKKOLT) {
                savValtas(s.getJobbSav());
                setAllapot(JarmuAllapot.HALAD);

                return;
            }
            if(s.getBalSavAllapot() != SavAllapot.BLOKKOLT){
                savValtas(s.getBalSav());
                setAllapot(JarmuAllapot.HALAD);
            }
        }
        else{
            if(allapot == JarmuAllapot.HALAD) {
                aktualisSav.letapos(this);
                haladasSavban(sebesseg*10);//TODO hány egyseget lépjen?


            }
        }
    }

    public void haladasSavban(int tav){
        int ujPoz = pozicioASavon + tav;
        if(ujPoz > 100) {
            pozicioASavon = ujPoz -100;
            utvonalFrissit();
            System.out.println("[ESEMENY] "+id+ " | UJ SZAKASZRA LEPETT | "+aktualisSav.getId() +" savban (uj pozicio: "+pozicioASavon+")");
        }else{
            pozicioASavon = ujPoz;
            System.out.println("[ESEMENY] "+id+ " | MOZGOTT | "+aktualisSav.getId() +" savban (uj pozicio: "+pozicioASavon+")");
        }
    }

    /**
     * Lepteti egy egyseget az utvonalat
     */
    public void utvonalFrissit() {
        Utszakasz[] ujUtvonal = new Utszakasz[Utvonal.length];

        for(int i = 1; i < Utvonal.length; i++) {
            ujUtvonal[i-1] = Utvonal[i];
        }

        this.Utvonal = ujUtvonal;
        //aktualisSav = Utvonal.savok[0]; //TODO Savkezeles
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
        
        //this.Utvonal = ut.utvonalKeres();
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