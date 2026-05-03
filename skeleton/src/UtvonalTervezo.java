import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Az útvonalak tervezéséért és a hálózat gráfjának kezeléséért felelős osztály.
 * Feladata az útzárak detektálása, új útvonalak keresése, valamint a játékos
 * által kijelölt buszútvonalak ellenőrzése és érvényesítése.
 */
public class UtvonalTervezo {
    
    /**
     * A forgalomirányító referenciája. Ezen keresztül értesíti a járműveket
     * az útváltozásokról az UML asszociáció alapján.
     */
    private ForgalomIranyito forgalomIranyito;
    private Map<String, Utszakasz> utak;

    /**
     * Konstruktor
     */
    public UtvonalTervezo(ForgalomIranyito forgalomIranyito) {
        this.forgalomIranyito = forgalomIranyito;
        this.utak = new HashMap<>();
    }

    public void addUt(String id, Utszakasz ut) {
        utak.put(id, ut);
    }

    public Utszakasz getUt(String id) {
        return utak.get(id);
    }

    private boolean isUtszakaszJarhato(Utszakasz u) {
        if (u.getSavok() == null || u.getSavok().isEmpty()) return false;
        for (Sav s : u.getSavok()) {
            if (s.getAllapot() != SavAllapot.BLOKKOLT) {
                return true;
            }
        }
        return false;
    }

    /**
     * Detektálja, ha egy útszakasz blokkolttá válik, frissíti a belső hálózatot,
     * és riasztja a forgalomirányítót a kialakult helyzetről.
     */
    public void utzarDetektal(String blokkoltSavId) {
        System.out.println("[ESEMENY] UTVONALTERVEZO | UTZAR_DETEKTALVA | " + blokkoltSavId + " sav blokkolt");
        if (forgalomIranyito != null) {
            forgalomIranyito.utzarEsemeny(this);
        }
    }

    /**
     * Frissíti a belső térképgráfot (pl. kiveszi a blokkolt utakat).
     */
    public void utFrissites() {

    }

    /**
     * Új, optimális útvonalat keres a hálózatban a paraméterek alapján.
     * @return Az új útvonal.
     */
    public Utszakasz[] utvonalKeres(Csomopont honnan, Csomopont hova) {
        Map<Csomopont, Integer> tavolsag = new HashMap<>();
        Map<Csomopont, Utszakasz> elozoUt = new HashMap<>();
        Map<Csomopont, Csomopont> elozoCsomopont = new HashMap<>();
        List<Csomopont> feldolgozatlan = new ArrayList<>();

        tavolsag.put(honnan, 0);
        feldolgozatlan.add(honnan);

        while (!feldolgozatlan.isEmpty()) {
            Csomopont u = null;
            int minTav = Integer.MAX_VALUE;
            for (Csomopont c : feldolgozatlan) {
                if (tavolsag.getOrDefault(c, Integer.MAX_VALUE) < minTav) {
                    minTav = tavolsag.get(c);
                    u = c;
                }
            }
            if (u == null || u == hova) break;
            feldolgozatlan.remove(u);

            for (Utszakasz ut : u.getUtszakaszok()) {
                if (!isUtszakaszJarhato(ut)) continue;

                Csomopont v = ut.getMasikVeg(u);
                if (v == null) continue;

                int altTavolsag = tavolsag.get(u) + ut.getHossz();
                if (altTavolsag < tavolsag.getOrDefault(v, Integer.MAX_VALUE)) {
                    tavolsag.put(v, altTavolsag);
                    elozoCsomopont.put(v, u);
                    elozoUt.put(v, ut);
                    if (!feldolgozatlan.contains(v)) {
                        feldolgozatlan.add(v);
                    }
                }
            }
        }

        if (!elozoCsomopont.containsKey(hova)) return null;

        List<Utszakasz> path = new ArrayList<>();
        Csomopont curr = hova;
        while (curr != honnan) {
            path.add(0, elozoUt.get(curr));
            curr = elozoCsomopont.get(curr);
        }
        return path.toArray(new Utszakasz[0]);
    }

    /**
     * A játékos által megadott csomópontok alapján kijelöl egy új útvonalat a busz számára.
     *
     * @param b A busz, amelynek az útvonalat kijelölik.
     * @param csomopontok A játékos által kijelölt csomópontok tömbje.
     */
    public void utKijelol(Busz b, Csomopont[] csomopontok) {
        if (!utEllenorzes(csomopontok)) {
            return; 
        }

        Utszakasz[] ujUt = new Utszakasz[csomopontok.length - 1];
        for (int i = 0; i < csomopontok.length - 1; i++) {
            Csomopont c1 = csomopontok[i];
            Csomopont c2 = csomopontok[i+1];
            for (Utszakasz u : c1.getUtszakaszok()) {
                if (c2.getUtszakaszok().contains(u)) {
                    ujUt[i] = u;
                    break;
                }
            }
        }
        b.UtvonalatKijelol(ujUt);
    }

    /**
     * Ellenőrzi, hogy a megadott csomópontok sorozata érvényes, folytonos útvonalat alkot-e.
     *
     * @param csomopontok Az ellenőrizendő csomópontok.
     * @return Igaz, ha az útvonal bejárható.
     */
    public boolean utEllenorzes(Csomopont[] csomopontok) {
        for (int i = 0; i < csomopontok.length - 1; i++) {
            boolean vanKozos = false;
            for (Utszakasz u : csomopontok[i].getUtszakaszok()) {
                if (csomopontok[i+1].getUtszakaszok().contains(u)) {
                    vanKozos = true;
                    break;
                }
            }
            if (!vanKozos) return false;
        }
        return true;
    }

    public void setForgalomIranyito(ForgalomIranyito forgalomIranyito) {
        this.forgalomIranyito = forgalomIranyito;
    }
}