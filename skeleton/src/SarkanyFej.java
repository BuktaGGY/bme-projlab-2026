public class SarkanyFej extends KotroFej {

    public SarkanyFej() {
        super(150); // Ide a te beállított árad kerüljön!
    }

    @Override
    public void takarit(Sav sav, Hokotro hk) {
        if (hk.getBiokerozin() >= 50) {
            hk.setBiokerozin(hk.getBiokerozin() - 50);
            
            sav.hoEltuntet();
            if (sav.getAllapot() == SavAllapot.JEGPANCEL) {
                sav.setSavAllapot(SavAllapot.TISZTA);
            }
            
            System.out.println("[ESEMENY] " + hk.getId() + " | TAKARITOTT | " + sav.getId() + " sav (maradek ho: " + sav.getHoVastagsag() + "cm)");
        } else {
            System.out.println("[ESEMENY] " + hk.getId() + " | NYERSANYAG_ELFOGYOTT | leallt a sarkanyfej");
        }
    }

    @Override
    public String getFejTipus() {
        return "sarkany";
    }
}