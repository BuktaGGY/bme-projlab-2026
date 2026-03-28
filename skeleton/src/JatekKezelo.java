public class JatekKezelo {
    Idojaraskezelo idojaraskezelo;
    UtvonalTervezo utvonalTervezo;
    ForgalomIranyito forgalomIranyito;
    GazdasagKezelo gazdasagKezelo;
    public boolean isRunning;
    public int aktualisTick;
    public int buszPontszamok;

    public JatekKezelo(String name) {
        Skeleton.ctor(this, name);
    }

    public void start(){
        Skeleton.call(this, "start");
        Skeleton.ret();
    }

    public void stop(){
        Skeleton.call(this, "stop");
        Skeleton.ret();
    }

    public void tick(){
        Skeleton.call(this, "tick");

        idojaraskezelo.Havaz();
        idojaraskezelo.olvasztasKezeles();

        Skeleton.ret();
    }
}
