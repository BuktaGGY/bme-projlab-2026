import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.awt.Point;
import javax.swing.Timer;

/**
 * A grafikus felulet vezerlo osztalya.
 * Osszekapcsolja a Swing feluletet a jatekmodellel, kezeli a demo palya
 * letrehozasat, az utvonalkijelolest, a vasarlasokat es a szimulacio lepteteset.
 */
public class GrafikusVezerlo {
    /** Egy uj busz vasarlasi ara. */
    private static final int UJ_BUSZ_AR = 800;
    /** Egy uj hokotro vasarlasi ara. */
    private static final int UJ_HOKOTRO_AR = 5000;
    /** A so vagy zuzalek feltoltes ara. */
    private static final int SO_AR = 300;
    /** A biokerozin feltoltes ara. */
    private static final int BIOKEROZIN_AR = 500;

    /** A vezerelt jatekmodell kozponti kezeloje. */
    private JatekKezelo jatekKezelo;
    /** A csomopontok kepernyobeli elrendezeset tarolja. */
    private GrafikusElrendezes elrendezes;
    /** A grafikus palyan megjeleno csomopontok listaja. */
    private final List<Csomopont> csomopontok = new ArrayList<>();
    /** A grafikus palyan megjeleno Point Of Interest objektumok listaja. */
    private final List<PointOfInterest> poik = new ArrayList<>();
    /** A jatekos altal eppen kijelolt csomopontsor. */
    private final List<Csomopont> kijeloltUtvonal = new ArrayList<>();
    private int buszSorszam = 2;
    private int hokotroSorszam = 2;
    /** Az automatikus leptetest vegzo Swing timer. */
    private Timer timer;
    /** A terkep panel, amelyen a View objektumok regisztralva vannak. */
    private TerkepPanel terkepPanel;

    /**
     * Letrehozza a grafikus vezerlot es bekapcsolja a grafikus jatekhoz
     * hasznalt finomitott szimulacios szabalyokat.
     */
    public GrafikusVezerlo() {
        SzimulacioBeallitasok.finomitottSzimulacio = true;
        ujDemoPalya();
    }

