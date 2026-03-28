public class Hokotro {
    private int so = 50;
    private int biokerozin = 100;

    public Hokotro(String name) {
        Skeleton.ctor(this, name);
    }

    public int getSo() {
        Skeleton.call(this, "getSo");
        Skeleton.ret(String.valueOf(so));
        return so;
    }

    public void setSo(int so) {
        Skeleton.call(this, "setSo", String.valueOf(so));
        this.so = so;
        Skeleton.ret("void");
    }

    public int getBiokerozin() {
        Skeleton.call(this, "getBiokerozin");
        Skeleton.ret(String.valueOf(biokerozin));
        return biokerozin;
    }

    public void setBiokerozin(int biokerozin) {
        Skeleton.call(this, "setBiokerozin", String.valueOf(biokerozin));
        this.biokerozin = biokerozin;
        Skeleton.ret("void");
    }
}