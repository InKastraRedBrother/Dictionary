del /Q /F C:\Java\apache-tomcat-9.0.65\webapps\Dictionary-1.0.war
set "CATALINA_HOME=C:\Java\apache-tomcat-9.0.65"
set "JAVA_HOME=C:\Java\jdk-11.0.2"
call %CATALINA_HOME%\bin\shutdown.bat  run
pause
call mvn clean package
pause
xcopy target\Dictionary-1.0.war C:\Java\apache-tomcat-9.0.65\webapps\
pause
call %CATALINA_HOME%\bin\catalina.bat run
pause