 /*************************\
 *                         *
 *     FinShare v1.0       *
 * Brian Shannon X00022027 *
 *   3rd year Computing    *
 *    (ITT Dublin) 2006    *
 *                         *
 \*Object:Crypt************/

package finNetwork;

import java.util.Random;
 
public class Crypt //symmetric-key encryption
{
	private String stringOutput;
	private char letter;
	private char letter2;
	private char charBuffer[];
	private int Index;
	private char startpoint;
	private char point;
	private int ChecksomeTotal;
	//private int ChecksomeTemp;
	private Random generator;
	
	public Crypt()
	{
		ChecksomeTotal = 0;
	//	ChecksomeTemp = 0;
		//startpoint = 'z';
		//point = startpoint;
		generator = new Random();//22027 seed
		//Index = generator.nextInt(20);//20 Range
		letter = '*';
		letter2 = '*';
	}
	
public String InCrypt(String stringInput)//(String StringX)
{
	Index = generator.nextInt(10)+1;
	//stringInput = stringInput;//.toLowerCase();
	charBuffer = stringInput.toCharArray();
	
	startpoint = charBuffer[charBuffer.length-1];
	point = startpoint;
	
  for(int counter = 0; counter <Index; counter++)// Index loop
  {		//System.out.println(point++);
	
	for (int counter_iner = 0; counter_iner<charBuffer.length; counter_iner++)//Crypt loop
	{
		letter = charBuffer[counter_iner];

		switch(letter)
        {
	        case 'a':{letter2 = 'X';	break;}
            case 'b':{letter2 = 'N';	break;}
            case 'c':{letter2 = '/';	break;}
            case 'd':{letter2 = 'M';	break;}
            case 'e':{letter2 = '#';	break;}
            case 'f':{letter2 = 'E';	break;}
            case 'g':{letter2 = 'O';	break;}
            case 'h':{letter2 = 'a';	break;}
            case 'i':{letter2 = '-';	break;}
            case 'j':{letter2 = '1';	break;}
            case 'k':{letter2 = '_';	break;}
            case 'l':{letter2 = 'g';	break;}
            case 'm':{letter2 = 'h';	break;}
            case 'n':{letter2 = 'y';	break;}
            case 'o':{letter2 = '(';	break;}
            case 'p':{letter2 = '+';	break;}
            case 'q':{letter2 = '0';	break;}
            case 'r':{letter2 = 'J';	break;}
            case 's':{letter2 = 'C';	break;}
            case 't':{letter2 = 'z';	break;}
            case 'u':{letter2 = 'D';	break;}
            case 'v':{letter2 = '>';	break;}
            case 'w':{letter2 = 'H';	break;}
            case 'x':{letter2 = 'I';	break;}
            case 'y':{letter2 = ']';	break;}
            case 'z':{letter2 = 'x';	break;}
            
	        case 'A':{letter2 = '9';	break;}
            case 'B':{letter2 = 'Z';	break;}
            case 'C':{letter2 = 'A';	break;}
            case 'D':{letter2 = '<';	break;}
            case 'E':{letter2 = 'G';	break;}
            case 'F':{letter2 = 'u';	break;}
            case 'G':{letter2 = 'Y';	break;}
            case 'H':{letter2 = '.';	break;}
            case 'I':{letter2 = '2';	break;}
            case 'J':{letter2 = ':';	break;}
            case 'K':{letter2 = 'P';	break;}
            case 'L':{letter2 = ' ';	break;}
            case 'M':{letter2 = 'B';	break;}
            case 'N':{letter2 = 'W';	break;}
            case 'O':{letter2 = 'w';	break;}
            case 'P':{letter2 = '8';	break;}
            case 'Q':{letter2 = ';';	break;}
            case 'R':{letter2 = 'v';	break;}
            case 'S':{letter2 = 'V';	break;}
            case 'T':{letter2 = '}';	break;}
            case 'U':{letter2 = 't';	break;}
            case 'V':{letter2 = 's';	break;}
            case 'W':{letter2 = '^';	break;}
            case 'X':{letter2 = '%';	break;}
            case 'Y':{letter2 = 'r';	break;}
            case 'Z':{letter2 = '=';	break;}
            
            case '1':{letter2 = '*';	break;}
            case '2':{letter2 = '7';	break;}
            case '3':{letter2 = 'K';	break;}
            case '4':{letter2 = ',';	break;}
            case '5':{letter2 = ')';	break;}
            case '6':{letter2 = '3';	break;}
            case '7':{letter2 = '?';	break;}
            case '8':{letter2 = '~';	break;}
            case '9':{letter2 = '@';	break;}
            case '0':{letter2 = 'f';	break;} 
            
	        case ' ':{letter2 = 'i';	break;}
            case '>':{letter2 = 'L';	break;} 
            case '<':{letter2 = 'j';	break;} 
            case '#':{letter2 = '6';	break;} 
            case '(':{letter2 = 'Q';	break;} 
            case ')':{letter2 = '!';	break;} 
            case '{':{letter2 = 'b';	break;}
            case '}':{letter2 = '4';	break;}
            case '[':{letter2 = 'c';	break;}
            case ']':{letter2 = 'd';	break;}
            case '/':{letter2 = 'k';	break;} 
            case '_':{letter2 = 'l';	break;}
            case '*':{letter2 = '{';	break;}
            case '@':{letter2 = 'm';	break;}
            case '%':{letter2 = 'U';	break;}
            case '!':{letter2 = 'n';	break;}
            case '?':{letter2 = 'o';	break;}
            case '&':{letter2 = 'R';	break;}
            case '^':{letter2 = 'F';	break;}
            case '-':{letter2 = '&';	break;}
            case '+':{letter2 = 'p';	break;}
            case '=':{letter2 = '5';	break;}
            case '.':{letter2 = 'T';	break;}
            case ',':{letter2 = 'q';	break;}
            case '~':{letter2 = '[';	break;}
            case ':':{letter2 = 'e';	break;}
            case ';':{letter2 = 'S';	break;}
            
    	}//end of switch
		
	charBuffer[counter_iner] = letter2;
    }//end of Crypt loop
	
	//System.out.println(charBuffer);
	point++;	//System.out.println(counter +""+ charBuffer);
	}//end of Index loop
  
  //int i = (int) c;
  for(int CheckCounter = 0; CheckCounter<charBuffer.length; CheckCounter++)//CheckSome
  {
	  ChecksomeTotal += (int) charBuffer[CheckCounter];
	//  System.out.println("   Check some Total " + ChecksomeTotal);
  }
	stringOutput = new String(charBuffer);//char array 2 String
	stringOutput = stringOutput + point + startpoint; // add index to end of massage
	
	
	
	
	return stringOutput;//output
	
	}//end of function

public String DeCrypt(char charBuffer[])//(String StringX)
{
	//stringInput = stringInput.toLowerCase();
	//charBuffer = stringInput.toCharArray();
	
	point = charBuffer[charBuffer.length-3];
	startpoint = charBuffer[charBuffer.length-2];
	
	while(point != startpoint)
	{
	for (int counter = 0; counter<charBuffer.length; counter++)
	{
		letter = charBuffer[counter];

		switch(letter)
        {
        case 'X':{letter2 = 'a';	break;}
        case 'N':{letter2 = 'b';	break;}
        case '/':{letter2 = 'c';	break;}
        case 'M':{letter2 = 'd';	break;}
        case '#':{letter2 = 'e';	break;}
        case 'E':{letter2 = 'f';	break;}
        case 'O':{letter2 = 'g';	break;}
        case 'a':{letter2 = 'h';	break;}
        case '-':{letter2 = 'i';	break;}
        case '1':{letter2 = 'j';	break;}
        case '_':{letter2 = 'k';	break;}
        case 'g':{letter2 = 'l';	break;}
        case 'h':{letter2 = 'm';	break;}
        case 'y':{letter2 = 'n';	break;}
        case '(':{letter2 = 'o';	break;}
        case '+':{letter2 = 'p';	break;}
        case '0':{letter2 = 'q';	break;}
        case 'J':{letter2 = 'r';	break;}
        case 'C':{letter2 = 's';	break;}
        case 'z':{letter2 = 't';	break;}
        case 'D':{letter2 = 'u';	break;}
        case '>':{letter2 = 'v';	break;}
        case 'H':{letter2 = 'w';	break;}
        case 'I':{letter2 = 'x';	break;}
        case ']':{letter2 = 'y';	break;}
        case 'x':{letter2 = 'z';	break;}
        
        case '9':{letter2 = 'A';	break;}
        case 'Z':{letter2 = 'B';	break;}
        case 'A':{letter2 = 'C';	break;}
        case '<':{letter2 = 'D';	break;}
        case 'G':{letter2 = 'E';	break;}
        case 'u':{letter2 = 'F';	break;}
        case 'Y':{letter2 = 'G';	break;}
        case '.':{letter2 = 'H';	break;}
        case '2':{letter2 = 'I';	break;}
        case ':':{letter2 = 'J';	break;}
        case 'P':{letter2 = 'K';	break;}
        case ' ':{letter2 = 'L';	break;}
        case 'B':{letter2 = 'M';	break;}
        case 'W':{letter2 = 'N';	break;}
        case 'w':{letter2 = 'O';	break;}
        case '8':{letter2 = 'P';	break;}
        case ';':{letter2 = 'Q';	break;}
        case 'v':{letter2 = 'R';	break;}
        case 'V':{letter2 = 'S';	break;}
        case '}':{letter2 = 'T';	break;}
        case 't':{letter2 = 'U';	break;}
        case 's':{letter2 = 'V';	break;}
        case '^':{letter2 = 'W';	break;}
        case '%':{letter2 = 'X';	break;}
        case 'r':{letter2 = 'Y';	break;}
        case '=':{letter2 = 'Z';	break;}
        
        case '*':{letter2 = '1';	break;}
        case '7':{letter2 = '2';	break;}
        case 'K':{letter2 = '3';	break;}
        case ',':{letter2 = '4';	break;}
        case ')':{letter2 = '5';	break;}
        case '3':{letter2 = '6';	break;}
        case '?':{letter2 = '7';	break;}
        case '~':{letter2 = '8';	break;}
        case '@':{letter2 = '9';	break;}
        case 'f':{letter2 = '0';	break;} 
        
        case 'i':{letter2 = ' ';	break;}
        case 'L':{letter2 = '>';	break;} 
        case 'j':{letter2 = '<';	break;} 
        case '6':{letter2 = '#';	break;} 
        case 'Q':{letter2 = '(';	break;} 
        case '!':{letter2 = ')';	break;} 
        case 'b':{letter2 = '{';	break;}
        case '4':{letter2 = '}';	break;}
        case 'c':{letter2 = '[';	break;}
        case 'd':{letter2 = ']';	break;}
        case 'k':{letter2 = '/';	break;} 
        case 'l':{letter2 = '_';	break;}
        case '{':{letter2 = '*';	break;}
        case 'm':{letter2 = '@';	break;}
        case 'U':{letter2 = '%';	break;}
        case 'n':{letter2 = '!';	break;}
        case 'o':{letter2 = '?';	break;}
        case 'R':{letter2 = '&';	break;}
        case 'F':{letter2 = '^';	break;}
        case '&':{letter2 = '-';	break;}
        case 'p':{letter2 = '+';	break;}
        case '5':{letter2 = '=';	break;}
        case 'T':{letter2 = '.';	break;}
        case 'q':{letter2 = ',';	break;}
        case '[':{letter2 = '~';	break;}
        case 'e':{letter2 = ':';	break;}
        case 'S':{letter2 = ';';	break;}
        
        }//end of switch
		
		charBuffer[counter] = letter2;
		
	}//end of FOR Loop
	//System.out.println(charBuffer);
	point--;
	}//while
	charBuffer[charBuffer.length-2] = ' ';
	charBuffer[charBuffer.length-3] = ' ';
	//charBuffer[0] = ' ';
	stringOutput = new String(charBuffer);
	return stringOutput;
	}//end of function
}
