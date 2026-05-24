/** 
 * A sáv egy példányosítható osztály, amelynek elsődleges felelőssége egy sáv állapotának
 * nyilvántartása, illetve az állapotokat módosító események kezelése.
 * Felelős továbbá a saját járhatóságának megállapításáért, valamint ismeri a mellette lévő sávokat is.
*/
public class Sav {

    private String id;

    /**
     * A sávon található hó mennyiségét tárolja egész számként.
     */
    private int hoVastagsag;

    /**
     * Logikai érték, amely jelzi, hogy a sáv le van-e sózva.
     */
    private boolean isSozott;

    /**
     * Egész szám, amely tárolja, hogy még hány másodpercig marad sózott állapotban a sáv.
     */
    private int sozottIdo;

    /**
     * Egész szám, amelyet ha a hóVastagság elér, akkor már nem lesz járható a sáv.
     */
    private int kuszobErtek;

    /**
     * Egész szám, amely tárolja, hogy hányszor haladtak át rajta járművek, amely a jó letaposását befolyásolja.
     */
    private int letaposottDb;

    /**
     * Tárolja a sáv állapotát.
     */
    private SavAllapot savAllapot;

    /**
     * A sáv mellett közvetlen jobbra található sáv.
     */
    private Sav savJobbra;

    /**
     * A sáv mellett közvetlen balra található sáv.
     */
    private Sav savBalra;

    /**
     * Tárolja hogy a sávon található-e zúzalék.
     */
    private boolean isZuzalekos;

    /**
     * Eltárolja hogy a sáv melyik útszakaszon tartózkodik.
     */
    private Utszakasz szuloUtszakasz;

    /**
     * Konstruktor
     */
    public Sav(String id) {
        this.id = id;
        savAllapot = SavAllapot.TISZTA;
        isSozott = false;
        hoVastagsag = 0;
        letaposottDb = 0;
        kuszobErtek = 40;
        savJobbra = null;
        savBalra = null;
        isZuzalekos = false;
    }

    /**
     * Megnöveli a hóvastagságot a paraméterként kapott mennyiséggel.
     * Ha a sáv állapota TISZTA volt, akkor ezt megváltoztatja HAVAS állapotra.
     * @param mennyiseg Mennyivel kell a hó vastagságát növelni
     */
    public void hoNovel(int mennyiseg){
        hoVastagsag +=mennyiseg;
        if(hoVastagsag > 0 && savAllapot == SavAllapot.TISZTA){
            setSavAllapot(SavAllapot.HAVAS);
        }
        if(hoVastagsag >= kuszobErtek){
            setSavAllapot(SavAllapot.BLOKKOLT);
        }
    }

    /**
     * Eltünteti a sávon található hómennyiséget.
     * A hóVastagság változót kinullázza és visszaadja a hóVastagság nullázása előtti értékét.
     * @return A hó vastagsága kinullázás előtt
     */
    public int hoEltuntet(){
        int temp = hoVastagsag;
        hoVastagsag = 0;
        letaposottDb = 0;

        if(savAllapot == SavAllapot.HAVAS){
            savAllapot = SavAllapot.TISZTA;
        }
        return temp;
    }

    /**
     * Függvény, amely megnöveli a sózottIdő változót és a sáv állapotát SÓZOTT-ra állítja.
     */
    public void sotSzor(){
        savAllapot = SavAllapot.SOZOTT;
        isSozott = true;
        sozottIdo = 5; 
    }

    /**
     * Függvény, amely beállítja, hogy a sávon található zúzalék
     */
    public void zuzalekotSzor(){
        isZuzalekos = true;
    }

    public int zuzalekotEltuntet(){
        isZuzalekos = false;
        return 1;
    }

    /**
     * Mindent eltüntet a sávról és a sáv állapotát TISZTA állapotra állítja.
     */
    public void mindentEltuntet(){
        savAllapot = SavAllapot.TISZTA;
        isSozott = false;
        sozottIdo = 0;
        isZuzalekos = false;
        hoVastagsag = 0;
        letaposottDb = 0;
    }

    /**
     * A sávon található hómennyiséget a közvetlen jobbra található sávra rakja át.
     * A közvetlen jobbra található sáv hóNövel függvényét meghívja és paraméterül átadja a saját hóVastagság változójának értékét.
     * Végül a saját hóVastagság változóját kinullázza.
     */
    public void hoOldalra(){
        if (savJobbra != null) {
            savJobbra.hoNovel(hoVastagsag);
        }
    }