    /**
     * Letrehozza az alap demo palyat csomopontokkal, utakkal, savokkal,
     * POI objektumokkal es kezdo jarmuvekkel.
     */
    public void ujDemoPalya() {
        jatekKezelo = new JatekKezelo();
        elrendezes = new GrafikusElrendezes();
        csomopontok.clear();
        poik.clear();

        Csomopont a = ujCsomopont("A", 80, 100);
        Csomopont b = ujCsomopont("B", 300, 90);
        Csomopont c = ujCsomopont("C", 540, 110);
        Csomopont d = ujCsomopont("D", 760, 140);
        Csomopont e = ujCsomopont("E", 120, 320);
        Csomopont f = ujCsomopont("F", 340, 300);
        Csomopont g = ujCsomopont("G", 560, 330);
        Csomopont h = ujCsomopont("H", 780, 360);
        Csomopont i = ujCsomopont("I", 170, 560);
        Csomopont j = ujCsomopont("J", 420, 540);
        Csomopont k = ujCsomopont("K", 680, 560);

        SimaUt u1 = ujUt("U1", a, b, 110);
        SimaUt u2 = ujUt("U2", b, c, 120);
        SimaUt u3 = ujUt("U3", c, d, 110);
        SimaUt u4 = ujUt("U4", a, e, 110);
        SimaUt u5 = ujUt("U5", b, f, 105);
        SimaUt u6 = ujUt("U6", c, g, 110);
        SimaUt u7 = ujUt("U7", d, h, 115);
        SimaUt u8 = ujUt("U8", e, f, 115);
        SimaUt u9 = ujUt("U9", f, g, 120);
        SimaUt u10 = ujUt("U10", g, h, 115);
        SimaUt u11 = ujUt("U11", e, i, 130);
        SimaUt u12 = ujUt("U12", f, j, 130);
        SimaUt u13 = ujUt("U13", g, k, 125);
        SimaUt u14 = ujUt("U14", i, j, 120);
        SimaUt u15 = ujUt("U15", j, k, 130);
        SimaUt u16 = ujUt("U16", b, g, 160);
        SimaUt u17 = ujUt("U17", e, j, 150);
        SimaUt u18 = ujUt("U18", c, f, 145);

        Sav s1 = ujSav(u1, "S1");
        Sav s2 = ujSav(u1, "S2");
        Sav s3 = ujSav(u2, "S3");
        Sav s4 = ujSav(u2, "S4");
        Sav s5 = ujSav(u3, "S5");
        Sav s6 = ujSav(u4, "S6");
        Sav s7 = ujSav(u5, "S7");
        Sav s8 = ujSav(u6, "S8");
        Sav s9 = ujSav(u7, "S9");
        Sav s10 = ujSav(u8, "S10");
        Sav s11 = ujSav(u8, "S11");
        Sav s12 = ujSav(u9, "S12");
        Sav s13 = ujSav(u9, "S13");
        Sav s14 = ujSav(u10, "S14");
        Sav s15 = ujSav(u11, "S15");
        Sav s16 = ujSav(u12, "S16");
        Sav s17 = ujSav(u13, "S17");
        Sav s18 = ujSav(u14, "S18");
        Sav s19 = ujSav(u14, "S19");
        Sav s20 = ujSav(u15, "S20");
        Sav s21 = ujSav(u16, "S21");
        Sav s22 = ujSav(u17, "S22");
        Sav s23 = ujSav(u18, "S23");

        Lakas lakas1 = new Lakas("L1", a, null);
        Lakas lakas2 = new Lakas("L2", i, null);
        Lakas lakas3 = new Lakas("L3", h, null);
        Munkahely munkahely1 = new Munkahely("M1", g);
        Munkahely munkahely2 = new Munkahely("M2", c);
        Munkahely munkahely3 = new Munkahely("M3", k);
        Vegallomas v1 = new Vegallomas("V1", d, new ArrayList<>(), jatekKezelo);
        Vegallomas v2 = new Vegallomas("V2", i, new ArrayList<>(), jatekKezelo);
        Vegallomas v3 = new Vegallomas("V3", f, new ArrayList<>(), jatekKezelo);
        Garazs garazs1 = new Garazs("G1", a);
        Garazs garazs2 = new Garazs("G2", j);
        Garazs garazs3 = new Garazs("G3", h);
        poik.add(lakas1);
        poik.add(lakas2);
        poik.add(lakas3);
        poik.add(munkahely1);
        poik.add(munkahely2);
        poik.add(munkahely3);
        poik.add(v1);
        poik.add(v2);
        poik.add(v3);
        poik.add(garazs1);
        poik.add(garazs2);
        poik.add(garazs3);

        Auto auto1 = new Auto("A1", 10, s1, munkahely1);
        Auto auto2 = new Auto("A2", 55, s3, munkahely3);
        Auto auto3 = new Auto("A3", 20, s10, munkahely2);
        Auto auto4 = new Auto("A4", 75, s15, munkahely1);
        Auto auto5 = new Auto("A5", 35, s20, munkahely2);
        Auto auto6 = new Auto("A6", 45, s23, munkahely3);
        ingazoAutoBeallit(auto1, lakas1, munkahely1);
        ingazoAutoBeallit(auto2, lakas2, munkahely3);
        ingazoAutoBeallit(auto3, lakas3, munkahely2);
        ingazoAutoBeallit(auto4, lakas2, munkahely1);
        ingazoAutoBeallit(auto5, lakas1, munkahely2);
        ingazoAutoBeallit(auto6, lakas3, munkahely3);
        Busz busz = new Busz("B1", 30, s9, v2);
        busz.setAllomasok(v1, v2);
        busz.setJatekKezelo(jatekKezelo);
        Busz busz2 = new Busz("B2", 15, s18, v1);
        busz2.setAllomasok(v2, v1);
        busz2.setJatekKezelo(jatekKezelo);
        kezdoBuszUtvonalBeallit(busz, v1, v2);
        kezdoBuszUtvonalBeallit(busz2, v2, v1);
        Hokotro hokotro = new Hokotro("H1", new HanyoFej(), s6, 0);
        Hokotro hokotro2 = new Hokotro("H2", new JegToro(), s16, 25);
        hokotro.setForgalomIranyito(jatekKezelo.getForgalomIranyito());
        hokotro2.setForgalomIranyito(jatekKezelo.getForgalomIranyito());

        s12.hoNovel(20);
        s13.setParameterek(0, true, 0, false);
        s17.hoNovel(35);
        s21.hoNovel(15);

        jatekKezelo.getForgalomIranyito().addJarmu(auto1);
        jatekKezelo.getForgalomIranyito().addJarmu(auto2);
        jatekKezelo.getForgalomIranyito().addJarmu(auto3);
        jatekKezelo.getForgalomIranyito().addJarmu(auto4);
        jatekKezelo.getForgalomIranyito().addJarmu(auto5);
        jatekKezelo.getForgalomIranyito().addJarmu(auto6);
        jatekKezelo.getForgalomIranyito().addJarmu(busz);
        jatekKezelo.getForgalomIranyito().addJarmu(busz2);
        jatekKezelo.getForgalomIranyito().addJarmu(hokotro);
        jatekKezelo.getForgalomIranyito().addJarmu(hokotro2);
        jatekKezelo.palyaValtozott();
        inicializalViewk();
    }

