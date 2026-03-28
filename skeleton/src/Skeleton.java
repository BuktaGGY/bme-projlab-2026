import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Skeleton {
    // Objektumok neveit tarolo Map
    private static Map<Object, String> objects = new HashMap<>();
    
    // Behuzas (tabulatorok) melysege
    private static int depth = 0;
    
    // Kozos Scanner a beolvasashoz
    public static Scanner scanner = new Scanner(System.in);

    // 1. Objektum regisztralasa
    public static void ctor(Object obj, String name) {
        objects.put(obj, name);
    }

    // 2. Fuggvenyhivas kezdete (tobb parametert is varhat: param1, param2...)
    public static void call(Object caller, String methodName, String... params) {
        String objectName = objects.getOrDefault(caller, "ismeretlen");
        String paramStr = String.join(", ", params);
        
        System.out.println(getIndent() + objectName + "." + methodName + "(" + paramStr + ")");
        depth++; // Beljebb lepunk
    }

    // 3. Fuggvenyhivas vege (visszateresi ertekkel)
    public static void ret(String returnValue) {
        depth--; // Kijjebb lepunk
        System.out.println(getIndent() + "<- " + returnValue);
    }
	// 3.B Fuggvenyhivas vege (ha nincs visszateresi ertek)
    public static void ret() {
        depth--; // Kijjebb lepunk
        System.out.println(getIndent() + "<- void");
    }

    // 4. Kerdes feltevese a felhasznalonak
    public static int askQuestion(String question, String... answers) {
        System.out.println(getIndent() + "KERDES: " + question);
        for (int i = 0; i < answers.length; i++) {
            System.out.println(getIndent() + (i + 1) + ". VALASZ: " + answers[i]);
        }
        
        int choice = -1;
        while (true) {
            System.out.print(getIndent() + "Valasztott opcio : ");
            try {
                String input = scanner.nextLine();
                choice = Integer.parseInt(input.trim());
                if (choice >= 1 && choice <= answers.length) {
                    System.out.println(); // Ures sor a formazas vegett
                    return choice;
                }
            } catch (NumberFormatException e) {
                // Ha nem szamot adott meg, a ciklus ujraindul
            }
            // Csak akkor jutunk ide, ha rossz volt az input
            System.out.println(getIndent() + "Helytelen sorszam, probalja ujra!");
        }
    }

    // Varakozas a teszt vegen
    public static void pause() {
        System.out.println("A teszteset futasa befejezodott. Ha vissza szeretne lepni a tesztesetek listajahoz, akkor nyomjon le egy billentyut!");
        scanner.nextLine(); // Var egy Enter-re (barmilyen inputra)
    }

    // Segedfuggveny a tabulatorokhoz
    private static String getIndent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("\t"); // Tabulatort hasznalunk
        }
        return sb.toString();
    }
	
	public static String getName(Object obj) {
        if (obj == null) return "null";
        return objects.getOrDefault(obj, obj.toString());
    }
}