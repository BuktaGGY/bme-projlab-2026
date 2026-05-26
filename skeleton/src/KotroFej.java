public abstract class KotroFej {
    protected int ar;

    public KotroFej(int ar) {
        this.ar = ar;
    }
	
    public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }

    public abstract String getFejTipus();

	/**
     * A metódus a hókotróra felszerelt aktuális kotrófej
     * specifikus hó- és jégeltakarítási algoritmusát hajtja
     * végre az adott útszakaszon.
     * */
    public abstract void takarit(Sav sav, Hokotro kotro);
}