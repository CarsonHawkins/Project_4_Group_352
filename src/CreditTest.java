import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

public class CreditTest { 

	public static void main(String[] args)
	{
	Movie m1 = new Movie("Jurrasic Park", "1999");
	Movie m2 = new Movie("Forriest Gump", "1994");
	
	Credit c1 = new Credit(m1, Credit.MediaType.EPISODE, Credit.MakerType.ACTOR, "","", new MediaMaker());
	System.out.println(c1.toString());
	
	
	try
	{
		FileOutputStream fileOut =
				new FileOutputStream("/Project3.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(c1);
		out.close();
		fileOut.close();
		System.out.println("Serialized data is saved in /Project3.ser");
	}
	catch(IOException i)
	{
    i.printStackTrace();
	}
	
	Credit c1copy = null;
	
	try
      {
         FileInputStream fileIn = new FileInputStream("/Project3.ser");
         ObjectInputStream in = new ObjectInputStream(fileIn);
         c1copy = (Credit) in.readObject();
         in.close();
         fileIn.close();
      }catch(IOException i)
      {
         i.printStackTrace();
         return;
      }catch(ClassNotFoundException c)
      {
         System.out.println("Credit class not found");
         c.printStackTrace();
         return;
      }
	System.out.println("\n RESERIALIZED OUTPUT:");
	System.out.println(c1copy.toString());
	
	
	
	
	}
}
