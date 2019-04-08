# README #

# Chess Osv#


**Description:**  

  Created a web service using JavaOpenChess API, which is integrated with unikernel OSv and Containerised with Docker seperately. It is then deployed on AWS and in dockerHub respectively.

**Project Structure:** 

**Classes Overview**: 

  Using JavaOpenChess as base. Created wrapper classes (Total 7 classes: 4 Java Beans, 1 SpringBoot app, 1 Rest controller and 1 class with only static variables)
  3 Wrappers:
  
   - localhost:8080/newgame
   - localhost:8080/move
   - localhost:8080/quitgame
   
   Params in the Servlet_RestController class
    
    
**Explain/Notes:**

    - Build wrapper classes to use chess api as a spring boot rest service. Used gradle built to make Jar files.
    - Jar file is located in build/lib folder
    - Used ubuntu to build the OSV image ( initially tried with windows but ended up using VMWARE to boot linux and build from due after alot of workarounds)
    - Installed dockerHub and created image of the application. Deployed on docker hub (id: smaith2)
    - The code is properly commnted and provided relevant test cases as well for the wrapper functions.
    - The project can be executed either via IntelliJ or Grable locally.(gradle clean build)
    - OSV image in path
   

 
 **Deployment on AWS**
     
     YouTube Link: https://youtu.be/j1eikZawmz8
 
 **Docker Deployment**
 
 - Deployed image on docker: 
 
    - docker pull smaith2/shanks-chess
    - docker run -p 8080:8080 shanks-chess
