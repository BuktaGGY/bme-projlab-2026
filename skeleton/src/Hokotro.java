/**
 * A hókotró járművet reprezentáló osztály.
 * Nyilvántartja a gép erőforrásait (só- és biokerozin készlet), 
 * amelyeket a rászerelt különböző kotrófejek (pl. Sószóró, Sárkányfej) 
 * fognak felhasználni a takarítási műveletek során.
 */
public class Hokotro {
    
    /** * A hókotró tartályában lévő só mennyisége. 
     * Alapértelmezett kezdőértéke 50 egység.
     */
    private int so = 50;
    
    /** * A hókotró tartályában lévő biokerozin mennyisége. 
     * Alapértelmezett kezdőértéke 100 egység.
     */
    private int biokerozin = 100;

    /**
     * Konstruktor a Hókotró inicializálásához.
     * Létrehozáskor azonnal beregisztrálja magát a Szkeleton nyomkövetőjébe.
     * * @param name Az objektum példányosításkori neve (pl. "h1"), amit a diagramokon használunk.
     */
    public Hokotro(String name) {
        Skeleton.ctor(this, name);
    }

    /**
     * Lekérdezi a hókotróban lévő aktuális sómennyiséget.
     * Ezt hívja meg például a Sószóró fej, hogy ellenőrizze, van-e mit szórnia.
     * * @return A rendelkezésre álló só mennyisége.
     */
    public int getSo() {
        Skeleton.call(this, "getSo");
        Skeleton.ret(String.valueOf(so));
        return so;
    }

    /**
     * Beállítja (vagy csökkenti/növeli) a hókotró sókészletét a megadott értékre.
     * * @param so Az új sómennyiség, amire a tartály tartalmát be kell állítani.
     */
    public void setSo(int so) {
        Skeleton.call(this, "setSo", String.valueOf(so));
        this.so = so;
        Skeleton.ret("void");
    }

    /**
     * Lekérdezi a hókotróban lévő aktuális biokerozin mennyiséget.
     * Ezt hívja meg például a Sárkányfej, hogy ellenőrizze, tud-e jeget olvasztani.
     * * @return A rendelkezésre álló biokerozin mennyisége.
     */
    public int getBiokerozin() {
        Skeleton.call(this, "getBiokerozin");
        Skeleton.ret(String.valueOf(biokerozin));
        return biokerozin;
    }

    /**
     * Beállítja (vagy csökkenti/növeli) a hókotró biokerozin készletét a megadott értékre.
     * * @param biokerozin Az új biokerozin mennyiség, amire a tartály tartalmát be kell állítani.
     */
    public void setBiokerozin(int biokerozin) {
        Skeleton.call(this, "setBiokerozin", String.valueOf(biokerozin));
        this.biokerozin = biokerozin;
        Skeleton.ret("void");
    }
}