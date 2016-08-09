# appdirect
App Direct challenge : Created this applicaion to demonstrate how you can integrate AppDirect APIs to your own web apps. 

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
  run command "ant -f build.xml clean war"
  copy the app.war from  folder target and deploy it on tomcat server
  
 AppDirect APIs integrated
 
  1 Create subscription
  2 Change subscription
  3 Cancel subscription
 
 App Direct Configuration
 	1 Create subscription URL /app/notification/create?eventUrl={eventUrl}
  2 Change subscription URL /app/notification/change?eventUrl={eventUrl}
  3 Cancel subscription URL /app/notification/cancel?eventUrl={eventUrl}
  4 Login URL /app/login?openid_url={openid}
  
 Additonally, 
 	1 Added servlet filter for verfiying the oauth signature for the request coming from AppDirect server.
 	2 Added support of openId login
 
 Important Libaries:
 			jersy-oauth & oauth-signature: for verifying oauth signature 
 			signpost-core: for signing HTTP GET request
 
 Additional notes. App direct will not work in localhost mode. You need to host your applicatin on internet.			