    /**
     * Ha a sáv állapota JÉGPÁNCÉL, akkor ezt átállítja HAVAS állapotúra.
     * Továbbá növeli a sáv hóVastagság változóját.
     */
    public void jegTores(){
        if (savAllapot == SavAllapot.JEGPANCEL){
            savAllapot = SavAllapot.HAVAS;
            hoVastagsag += 5;
            letaposottDb = 0;
        }
    }

    /**
     * Növeli a letaposottDb változót eggyel, ha elérte a határt a letaposások száma,
     * akkor jégpánéllá válik az sáv állapota. A jármű megcsúszását is itt ellenőrizzük.
     * @param a A sávon áthaladó jármű
     */
    public void letapos(Jarmu a){
        letaposottDb++;
        int kuszob = SzimulacioBeallitasok.finomitottSzimulacio ? SzimulacioBeallitasok.jegpancelLetaposasiKuszob : 5;
        if(letaposottDb >= kuszob && savAllapot != SavAllapot.BLOKKOLT){
            setSavAllapot(SavAllapot.JEGPANCEL);
        }

        if (savAllapot == SavAllapot.JEGPANCEL && !isZuzalekos) {
            a.megcsuszik();
        }
    }

    /**
     * Visszaadja a neki közvetlen jobbra található sáv állapotát.
     * @return A jobbra található sáv állapota
     */
    public SavAllapot getJobbSavAllapot(){
        if (savJobbra == null){
            return SavAllapot.BLOKKOLT;
        }
        return savJobbra.getAllapot();
    }

    /**
     * Visszaadja a neki közvetlen balra található sáv állapotát.
     * @return A balra található sáv állapota
     */
    public SavAllapot getBalSavAllapot(){
        if (savBalra == null){
            return SavAllapot.BLOKKOLT;
        }
        return savBalra.getAllapot();
    }

    /**
     * Visszaadja a hóVastagság változó értékét.
     */
    public int getHoVastagsag(){
        return hoVastagsag;
    }

    /**
     * Beállítja a sáv állapotát a paraméterben kapott állapotra.
     * @param allapot A sáv új állapota
     */
    public void setSavAllapot(SavAllapot allapot){
        savAllapot = allapot;
    }

    /**
     * Visszaadja a sáv állapotát.
     * @return A saját állapota
     */
    public SavAllapot getAllapot(){
        return savAllapot;
    }

    /**
     * Beállítja a savJobbra változó értékét a paraméterben kapott sávra.
     * @param s A jobbra található sáv referenciája
     */
    public void setJobbSav(Sav s){
        savJobbra = s;
    }

    /**
     * Beállítja a savBalra változó értékét a paraméterben kapott sávra.
     * @param s A balra található sáv referenciája
     */
    public void setBalSav(Sav s){
        savBalra = s;
    }

    /**
     * Visszaadja a balra talalhato szomszedos sávot
     * @return bal szomszedos sav
     */
    public Sav getBalSav(){
        return savBalra;
    }

    /**
     * Visszaadja a jobb szomszedos sávot
     * @return jobb szomszédos sáv
     */
    public Sav getJobbSav() {
        return savJobbra;
    }

    /**
     * Visszaadja a sozott ido erteket
     * Teszteleshez kell
     * @return sozottIdo valtozo
     */
    public int getSozottIdo(){
        return sozottIdo;
    }

    /**
     * Sozott ido beallitasara szukseges segedfuggveny
     * Teszteleshez kell
     */
    public void setSozottIdo(int mennyiseg){
        sozottIdo = mennyiseg;
    }
    
    public String getId() { return id; }

    public boolean isZuzalekos() { return isZuzalekos; }

    public void setParameterek(int ho, boolean jeg, int so_ido, boolean zuzalek) {
        this.hoVastagsag = ho;
        this.sozottIdo = so_ido;
        this.isSozott = (so_ido > 0);
        this.isZuzalekos = zuzalek;
        
        if (ho >= this.kuszobErtek) {
            this.savAllapot = SavAllapot.BLOKKOLT;
        } else if (ho > 0) {
            this.savAllapot = SavAllapot.HAVAS;
        } else if (jeg) {
            this.savAllapot = SavAllapot.JEGPANCEL;
        } else {
            this.savAllapot = SavAllapot.TISZTA;
        }
    }


    
    public void setSzuloUtszakasz(Utszakasz ut) {
        this.szuloUtszakasz = ut;
    }

    public Utszakasz getSzuloUtszakasz() {
        return szuloUtszakasz;
    }

    public int getHossz() {
        if (szuloUtszakasz != null) {
            return szuloUtszakasz.getHossz();
        }
        return 100;
    }

    @Override
    public String toString() {
        return id;
    }
}
