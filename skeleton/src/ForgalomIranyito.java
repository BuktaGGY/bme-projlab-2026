import java.net.JarURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A forgalom irányításáért felelős központi osztály.
 * Nyilvántartja a pályán lévő járműveket, kezeli az ütközéseket,
 * és közvetítő (mediator) szerepet tölt be az ÚtvonalTervező és a Járművek között.
 */
public class ForgalomIranyito {


    /**
     * A forgalomirányító által felügyelt járművek listája.
     */
    private Map<String, Jarmu> jarmuvek;

	private UtvonalTervezo utvonalTervezo;
    private GazdasagKezelo gazdasagKezelo;


    /**
     * Konstruktor a Szkeleton teszteléshez.
     */
    public ForgalomIranyito() {
        jarmuvek = new HashMap<>();
    }

    public void setUtvonalTervezo(UtvonalTervezo ut) {
        this.utvonalTervezo = ut;
    }

    public void setGazdasagKezelo(GazdasagKezelo g) {
        this.gazdasagKezelo = g;
    }

    /**
     * Lépteti az összes nyilvántartott járművet a pályán.
     */
    public void mozgatJarmuvek() {

        for (Map.Entry<String, Jarmu> entry : jarmuvek.entrySet()) {
            String id = entry.getKey();
            Jarmu jarmu = entry.getValue();

            jarmu.frissitAllapot();
            jarmu.mozog(jarmu.Utvonal[0]);
        }

        this.utkozesVizsgalat();

    }
    /**
     * ELtakarítja a roncsot a pályáról, a takarításért járó összeg jováíródik a közös számlán.
     * @param jarmu Roncs jármű
     */
    public void roncsEltakarit(Jarmu jarmu) {
        
        if (jarmu instanceof Auto) {
            ((Auto) jarmu).megsemmisites();
        }
        
        if (utvonalTervezo != null) {
            utvonalTervezo.utFrissites();
        }
        
        if (gazdasagKezelo != null) {
            gazdasagKezelo.bevetelHozzaad(100);
        }
        
    }

    /**
     * Hozzáad egy új járművet a forgalomirányító nyilvántartásához.
     * @param j A hozzáadandó jármű.
     */
    public void addJarmu(Jarmu j) {
        jarmuvek.put(j.id, j);
    }

    public void deleteJarmu(Jarmu j) {jarmuvek.remove(j.id);}

    public Jarmu getJarmu(String id) {
        return  jarmuvek.get(id);
    }

    public boolean ContainsJarmuId(String id) {
        return jarmuvek.containsKey(id);
    }

    /**
     * Útzár esemény kezelése. Értesíti a nyilvántartott autókat, 
     * hogy egy útszakasz blokkolva lett, és újratervezésre van szükség.
     * @param ut Az útvonaltervező referenciája, ahonnan a járművek új utat kérhetnek.
     */
    public void utzarEsemeny(UtvonalTervezo ut) {

    }

    /**
     * Kiosztja a játékos által kijelölt új útvonalat a megadott busznak.
     * A csomópontokat útszakaszokká alakítja (a szkeletonban dummy módon), 
     * majd átadja a busznak.
     * @param b Az érintett busz.
     * @param csomopontok A kijelölt csomópontok tömbje.
     */
    public void buszUtvonalKiosztas(Busz b, Csomopont[] csomopontok) {
        
        Utszakasz[] ujUtvonal = new Utszakasz[0];

        b.UtvonalatKijelol(ujUtvonal);
        
    }



    /**
     * Ellenőrzi az esetleges ütközéseket
     */
    public void utkozesVizsgalat() {
        
    }

    /**
     * Ronccsá minősíti a résztvevő autókat, busz esetén mozgásképtelenné minősíti és beállítja az időzítőt,
     * ami megadja mikor tud újra mozogni a busz.
     * @param j1 Jármű1
     * @param j2 Jármű2
     */
    public void balesetKezel(Jarmu j1, Jarmu j2) {

        if(j1.getClass() == Busz.class && j2.getClass() == Busz.class){
            ((Busz) j1).balesetezik();
            ((Busz) j2).balesetezik();
        }
        if(j1.getClass() == Auto.class && j2.getClass() == Auto.class){
            ((Auto) j1).balesetezik();
            ((Auto) j2).balesetezik();
        }

    }
}