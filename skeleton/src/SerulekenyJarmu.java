/**
 * A fizikai behatásokra (időjárás, ütközés) érzékeny járművek absztrakt ősosztálya.
 * Ebbe a kategóriába tartoznak az autók és a buszok, amelyek képesek megcsúszni,
 * elakadni a mély hóban, vagy balesetet szenvedni.
 */
public abstract class SerulekenyJarmu extends Jarmu {


    /**
     * Alapértelmezett konstruktor.
     */
    public SerulekenyJarmu() {
        super();
    }

    /**
     * A jármű elakad a mély hóban (sebessége nullára csökken).
     */
    public void elakad() {

    }

    /**
     * A jármű megcsúszik a jégpáncélon, elveszítve az irányítást.
     */
    public void megcsuszik() {

    }

    /**
     * Absztrakt metódus a balesetek kezelésére. 
     * A konkrét leszármazottak (Auto, Busz) döntik el, hogyan reagálnak rá.
     */
    public abstract void balesetezik();
}