/** 
 * A sáv egy példányosítható osztály, amelynek elsődleges felelőssége egy sáv állapotának
 * nyilvántartása, illetve az állapotokat módosító események kezelése.
 * Felelős továbbá a sajáat járhatóságának megállapításáért, valamint ismeri a mellette lévő sávokat is.
*/
public class Sav {

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
     * Konstruktor
     */
    public Sav(String name){
        Skeleton.ctor(this, name);
        savAllapot = SavAllapot.TISZTA;
        hoVastagsag = 0;
        letaposottDb = 0;
        this.savJobbra = null;
        this.savBalra = null;
    }

    /**
     * Megnöveli a hóvastagságot a paraméterként kapott mennyiséggel.
     * Ha a sáv állapota TISZTA volt, akkor ezt megváltoztatja HAVAS állapotra.
     * @param mennyiseg Mennyivel kell a hó vastagságát növelni
     */
    public void hoNovel(int mennyiseg){
        Skeleton.call(this, "hoNovel", String.valueOf(mennyiseg));

        hoVastagsag +=mennyiseg;
        if(hoVastagsag > 0 && savAllapot == SavAllapot.TISZTA){
            setSavAllapot(SavAllapot.HAVAS);
        }
        if(hoVastagsag >= 40){
            setSavAllapot(SavAllapot.BLOKKOLT);
        }

        Skeleton.ret("void");
    }

    /**
     * Eltünteti a sávon található hómennyiséget.
     * A hóVastagság változót kinullázza és visszaadja a hóVastagság nullázása előtti értékét.
     * @return A hó vastagsága kinullázás előtt
     */
    public int hoEltuntet(){
        Skeleton.call(this, "hoEltuntet");

        Skeleton.ret("10");
        return 10;
    }

    /**
     * Függvény, amely megnöveli a sózottIdő változót és a sáv állapotát SÓZOTT-ra állítja.
     */
    public void sotSzor(){
        Skeleton.call(this, "sotSzor");
        Skeleton.ret("void");
    }

    /**
     * Mindent eltüntet a sávról és a sáv állapotát TISZTA állapotra állítja.
     */
    public void mindentEltuntet(){
        Skeleton.call(this, "mindentEltuntet");
        Skeleton.ret("void");
    }

    /**
     * A sávon található hómennyiséget a közvetlen jobbra található sávra rakja át.
     * A közvetlen jobbra található sáv hóNövel függvényét meghívja és paraméterül átadja a saját hóVastagság változójának értékét.
     * Végül a saját hóVastagság változóját kinullázza.
     */
    public void hoOldalra(){
        Skeleton.call(this, "hoOldalra");
        savJobbra.hoNovel(hoVastagsag);
        Skeleton.ret("void");
    }

    /**
     * Ha a sáv állapota JÉGPÁNCÉL, akkor ezt átállítja HAVAS állapotúra.
     * Továbbá növeli a sáv hóVastagság változóját.
     */
    public void jegTores(){
        Skeleton.call(this, "jegTores");
        Skeleton.ret("void");
    }

    /**
     * Növeli a letaposottDb változót eggyel, ha elérte a határt a letaposások száma,
     * akkor jégpánéllá válik az sáv állapota.
     * @param a A sávon áthaladó autó
     */
    public void letapos(Jarmu a){
        Skeleton.call(this, "letapos", Skeleton.getName(a));

        letaposottDb++;
        if(letaposottDb >= 5 && savAllapot != SavAllapot.BLOKKOLT){
            setSavAllapot(SavAllapot.JEGPANCEL);
        }
        Skeleton.ret("void");
    }

    /**
     * Visszaadja a neki közvetlen jobbra található sáv állapotát.
     * @return A jobbra található sáv állapota
     */
    public SavAllapot getJobbSavAllapot(){
        Skeleton.call(this, "getJobbSavAllapot");
        Skeleton.ret("SavAllapot");
        return savJobbra.getAllapot();
    }

    /**
     * Visszaadja a neki közvetlen balra található sáv állapotát.
     * @return A balra található sáv állapota
     */
    public SavAllapot getBalSavAllapot(){
        Skeleton.call(this, "getBalSavAllapot");
        Skeleton.ret("SavAllapot");
        return savBalra.getAllapot();
    }

    /**
     * Visszaadja a hóVastagság változó értékét.
     */
    public int getHoVastagsag(){
        Skeleton.call(this, "getHoVastagsag");
        Skeleton.ret("int");
        return hoVastagsag;
    }

    /**
     * Beállítja a sáv állapotát a paraméterben kapott állapotra.
     * @param allapot A sáv új állapota
     */
    public void setSavAllapot(SavAllapot allapot){
        Skeleton.call(this, "setSavAllapot", String.valueOf(allapot));

        savAllapot = allapot;

        Skeleton.ret("void");
    }

    /**
     * Visszaadja a sáv állapotát.
     * @return A saját állapota
     */
    public SavAllapot getAllapot(){
        Skeleton.call(this, "getAllapot");
        //Skeleton.ret("SavAllapot");
        return savAllapot;
    }

    /**
     * Beállítja a savJobbra változó értékét a paraméterben kapott sávra.
     * @param s A jobbra található sáv referenciája
     */
    public void setJobbSav(Sav s){
        Skeleton.call(this, "setJobbSav", Skeleton.getName(s));

        savJobbra = s;

        Skeleton.ret("void");
    }

    /**
     * Beállítja a savBalra változó értékét a paraméterben kapott sávra.
     * @param s A balra található sáv referenciája
     */
    public void setBalSav(Sav s){
        Skeleton.call(this, "setBalSav", String.valueOf(s));

        savBalra = s;

        Skeleton.ret("void");
    }

    /**
     * Visszaadja a balra talalhato szomszedos sávot
     * @return bal szomszedos sav
     */
    public Sav getBalSav(){
        Skeleton.call(this, "getBalSav");
        Skeleton.ret(Skeleton.getName(this.savBalra));
        return this.savBalra;
    }

    /**
     * Visszaadja a jobb szomszedos sávot
     * @return jobb szomszédos sáv
     */
	public Sav getJobbSav() {
        Skeleton.call(this, "getSzomszedosSav");
        Skeleton.ret(Skeleton.getName(this.savJobbra)); // A jobb oldalit adja vissza
        return this.savJobbra;
    }

}
