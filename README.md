# bme-projlab-2026


Fordítás és futtatás automatikusan:
1. Lépjen be a skeleton nevű mappába.
2. Windows rendszeren kattintson duplán run_gui nevű bat fájlra.
Mac OS / Linux rendszeren nyisson egy terminált a mappában, és futtassa a ./run_gui.sh paranccsal
3. Ezt követően a program fordítása és futtatása megtörténik, a kívánt teszt sorszámának megadásával a teszt lefutása megtörténik.

Fordítás manuálisan:
1. Lépjen be a skeleton nevű mappába.
2. Az üres részen nyomjon jobb klikket, majd válassza a Megnyitás a terminálban lehetőséget.
3. írja be, hogy javac -encoding UTF-8 -d bin src\*.java majd nyomjon entert.
4. Ezt követően a program fordítása megtörténik, létrejön a bin mappa a benne lévő .class fájlokkal és a program futtathatóvá válik.

Futtatás manuálisan:
1. Ha a fordítást követően nem zárta be a parancssort, akkor az használható a futtatáshoz is. Amennyiben bezárta a fordításban leírt módon nyisson egy újat a skeleton nevű mappában.
2. A parancssorba írja be, hogy java -cp bin Main
3. Ezt követően a program elindul, a kívánt teszt sorszámának megadásával a teszt lefutása megtörténik.