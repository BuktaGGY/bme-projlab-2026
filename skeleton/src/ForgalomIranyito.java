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

            if (jarmu.allapot == JarmuAllapot.HALAD || jarmu.allapot == JarmuAllapot.ELAKADT || jarmu.allapot == JarmuAllapot.MEGCSÚSZOTT) {
                jarmu.mozog(jarmu.Utvonal[0]);
            }
            this.utkozesVizsgalat();
        }
    }
    /**
     * ELtakarítja a roncsot a pályáról, a takarításért járó összeg jováíródik a közös számlán.
     * @param jarmu Roncs jármű
     */
    public void roncsEltakarit(Jarmu jarmu) {
        
        jarmu.megsemmisites();
        
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
        List<Jarmu> jarmuvekLista = new ArrayList<>(jarmuvek.values());
        for (int i = 0; i < jarmuvekLista.size(); i++){
            for (int j = i + 1; j < jarmuvekLista.size(); j++){
                Jarmu j1 = jarmuvekLista.get(i);
                Jarmu j2 = jarmuvekLista.get(j);
                if (j1.aktualisSav == j2.aktualisSav && Math.abs(j1.pozicioASavon - j2.pozicioASavon) <= 10){
                    if (j1.allapot == JarmuAllapot.MEGCSÚSZOTT || j2.allapot == JarmuAllapot.MEGCSÚSZOTT){
                        balesetKezel(j1, j2);
                        System.out.println("[ESEMENY] " + j1.id + ", " + j2.id + " | BALESET | " + j1.aktualisSav.getId() + " sav allapota BLOKKOLT lett");
                    }
                }
            }
        }
    }

    /**
     * Ronccsá minősíti a résztvevő autókat, busz esetén mozgásképtelenné minősíti és beállítja az időzítőt,
     * ami megadja mikor tud újra mozogni a busz.
     * @param j1 Jármű1
     * @param j2 Jármű2
     */
    public void balesetKezel(Jarmu j1, Jarmu j2) {
        j1.balesetezik();
        j2.balesetezik();
    }
}