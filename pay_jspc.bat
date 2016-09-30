cd C:\Oracle\Middleware\user_projects\domains\base_domain\bin
call setDomainEnv.cmd
set CLASSPATH=%CLASSPATH%:C:\Oracle\Middleware\wlserver_10.3\server\lib\weblogic.jar
cd autodeploy\pay\WEB-INF\classes
echo ********[Deletion Of Un necessary Files/Directories Started]*********
echo Deleting .\WEB-INF\classes\com Directory
rd /s /q com
echo Deleting .\WEB-INF\classes\jsp_servlet Directory
rd /s /q jsp_servlet
cd..
cd..
echo Deleting .\images Directory
rd /s /q images
echo Deleting .\styles Directory
rd /s /q styles
echo Deleting .\script Directory
rd /s /q script
echo Deleting .\views Directory
rd /s /q views
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
java weblogic.jspc -keepgenerated -d .\WEB-INF\classes payViews\*.jsp secure\*.jsp
ant
pause