

public class Csomopont {

    private int id;

    private Utszakasz[] utszakaszok;

    public int getID(){
        Skeleton.call(this, "getID");
        Skeleton.ret(String.valueOf(id));
        return id;
    }

    public Utszakasz[] getUtszakaszok(){
        Skeleton.call(this, "getUtszakaszok");
        Skeleton.ret(Skeleton.getName(utszakaszok));
        return null;
    }
}