    /**
     * Letrehoz egy csomopontot es beallitja a kepernyobeli poziciojat.
     * @param id A csomopont azonositoja.
     * @param x Kepernyobeli x koordinata.
     * @param y Kepernyobeli y koordinata.
     * @return A letrehozott csomopont.
     */
    private Csomopont ujCsomopont(String id, int x, int y) {
        Csomopont csomopont = new Csomopont(id);
        csomopontok.add(csomopont);
        elrendezes.setPozicio(csomopont, x, y);
        return csomopont;
    }

    /**
     * Letrehoz egy utat ket csomopont kozott es regisztralja az utvonaltervezoben.
     * @param id Az ut azonositoja.
     * @param eleje Az ut egyik vege.
     * @param vege Az ut masik vege.
     * @param hossz Az ut hossza.
     * @return A letrehozott ut.
     */
    private SimaUt ujUt(String id, Csomopont eleje, Csomopont vege, int hossz) {
        SimaUt ut = new SimaUt(id, eleje, vege, hossz);
        jatekKezelo.getUtvonalTervezo().addUt(id, ut);
        eleje.addUtszakasz(ut);
        vege.addUtszakasz(ut);
        return ut;
    }

    /**
     * Letrehoz es hozzaad egy savot egy utszakaszhoz.
     * @param ut Az utszakasz, amelyhez a sav tartozik.
     * @param id A sav azonositoja.
     * @return A letrehozott sav.
     */
    private Sav ujSav(Utszakasz ut, String id) {
        Sav sav = new Sav(id);
        ut.addSav(sav);
        return sav;
    }

    /**
     * Beallitja, hogy egy auto ket POI kozott automatikusan ingazzon.
     * @param auto A beallitando auto.
     * @param honnan Az indulo POI.
     * @param hova A cel POI.
     */
    private void ingazoAutoBeallit(Auto auto, PointOfInterest honnan, PointOfInterest hova) {
        auto.setIngazoPoi(honnan, hova);
        auto.setCel(hova);
        auto.setAktualisCsomopont(honnan.getCsomopont());
        auto.setStartSav(null);
        auto.setUtvonalTervezo(jatekKezelo.getUtvonalTervezo());
    }

    /**
     * Kezdo utvonalat tervez egy busznak ket vegallomas kozott.
     * @param busz A beallitando busz.
     * @param honnan Az indulo vegallomas.
     * @param hova A cel vegallomas.
     */
    private void kezdoBuszUtvonalBeallit(Busz busz, Vegallomas honnan, Vegallomas hova) {
        Utszakasz[] utvonal = jatekKezelo.getUtvonalTervezo().utvonalKeres(honnan.getCsomopont(), hova.getCsomopont());
        if (utvonal != null && utvonal.length > 0) {
            busz.UtvonalatKijelol(utvonal);
            busz.setStartSav(utvonal[0].getSavok().get(0));
            busz.beallitKezdoIrany(honnan.getCsomopont());
        }
    }

    /**
     * Beallitja a terkep panelt es inicializalja a View objektumokat.
     * @param panel A terkep panel.
     */
    public void setTerkepPanel(TerkepPanel panel) {
        this.terkepPanel = panel;
        inicializalViewk();
    }

