import java.util.ArrayList;
import java.util.List;

public class ForgalomIranyito {
    private List<Jarmu> jarmuvek = new ArrayList<>();

    public ForgalomIranyito(String name) {
        Skeleton.ctor(this, name);
    }

    public void addJarmu(Jarmu j) {
        jarmuvek.add(j);
    }

    public void utzarEsemeny(UtvonalTervezo ut) {
        Skeleton.call(this, "utzarEsemeny", "ut");

        for (Jarmu j : jarmuvek) {
            if (j instanceof Auto) {
                ((Auto) j).ujraTervezes(ut);
            }
        }
        
        Skeleton.ret();
    }
}