The project RationalNumberRepresentations is meant to display any rational number in any base in either fraction form or repeating decimal form. 

The user will input a rational number as a fraction or a repeating decimal and the input can be in any base and 
	the output will be the same rational number in a base of the user's choosing.

It uses Java BigIntegers to represent the numbers, so instead of the integer size limit, the size limit is 2 to the power 2 billion. 

The directory Numbers/src/appletUI/MainApplet.java contains the swing applet from which this program can be run. 

The directory Numbers/src consists of the following packages:
	- appletUI: This contains the java class in which the applet UI sits. It obtains the input strings for the numbers
				and then shows the resultant output. 
	- ioMethods: This is the layer between the UI and the core. The UI hooks into these methods which return
				 the information from the core methods. In this case, this breaks up the input strings
				 into pieces so that the core can convert it. Once the core has done the conversion, this returns the
				 resultant strings. 
	- core: This is the heart of the program. It takes in BigIntegers as different parts of the rational number representation
				 and converts the number into a different rational number representation. Fraction.java is where all the
				 conversion takes place. The other java classes are data placeholders. 
	- coreUtils: These are a series of static methods that are used to aid the core in the conversion. The methods are general
				 enough to be useful in other programs. 