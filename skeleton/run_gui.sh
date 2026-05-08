#!/bin/sh
javac -encoding UTF-8 -d bin src/*.java
java -cp bin GrafikusMain
