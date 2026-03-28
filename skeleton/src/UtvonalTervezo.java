public class UtvonalTervezo {
    private ForgalomIranyito forgalomIranyito;

    public UtvonalTervezo(String name) {
        Skeleton.ctor(this, name);
    }

    public void setForgalomIranyito(ForgalomIranyito fi) {
        this.forgalomIranyito = fi;
    }

    public void utzarDetektal() {
        Skeleton.call(this, "utzarDetektal");
        

        this.utFrissites();

        if (forgalomIranyito != null) {
            forgalomIranyito.utzarEsemeny(this);
        }
        
        Skeleton.ret();
    }

    public void utFrissites() {
        Skeleton.call(this, "utFrissites");
        Skeleton.ret();
    }

    public String utvonalKeres() {
        Skeleton.call(this, "utvonalKeres");
        Skeleton.ret("utvonal");
        return "utvonal";
    }
}