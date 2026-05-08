import java.util.ArrayList;
import java.util.List;

/**
 * Egy útszakasz egy absztrakt osztály, amelynek legfőbb felelőssége, hogy egy alapsablonként szolgáljon a különböző út típusoknak.
 * Tárolja a hálózatban tárolt helyét egy útszakasznak, tárolja a kezdő- és a végpontot, továbbá az útszakasz hosszát.
 * Emellett tárolóként is szolgál, birtokolja a rajta futó sávokat és kezeli az életciklusukat.
 */
public abstract class Utszakasz {
    protected String id;
    protected int hossz;
    protected Csomopont eleje;
    protected Csomopont vege;
    protected List<Sav> savok;

    public Utszakasz(String id, Csomopont eleje, Csomopont vege, int hossz) {
        this.id = id;
        this.eleje = eleje;
        this.vege = vege;
        this.hossz = hossz;
        this.savok = new ArrayList<>();
    }

	public int getHossz(){
		return this.hossz;
	}

    public void addSav(Sav s) {
        if (!savok.isEmpty()){
            Sav utolsoSav = savok.get(savok.size() - 1);
            utolsoSav.setJobbSav(s);
            s.setBalSav(utolsoSav);
        }
        s.setSzuloUtszakasz(this);
        savok.add(s);
    }

    /**
     * Visszaadja az útszakasz másik végén lévő csomópontot.
     */
    public Csomopont getMasikVeg(Csomopont cs) {
        if (eleje != null && eleje.getID().equals(cs.getID())) return vege;
        if (vege != null && vege.getID().equals(cs.getID())) return eleje;
        return null;
    }

    public Sav getSav(int id) {
        return this.savok.get(id);
    }
	
	public List<Sav> getSavok() {
        return this.savok;
    }
    
    public String getId() { return id; }
}
