public class SoproFej extends KotroFej {
    
    public SoproFej() {
        super(500); 
    }


	/**
     * Eltakarítja a havat az aktuális sávról, és áttolja azt a megadott szomszédos sávra.
     * @param sav           Az a sáv, amelyen a hókotró éppen tartózkodik és takarít.
     * @param kotro         A műveletet végző hókotró referenciája.
     */
    @Override
    public void takarit(Sav sav, Hokotro kotro) {
        int eltuntetettHo = sav.hoEltuntet();
        
        if (sav.getJobbSav() != null && eltuntetettHo > 0) {
            sav.getJobbSav().hoNovel(eltuntetettHo);
            if (sav.isZuzalekos()) 
                sav.getJobbSav().zuzalekotSzor();
        }
        
        sav.zuzalekotEltuntet();
    }

    @Override
    public String getFejTipus() {
        return "sopro";
    }
}