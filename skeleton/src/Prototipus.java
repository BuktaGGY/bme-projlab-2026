import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A Prototípus tesztelési felületét kezelő osztály.
 * Felelőssége a szabványos bemenet olvasása, a modell vezérlése és a kimenet írása.
 */
public class Prototipus {
    // A játékot vezérlő fő osztály
    private JatekKezelo jk;

    // Azonosítókhoz kötött objektumtárolók a parancsok feldolgozásához
    private Map<String, Csomopont> csomopontok = new HashMap<>();
    private Map<String, Utszakasz> utak = new HashMap<>();
    private Map<String, Sav> savok = new HashMap<>();
    private Map<String, PointOfInterest> poik = new HashMap<>();

    /**
     * A Prototipus konstruktora.
     * Példányosítja a játékkezelőt és előkészíti a memóriát.
     */
    public Prototipus() {
        this.jk = new JatekKezelo();
    }


/**
     * Elindítja a beolvasási ciklust. A program addig olvas a standard bemenetről, 
     * amíg a 'kilep' parancsot meg nem kapja, vagy el nem fogy a bemenet (EOF).
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bemenet:");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            String command = parts[0];

            try {
                switch (command) {
                    case "uj_csomopont":
                        String cpId = parts[1];
                        Csomopont cp = new Csomopont(cpId);
                        csomopontok.put(cpId, cp);
                        System.out.println("[OK] Csomopont letrehozva (" + cpId + ")");
                        break;

                    case "uj_ut":
                        String utId = parts[1];
                        Csomopont c1 = csomopontok.get(parts[2]);
                        Csomopont c2 = csomopontok.get(parts[3]);
                        int hossz = Integer.parseInt(parts[4]);
                        
                        SimaUt ut = new SimaUt(utId, c1, c2, hossz);
                        utak.put(utId, ut);
                        c1.addUtszakasz(ut);
                        c2.addUtszakasz(ut);
                        System.out.println("[OK] Ut letrehozva (" + utId + ")");
                        break;

                    case "hozzaad_sav":
                        String parentUtId = parts[1];
                        String savId = parts[2];
                        
                        Sav sav = new Sav(savId);
                        utak.get(parentUtId).addSav(sav);
                        savok.put(savId, sav);
                        System.out.println("[OK] Sav hozzaadva (" + savId + ")");
                        break;

                    case "allit_sav":
                        String sId = parts[1];
                        int ho = Integer.parseInt(parts[2]);
                        boolean jeg = parts[3].equals("igen");
                        int soIdo = Integer.parseInt(parts[4]);
                        boolean zuzalek = parts[5].equals("igen");
                        
                        savok.get(sId).setParameterek(ho, jeg, soIdo, zuzalek);
                        System.out.println("[OK] Sav parameterei beallitva (" + sId + ")");
                        break;


                    //TODO ezt valaki nézze át
                    case "uj_poi":
                        String poiId = parts[1];
                        String cspId = parts[2];

                        String poiType = parts[3];
                        switch (poiType) {
                            case "lakas": poik.put(poiId,new Lakas(poiId)); break;
                            case "munkahely": poik.put(poiId,new Munkahely(poiId)); break;
                            case "vegallomas": poik.put(poiId,new Vegallomas(poiId)); break;
                            case "garazs":  poik.put(poiId,new Garazs(poiId)); break;
                        }
                        poik.get(poiId).setCsomopont(csomopontok.get(cspId));

                        System.out.println("[OK] Point of Interest letrehozva (" + poiType + ")");
                        break;

                    //TODO bemeneti parameterek ellenorzese
                    case "lerak_auto":
                        String autoId = parts[1];
                        String autoSavId = parts[2];
                        int autoPozicio = Integer.parseInt(parts[3]);
                        String celPoiId = parts[4];

                        Sav autoSav = savok.get(autoSavId);
                        PointOfInterest autoCel = poik.get(celPoiId);
                        Auto a =  new Auto(autoId, autoPozicio, autoSav, autoCel);

                        jk.getForgalomIranyito().addJarmu(a);

                        System.out.println("[OK] Auto lerakva sávon: "+ autoSav.getId());
                        break;

                    case "lerak_busz":
                        String buszId = parts[1];
                        String buszSavId = parts[2];
                        int buszPozicio = Integer.parseInt(parts[3]);
                        String buszVegallomasPoiId = parts[4]; //TODO utvonal beállitas

                        Sav kezdoSav = savok.get(buszSavId);
                        Busz b =  new Busz(buszId, buszPozicio,kezdoSav );

                        jk.getForgalomIranyito().addJarmu(b);
                        System.out.println("[OK] Busz lerakva sávon: "+ kezdoSav.getId());
                        break;

                    case "lerak_hokotro":
                        String hokotroId = parts[1];
                        String hokotroSavId = parts[2];
                        int hokotroPozicio = Integer.parseInt(parts[3]);
                        String fejTipus =  parts[4];
                        
                        Hokotro hk = null;
                        switch (fejTipus) {
                            case "sopro":  hk = new Hokotro(hokotroId,new SoproFej(),savok.get(hokotroSavId),hokotroPozicio); break;
                            case "soszoro":  hk = new Hokotro(hokotroId,new Soszoro(),savok.get(hokotroSavId),hokotroPozicio); break;
                            case "hanyo":  hk = new Hokotro(hokotroId,new HanyoFej(),savok.get(hokotroSavId),hokotroPozicio); break;
                            case "jegtoro":  hk = new Hokotro(hokotroId,new JegToro(),savok.get(hokotroSavId),hokotroPozicio); break;
                            case "sarkany":  hk = new Hokotro(hokotroId,new SarkanyFej(),savok.get(hokotroSavId),hokotroPozicio); break;
                            case "zuzalekszoro": hk = new Hokotro(hokotroId,new zuzalekSzoro(),savok.get(hokotroSavId),hokotroPozicio); break;
                        }

                        jk.getForgalomIranyito().addJarmu(hk);
                        System.out.println("[OK] Hokotro lerakva sávon: "+hokotroId+" Fej: "+fejTipus);
                        break;

                    case "utvonal":
                        System.out.println("[INFO] utvonal parancs meg nincs implementalva.");
                        break;

                    case "lep":
                        int n = Integer.parseInt(parts[1]);
                        jk.start();

                        for (int i = 0; i < n; i++) {
                            jk.tick();
                        }
                        break;

                    case "havaz_sav":
                        String savAzon = parts[1];
                        int hoMennyiseg = Integer.parseInt(parts[2]);

                        savok.get(savAzon).hoNovel(hoMennyiseg);
                        System.out.println("[OK] "+ hoMennyiseg + " cm ho elhelyezve savon: " + savAzon);
                        break;

                    case "havaz_globalis":
                        int hoMennyiseg2 = Integer.parseInt(parts[1]);

                        for(Map.Entry<String, Sav> entry: savok.entrySet()) {
                            savok.get(entry.getKey()).hoNovel(hoMennyiseg2);
                        }

                        System.out.println("[OK] " + hoMennyiseg2+ " cm hó elhelyezve minden sávon");
                        break;

                    case "tankol":
                        System.out.println("[INFO] tankol parancs meg nincs implementalva.");
                        break;

                    case "cserel_fej":
                        System.out.println("[INFO] cserel_fej parancs meg nincs implementalva.");
                        break;

                    case "stat":
                        String statId = parts[1];
                        if (statId.equals("kassza")) {
                            System.out.println("[STAT] KASSZA | egyenleg: " + jk.gazdasagKezelo.getKozosKassza() + " | pontszam: " + jk.buszPontszamok);
                        } else if (savok.containsKey(statId)) {
                            Sav s = savok.get(statId);
                            String jegStr = (s.getAllapot() == SavAllapot.JEGPANCEL) ? "igen" : "nem";
                            String zuzalekStr = s.isZuzalekos() ? "igen" : "nem";
                            String blokkoltStr = (s.getAllapot() == SavAllapot.BLOKKOLT) ? "igen" : "nem";
                            
                            System.out.println("[STAT] SAV " + s.getId() + " | ho: " + s.getHoVastagsag() + 
                                               " | jeg: " + jegStr + " | so_ido: " + s.getSozottIdo() + 
                                               " | zuzalek: " + zuzalekStr + " | blokkolt: " + blokkoltStr);
                        } else {
                            if(jk.getForgalomIranyito().ContainsJarmuId(statId)) {
                                Jarmu jarmu = jk.getForgalomIranyito().getJarmu(statId);
                                jarmu.statKiir();
                            }
                            if(poik.containsKey(statId)) {
                                PointOfInterest poi = poik.get(statId);
                                poi.StatKiir();
                            }
                        }
                        break;
                        
                    case "stat_minden":
                        System.out.println("[INFO] stat_minden parancs meg nincs implementalva.");
                        break;

                    case "kilep":
                        System.out.println("[OK] Kilepes");
                        scanner.close();
                        return;

                    default:
                        System.out.println("[HIBA] Ismeretlen parancs: " + command);
                        break;
                }
            } catch (Exception e) {
                System.out.println("[HIBA] Rossz parameterezes a parancsnak: " + line);
            }
        }
        scanner.close();
    }
}