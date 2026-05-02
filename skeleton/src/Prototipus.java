import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * A Prototípus tesztelési felületét kezelő osztály.
 * Felelőssége a szabványos bemenet olvasása, a modell vezérlése és a kimenet írása.
 */
public class Prototipus {
    private final JatekKezelo jk;
    
    private Map<String, Csomopont> csomopontok = new HashMap<>();
    private Map<String, Sav> savok = new HashMap<>();
    private Map<String, PointOfInterest> poik = new HashMap<>();
    private Map<String, Hokotro> hokotrok = new HashMap<>();
    private Map<String, Busz> buszok = new HashMap<>();
    private Map<String, Auto> autok = new HashMap<>();

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
	
	
	
	/**
     * Egyetlen sornyi parancsot dolgoz fel és hajt végre.
     */
    private boolean egyParancsFeldolgozasa(String line, Scanner scanner) {
        String[] parts = line.split("\\s+");
        String command = parts[0];

        try {
            switch (command) {
                case "load":
                    String fajlNev = parts[1];
                    try {
                        java.io.File file = new java.io.File(fajlNev);
                        Scanner fileScanner = new Scanner(file);
                        while (fileScanner.hasNextLine()) {
                            String fileLine = fileScanner.nextLine().trim();
                            if (!fileLine.isEmpty()) {
                                boolean fajlFolytat = egyParancsFeldolgozasa(fileLine, fileScanner); 
                                if (!fajlFolytat) {
                                    break;
                                }
                            }
                        }
                        fileScanner.close();
                        System.out.println("[OK] Fajl beolvasasa sikeres: " + fajlNev);
                    } catch (java.io.FileNotFoundException e) {
                        System.out.println("[HIBA] Fajl nem talalhato: " + fajlNev);
                    }
                    break;

                case "random":
                    boolean isRandom = parts[1].equals("be");
                    JatekKezelo.veletlenBe = isRandom;
                    System.out.println("[OK] Veletlenszeruseg beallitva: " + parts[1]);
                    break;

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
                    
                    SimaUt utObject = new SimaUt(utId, c1, c2, hossz);
                    jk.getUtvonalTervezo().addUt(utId, utObject);

                    c1.addUtszakasz(utObject);
                    c2.addUtszakasz(utObject);
                    System.out.println("[OK] Ut letrehozva (" + utId + ")");
                    break;

                case "hozzaad_sav":
                    String parentUtId = parts[1];
                    String savId = parts[2];
                    Sav sav = new Sav(savId);
                    
                    Utszakasz ut = jk.getUtvonalTervezo().getUt(parentUtId);
                    if (ut != null) {
                        List<Sav> eddigiSavok = ut.getSavok();
                    
                        if (!eddigiSavok.isEmpty()) {
                            Sav elozoSav = eddigiSavok.get(eddigiSavok.size() - 1);
                            elozoSav.setJobbSav(sav);
                            sav.setBalSav(elozoSav);
                        }
                        ut.addSav(sav); 
                    }
                    
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

                case "uj_poi":
                    String poiId = parts[1];
                    String cspId = parts[2];
                    String poiType = parts[3];
                    switch (poiType) {
                        case "lakas": poik.put(poiId,new Lakas(poiId, null,null)); break;
                        case "munkahely": poik.put(poiId,new Munkahely(poiId,null)); break;
                        case "vegallomas": poik.put(poiId,new Vegallomas(poiId, null, null, null)); break;
                        case "garazs":  poik.put(poiId,new Garazs(poiId, null)); break;
                    }
                    poik.get(poiId).setCsomopont(csomopontok.get(cspId));
                    System.out.println("[OK] Point of Interest letrehozva (" + poiId + ")");
                    break;

                case "lerak_auto":
                    String autoId = parts[1];
                    String autoSavId = parts[2];
                    int autoPozicio = Integer.parseInt(parts[3]);
                    String celPoiId = parts[4];

                    Sav autoSav = savok.get(autoSavId);
                    PointOfInterest autoCel = poik.get(celPoiId);
                    Auto a =  new Auto(autoId, autoPozicio, autoSav, autoCel);
                    autok.put(autoId, a);
                    jk.getForgalomIranyito().addJarmu(a);

                    System.out.println("[OK] Auto lerakva ("+ autoId +")");
                    break;

                case "lerak_busz":
                    String buszId = parts[1];
                    String buszSavId = parts[2];
                    int buszPozicio = Integer.parseInt(parts[3]);
                    String buszVegallomasPoiId = parts[4]; 

                    Sav kezdoSav = savok.get(buszSavId);
                    PointOfInterest celPoi = poik.get(buszVegallomasPoiId);
                    
                    Busz b = new Busz(buszId, buszPozicio, kezdoSav, (Vegallomas) celPoi);

                    buszok.put(buszId, b);
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
                    hokotrok.put(hokotroId, hk);
                    jk.getForgalomIranyito().addJarmu(hk);
                    System.out.println("[OK] Hokotro lerakva sávon: "+hokotroId+" Fej: "+fejTipus);
                    break;

                case "vasarol_hokotro":
                    String ujHkId = parts[1];
                    String garazsId = parts[2];
                    
                    if (poik.containsKey(garazsId) && poik.get(garazsId).getClass().getSimpleName().equals("Garazs")) {
                        Garazs g = (Garazs) poik.get(garazsId);
                        
                        if (jk.gazdasagKezelo != null && jk.gazdasagKezelo.fizetes(1000)) {
                            
                            Csomopont garazsCsp = g.getCsomopont();
                            
                            if(garazsCsp != null && !garazsCsp.getUtszakaszok().isEmpty()) {
                                Sav lerakoSav = garazsCsp.getUtszakaszok().get(0).getSavok().get(0);
                                
                                Hokotro ujHk = new Hokotro(ujHkId, new SoproFej(), lerakoSav, 0);
                                
                                hokotrok.put(ujHkId, ujHk);
                                jk.getForgalomIranyito().addJarmu(ujHk);
                                
                                System.out.println("[OK] Uj hokotro vasarolva: " + ujHkId + " a " + garazsId + " garazsnal");
                                System.out.println("[ESEMENY] KOLTSEGVETES | VASARLAS | egyenleg: " + jk.gazdasagKezelo.getKozosKassza());
                            } else {
                                System.out.println("[HIBA] A garazs csomopontjanak nincs ervenyes utja!");
                            }
                        } else {
                            System.out.println("[HIBA] Nincs eleg penz az uj hokotrora!");
                        }
                    } else {
                        System.out.println("[HIBA] A megadott POI nem letezik vagy nem garazs: " + garazsId);
                    }
                    break;

                case "utvonal":
                    String jarmuId = parts[1];
                    String utvonal = line.substring(line.indexOf(parts[1]) + parts[1].length()).trim();
                    String[] csomopontokIDs = utvonal.split(",\\s*|\\s+");
                    if (buszok.containsKey(jarmuId)){
                        Busz bus = buszok.get(jarmuId);
                        boolean ervenyes = true;
                        Csomopont[] kijeloltUt = new Csomopont[csomopontokIDs.length];
                        for (int i = 0; i < csomopontokIDs.length; i++){
                            if (!csomopontok.containsKey(csomopontokIDs[i])){
                                ervenyes = false;
                                break;
                            }
                            kijeloltUt[i] = csomopontok.get(csomopontokIDs[i]);
                        }
                        if (ervenyes){
                            for (int i = 0; i < kijeloltUt.length - 1; i++){
                                Csomopont akt = kijeloltUt[i];
                                Csomopont kov = kijeloltUt[i + 1];
                                boolean vanKozosUt = false;
                                for (Utszakasz u : akt.getUtszakaszok()){
                                    if (kov.getUtszakaszok().contains(u)){
                                        vanKozosUt = true;
                                        break;
                                    }
                                }
                                if (!vanKozosUt){
                                    ervenyes = false;
                                    break;
                                }
                            }
                        }
                        if (!ervenyes){
                            System.out.println("[HIBA] Ervenytelen utvonal megadva");
                        } else {
                            System.out.println("[OK] Utvonal sikeresen beallitva (" + jarmuId + ")");
                        }
                    } else if (hokotrok.containsKey(jarmuId)){
                        System.out.println("[OK] Utvonal beallitva (" + jarmuId + ")");
                    }
                    break;

                case "lep":
                    int n = Integer.parseInt(parts[1]);
                    if (jk.idojaraskezelo != null){
                        jk.idojaraskezelo.setOsszesSav(new ArrayList<>(savok.values()));
                    }
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

                    System.out.println("[OK] " + hoMennyiseg2 + " cm hó elhelyezve minden sávon");
                    break;

                case "tankol":
                    String tankol_jarmuId = parts[1];
                    String anyag = parts[2];
                    if (hokotrok.containsKey(tankol_jarmuId)){
                        Hokotro tankol_kotro = hokotrok.get(tankol_jarmuId);
                        int fizetendo = 0;
                        switch (anyag){
                            case "so": fizetendo = 150; break;
                            case "biokerozin": fizetendo = 300; break;
                            case "zuzalek": fizetendo = 150; break;
                        }
                        if (fizetendo > 0 && jk.gazdasagKezelo != null && jk.gazdasagKezelo.fizetes(fizetendo)){
                            if (anyag.equals("so")){
                                tankol_kotro.setSo(100);
                                System.out.println("[OK] " + tankol_jarmuId + " tankolas sikeres (uj so: 100)");
                            } else if (anyag.equals("biokerozin")){
                                tankol_kotro.setBiokerozin(100);
                                System.out.println("[OK] " + tankol_jarmuId + " tankolas sikeres (uj biokerozin: 100)");
                            } else if (anyag.equals("zuzalek")){
                                tankol_kotro.setZuzalek(100);
                                System.out.println("[OK] " + tankol_jarmuId + " tankolas sikeres (uj zuzalek: 100)");
                            }
                            System.out.println("[ESEMENY] KOLTSEGVETES | VASARLAS | egyenleg: " + jk.gazdasagKezelo.getKozosKassza());
                        } else {
                            System.out.println("[HIBA] Sikertelen tankolas (nincs eleg penz vagy ismeretlen anyag)");
                        }
                    }
                    break;

                case "cserel_fej":
                    String jarmuID = parts[1];
                    String ujFej = parts[2];
                    if (hokotrok.containsKey(jarmuID)){
                        Hokotro kotro = hokotrok.get(jarmuID);
                        KotroFej kFej = null;
                        switch (ujFej) {
                            case "sopro": kFej = new SoproFej(); break;
                            case "soszoro": kFej = new Soszoro(); break;
                            case "hanyo": kFej = new HanyoFej(); break;
                            case "jegtoro": kFej = new JegToro(); break;
                            case "sarkany": kFej = new SarkanyFej(); break;
                            case "zuzalekszoro": kFej = new zuzalekSzoro(); break;
                        }
                        if (kFej != null && jk.gazdasagKezelo != null){
                            int ar = kFej.getAr();
                            if (jk.gazdasagKezelo.fizetes(ar)){
                                kotro.setKotrofej(kFej);
                                System.out.println("[OK] " + jarmuID + " fejcsere sikeres (uj fej: " + ujFej + ")");
                                System.out.println("[ESEMENY] KOLTSEGVETES | VASARLAS | egyenleg: " + jk.gazdasagKezelo.getKozosKassza());
                            } else {
                                System.out.println("[HIBA] Nincs eleg penz a fejcserehez");
                            }
                        }
                    }
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
                    if (savok.values().isEmpty() || autok.values().isEmpty() || hokotrok.values().isEmpty() || buszok.values().isEmpty()){
                        break;
                    }
                    for (Map.Entry<String,Sav> entry : savok.entrySet()){
                        Sav s = entry.getValue();
                        String jegStr = (s.getAllapot() == SavAllapot.JEGPANCEL) ? "igen" : "nem";
                        String zuzalekStr = s.isZuzalekos() ? "igen" : "nem";
                        String blokkoltStr = (s.getAllapot() == SavAllapot.BLOKKOLT) ? "igen" : "nem";
                        System.out.println("[STAT] SAV " + s.getId() + " | ho: " + s.getHoVastagsag() + " | jeg: " + jegStr + "so_ido" + s.getSozottIdo() + " | zuzalek: " + zuzalekStr + " | blokkolt: " + blokkoltStr);
                    }
                    for (Auto stat_auto : autok.values()){
                        stat_auto.statKiir();
                    }
                    for (Busz stat_buszok : buszok.values()){
                        stat_buszok.statKiir();
                    }
                    for (Hokotro stat_hokotrok : hokotrok.values()){
                        stat_hokotrok.statKiir();
                    }
                    break;

                case "kilep":
                    System.out.println("[OK] Kilepes");
                    return false;

                default:
                    System.out.println("[HIBA] Ismeretlen parancs: " + command);
                    break;
            }
        } catch (Exception e) {
            System.out.println("[HIBA] Rossz parameterezes a parancsnak: " + line);
            e.printStackTrace();
        }
		return true;
    }
	 
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bemenet:");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            egyParancsFeldolgozasa(line, scanner);
        }
        scanner.close();
    }
}