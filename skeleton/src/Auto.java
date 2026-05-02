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
        this.sebesseg = 10;
    }

    /**
     * Frissíti az autó állapotát, ha az útszakasz amin halad nem járható.
     */
    @Override
    public void frissitAllapot() {
        if (allapot == JarmuAllapot.RONCS){
            return;
        }
        if (aktualisSav.getAllapot() == SavAllapot.BLOKKOLT){
            setAllapot(JarmuAllapot.ELAKADT);
            System.out.println("[ESEMENY] " + this.id + " | ELAKADT | " + aktualisSav.getId() + " savban (ho: " + aktualisSav.getHoVastagsag() + "cm)");
        } else {
            setAllapot(JarmuAllapot.HALAD);
        }
    }

    @Override
    public void mozog(Object utszakasz) {
        if (this.allapot == JarmuAllapot.ELAKADT) {
            // Kiszabadulás logika
            Sav s = this.aktualisSav;
            if (s.getJobbSavAllapot() != SavAllapot.BLOKKOLT) {
                savValtas(s.getJobbSav());
                setAllapot(JarmuAllapot.HALAD);
                System.out.println("[ESEMENY] " + this.id + " | SAVOT_VALTOTT | " + s.getId() + " -> " + aktualisSav.getId() + " savra");
            } else if (s.getBalSavAllapot() != SavAllapot.BLOKKOLT) {
                savValtas(s.getBalSav());
                setAllapot(JarmuAllapot.HALAD);
                System.out.println("[ESEMENY] " + this.id + " | SAVOT_VALTOTT | " + s.getId() + " -> " + aktualisSav.getId() + " savra");
            } else {
                return;
            }
        }

        if (aktualisSav.getAllapot() == SavAllapot.JEGPANCEL && !aktualisSav.isZuzalekos()) {
            megcsuszik();
        } else if (allapot == JarmuAllapot.HALAD) {
            aktualisSav.letapos(this);
            super.mozog(utszakasz);
        }
    }

    @Override
    protected void celbaErt() {
        System.out.println("[ESEMENY] " + id + " | CELBA_ERT | " + cel.getId());
        megsemmisites();
    }

    public void haladasSavban(int tav){
        int ujPoz = pozicioASavon + tav;
        if(ujPoz > 100) {
            pozicioASavon = ujPoz -100;
            utvonalFrissit();
            System.out.println("[ESEMENY] "+id+ " | UJ SZAKASZRA LEPETT | "+aktualisSav.getId() +" savban (uj pozicio: "+pozicioASavon+")");
        }else{
            pozicioASavon = ujPoz;
            if (allapot != JarmuAllapot.MEGCSÚSZOTT) {
                System.out.println("[ESEMENY] "+id+ " | MOZGOTT | "+aktualisSav.getId() +" savban (uj pozicio: "+pozicioASavon+")");
            }
        }
    }

    /**
     * Lépteti (updateli) egy egységgel az útvonalat.
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

    @Override
    public void megcsuszik() {
        setAllapot(JarmuAllapot.MEGCSÚSZOTT);
        System.out.println("[ESEMENY] " + this.id + " | MEGCSUSZOTT | " + aktualisSav.getId() + " savban");
    }

    /**
     * Újratervezi az autó útvonalát, amin az autó haladni fog.
     * @param ut út
     */
    public void ujraTervezes(UtvonalTervezo ut) {
        if (aktualisSav == null || aktualisSav.getSzuloUtszakasz() == null) return;

        Csomopont honnan = aktualisSav.getSzuloUtszakasz().getMasikVeg(null);
        if (aktualisSav.getSzuloUtszakasz().eleje != null) {
            honnan = aktualisSav.getSzuloUtszakasz().vege; 
        }
        Csomopont hova = cel.getCsomopont();

        Utszakasz[] ujUtvonal = ut.utvonalKeres(honnan, hova);
        if (ujUtvonal != null) {
            this.Utvonal = ujUtvonal;
            this.utvonalIndex = 0;
            System.out.println("[ESEMENY] " + id + " | UJRATERVEZES | uj utvonal beallitva (kerulo)");
        }
    }

    /**
     * A roncs eltakarítása után törli az autó objektumot.
     */
    public void megsemmisites() {
        
        if (aktualisSav != null) {
            aktualisSav.setSavAllapot(SavAllapot.TISZTA); 
        }
        
    }

    public void setCel(PointOfInterest cel) {
        this.cel = cel;
    }

    public void statKiir(){
        System.out.println("[STAT] AUTO "+ this.id + " | sav: " + aktualisSav.getId() + " | poz: " + pozicioASavon
        +" | allapot: "+ allapot + " | cel: " + cel.getId());
    }

}