    /**
     * Letrehozza az utszakasz, csomopont es jarmu View objektumokat es regisztralja oket a terkep panelnel.
     */
    private void inicializalViewk() {
        if (terkepPanel == null) return;
        List<IRajzolhato> utakViewk = new ArrayList<>();
        for (Utszakasz ut : getUtak()) {
            utakViewk.add(new UtszakaszView(ut));
        }
        for (Csomopont cs : csomopontok) {
            utakViewk.add(new CsomopontView(cs));
        }
        terkepPanel.setUtszakaszViewk(utakViewk);
        terkepPanel.clearJarmuViewk();
        for (Jarmu j : getJarmuvek()) {
            terkepPanel.addJarmuView(createJarmuView(j));
        }
    }

    /**
     * Letrehozza a jarmuhoz tartozo View peldanyt a jarmutipus alapjan.
     * @param jarmu A megjelennitendo jarmu.
     * @return A jarmuhoz tartozo IRajzolhato View.
     */
    private IRajzolhato createJarmuView(Jarmu jarmu) {
        if (jarmu.asHokotro() != null) return new HokotroView(jarmu);
        if (jarmu.asBusz() != null) return new BuszView(jarmu);
        return new AutoView(jarmu);
    }

    /**
     * Egy szimulacios lepest hajt vegre.
     */
    public void lep() {
        jatekKezelo.start();
        jatekKezelo.tick();
    }

    /**
     * Elinditja az automatikus leptetest.
     * @param afterTick A tick utan vegrehajtando feluletfrissito muvelet.
     */
    public void automataIndit(Runnable afterTick) {
        if (timer != null && timer.isRunning()) {
            return;
        }
        timer = new Timer(1400, e -> {
            lep();
            afterTick.run();
        });
        timer.start();
    }

    /**
     * Megallitja az automatikus leptetest.
     */
    public void automataMegallit() {
        if (timer != null) {
            timer.stop();
        }
    }

    /**
     * Havat ad a kivalasztott savhoz.
     * @param sav A modositando sav.
     * @param mennyiseg A ho mennyisege centimterben.
     */
    public void havaz(Sav sav, int mennyiseg) {
        if (sav != null) {
            sav.hoNovel(mennyiseg);
            jatekKezelo.palyaValtozott();
        }
    }

    /**
     * Jegpancel allapotot allit be a megadott savon.
     * @param sav A modositando sav.
     */
    public void allitJeg(Sav sav) {
        if (sav != null) {
            sav.setParameterek(sav.getHoVastagsag(), true, sav.getSozottIdo(), sav.isZuzalekos());
            jatekKezelo.palyaValtozott();
        }
    }

    /**
     * Megtisztitja a megadott savot minden hotol, jegtol es zuzalektol.
     * @param sav A tisztitando sav.
     */
    public void tisztit(Sav sav) {
        if (sav != null) {
            sav.mindentEltuntet();
            jatekKezelo.palyaValtozott();
        }
    }

    /**
     * Lecsereli egy hokotro felszerelt fejet, ha a kasszaban van eleg penz.
     * @param hokotro A modositando hokotro.
     * @param fejTipus Az uj fej tipusa.
     */
    public void cserelFej(Hokotro hokotro, String fejTipus) {
        if (hokotro == null) {
            return;
        }
        KotroFej fej = null;
        if ("sopro".equals(fejTipus)) fej = new SoproFej();
        if ("soszoro".equals(fejTipus)) fej = new Soszoro();
        if ("hanyo".equals(fejTipus)) fej = new HanyoFej();
        if ("jegtoro".equals(fejTipus)) fej = new JegToro();
        if ("sarkany".equals(fejTipus)) fej = new SarkanyFej();
        if ("zuzalekszoro".equals(fejTipus)) fej = new zuzalekSzoro();
        if (fej != null && jatekKezelo.getGazdasagKezelo().fizetes(fej.getAr())) {
            hokotro.setKotrofej(fej);
            jatekKezelo.palyaValtozott();
        }
    }

