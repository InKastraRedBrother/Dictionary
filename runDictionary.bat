chcp 1251
del /Q .\out\production\Dictionary.Dictionary\*.class
pause
javac -encoding utf8 -d .\out\production\Dictionary -sourcepath src src\Dictionary\*.java
pause
java -Dfile.encoding=UTF8 -cp .\out\production\Dictionary Dictionary.Main
pause