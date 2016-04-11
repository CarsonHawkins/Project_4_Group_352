import java.io.*;

public class MovieSerializationTest 
{
	public static void main(String[] args)
	{
		Movie m1 = new Movie("Jurrasic Park", "1999");
		Movie m2 = new Movie("Forriest Gump", "1994");
	
		System.out.println(m1.toString());
		System.out.println(m2.toString());
		
		
		try
		{
			FileOutputStream fileOut =
					new FileOutputStream("/Project3.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(m1);
			out.writeObject(m2);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in /Project3.ser");
		}
		catch(IOException i)
		{
        i.printStackTrace();
		}
		
		Movie m1copy = null;
		Movie m2copy = null;
				
		
		try
	      {
	         FileInputStream fileIn = new FileInputStream("/Project3.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         m1copy = (Movie) in.readObject();
	         m2copy = (Movie) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Movie class not found");
	         c.printStackTrace();
	         return;
	      }
		System.out.println("\n RESERIALIZED OUTPUT:");
		System.out.println(m1copy.toString());
		System.out.println(m2copy.toString());
	}
}