    /**
     * Feltolti a hokotro egyik fogyaszthato anyagat, ha van eleg penz.
     * @param hokotro A feltoltendo hokotro.
     * @param anyag A feltoltendo anyag neve.
     */
    public void tankol(Hokotro hokotro, String anyag) {
        if (hokotro == null) {
            return;
        }
        int ar = 0;
        if ("so".equals(anyag)) ar = SO_AR;
        if ("biokerozin".equals(anyag)) ar = BIOKEROZIN_AR;
        if ("zuzalek".equals(anyag)) ar = SO_AR;
        if (ar > 0 && jatekKezelo.getGazdasagKezelo().fizetes(ar)) {
            if ("so".equals(anyag)) hokotro.setSo(100);
            if ("biokerozin".equals(anyag)) hokotro.setBiokerozin(100);
            if ("zuzalek".equals(anyag)) hokotro.setZuzalek(100);
            jatekKezelo.palyaValtozott();
        }
    }

    /**
     * Uj buszt vasarol es forgalomba allitja az alap vegallomasparon.
     * @return Igaz, ha a vasarlas es lerakas sikeres volt.
     */
    public boolean ujBuszVasarlas() {
        if (!jatekKezelo.getGazdasagKezelo().fizetes(UJ_BUSZ_AR)) {
            return false;
        }
        Vegallomas cel = getVegallomasok().isEmpty() ? null : getVegallomasok().get(0);
        Sav sav = elsoElhelyezhetoSav();
        if (sav == null || cel == null) {
            return false;
        }
        Busz busz = new Busz("B" + buszSorszam++, 0, sav, cel);
        if (getVegallomasok().size() >= 2) {
            busz.setAllomasok(getVegallomasok().get(0), getVegallomasok().get(1));
            kezdoBuszUtvonalBeallit(busz, getVegallomasok().get(0), getVegallomasok().get(1));
        }
        busz.setJatekKezelo(jatekKezelo);
        jatekKezelo.getForgalomIranyito().addJarmu(busz);
        if (terkepPanel != null) {
            terkepPanel.addJarmuView(new BuszView(busz));
        }
        jatekKezelo.palyaValtozott();
        return true;
    }

    /**
     * Uj hokotrot vasarol es lerakja az elso elerheto savra.
     * @return Igaz, ha a vasarlas es lerakas sikeres volt.
     */
    public boolean ujHokotroVasarlas() {
        if (!jatekKezelo.getGazdasagKezelo().fizetes(UJ_HOKOTRO_AR)) {
            return false;
        }
        Sav sav = elsoElhelyezhetoSav();
        if (sav == null) {
            return false;
        }
        Hokotro hokotro = new Hokotro("H" + hokotroSorszam++, new HanyoFej(), sav, 0);
        hokotro.setForgalomIranyito(jatekKezelo.getForgalomIranyito());
        jatekKezelo.getForgalomIranyito().addJarmu(hokotro);
        if (terkepPanel != null) {
            terkepPanel.addJarmuView(new HokotroView(hokotro));
        }
        jatekKezelo.palyaValtozott();
        return true;
    }

    /**
     * Torli a jatekos altal eppen kijelolt utvonalat.
     */
    public void utvonalTorol() {
        kijeloltUtvonal.clear();
        jatekKezelo.palyaValtozott();
    }

    /**
     * Hozzaad egy csomopontot a kijelolt utvonalhoz, ha az ervenyes folytatas.
     * @param csomopont A hozzaadando csomopont.
     * @return Igaz, ha a csomopont bekerult az utvonalba.
     */
    public boolean utvonalCsomopontHozzaad(Csomopont csomopont) {
        if (csomopont == null) {
            return false;
        }
        if (!kijeloltUtvonal.isEmpty()) {
            Csomopont elozo = kijeloltUtvonal.get(kijeloltUtvonal.size() - 1);
            if (elozo == csomopont) {
                return false;
            }
            if (koztesUt(elozo, csomopont) == null) {
                return false;
            }
        }
        kijeloltUtvonal.add(csomopont);
        jatekKezelo.palyaValtozott();
        return true;
    }

    /**
     * A kijelolt utvonalat kiosztja a megadott busznak.
     * @param busz A busz, amely az uj utvonalat kapja.
     * @return Igaz, ha az utvonal kiosztasa sikeres volt.
     */
    public boolean utvonalKiosztBusznak(Busz busz) {
        Utszakasz[] utak = kijeloltUtvonalSzakaszok();
        if (busz == null || utak == null) {
            return false;
        }
        Csomopont elsoKezdo = kijeloltUtvonal.get(0);
        busz.UtvonalatKijelol(elokeszitUtvonal(busz, utak, elsoKezdo));
        kijeloltUtvonal.clear();
        jatekKezelo.palyaValtozott();
        return true;
    }

