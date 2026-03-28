public class Busz extends SerulekenyJarmu implements IranyitottJarmu {

    private Csomopont kezdoAllomas;
    private Csomopont vegAllomas;
    private int blokkoltSzamlalo;


    /**
     *
     */
    @Override
    public void balesetezik() {
        Skeleton.call(this, "balesetezik");
        Skeleton.ret();
    }

    /**
     *
     */
    @Override
    public void frissitAllapot() {
        Skeleton.call(this, "frissitAllapot");

        if(blokkoltSzamlalo > 0 ){
            blokkoltSzamlalo--;
        }
        Skeleton.ret();
    }

    public void setBlokk(int b){
        blokkoltSzamlalo=b;
    }

    public void megfordul(){
        Skeleton.call(this, "megfordul");

        //vegAllomas.addErintes();
        Csomopont temp = kezdoAllomas;
        kezdoAllomas = vegAllomas;
        vegAllomas = temp;

        Skeleton.ret();
    }

    /**
     * @param ujUtvonal Egy Utszakasz objektumokból álló tömb.
     */
    @Override
    public void UtvonalatKijelol(Utszakasz[] ujUtvonal) {
        Skeleton.call(this, "utvonalatKijelol");

        this.Utvonal = ujUtvonal;

        Skeleton.ret();
    }
}
