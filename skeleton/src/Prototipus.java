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
    private Map<String, Jarmu> jarmuvek = new HashMap<>();

    // Ideiglenes tároló a hókotróknak
    private Map<String, Hokotro> hokotrok = new HashMap<>();

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

                //innentol meg nem keszek

                    case "uj_poi":
                        String poiId = parts[1];
                        String cspId = parts[2];

                        String poiName = parts[3];
                        switch (poiName) {
                            case "lakas": poik.put(poiId,new Lakas("Lakas")); break;
                            case "munkahely": poik.put(poiId,new Lakas("Munkahely")); break;
                            case "vegallomas": poik.put(poiId,new Lakas("Vegallomas")); break;
                            case "garazs":  poik.put(poiId,new Lakas("Garazs")); break;
                        }
                        poik.get(poiId).setCsomopont(csomopontok.get(cspId));

                        System.out.println("[OK] Point of Interest letrehozva (" + poiName + ")");
                        break;

                    case "lerak_auto":
                        System.out.println("[INFO] lerak_auto parancs meg nincs implementalva.");
                        break;

                    case "lerak_busz":
                        System.out.println("[INFO] lerak_busz parancs meg nincs implementalva.");
                        break;

                    case "lerak_hokotro":
                        System.out.println("[INFO] lerak_hokotro parancs meg nincs implementalva.");
                        break;

                    case "utvonal":
                        System.out.println("[INFO] utvonal parancs meg nincs implementalva.");
                        break;

                    case "lep":
                        int n = Integer.parseInt(parts[1]);
                        for (int i = 0; i < n; i++) {
                            jk.tick();
                        }
                        break;

                    case "havaz_sav":
                        System.out.println("[INFO] havaz_sav parancs meg nincs implementalva.");
                        break;

                    case "havaz_globalis":
                        System.out.println("[INFO] havaz_globalis parancs meg nincs implementalva.");
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
                            // TODO: Járművek és POI-k statisztikájának kiírása
                            System.out.println("[INFO] stat parancs ehhez az objektumhoz meg nincs implementalva: " + statId);
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