
public class zuzalekSzoro extends KotroFej{

    public zuzalekSzoro(){
        super(150);
    }

    /**
     * Zuzalékot szór az aktuális sávra.
     * @param sav           Az a sáv, amelyen a hókotró éppen tartózkodik és takarít.
     * @param kotro         A műveletet végző hókotró referenciája.
     */
    @Override
    public void takarit(Sav sav, Hokotro kotro) {
        if (kotro.getZuzalek() > 0) {
            sav.zuzalekotSzor();
            kotro.setZuzalek(kotro.getZuzalek() - 1); 
        }
    }
    @Override
    public String getFejTipus() {
        return "zuzalekszoro";
    }
}
