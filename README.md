Created by Ayush Adhikari, James Darien Widyadi, and Michael William Wijaya


To run the simulation, download all the files in src folder(except the launch.json file). You will need javafx to run the simulation.
Javafx is free and open source, and it can be dowload through their website: https://gluonhq.com/products/javafx/

VS CODE:
To run it VS code, you need dowload javafx(our code was created with javafx-sdk-23.0.2 but other version should work).
Add all the .jar files to your refrence libraries.
Refrence libraries section can be found under "Java Projects" inside vs code right under all your files.
After navigating to refrence libraries, all you have to do is click the "+" icon, look for your javafx/lib folder
in file explorer and select all the .jar files. This will successfully add all the files to your refrence library.

Next step is to create a launch.json file and run the program(Ctrl + F5).
To create a launch.json file, go to Run and Debug, and click the link which promts you to make one.
An example of launch.json is avaiable in the src folder.
Your launch.json file should match ours(the on the src folder).
The only cang you need to make is replace the path to the javafx libirary with yours(wherever your javafx/lib folder is located) in "vmArgs" section.
