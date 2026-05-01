
public class zuzalekSzoro extends KotroFej{

    public zuzalekSzoro(){
        super(150);
    }

    @Override
    public void takarit(Sav sav, Hokotro kotro) {
        if (kotro.getZuzalek() > 0) {
            sav.zuzalekotSzor();
            kotro.setZuzalek(kotro.getSo() - 1);
        }
    }
    @Override
    public String getFejTipus() {
        return "zuzalekszoro";
    }
}
