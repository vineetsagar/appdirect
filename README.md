# appdirect
App Direct challenge : Created this application to demonstrate how you can integrate AppDirect APIs to your own web apps. 

Prerequisite:
Apache tomcat server(6.0)
Apache ant (1.9.7) for building project
JRE 1.7 

Install:
Checkout/clone the project 
Go to project directory and perform the following steps
open conf.properties file
change consumer key (as configured in appdirect)
change consumer secret(as configured in appdirect)
change login success url (change only domain part)
  Run command "ant -f build.xml clean war"
  Copy the app.war from  folder target and deploy it on tomcat server
  
 AppDirect APIs integrated
  Create subscription
  Change subscription
  Cancel subscription
 
 App Direct Configuration
 Create subscription URL /app/notification/create?eventUrl={eventUrl}
 Change subscription URL /app/notification/change?eventUrl={eventUrl}
 Cancel subscription URL /app/notification/cancel?eventUrl={eventUrl}
 Login URL /app/login?openid_url={openid}
  
 Additionally, 
 	1 Added servlet filter for verifying the oAuth signature for the request coming from AppDirect server.
 	2 Added support of openId login
 
 Important Libraries:
jersy-oauth & oauth-signature: for verifying oauth signature 
 signpost-core: for signing HTTP GET request
 
 Additional notes. App direct will not work in localhost mode. You need to host your applicatin on internet.			