    /**
     * A kijelolt utvonalat kiosztja a megadott hokotronak.
     * @param hokotro A hokotro, amely az uj utvonalat kapja.
     * @return Igaz, ha az utvonal kiosztasa sikeres volt.
     */
    public boolean utvonalKiosztHokotronak(Hokotro hokotro) {
        Utszakasz[] utak = kijeloltUtvonalSzakaszok();
        if (hokotro == null || utak == null) {
            return false;
        }
        Csomopont elsoKezdo = kijeloltUtvonal.get(0);
        hokotro.UtvonalatKijelol(elokeszitUtvonal(hokotro, utak, elsoKezdo));
        kijeloltUtvonal.clear();
        jatekKezelo.palyaValtozott();
        return true;
    }

    /**
     * Ha a jarmu jelenleg egy mas utszakaszon halad, mint amin az uj utvonal kezdodik,
     * megvizsgalja, hogy az aktualis szakasz kilepo csomopontja csatlakozik-e az uj utvonal
     * elso szakaszahoz. Ha igen, az aktualis szakaszt a lista ele fuzi (a jarmu befejezi
     * az aktualis szakaszt, majd termeszetesen athalad az ujra). Ha nem csatlakozik, a jarmu
     * teleportal az uj utvonal elejere, elkerulve a helytelen irany-flag miatti vizualis ugrust.
     *
     * @param ujKezdoCsomopont Az uj utvonal elso csomopontja (a jatekos elso klikkje) —
     *                         meghatározza a haladasi iranyt az elso uj utszakaszon.
     */
    private Utszakasz[] elokeszitUtvonal(Jarmu jarmu, Utszakasz[] utak, Csomopont ujKezdoCsomopont) {
        if (utak == null || utak.length == 0) return utak;
        Sav sav = jarmu.getAktualisSav();
        if (sav == null) return utak;
        Utszakasz aktualis = sav.getSzuloUtszakasz();
        if (aktualis == null) return utak;

        if (aktualis == utak[0]) {
            // A jarmu mar ezen az uton van: az iranyt a klikk-sorrendbol allitjuk be.
            // Ha az irany fordul, a poziciót is tükrozzük, hogy ne ugorjon vizualisan.
            // FONTOS: setAktualisIranyForditott-ot hasznalunk, nem beallitKezdoIrany-t,
            // mert az UtvonalatKijelol meg nem futott le, es beallitKezdoIrany meg a
            // REGI Utvonal[0]-t hasznalja referenciakent.
            boolean ujIrany = (ujKezdoCsomopont == utak[0].getVege());
            if (ujIrany != jarmu.isAktualisIranyForditott()) {
                jarmu.setPozicioASavon(sav.getHossz() - jarmu.getPozicioASavon());
                jarmu.setAktualisIranyForditott(ujIrany);
            }
            return utak;
        }

        Csomopont kilepes = jarmu.isAktualisIranyForditott()
                ? aktualis.getEleje() : aktualis.getVege();
        if (kilepes == utak[0].getEleje() || kilepes == utak[0].getVege()) {
            Utszakasz[] teljes = new Utszakasz[utak.length + 1];
            teljes[0] = aktualis;
            System.arraycopy(utak, 0, teljes, 1, utak.length);
            return teljes;
        }

        // Nem csatlakozik kozvetlenul: utvonalat keresunk a kilepo csomoponttol
        // az uj utvonal kezdopontjaig, hogy a jarmu odamenjen elobb.
        Utszakasz[] odaUt = jatekKezelo.getUtvonalTervezo().utvonalKeres(kilepes, ujKezdoCsomopont);
        if (odaUt != null && odaUt.length > 0) {
            // Teljes ut: [aktualis szakasz] + [oda vezeto ut] + [uj utvonal]
            Utszakasz[] teljes = new Utszakasz[1 + odaUt.length + utak.length];
            teljes[0] = aktualis;
            System.arraycopy(odaUt, 0, teljes, 1, odaUt.length);
            System.arraycopy(utak, 0, teljes, 1 + odaUt.length, utak.length);
            return teljes;
        }

        // Ha nincs ut (elzart palya), vegso tartalek: teleport a kezdoponthoz
        jarmu.setStartSav(utak[0].getSavok().get(0));
        jarmu.setPozicioASavon(0);
        jarmu.setAktualisIranyForditott(ujKezdoCsomopont == utak[0].getVege());
        return utak;
    }

