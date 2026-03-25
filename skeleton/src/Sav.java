public class Sav {
    
    public Sav(String name) {
        Skeleton.ctor(this, name);
    }

    public int hoEltuntet() {
        Skeleton.call(this, "hoEltuntet");
        Skeleton.ret("10"); // Visszaterunk valami teszt adattal
        return 10;
    }

    public void addHo(int mennyiseg) {
        Skeleton.call(this, "addHo", String.valueOf(mennyiseg));
        Skeleton.ret("void");
    }

    public void sotSzor() {
        Skeleton.call(this, "sotSzor");
        Skeleton.ret("void");
    }

    public void jegTores() {
        Skeleton.call(this, "jegTores");
        Skeleton.ret("void");
    }

    public void mindentEltuntet() {
        Skeleton.call(this, "mindentEltuntet");
        Skeleton.ret("void");
    }
}