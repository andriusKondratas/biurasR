* Maven (3.2.3 - 3.3.3) - http://maven.apache.org/download.html###, #profiles
  * dev1 - Andrius Kondratas
  * dev2 - Modestas Liutkus

* Setup
  * Deploy to local tomact instance : `cd ${project_homedir}` - > `mvn tomcat7:redeploy -Pdev1`
  * In order profiles to work - copy `settings.xml` to `${maven_homedir}\.m2`
  * Before deploy to any env., select respected profile. Fx. when running locally use your profile.
  * install oracle driver into repository - http://www.oracle.com/technetwork/database/features/jdbc/jdbc-drivers-12c-download-1958347.html `mvn install:install-file -Dfile=${jar_locationdir}\ojdbc7.jar -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.1.0 -Dpackaging=jar`  

* Eclipse
  * jre - some release of 1.8 stack
  * compiler - compliance level - 1.8
  * code format - UTF8 resource files encoding, 160 line length, indentation - tabs

* DB Tips
  * Usually when DB structure changes you need to recreate tables
  * Fastest way to do it is with sys user: `drop user X cascade`
  * Create user before initial setup script
```
create user X identified by "X";
grant CREATE SESSION, ALTER SESSION, CREATE DATABASE LINK, 
  CREATE MATERIALIZED VIEW, CREATE PROCEDURE, CREATE PUBLIC SYNONYM, 
  CREATE ROLE, CREATE SEQUENCE, CREATE SYNONYM, CREATE TABLE,  
  CREATE TRIGGER, CREATE TYPE, CREATE VIEW, UNLIMITED TABLESPACE 
  to X;
```  
 * Just in case you got stuck with drop
```
SELECT SID, SERIAL#, STATUS
  FROM V$SESSION
  WHERE USERNAME = 'X'; 
  
ALTER SYSTEM KILL SESSION 'SID,SERIAL';    
```
