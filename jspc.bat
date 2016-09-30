cd C:\Oracle\Middleware\user_projects\domains\base_domain\bin
call setDomainEnv.cmd
set CLASSPATH=%CLASSPATH%:C:\Oracle\Middleware\wlserver_10.3\server\lib\weblogic.jar
cd autodeploy\ess\WEB-INF\classes
echo ********[Deletion Of Un necessary Files/Directories Started]*********
echo Deleting .\WEB-INF\classes\com Directory
rd /s /q com
echo Deleting .\WEB-INF\classes\jsp_servlet Directory
rd /s /q jsp_servlet
cd..
cd..
echo Deleting .\payImages Directory
rd /s /q payImages
echo Deleting .\payStyles Directory
rd /s /q payStyles
echo Deleting .\payScript Directory
rd /s /q payScript
echo Deleting .\payViews Directory
rd /s /q payViews
echo Deleting .\Documents Directory
rd /s /q Documents
echo Deleting .\database Directory
rd /s /q database
echo Deleting .\cas Directory
rd /s /q cas
echo Deleting .\Deployment\ess.war File
cd Deployment
del ess.war
echo Deleting .\Deployment\pay.war File
del pay.war
cd..
echo ********[Deletion Of Un necessary Files/Directories Completed]*********
java weblogic.jspc -keepgenerated -d .\WEB-INF\classes views\*.jsp secure\*.jsp
ant
pause