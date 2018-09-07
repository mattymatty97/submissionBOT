this is a readme file to list how to make it working :smirk:

first download the entire root folder onto your pc
second :
 - windows: 
    make gradlew.bat executable
    run "./gradlew.bat stage"
    
 - linux/IOS:
    make gradlew executable
    run "./gradlew stage"
    
third:
a folder called "build"  should be appeared
in it you'll find a "libs" folder
there is a "submissionBOT...-all.jar" copy this file

put in the folder where you want to run it

 -windows: 
     create a file in .BAT format
     write in it:
     "SET BOT_TOKEN=" and add your bot token .... than a ";"
     "java -jar " and put .jar file name .... and a ";"
     save all and run it , enjoi
     
 -linux:
    create a file in .sh format
    write in it:
    "#!/bin/bash"
    "export BOT_TOKEN=" and add your bot token
    "java -jar " and put .jar file name
    save all and run it, enjoi
    
 all submitted files will be saved into "files" folder
 while the submission log will be saved into "log.txt" file
 
 --------------------------------------------------------------------------------------
 
 linux example:

 #!/bin/bash
 export BOT_TOKEN=adrrf.aaewre.32445234sdfweDWEDgewCCEW
 java -jar ./submissionBot-1.0-all.jar
 
 
 windows example:

 SET BOT_TOKEN=adrrf.aaewre.32445234sdfweDWEDgewCCEW
 java -jar ./submissionBot-1.0-all.jar
