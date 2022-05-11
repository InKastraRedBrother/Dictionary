del /Q bin\*.class
pause

javac -d .\bin -sourcepath src src\*.java
pause
java -cp .\bin Main
pause