    /**
     * Megkeresi, hogy a megadott kepernyokoordinata alatt van-e csomopont.
     * @param x Kepernyobeli x koordinata.
     * @param y Kepernyobeli y koordinata.
     * @return A talalt csomopont, vagy null.
     */
    public Csomopont csomopontKoordinatanal(int x, int y) {
        for (Csomopont csomopont : csomopontok) {
            CsomopontPozicio p = elrendezes.getPozicio(csomopont);
            if (p == null) {
                continue;
            }
            int dx = p.getX() - x;
            int dy = p.getY() - y;
            if (dx * dx + dy * dy <= 22 * 22) {
                return csomopont;
            }
        }
        return null;
    }

    /**
     * Megkeresi, hogy a megadott kepernyokoordinata alatt van-e jarmu.
     * @param x Kepernyobeli x koordinata.
     * @param y Kepernyobeli y koordinata.
     * @return A talalt jarmu, vagy null.
     */
    public Jarmu jarmuKoordinatanal(int x, int y) {
        for (Jarmu jarmu : getJarmuvek()) {
            Point p = jarmuKepernyoPont(jarmu);
            if (p == null) {
                continue;
            }
            int dx = p.x - x;
            int dy = p.y - y;
            if (dx * dx + dy * dy <= 18 * 18) {
                return jarmu;
            }
        }
        return null;
    }

    /**
     * Kiszamolja egy jarmu aktualis kepernyopontjat a savon elfoglalt pozicioja alapjan.
     * @param jarmu A vizsgalt jarmu.
     * @return A jarmu kepernyopontja, vagy null, ha nem rajzolhato.
     */
    private Point jarmuKepernyoPont(Jarmu jarmu) {
        Sav sav = jarmu.getAktualisSav();
        if (sav == null || sav.getSzuloUtszakasz() == null) {
            return null;
        }
        Utszakasz ut = sav.getSzuloUtszakasz();
        CsomopontPozicio p1 = elrendezes.getPozicio(ut.getEleje());
        CsomopontPozicio p2 = elrendezes.getPozicio(ut.getVege());
        if (p1 == null || p2 == null) {
            return null;
        }
        int savIndex = ut.getSavok().indexOf(sav);
        int savDb = Math.max(1, ut.getSavok().size());
        double arany = Math.max(0.0, Math.min(1.0, jarmu.getPozicioASavon() / (double) sav.getHossz()));
        if (jarmu.isAktualisIranyForditott()) {
            arany = 1.0 - arany;
        }
        return savPont(p1, p2, savIndex, savDb, arany);
    }

    /**
     * Kiszamolja egy sav adott aranyban levo kepernyopontjat.
     * @param p1 Az utszakasz egyik vegpontjanak pozicioja.
     * @param p2 Az utszakasz masik vegpontjanak pozicioja.
     * @param savIndex A sav indexe az utszakaszon.
     * @param savDb Az utszakasz savjainak szama.
     * @param arany A pont helye az utszakaszon 0 es 1 kozott.
     * @return A kiszamolt kepernyopont.
     */
    private Point savPont(CsomopontPozicio p1, CsomopontPozicio p2, int savIndex, int savDb, double arany) {
        double x1 = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();
        double dx = x2 - x1;
        double dy = y2 - y1;
        double hossz = Math.max(1.0, Math.sqrt(dx * dx + dy * dy));
        double nx = -dy / hossz;
        double ny = dx / hossz;
        double offset = (savIndex - (savDb - 1) / 2.0) * 16.0;
        int px = (int) Math.round(x1 + dx * arany + nx * offset);
        int py = (int) Math.round(y1 + dy * arany + ny * offset);
        return new Point(px, py);
    }

    /**
     * Visszaadja az aktualisan kijelolt csomopontsorozatot.
     * @return A kijelolt utvonal csomopontjai.
     */
    public List<Csomopont> getKijeloltUtvonal() {
        return kijeloltUtvonal;
    }

