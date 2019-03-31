# README #

# Cloud CS441 - Homework 4 #


**Description:**  
Created a web service using JavaOpenChess API, which is integrated with unikernel OSv and Containerised with Docker seperately. It is then deployed on AWS and in dockerHub respectively.

**Project Structure:** 

- **Classes Overview**: Using JavaOpenChess as base. Created wrapper classes (Total 7 classes: 4 Java Beans, 1 SpringBoot app, 1 Rest controller and 1 class having static methods)
    
    
**Explain/Notes:**

    - Build wrapper classes to use chess api as a spring boot rest service. Used gradle built to make Jar files.
    - Jar file is located in build/lib folder
    - Used ubuntu to build the OSV image ( initially tried with windows but ended up using VMWARE to boot linux and build from due after alot of workarounds)
    - Installed dockerHub and created image of the application. Deployed on docker hub (id: smaith2)
    - The code is properly commnted and provided relevant test cases as well for the wrapper functions.
    - The project can be executed either via IntelliJ or Grable locally.(gradle clean build)
    - 

 
 **OSV Deployment on AWS**
     
     YouTube Link:
 
 **Docker Deployment**
 
 - Deployed image on docker: docker pull smaith2/shanks-chess
 
 
 **Bonus:**
    Spend alot of time on working around Osv and environment setup.
 

 PS: spent most of the time in setting up wrappers. Thought it was supposed to be the hard part but then OSv was not buillding image because of the swing framework in the JavaOpenChess. So decoupled those classes in the source and then build image. Those roadblocks took alot of time.
 In hindsight should have dual booted my system with linux (more than 60% of the effort was spent on setting up VBox, then VMware, then Capstan(enabling virtualization etc.)
 
That's It ! :) 