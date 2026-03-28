public abstract class Jarmu {
    protected int id;
    protected int pozicioASavon;
    protected int sebesseg;

    public Jarmu() {
    }

    public abstract void frissitAllapot();

    public void utvonalFrissit() {
        Skeleton.call(this, "utvonalFrissit");
        Skeleton.ret();
    }

    public void mozog(Object utszakasz) {
        Skeleton.call(this, "mozog", "utszakasz");
        Skeleton.ret();
    }

    public void setAllapot(String allapot) {
        Skeleton.call(this, "setAllapot", allapot);
        Skeleton.ret();
    }
}