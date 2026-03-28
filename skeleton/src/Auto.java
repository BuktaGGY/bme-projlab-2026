public class Auto extends SerulekenyJarmu {
    public Auto(String name) {
        super();
        Skeleton.ctor(this, name);
    }

    @Override
    public void frissitAllapot() {
        Skeleton.call(this, "frissitAllapot");
        Skeleton.ret();
    }

    @Override
    public void balesetezik() {
        Skeleton.call(this, "balesetezik");
        Skeleton.ret();
    }

    public void ujraTervezes(UtvonalTervezo ut) {
        Skeleton.call(this, "ujraTervezes", "ut");
        
        ut.utvonalKeres();
        this.utvonalFrissit();
        
        Skeleton.ret();
    }

    public void megsemmisites() {
        Skeleton.call(this, "megsemmisites");
        Skeleton.ret();
    }

}