    /**
     * A kijelolt csomopontsort utszakasz tombbe alakitja.
     * @return Az utvonal utszakaszai, vagy null ervenytelen utvonal eseten.
     */
    private Utszakasz[] kijeloltUtvonalSzakaszok() {
        if (kijeloltUtvonal.size() < 2) {
            return null;
        }
        Utszakasz[] utak = new Utszakasz[kijeloltUtvonal.size() - 1];
        for (int i = 0; i < kijeloltUtvonal.size() - 1; i++) {
            Utszakasz ut = koztesUt(kijeloltUtvonal.get(i), kijeloltUtvonal.get(i + 1));
            if (ut == null) {
                return null;
            }
            utak[i] = ut;
        }
        return utak;
    }

    /**
     * Megkeresi a ket csomopont kozotti kozos utat.
     * @param a Az egyik csomopont.
     * @param b A masik csomopont.
     * @return A kozos utszakasz, vagy null.
     */
    private Utszakasz koztesUt(Csomopont a, Csomopont b) {
        for (Utszakasz ut : a.getUtszakaszok()) {
            if (b.getUtszakaszok().contains(ut)) {
                return ut;
            }
        }
        return null;
    }

    /**
     * Visszaadja az elso olyan savot, ahova uj jarmu lerakhato.
     * @return Az elso elerheto sav, vagy null.
     */
    private Sav elsoElhelyezhetoSav() {
        for (Utszakasz ut : getUtak()) {
            if (!ut.getSavok().isEmpty()) {
                return ut.getSavok().get(0);
            }
        }
        return null;
    }

    /**
     * Visszaadja a palya osszes savjat.
     * @return Savlista.
     */
    public List<Sav> getSavok() {
        List<Sav> savok = new ArrayList<>();
        for (Utszakasz ut : getUtak()) {
            savok.addAll(ut.getSavok());
        }
        return savok;
    }

    /**
     * Visszaadja a palyan levo hokotrok listajat.
     * @return Hokotro lista.
     */
    public List<Hokotro> getHokotrok() {
        List<Hokotro> hokotrok = new ArrayList<>();
        for (Jarmu jarmu : getJarmuvek()) {
            Hokotro hokotro = jarmu.asHokotro();
            if (hokotro != null) {
                hokotrok.add(hokotro);
            }
        }
        return hokotrok;
    }

    /**
     * Visszaadja a palyan levo buszok listajat.
     * @return Busz lista.
     */
    public List<Busz> getBuszok() {
        List<Busz> buszok = new ArrayList<>();
        for (Jarmu jarmu : getJarmuvek()) {
            Busz busz = jarmu.asBusz();
            if (busz != null) {
                buszok.add(busz);
            }
        }
        return buszok;
    }

    /**
     * Visszaadja a palyan levo vegallomasokat.
     * @return Vegallomas lista.
     */
    public List<Vegallomas> getVegallomasok() {
        List<Vegallomas> vegallomasok = new ArrayList<>();
        for (PointOfInterest poi : poik) {
            Vegallomas vegallomas = poi.asVegallomas();
            if (vegallomas != null) {
                vegallomasok.add(vegallomas);
            }
        }
        return vegallomasok;
    }

    /**
     * Visszaadja az uthalozat utszakaszait.
     * @return Az utak gyujtemenye.
     */
    public Collection<Utszakasz> getUtak() {
        return jatekKezelo.getUtvonalTervezo().getUtak().values();
    }

    /**
     * Visszaadja a palyan levo osszes jarmuvet.
     * @return Jarmu gyujtemeny.
     */
    public Collection<Jarmu> getJarmuvek() {
        return jatekKezelo.getForgalomIranyito().getJarmuvek();
    }

    /**
     * Visszaadja a grafikus palyan megjeleno POI objektumokat.
     * @return POI lista.
     */
    public List<PointOfInterest> getPoik() {
        return poik;
    }

    /**
     * Visszaadja a grafikus palya csomopontjait.
     * @return Csomopont lista.
     */
    public List<Csomopont> getCsomopontok() {
        return csomopontok;
    }

    /**
     * Visszaadja a grafikus elrendezest.
     * @return A csomopontok kepernyobeli elrendezese.
     */
    public GrafikusElrendezes getElrendezes() {
        return elrendezes;
    }

    /**
     * Visszaadja a jatek kozponti kezelojet.
     * @return A jatekkezelo peldany.
     */
    public JatekKezelo getJatekKezelo() {
        return jatekKezelo;
    }
}
