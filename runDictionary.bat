chcp 1251
del /Q .\out\production\Dictionary\*.class
pause
javac -encoding utf8 -d .\out\production\Dictionary -sourcepath src src\*.java
pause
java -Dfile.encoding=UTF8 -cp .\out\production\Dictionary Main
pause