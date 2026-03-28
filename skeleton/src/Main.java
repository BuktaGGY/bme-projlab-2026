import java.util.ArrayList;
import java.util.List;

public class Main {

    // Egy egyszeru interfesz a tesztesetek tarolasara
    interface TestCase {
        String getName();
        void run();
    }

    public static void main(String[] args) {
        List<TestCase> tests = new ArrayList<>();
        
        // --- TESZTESETEK REGISZTRALASA ---
        /*
        tests.add(new TestCase() {
            public String getName() { return "Auto tiszta uton halad"; }
            public void run() { testAutoTisztaUton(); }
        });
        
        tests.add(new TestCase() {
            public String getName() { return "Hokotro Sarkanyfejjel takarit"; }
            public void run() { testSarkanyFej(); }
        });*/

        tests.add(new TestCase(){
            public String getName() {
                return "Kornyezeti Havazas";
            }
            public void run(){
                testKornyezetiHavazas();
            }
        });
		
		tests.add(new TestCase() {
            public String getName() { return "Hokotro Soprofejjel takarit"; }
            public void run() { testSoproFej(); }
        });
        
        tests.add(new TestCase() {
            public String getName() { return "Hokotro Hanyofejjel takarit"; }
            public void run() { testHanyoFej(); }
        });

        tests.add(new TestCase() {
            public String getName() { return "Hokotro Jegtorovel jegpancelt takarit"; }
            public void run() { testJegToro(); }
        });

        tests.add(new TestCase() {
            public String getName() { return "Hokotro Sarkanyfejjel havat/jeget olvaszt"; }
            public void run() { testSarkanyFej(); }
        });

        tests.add(new TestCase() {
            public String getName() { return "Hokotro Soszoroval besozza az aktualis savot"; }
            public void run() { testSoszoro(); }
        });

        tests.add(new TestCase() {
            public String getName() { return "Jarmu letapossa a savot"; }
            public void run() { testLetapos(); }
        });

        tests.add(new TestCase() {
            public String getName() { return "Auto iranyt valt"; }
            public void run() { testAutoIranytValt(); }
        });

        // Ide johet majd a tobbi (m. TEST) ...

        // --- MENU CIKLUS ---
        while (true) {
            System.out.println("\n--- TESZTESETEK LISTAJA ---");
            for (int i = 0; i < tests.size(); i++) {
                System.out.println((i + 1) + ". TEST: " + tests.get(i).getName());
            }
            System.out.println("0. KILEPES");
            
            System.out.print("\nAdja meg a teszteset sorszamat: ");
            String input = Skeleton.scanner.nextLine();
            
            try {
                int choice = Integer.parseInt(input.trim());
                if (choice == 0) {
                    System.out.println("Kilepes...");
                    break;
                }
                
                if (choice >= 1 && choice <= tests.size()) {
                    System.out.println("\n" + choice + ". TEST: " + tests.get(choice - 1).getName());
                    
                    // A kivalasztott teszt lefuttatasa
                    tests.get(choice - 1).run();
                    
                    // Varakozas a teszt vegen
                    Skeleton.pause();
                } else {
                    System.out.println("Nincs ilyen sorszamu teszteset. Probalja ujra!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ervenytelen input! Kerem egy szamot adjon meg.");
            }
        }
    }

    // ==========================================
    // TESZTESETEK
    // ==========================================
	
    /*
    private static void testAutoTisztaUton() {
        // 1. Inicializalas
        Object f = new Object(); Skeleton.ctor(f, "f");
        Object a = new Object(); Skeleton.ctor(a, "a");
        Object s = new Object(); Skeleton.ctor(s, "s");

        // 2. Futas
        Skeleton.call(f, "mozgatJarmuvek");
        
        Skeleton.call(a, "frissitAllapot");
        
        Skeleton.call(a, "mozog");
        
        Skeleton.call(s, "letapos", "a");
        Skeleton.ret("void");
        
        Skeleton.ret("void");
        
        Skeleton.ret("void");
        
        Skeleton.ret("void");
    }


    private static void testSarkanyFej() {
        // 1. Inicializalas
        Object h = new Object(); Skeleton.ctor(h, "h");
        Object sf = new Object(); Skeleton.ctor(sf, "sf");
        Object s = new Object(); Skeleton.ctor(s, "s");

        // 2. Futas
        Skeleton.call(h, "frissitAllapot");
        
        int answer = Skeleton.askQuestion("Van elegendo biokerozin a hokotroban?", "Igen", "Nem");
        
        if (answer == 1) { 
            Skeleton.call(sf, "takarit", "aktualisSav", "h");
            
            Skeleton.call(s, "mindentEltuntet");
            Skeleton.ret("void");
            
            Skeleton.ret("void");
        }
        
        Skeleton.ret("void");
    }
	*/

    // Ezt csak en random probalgattam, nem hiszem hogy jo 
    private static void testKornyezetiHavazas(){
        Object ik = new Object();
        Skeleton.ctor(ik, "ik");
        Sav s = new Sav("s");
        int answer = Skeleton.askQuestion("Van so a savon?", "Igen", "Nem");
        if (answer == 2){
            s.getAllapot();
            Skeleton.ret("HAVAS");
            s.hoNovel(10);
        } else {
            s.getAllapot();
            Skeleton.ret("SOZOTT");
        }
        Skeleton.ret("void");
    }

    public static void testAutoIranytValt() {
    UtvonalTervezo ut = new UtvonalTervezo("ut");
    ForgalomIranyito fi = new ForgalomIranyito("fi");
    Auto a1 = new Auto("a1");

    ut.setForgalomIranyito(fi);
    fi.addJarmu(a1);

    ut.utzarDetektal();
    }
	
	private static void testSoproFej() {
        // 1. Inicializalas (Palyakep felépitese)
        Sav s1 = new Sav("s1");
        Sav s2 = new Sav("s2");
        Hokotro h = new Hokotro("h");
        SoproFej sf = new SoproFej("sf");

        System.out.println("\n--- Szinpad felepitese ---");
        s1.setJobbSav(s2);
        s1.hoNovel(20);

        System.out.println("\n--- Teszt futasa ---");
        // Itt mar a valodi fuggvenyt hivjuk!
        sf.takarit(s1, h);
    }

    private static void testHanyoFej() {
        Sav s1 = new Sav("s1");
        Hokotro h = new Hokotro("h");
        HanyoFej hf = new HanyoFej("hf");

        System.out.println("\n--- Szinpad felepitese ---");
        s1.hoNovel(30);

        System.out.println("\n--- Teszt futasa ---");
        hf.takarit(s1, h);
    }

    private static void testJegToro() {
        Sav s1 = new Sav("s1");
        Hokotro h = new Hokotro("h");
        JegToro jt = new JegToro("jt");

        System.out.println("\n--- Szinpad felepitese ---");
        s1.setSavAllapot(SavAllapot.JEGPANCEL);

        System.out.println("\n--- Teszt futasa ---");
        jt.takarit(s1, h);
    }

    private static void testSarkanyFej() {
        Sav s1 = new Sav("s1");
        Hokotro h = new Hokotro("h");
        SarkanyFej sf = new SarkanyFej("sf");

        System.out.println("\n--- Szinpad felepitese ---");
        s1.hoNovel(50);
        
        // Interaktiv resz
        int answer = Skeleton.askQuestion("Van elegendo biokerozin a hokotroban?", "Igen", "Nem");
        if (answer == 1) {
            h.setBiokerozin(100);
        } else {
            h.setBiokerozin(0);
        }

        System.out.println("\n--- Teszt futasa ---");
        sf.takarit(s1, h);
    }

    private static void testSoszoro() {
        Sav s1 = new Sav("s1");
        Hokotro h = new Hokotro("h");
        Soszoro sz = new Soszoro("sz");

        System.out.println("\n--- Szinpad felepitese ---");
        s1.setSavAllapot(SavAllapot.JEGPANCEL);

        // Interaktiv resz
        int answer = Skeleton.askQuestion("Van elegendo so a hokotroban?", "Igen", "Nem");
        if (answer == 1) {
            h.setSo(50);
        } else {
            h.setSo(0); // Elfogyott a so
        }

        System.out.println("\n--- Teszt futasa ---");
        sz.takarit(s1, h);
    }

    private static void testLetapos() {
        Sav s1 = new Sav("s1");
        Auto a = new Auto("a");

        System.out.println("\n--- Szinpad felepitese ---");
        s1.hoNovel(10);

        System.out.println("\n--- Teszt futasa ---");
        s1.letapos(a);
    }
}