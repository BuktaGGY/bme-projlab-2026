public class SoproFej extends KotroFej {
    
    // A szkeleton a inicializáláskor adja meg a nevet (pl. "sf")
    public SoproFej() {
        super(100); 
    }


	/**
     * Eltakarítja a havat az aktuális sávról, és áttolja azt a megadott szomszédos sávra.
     * * @param sav           Az a sáv, amelyen a hókotró éppen tartózkodik és takarít.
     * @param kotro         A műveletet végző hókotró referenciája.
     */
    @Override
    public void takarit(Sav sav, Hokotro kotro) {
        int eltuntetettHo = sav.hoEltuntet();
        sav.zuzalekotEltuntet();
        if (sav.getJobbSav() != null && eltuntetettHo > 0) {
            sav.getJobbSav().hoNovel(eltuntetettHo);
            sav.getJobbSav().zuzalekotSzor();
        }
        
    }

    @Override
    public String getFejTipus() {
        return "sopro";
    }
}