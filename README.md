# bme-projlab-2026

Futtatás: Alapvetoen ha uj dolgot irsz akkor ki kell adni
a parancsot a skeleton mappabol, hogy: javac -d bin src\*.java.
(Kb annyit tesz, hogy a -d flaggel megadjuk hogy hova akarjuk
tenni a class fileokat, utana a src\*.java pedig megadja a 
forrasfileok helyet)
Ezt azert igy, mertkulon mappaba tettem a source fileokat es a class fileokat 
az atlathatosag kedveert. Futtatni pedig: java -cp bin Main.
(A -cp argumentum megadja hogy melyik mappaban van a classpath, 
ergo hogy hol vannak a .class fileok)

Innentol intuitivan menni fog, meg persze mondja a program, hogy mikor mit kell csinalni.

Meg csak ket tesztet irtam, egyik az AUTO TISZTA UTON HALAD, a masik pedig
a HOKOTRO SARKANYFEJJEL TAKARIT nevre hallgat.

ps.: Betettem egy .bat filet, amivel windows rendszereken egybol tudjuk futtatni 
a programot, nem kell cmd-be irkalni a java commandokat, eleg csak erre ramenni.
Ez csak windowson mukodik, mac-en vagy linuxon a .sh script kellene hogy mukodjon, viszont 
ezt nem tudom tesztelni, szoval valaki jelezzen majd hogy megy e.
Kilepni a 0-as gomb beutesevel a menuben.