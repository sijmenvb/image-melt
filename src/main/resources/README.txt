to run the program click the run.bat Read the text in the terminal! it has useful information. (if you get weird text message me, something went wrong!) 

in the images folder you should place images and a json file with the same name 
e.g. "H1 head.png" should have a "H1 head.json" file.
Note: feel free to use as many sub folders. the program will treat it as if they are all in one file.

template.json is a file you can easily copy and change the values of. (notepad++ or vscode will edit json nicely)

when updating make sure to delete the run.dat file! (and double click the .jar to generate a new one.)

it currently supports:

----- depth: ----- 
depth is the layer depth. lower number means that it is lower in the stack. e.g. background can be 0.0 and the head can be 1.0
if there is more images with the same depth one is picked at random.

the depths are floating point numbers e.g. 1.5 this makes it always possible to add a depth in between other layers. 
e.g. if the eyes are at 1.7 and the ears at 1.8 you can still add eye shadow at 1.75