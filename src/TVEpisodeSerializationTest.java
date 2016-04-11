import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TVEpisodeSerializationTest 
{

	public static void main(String[] args)
	{
		TVEpisode ep1 = new TVEpisode("Pokemon", "1999", "Pokemon, I Choose You!", "1999");
		System.out.println(ep1.toString());
		
		try
		{
			FileOutputStream fileOut =
					new FileOutputStream("/Project3.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(ep1);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in /Project3.ser");
		}
		catch(IOException i)
		{
        i.printStackTrace();
		}
		
		TVEpisode e1copy = null;
		
		try
	      {
	         FileInputStream fileIn = new FileInputStream("/Project3.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         e1copy = (TVEpisode) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("TVEpisode class not found");
	         c.printStackTrace();
	         return;
	      }
		System.out.println("\n RESERIALIZED OUTPUT:");
		System.out.println(e1copy.toString());
		
	}

}
