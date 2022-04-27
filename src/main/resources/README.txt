to run the program click the run.bat Read the text in the terminal! it has usefull information. (if you get weird text message me, something went wrong!) 

in the images folder you should place images and a json file with the same name 
e.g. "H1 head.png" should have a "H1 head.json" file.

template.json is a file you can easily copy and change the values of. (notepad++ or vscode will edit json nicely)


it currently supports:

----- depth: ----- 
depth is the layer depth. lower number means that it is lower in the stack. e.g. background can be 0.0 and the head can be 1.0
if there is more images with the same depth one is picked at random.

the depths are floating point numbers eg 1.5 this makes it always posible to add a depth in between other layers. 
e.g. if the eyes are at 1.7 and the ears at 1.8 you can still add eyeshadow at 1.75