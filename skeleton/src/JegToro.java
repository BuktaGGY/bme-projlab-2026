public class JegToro extends KotroFej {

    public JegToro() {
        super(300);
    }


    @Override
    public String getFejTipus() {
        return "jegtoro";
    }

    /**
     * Feltöri a jégpáncélt az aktuális sávon, fizikai erővel járhatóvá téve azt a forgalom számára.
     * * @param sav           Az a sáv, amelyen a jégtörés történik.
     * @param kotro         A műveletet végző hókotró referenciája.
     */
    @Override
    public void takarit(Sav sav, Hokotro kotro) {
        sav.jegTores();
    }
}