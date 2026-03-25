public abstract class KotroFej {
    protected int ar;

    public KotroFej(int ar) {
        this.ar = ar;
    }
	
	//getter, setter
    public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }


	/*A metódus a hókotróra felszerelt aktuális kotrófej
	specifikus hó- és jégeltakarítási algoritmusát hajtja
	végre az adott útszakaszon.
	A függvény polimorf módon működik: a pontos viselkedése
	a csatlakoztatott fej típusától függ (pl. a sószóró sót
	szór, a jégtörő jeget tör, a söprőfej pedig a szomszédos
	sávra tolja a havat).*/
    public abstract void takarit(Sav sav, Sav szomszedosSav, Hokotro kotro);
}