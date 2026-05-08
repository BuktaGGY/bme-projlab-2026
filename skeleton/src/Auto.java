/**
 * Az osztály felelőssége, hogy meghatározza a játékban szereplő autók működését.
 * Megvalósítja az autó mozgását, frissíti az állapotát.
 */
public class Auto extends SerulekenyJarmu {
    private PointOfInterest cel;
    private PointOfInterest egyikPoi;
    private PointOfInterest masikPoi;
    private Csomopont aktualisCsomopont;
    private UtvonalTervezo utvonalTervezo;
    private int varakozasTick;

    public Auto(String id,  int pozicioSavban, Sav startSav, PointOfInterest cel) {
        super(id);
        this.allapot = JarmuAllapot.HALAD;
        this.pozicioASavon = pozicioSavban;
        this.setStartSav(startSav);
        this.cel = cel;
        this.sebesseg = SzimulacioBeallitasok.finomitottSzimulacio ? 2 : 10;
        this.varakozasTick = 0;
    }

    /**
     * Frissíti az autó állapotát, ha az útszakasz amin halad nem járható.
     */
    @Override
    public void frissitAllapot() {
        if (allapot == JarmuAllapot.RONCS){
            return;
        }
        if (varakozasTick > 0) {
            varakozasTick--;
            if (varakozasTick == 0) {
                utvonalTervezesAktualisCsomopontbol();
            }
            return;
        }
        if ((Utvonal == null || Utvonal.length == 0 || aktualisSav == null) && aktualisCsomopont != null) {
            utvonalTervezesAktualisCsomopontbol();
        }
        if (aktualisSav == null) {
            return;
        }
        if (aktualisSav.getAllapot() == SavAllapot.BLOKKOLT){
            if(allapot == JarmuAllapot.ELAKADT){return;}
            setAllapot(JarmuAllapot.ELAKADT);
            System.out.println("[ESEMENY] " + this.id + " | ELAKADT | " + aktualisSav.getId() + " savban (ho: " + aktualisSav.getHoVastagsag() + "cm)");
        } else {
            setAllapot(JarmuAllapot.HALAD);
        }
    }

    @Override
    public void mozog() {
        if (varakozasTick > 0 || aktualisSav == null || Utvonal == null || Utvonal.length == 0) {
            return;
        }
        if (this.allapot == JarmuAllapot.ELAKADT) {
            // Kiszabadulás logika
            Sav s = this.aktualisSav;
            if (szomszedosSavJarhato(s.getJobbSav())) {
                savValtas(s.getJobbSav());
                setAllapot(JarmuAllapot.HALAD);
                System.out.println("[ESEMENY] " + this.id + " | SAVOT_VALTOTT | " + s.getId() + " -> " + aktualisSav.getId() + " savra");
            } else if (szomszedosSavJarhato(s.getBalSav())) {
                savValtas(s.getBalSav());
                setAllapot(JarmuAllapot.HALAD);
                System.out.println("[ESEMENY] " + this.id + " | SAVOT_VALTOTT | " + s.getId() + " -> " + aktualisSav.getId() + " savra");
            } else {
                return;
            }
        }

        if (aktualisSav.getAllapot() == SavAllapot.JEGPANCEL && !aktualisSav.isZuzalekos()) {
            megcsuszik();
        }
        if (allapot == JarmuAllapot.HALAD) {
            aktualisSav.letapos(this);
            super.mozog();
        }
    }

    @Override
    protected void celbaErt() {
        System.out.println("[ESEMENY] " + id + " | CELBA_ERT | " + cel.getId());
        aktualisCsomopont = cel.getCsomopont();
        aktualisSav = null;
        Utvonal = new Utszakasz[0];
        utvonalIndex = 0;
        pozicioASavon = 0;
        cel = kovetkezoCel();
        varakozasTick = 3;
    }

    public void haladasSavban(int tav){
        int ujPoz = pozicioASavon + tav;
        if(ujPoz > 100) {
            pozicioASavon = ujPoz -100;
            utvonalFrissit();
            System.out.println("[ESEMENY] "+id+ " | UJ SZAKASZRA LEPETT | "+aktualisSav.getId() +" savban (uj pozicio: "+pozicioASavon+")");
        }else{
            pozicioASavon = ujPoz;
            if (allapot != JarmuAllapot.MEGCSUSZOTT) {
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
        if (allapot == JarmuAllapot.MEGCSUSZOTT || !SzimulacioBeallitasok.esely(SzimulacioBeallitasok.autoMegcsuszasEsely)) {
            return;
        }
        setAllapot(JarmuAllapot.MEGCSUSZOTT);
        System.out.println("[ESEMENY] " + this.id + " | MEGCSUSZOTT | " + aktualisSav.getId() + " savban");
    }

    /**
     * Újratervezi az autó útvonalát, amin az autó haladni fog.
     * @param ut út
     */
    public void ujraTervezes(UtvonalTervezo ut) {
        if (aktualisSav == null || aktualisSav.getSzuloUtszakasz() == null) return;

        Csomopont honnan = null;
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

    private boolean szomszedosSavJarhato(Sav sav) {
        if (sav == null || sav.getAllapot() == SavAllapot.BLOKKOLT) {
            return false;
        }
        if (!SzimulacioBeallitasok.finomitottSzimulacio) {
            return true;
        }
        return sav.getHoVastagsag() == 0
                && (sav.getAllapot() == SavAllapot.TISZTA || sav.getAllapot() == SavAllapot.SOZOTT);
    }

    public void setIngazoPoi(PointOfInterest egyikPoi, PointOfInterest masikPoi) {
        this.egyikPoi = egyikPoi;
        this.masikPoi = masikPoi;
        if (this.cel == null) {
            this.cel = masikPoi;
        }
    }

    public void setAktualisCsomopont(Csomopont aktualisCsomopont) {
        this.aktualisCsomopont = aktualisCsomopont;
    }

    public void setUtvonalTervezo(UtvonalTervezo utvonalTervezo) {
        this.utvonalTervezo = utvonalTervezo;
    }

    private PointOfInterest kovetkezoCel() {
        if (egyikPoi == null || masikPoi == null || aktualisCsomopont == null) {
            return cel;
        }
        if (aktualisCsomopont == egyikPoi.getCsomopont()) {
            return masikPoi;
        }
        return egyikPoi;
    }

    private void utvonalTervezesAktualisCsomopontbol() {
        if (utvonalTervezo == null || aktualisCsomopont == null || cel == null || cel.getCsomopont() == null) {
            return;
        }
        Utszakasz[] ujUtvonal = utvonalTervezo.utvonalKeres(aktualisCsomopont, cel.getCsomopont());
        if (ujUtvonal != null && ujUtvonal.length > 0) {
            Utvonal = ujUtvonal;
            utvonalIndex = 0;
            aktualisSav = ujUtvonal[0].getSavok().get(0);
            pozicioASavon = 0;
            aktualisCsomopont = null;
            System.out.println("[ESEMENY] " + id + " | UTVONAL_TERVEZVE | cel: " + cel.getId());
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
        String savId = aktualisSav != null ? aktualisSav.getId() : "nincs";
        String celId = cel != null ? cel.getId() : "nincs";
        String allapotStr = varakozasTick > 0 ? "VARAKOZIK" : allapot.toString();
        System.out.println("[STAT] AUTO "+ this.id + " | sav: " + savId + " | poz: " + pozicioASavon
        +" | allapot: "+ allapotStr + " | cel: " + celId);
    }

}
