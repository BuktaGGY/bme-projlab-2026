import java.util.ArrayList;
import java.util.List;

/**
 * A forgalom irányításáért felelős központi osztály.
 * Nyilvántartja a pályán lévő járműveket, kezeli az ütközéseket,
 * és közvetítő (mediator) szerepet tölt be az ÚtvonalTervező és a Járművek között.
 */
public class ForgalomIranyito {
    
    /**
     * A forgalomirányító által felügyelt járművek listája.
     */
    private List<Jarmu> jarmuvek = new ArrayList<>();

    /**
     * Konstruktor a Szkeleton teszteléshez.
     * @param name A példány azonosító neve a naplózáshoz.
     */
    public ForgalomIranyito(String name) {
        Skeleton.ctor(this, name);
    }

    /**
     * Hozzáad egy új járművet a forgalomirányító nyilvántartásához.
     * @param j A hozzáadandó jármű.
     */
    public void addJarmu(Jarmu j) {
        jarmuvek.add(j);
    }

    /**
     * Útzár esemény kezelése. Értesíti a nyilvántartott autókat, 
     * hogy egy útszakasz blokkolva lett, és újratervezésre van szükség.
     * @param ut Az útvonaltervező referenciája, ahonnan a járművek új utat kérhetnek.
     */
    public void utzarEsemeny(UtvonalTervezo ut) {
        Skeleton.call(this, "utzarEsemeny", "ut");

        for (Jarmu j : jarmuvek) {
            if (j instanceof Auto) {
                ((Auto) j).ujraTervezes(ut);
            }
        }
        
        Skeleton.ret();
    }

    /**
     * Kiosztja a játékos által kijelölt új útvonalat a megadott busznak.
     * A csomópontokat útszakaszokká alakítja (a szkeletonban dummy módon), 
     * majd átadja a busznak.
     * @param b Az érintett busz.
     * @param csomopontok A kijelölt csomópontok tömbje.
     */
    public void buszUtvonalKiosztas(Busz b, Csomopont[] csomopontok) {
        Skeleton.call(this, "buszUtvonalKiosztas", "b", "csomopontok");
        
        Utszakasz[] ujUtvonal = new Utszakasz[0];

        b.UtvonalatKijelol(ujUtvonal);
        
        Skeleton.ret();
    }

    /**
     * Lépteti az összes nyilvántartott járművet a pályán.
     */
    public void mozgatJarmuvek() {
        Skeleton.call(this, "mozgatJarmuvek");
        
        Object aktualisUtszakasz = new Object(); Skeleton.ctor(aktualisUtszakasz, "utszakasz");

        for (Jarmu j : jarmuvek) {
            j.frissitAllapot();
            j.mozog(aktualisUtszakasz);
        }

        this.utkozesVizsgalat();
        
        Skeleton.ret();
    }

    public void utkozesVizsgalat() {
        Skeleton.call(this, "utkozesVizsgalat");

        List<Auto> autok = new ArrayList<>();
        for (Jarmu j : jarmuvek) {
            if (j instanceof Auto) {
                autok.add((Auto) j);
            }
        }

        if (autok.size() >= 2) {
            autok.get(0).balesetezik();
            autok.get(1).balesetezik();
        }
        
        Skeleton.ret();
    }

    /**
     * Ronccsá minősíti a résztvevő autókat, busz esetén mozgásképtelenné minősíti és beállítja az időzítőt,
     * ami megadja mikor tud újra mozogni a busz.
     * @param j1 Jármű1
     * @param j2 Jármű2
     */
    public void balesetKezel(Jarmu j1, Jarmu j2) {
        Skeleton.call(this, "balesetKezel");

        if(j1.getClass() == Busz.class && j2.getClass() == Busz.class){
            ((Busz) j1).balesetezik();
            ((Busz) j2).balesetezik();
        }
        if(j1.getClass() == Auto.class && j2.getClass() == Auto.class){
            ((Auto) j1).balesetezik();
            ((Auto) j2).balesetezik();
        }

        Skeleton.ret();
    }
}