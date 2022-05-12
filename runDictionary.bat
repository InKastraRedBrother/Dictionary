del /Q bin\*.class
pause

javac -encoding utf8 -d .\bin -sourcepath src src\*.java
pause
java -Dfile.encoding=UTF-8 -cp .\bin Main

pause