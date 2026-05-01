import java.util.HashMap;
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
    private final ForgalomIranyito forgalomIranyito;

    private Map<String, Utszakasz> utak;

    /**
     * Konstruktor
     */
    public UtvonalTervezo(ForgalomIranyito forgalomIranyito) {
        this.forgalomIranyito = forgalomIranyito;
        utak = new HashMap<>();
    }

    public void addUt(String id, Utszakasz ut) {
        utak.put(id, ut);
    }

    public Utszakasz getUt(String id) {
        return utak.get(id);
    }


    /**
     * Detektálja, ha egy útszakasz blokkolttá válik, frissíti a belső hálózatot,
     * és riasztja a forgalomirányítót a kialakult helyzetről.
     */
    public void utzarDetektal() {
        this.utFrissites();
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
     * @return Az új útvonal (szkeletonban egy dummy String/objektum).
     */
    public String utvonalKeres() {

        return null;
    }

    /**
     * A játékos által megadott csomópontok alapján kijelöl egy új útvonalat a busz számára.
     *
     * @param b A busz, amelynek az útvonalat kijelölik.
     * @param csomopontok A játékos által kijelölt csomópontok tömbje.
     */
    public void utKijelol(Busz b, Csomopont[] csomopontok) {

        boolean ervenyes = this.utEllenorzes(csomopontok);

        if (forgalomIranyito != null && ervenyes) {
            forgalomIranyito.buszUtvonalKiosztas(b, csomopontok);
        }
        
    }

    /**
     * Ellenőrzi, hogy a megadott csomópontok sorozata érvényes, folytonos útvonalat alkot-e.
     *
     * @param csomopontok Az ellenőrizendő csomópontok.
     * @return Igaz, ha az útvonal bejárható.
     */
    public boolean utEllenorzes(Csomopont[] csomopontok) {

        return true;
    }

    public void setForgalomIranyito(ForgalomIranyito forgalomIranyito) {
    }
}