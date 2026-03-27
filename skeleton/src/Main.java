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
        
        tests.add(new TestCase() {
            public String getName() { return "Auto tiszta uton halad"; }
            public void run() { testAutoTisztaUton(); }
        });
        
        tests.add(new TestCase() {
            public String getName() { return "Hokotro Sarkanyfejjel takarit"; }
            public void run() { testSarkanyFej(); }
        });

        tests.add(new TestCase(){
            public String getName() {
                return "Kornyezeti Havazas";
            }
            public void run(){
                testKornyezetiHavazas();
            }
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
}