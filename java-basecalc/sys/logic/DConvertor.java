package sys.logic;

public class DConvertor {
	
	public static int hex(String str)
	{
		//0A -> 10
		int value=0;
		for(int i=0; i<str.length(); i++)
		{
			switch(str.charAt(str.length()-i-1))
			{
			case '0': break;
			case '1': value += 1<<i*4; break;
			case '2': value += 2<<i*4; break;
			case '3': value += 3<<i*4; break;
			case '4': value += 4<<i*4; break;
			case '5': value += 5<<i*4; break;
			case '6': value += 6<<i*4; break;
			case '7': value += 7<<i*4; break;
			case '8': value += 8<<i*4; break;
			case '9': value += 9<<i*4; break;
			case 'a':
			case 'A': value += 10<<i*4; break;
			case 'b':
			case 'B': value += 11<<i*4; break;
			case 'c':
			case 'C': value += 12<<i*4; break;
			case 'd':
			case 'D': value += 13<<i*4; break;
			case 'e':
			case 'E': value += 14<<i*4; break;
			case 'f':
			case 'F': value += 15<<i*4; break;
			default: return 0;
			}
			
		}
		return value;
	}
	
	public static int bin(String str)
	{
		// 1111 -> 15
		int value=0;
		for(int i=0; i<str.length(); i++)
		{
			switch(str.charAt(str.length()-i-1))
			{
			case '0': break;
			case '1': value += 1<<i; break;
			default: return 0;
			}
			
		}
		return value;
		
	}
	
}
