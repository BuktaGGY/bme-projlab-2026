public class SarkanyFej extends KotroFej {

    public SarkanyFej() {
        super(150); // Ide a te beállított árad kerüljön!
    }

    @Override
    public void takarit(Sav sav, Hokotro hk) {
        // Ellenőrizzük, van-e elég biokerozin (50-et fogyaszt)
        if (hk.getBiokerozin() >= 50) {
            hk.setBiokerozin(hk.getBiokerozin() - 50);
            
            // A sárkány leolvasztja a havat és a jeget is
            sav.hoEltuntet();
            if (sav.getAllapot() == SavAllapot.JEGPANCEL) {
                sav.setSavAllapot(SavAllapot.TISZTA);
            }
            
            // EZ A SOR HIÁNYZOTT VALÓSZÍNŰLEG:
            System.out.println("[ESEMENY] " + hk.getId() + " | TAKARITOTT | " + sav.getId() + " sav (maradek ho: " + sav.getHoVastagsag() + "cm)");
        } else {
            // ÉS EZ A SOR IS HIÁNYZOTT:
            System.out.println("[ESEMENY] " + hk.getId() + " | NYERSANYAG_ELFOGYOTT | leallt a sarkanyfej");
        }
    }

    @Override
    public String getFejTipus() {
        return "sarkany";
    }
}