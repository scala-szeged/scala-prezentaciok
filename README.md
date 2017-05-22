# Scala előadások

JDK 8 és Scala installálása kell az előadások lejátszásához

Scala -t innen lehet letölteni: https://www.scala-lang.org/download/

A következő utasítások szükségesek a prezentáció elindításához:

cd scala-alapok  

Windows git-bash nélkül: <br>
Scala-alapok.bat  

Linux / OS X / Windows git-bash -al: <br>
./Scala-alapok.sh

Az elindiló Scala Read Eval Print Loop -ban ezeket kell beírni és Enter -t nyomni rá:

val replesent = REPLesent(80, 25, intp=$intp)

import replesent._

f


Ha rosszul jelennek meg az ékezetes karakterek, akkor a git-bash címsorára
kell jobb gombbal kattintani. Ott Options -t választani, azon belül
Text -et és a locale-t hu_hu, ISO-8859-2 -re állítani.
