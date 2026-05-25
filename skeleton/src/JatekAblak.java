import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

/**
 * A grafikus jatek foablaka.
 * Felelossege a menuk, a szerepkor alapu oldalso vezerlopanel,
 * a statuszsor es a naplo megjelenitese, valamint a felhasznaloi
 * esemenyek tovabbitasa a GrafikusVezerlo fele.
 */
public class JatekAblak extends JFrame {
    /** A felulet es a modell kozotti vezerlo objektum. */
    private final GrafikusVezerlo vezerlo;
    /** A palya grafikus megjeleniteset vegzo panel. */
    private final TerkepPanel terkepPanel;
    private final JComboBox<Busz> buszCombo = new JComboBox<>();
    private final JComboBox<Hokotro> hokotroCombo = new JComboBox<>();
    private final JComboBox<Sav> savCombo = new JComboBox<>();
    private final JComboBox<String> fejCombo = new JComboBox<>(new String[] {
        "sopro", "hanyo", "jegtoro", "soszoro", "sarkany", "zuzalekszoro"
    });
    private final JLabel statusLabel = new JLabel();
    private final JLabel utvonalLabel = new JLabel();
    private final JTextArea naploMezo = new JTextArea();

    /**
     * Letrehozza a jatek foablakat es feliratkoztatja a nezeteket a modellvaltozasokra.
     * @param vezerlo A grafikus vezerlo, amelyen keresztul a modell elerheto.
     */
    public JatekAblak(GrafikusVezerlo vezerlo) {
        this.vezerlo = vezerlo;
        this.terkepPanel = new TerkepPanel(vezerlo);
        vezerlo.setTerkepPanel(terkepPanel);
        setTitle("Zuzmaravaros - buszvezetok es takaritok");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setJMenuBar(menu());
        terkepPanel.setKattintasKezelo(new UtvonalKattintasKezelo() {
            @Override
            public void csomopontKivalasztva(Csomopont csomopont) {
                JatekAblak.this.csomopontKivalasztva(csomopont);
            }

            @Override
            public void jarmuKivalasztva(Jarmu jarmu) {
                JatekAblak.this.jarmuKivalasztva(jarmu);
            }

            @Override
            public void uresTeruletKivalasztva() {
                JatekAblak.this.uresTeruletKivalasztva();
            }
        });
        JScrollPane terkepGorgeto = new JScrollPane(terkepPanel);
        terkepGorgeto.getHorizontalScrollBar().setUnitIncrement(16);
        terkepGorgeto.getVerticalScrollBar().setUnitIncrement(16);
        add(terkepGorgeto, BorderLayout.CENTER);
        add(oldalsoPanel(), BorderLayout.EAST);
        add(alsoSav(), BorderLayout.SOUTH);
        naploMezo.setEditable(false);
        naploMezo.setRows(8);
        naploMezo.setFont(naploMezo.getFont().deriveFont(Font.PLAIN, 11f));
        observerFeliratkozas();
        frissit();
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Letrehozza a fo menu sort.
     * @return A feltoltott menu sor.
     */
    private JMenuBar menu() {
        JMenuBar bar = new JMenuBar();
        JMenu fajl = new JMenu("Fajl");
        JMenuItem uj = new JMenuItem("Uj demo palya");
        uj.addActionListener(e -> {
            vezerlo.automataMegallit();
            vezerlo.ujDemoPalya();
            observerFeliratkozas();
            naplo("Uj demo palya betoltve.");
            frissit();
        });
        JMenuItem kilep = new JMenuItem("Kilepes");
        kilep.addActionListener(e -> dispose());
        fajl.add(uj);
        fajl.addSeparator();
        fajl.add(kilep);

        JMenu szimulacio = new JMenu("Szimulacio");
        JMenuItem lep = new JMenuItem("Egy tick");
        lep.addActionListener(e -> egyTick());
        JMenuItem start = new JMenuItem("Automata inditas");
        start.addActionListener(e -> automataIndit());
        JMenuItem stop = new JMenuItem("Automata megallitas");
        stop.addActionListener(e -> automataMegallit());
        szimulacio.add(lep);
        szimulacio.add(start);
        szimulacio.add(stop);

        bar.add(fajl);
        bar.add(szimulacio);
        return bar;
    }

    /**
     * Letrehozza a jobb oldali, gorgetheto vezerlopanelt.
     * @return Az oldalso panel.
     */
    private JPanel oldalsoPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setPreferredSize(new Dimension(340, 560));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JTabbedPane tabok = new JTabbedPane();
        tabok.addTab("Buszvezetok", buszvezetoPanel());
        tabok.addTab("Takaritok", takaritoPanel());
        tabok.addTab("Varos", varosPanel());

        JScrollPane vezerloGorgeto = new JScrollPane(tabok);
        vezerloGorgeto.setBorder(BorderFactory.createEmptyBorder());
        vezerloGorgeto.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        vezerloGorgeto.getVerticalScrollBar().setUnitIncrement(14);

        JScrollPane naploGorgeto = new JScrollPane(naploMezo);
        naploGorgeto.setPreferredSize(new Dimension(0, 120));

        panel.add(vezerloGorgeto, BorderLayout.CENTER);
        panel.add(naploGorgeto, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Letrehozza az also statusz- es jelmagyarazat sort.
     * @return Az also informacios sav.
     */
    private JPanel alsoSav() {
        JPanel panel = new JPanel(new BorderLayout(8, 4));
        panel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        JPanel jelmagyarazat = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        jelmagyarazat.add(new JLabel("Savok: szurke=tiszta"));
        jelmagyarazat.add(new JLabel("vilagos=havas"));
        jelmagyarazat.add(new JLabel("kek=jeg"));
        jelmagyarazat.add(new JLabel("sarga=sozott"));
        jelmagyarazat.add(new JLabel("sotet=blokkolt"));
        jelmagyarazat.add(new JLabel("| Jarmu: A=auto B=busz H=hokotro !=megcsuszott"));
        panel.add(statusLabel, BorderLayout.NORTH);
        panel.add(jelmagyarazat, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Letrehozza a buszvezetoknek szolo fult.
     * @return A buszvezerlo panel.
     */
    private JPanel buszvezetoPanel() {
        JPanel panel = kompaktPanel();
        panel.add(sugoDoboz("Buszvezetok: valassz buszt, kattints sorban csomopontokra a terkepen, majd oszd ki az utvonalat."));
        JPanel valasztas = szekcio("Busz");
        valasztas.add(new JLabel("Kivalasztott busz"));
        valasztas.add(buszCombo);
        panel.add(valasztas);

        JButton utvonalBusznak = gomb("Utvonal kiosztasa busznak");
        utvonalBusznak.addActionListener(e -> {
            boolean siker = vezerlo.utvonalKiosztBusznak((Busz) buszCombo.getSelectedItem());
            naplo(siker ? "Busz uj utvonalat kapott." : "Busz utvonal kiosztasa sikertelen.");
            frissit();
        });

        JButton ujBusz = gomb("Uj busz vasarlasa");
        ujBusz.addActionListener(e -> {
            boolean siker = vezerlo.ujBuszVasarlas();
            naplo(siker ? "Uj busz forgalomba allitva." : "Nincs eleg penz vagy nincs ervenyes lerako sav.");
            frissit();
        });

        JPanel muveletek = szekcio("Muveletek");
        muveletek.add(utvonalBusznak);
        muveletek.add(ujBusz);
        panel.add(muveletek);
        panel.add(utvonalPanel());
        return panel;
    }

    /**
     * Letrehozza a takaritoknak szolo fult.
     * @return A hokotro vezerlo panel.
     */
    private JPanel takaritoPanel() {
        JPanel panel = kompaktPanel();
        panel.add(sugoDoboz("Takaritok: valassz hokotrot, jelolj ki utat a terkepen, majd kuldd ki takaritani vagy intezz garazs muveletet."));
        JPanel valasztas = szekcio("Hokotro");
        valasztas.add(new JLabel("Kivalasztott hokotro"));
        valasztas.add(hokotroCombo);
        panel.add(valasztas);

        JButton utvonalHokotronak = gomb("Utvonal kiosztasa hokotronak");
        utvonalHokotronak.addActionListener(e -> {
            boolean siker = vezerlo.utvonalKiosztHokotronak((Hokotro) hokotroCombo.getSelectedItem());
            naplo(siker ? "Hokotro uj utvonalat kapott." : "Hokotro utvonal kiosztasa sikertelen.");
            frissit();
        });

        JButton ujHokotro = gomb("Uj hokotro vasarlasa");
        ujHokotro.addActionListener(e -> {
            boolean siker = vezerlo.ujHokotroVasarlas();
            naplo(siker ? "Uj hokotro forgalomba allitva." : "Nincs eleg penz uj hokotrora.");
            frissit();
        });

        JButton fejcsere = gomb("Fejcsere garazsban");
        fejcsere.addActionListener(e -> {
            Hokotro hk = (Hokotro) hokotroCombo.getSelectedItem();
            if (!vezerlo.hokotroGarazsban(hk)) {
                naplo("Fejcsere sikertelen: hokotro nincs garazs kozeleben! (A, J vagy H csomopont)");
            } else {
                vezerlo.cserelFej(hk, (String) fejCombo.getSelectedItem());
                naplo("Fejcsere elvegezve: " + fejCombo.getSelectedItem());
            }
            frissit();
        });

        JButton so = gomb("So vasarlasa");
        so.addActionListener(e -> tankol("so"));
        JButton kerozin = gomb("Biokerozin vasarlasa");
        kerozin.addActionListener(e -> tankol("biokerozin"));
        JButton zuzalek = gomb("Zuzalek vasarlasa");
        zuzalek.addActionListener(e -> tankol("zuzalek"));

        JPanel utvonal = szekcio("Utvonal");
        utvonal.add(utvonalHokotronak);
        panel.add(utvonal);

        JPanel garazs = szekcio("Garazs / vasarlas");
        garazs.add(new JLabel("Kotrofej"));
        garazs.add(fejCombo);
        garazs.add(fejcsere);
        garazs.add(so);
        garazs.add(kerozin);
        garazs.add(zuzalek);
        garazs.add(ujHokotro);
        panel.add(garazs);
        panel.add(utvonalPanel());
        return panel;
    }

    /**
     * Letrehozza a varos es szimulacio kezelesere szolgalo fult.
     * @return A varos panel.
     */
    private JPanel varosPanel() {
        JPanel panel = kompaktPanel();
        panel.add(sugoDoboz("Varos ful: itt tudod leptetni a szimulaciot, illetve teszteleshez gyorsan havat vagy jeget tenni egy savra."));
        JButton lep = gomb("Egy tick");
        lep.addActionListener(e -> egyTick());
        JButton start = gomb("Start");
        start.addActionListener(e -> automataIndit());
        JButton stop = gomb("Stop");
        stop.addActionListener(e -> automataMegallit());

        JButton havaz = gomb("Havazas +10 cm");
        havaz.addActionListener(e -> {
            vezerlo.havaz((Sav) savCombo.getSelectedItem(), 10);
            naplo("Havazas a kivalasztott savon.");
            frissit();
        });
        JButton jeg = gomb("Jegpancel");
        jeg.addActionListener(e -> {
            vezerlo.allitJeg((Sav) savCombo.getSelectedItem());
            naplo("Jegpancel beallitva.");
            frissit();
        });
        JButton tisztit = gomb("Sav tisztitasa");
        tisztit.addActionListener(e -> {
            vezerlo.tisztit((Sav) savCombo.getSelectedItem());
            naplo("Sav megtisztitva.");
            frissit();
        });

        JPanel szimulacio = szekcio("Szimulacio");
        szimulacio.add(lep);
        szimulacio.add(start);
        szimulacio.add(stop);
        panel.add(szimulacio);

        JPanel idojaras = szekcio("Idojaras / teszt muveletek");
        idojaras.add(new JLabel("Kivalasztott sav"));
        idojaras.add(savCombo);
        idojaras.add(havaz);
        idojaras.add(jeg);
        idojaras.add(tisztit);
        panel.add(idojaras);
        return panel;
    }

    /**
     * Letrehozza az aktualis utvonal kijelzeset es torleset vegzo panelt.
     * @return Az utvonal panel.
     */
    private JPanel utvonalPanel() {
        JPanel panel = szekcio("Kijelolt utvonal");
        JButton torol = gomb("Kijelolt utvonal torlese");
        torol.addActionListener(e -> {
            vezerlo.utvonalTorol();
            naplo("Kijelolt utvonal torolve.");
            frissit();
        });
        panel.add(new JLabel("Csomopontokra kattintva epul"));
        panel.add(utvonalLabel);
        panel.add(torol);
        return panel;
    }

    /**
     * Kompakt, fuggoleges elrendezesu panelt keszit a vezerlogomboknak.
     * @return Az uj panel.
     */
    private JPanel kompaktPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        return panel;
    }

    /**
     * Keretezett szekciot keszit a jobb oldali panelhez.
     * @param cim A szekcio cime.
     * @return Az uj szekciopanel.
     */
    private JPanel szekcio(String cim) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 4, 4));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                cim,
                TitledBorder.LEFT,
                TitledBorder.TOP));
        return panel;
    }

    /**
     * Roviden magyarazo dobozt keszit a ful tetejere.
     * @param szoveg A megjelenitendo segedszoveg.
     * @return Az uj segitseg panel.
     */
    private JLabel sugoDoboz(String szoveg) {
        JLabel label = new JLabel("<html><body style='width:260px'>" + szoveg + "</body></html>");
        label.setBorder(BorderFactory.createEmptyBorder(2, 4, 8, 4));
        label.setFont(label.getFont().deriveFont(Font.PLAIN, 11f));
        return label;
    }

    /**
     * Kompakt meretu gombot hoz letre az oldalso panelhez.
     * @param szoveg A gomb felirata.
     * @return Az uj gomb.
     */
    private JButton gomb(String szoveg) {
        JButton gomb = new JButton(szoveg);
        gomb.setMargin(new java.awt.Insets(2, 6, 2, 6));
        gomb.setFont(gomb.getFont().deriveFont(Font.PLAIN, 11f));
        gomb.setPreferredSize(new Dimension(0, 24));
        return gomb;
    }

    /**
     * Feldolgozza a terkepen kivalasztott csomopontot.
     * @param csomopont A felhasznalo altal kivalasztott csomopont.
     */
    private void csomopontKivalasztva(Csomopont csomopont) {
        boolean siker = vezerlo.utvonalCsomopontHozzaad(csomopont);
        naplo(siker ? "Utvonalhoz adva: " + csomopont.getID() : "Nem szomszedos csomopont: " + csomopont.getID());
        frissit();
    }

    /**
     * Feldolgozza a terkepen kivalasztott jarmuvet.
     * @param jarmu A felhasznalo altal kivalasztott jarmu.
     */
    private void jarmuKivalasztva(Jarmu jarmu) {
        Busz busz = jarmu.asBusz();
        Hokotro hokotro = jarmu.asHokotro();
        if (busz != null) {
            buszCombo.setSelectedItem(busz);
            naplo("Busz kivalasztva: " + jarmu.getId());
        } else if (hokotro != null) {
            hokotroCombo.setSelectedItem(hokotro);
            naplo("Hokotro kivalasztva: " + jarmu.getId());
        } else {
            naplo("Jarmu kivalasztva: " + jarmu.getId());
        }
        frissit();
    }

    /**
     * Ures terkepreszre kattintas eseten torli az aktualis utvonalkijelolest.
     */
    private void uresTeruletKivalasztva() {
        if (!vezerlo.getKijeloltUtvonal().isEmpty()) {
            vezerlo.utvonalTorol();
            naplo("Kijeloles torolve.");
            frissit();
        }
    }

    /**
     * Egy szimulacios lepest indit.
     */
    private void egyTick() {
        vezerlo.lep();
        naplo("Egy tick lefutott.");
        frissit();
    }

    /**
     * Elinditja az automatikus leptetest.
     */
    private void automataIndit() {
        vezerlo.automataIndit(this::frissit);
        naplo("Automata leptetes elindult.");
    }

    /**
     * Leallitja az automatikus leptetest.
     */
    private void automataMegallit() {
        vezerlo.automataMegallit();
        naplo("Automata leptetes megallitva.");
    }

    /**
     * Feltoltesi vasarlast indit a kivalasztott hokotrohöz — csak garazsban ervenyes.
     * @param anyag A vasarolando anyag neve.
     */
    private void tankol(String anyag) {
        Hokotro hk = (Hokotro) hokotroCombo.getSelectedItem();
        if (!vezerlo.hokotroGarazsban(hk)) {
            naplo("Tankolas sikertelen: hokotro nincs garazs kozeleben! (A, J vagy H csomopont)");
        } else {
            vezerlo.tankol(hk, anyag);
            naplo("Tankolas elvegezve: " + anyag);
        }
        frissit();
    }

    /**
     * Feliratkoztatja a terkepet es az ablakot a modell valtozasaira.
     */
    private void observerFeliratkozas() {
        vezerlo.getJatekKezelo().addMegfigyelo(terkepPanel);
        vezerlo.getJatekKezelo().addMegfigyelo(() -> SwingUtilities.invokeLater(this::frissit));
    }

    /**
     * Frissiti a listakat, a statuszsort es a terkepet.
     */
    private void frissit() {
        Busz valasztottBusz = (Busz) buszCombo.getSelectedItem();
        Hokotro valasztottHokotro = (Hokotro) hokotroCombo.getSelectedItem();
        Sav valasztottSav = (Sav) savCombo.getSelectedItem();

        buszCombo.removeAllItems();
        for (Busz busz : vezerlo.getBuszok()) {
            buszCombo.addItem(busz);
        }
        if (vezerlo.getBuszok().contains(valasztottBusz)) {
            buszCombo.setSelectedItem(valasztottBusz);
        }

        hokotroCombo.removeAllItems();
        for (Hokotro hokotro : vezerlo.getHokotrok()) {
            hokotroCombo.addItem(hokotro);
        }
        if (vezerlo.getHokotrok().contains(valasztottHokotro)) {
            hokotroCombo.setSelectedItem(valasztottHokotro);
        }

        savCombo.removeAllItems();
        for (Sav sav : vezerlo.getSavok()) {
            savCombo.addItem(sav);
        }
        if (vezerlo.getSavok().contains(valasztottSav)) {
            savCombo.setSelectedItem(valasztottSav);
        }

        utvonalLabel.setText(utvonalSzoveg());
        statusLabel.setText("Tick: " + vezerlo.getJatekKezelo().getAktualisTick()
                + " | Kassza: " + vezerlo.getJatekKezelo().getGazdasagKezelo().getKozosKassza()
                + " Ft | Busz fordulok: " + vezerlo.getJatekKezelo().getBuszPontszamok()
                + " | Buszok: " + vezerlo.getBuszok().size()
                + " | Hokotrok: " + vezerlo.getHokotrok().size());
        terkepPanel.repaint();
    }

    /**
     * Szoveges formaju utvonalat keszit a kijelolt csomopontokbol.
     * @return Az utvonal szoveges megjelenitese.
     */
    private String utvonalSzoveg() {
        if (vezerlo.getKijeloltUtvonal().isEmpty()) {
            return "-";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vezerlo.getKijeloltUtvonal().size(); i++) {
            if (i > 0) {
                sb.append(" -> ");
            }
            sb.append(vezerlo.getKijeloltUtvonal().get(i).getID());
        }
        return sb.toString();
    }

    /**
     * Sort ir a jobb oldali naplomezobe.
     * @param sor A kiirando naplosor.
     */
    private void naplo(String sor) {
        naploMezo.append(sor + "\n");
        naploMezo.setCaretPosition(naploMezo.getDocument().getLength());
    }
}
