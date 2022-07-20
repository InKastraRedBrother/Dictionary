chcp 1251\
del /Q /F target\
pause
rd /S /Q target
xcopy src\main\resources\spring\* target\classes\spring\ /E
xcopy src\main\resources\dictionariesfiles\* target\classes\dictionariesfiles\ /E

pause
javac -encoding utf8 -d .\target\classes\ -cp src\main\resources\spring\* -sourcepath src\main\java src\main\java\dictionary\Main.java
pause
java -Dfile.encoding=UTF8 -cp ".\target\classes\spring\*";.\target\classes dictionary.Main
pause
