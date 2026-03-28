public abstract class SerulekenyJarmu extends Jarmu {
    
    protected JarmuAllapot allapot;

    public SerulekenyJarmu() {
        super();
    }

    public void elakad() {
        Skeleton.call(this, "elakad");
        Skeleton.ret();
    }

    public void megcsuszik() {
        Skeleton.call(this, "megcsuszik");
        Skeleton.ret();
    }

    public abstract void balesetezik();
}