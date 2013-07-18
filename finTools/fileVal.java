package finTools;

public class fileVal 
{
	private String hash;
	private String Address;
	private static int count;
	private int thisCount;
	
	public fileVal(String H, String A)
	{
		count++;
		thisCount = count;
		hash = H;
		Address = A;
		//System.out.println(toString());
	}
	public String getHash()
	{
		return hash;
	}
	public String getAddress()
	{
		return Address;
	}
	
	public String toString()
	{
		return "finShare.fileVal #" + thisCount + " hash:" + hash +" Address:" + Address;
	}
	public static void reSetCounter()
	{
		count = 0;
	}
}
