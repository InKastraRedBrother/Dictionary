chcp 1251
del /Q .\out\production\Dictionary\dictionary\*.class
pause
javac -encoding utf8 -d .\out\production\Dictionary -sourcepath src\main\java src\main\java\dictionary\Main.java
pause
java -Dfile.encoding=UTF8 -cp .\out\production\Dictionary\ dictionary.Main
pause