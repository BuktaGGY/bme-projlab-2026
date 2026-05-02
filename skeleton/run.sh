#!/bin/bash
javac -d bin src/*.java
java -cp bin Main
read -p "Nyomj egy Enter-t a bezarashoz..."