import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.junit.Test;

public class TestMakerEntryView
{

	@Test
	public void test()
	{
		MakerEntryView mev = new MakerEntryView(Actor.class, "first", "last", "III");
		mev.addDoneListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					Actor actor = (Actor) mev.instantiate();
					assert(actor.getMdbMediaFirstName().equals("first"));
					assert(actor.getMdbMediaLastName().equals("last"));
					assert(actor.getMdbMediaDisambiguationNumber().equals("III"));
				} catch (InstantiationException e1)
				{
					assert false;
					e1.printStackTrace();
				} catch (IllegalAccessException e1)
				{
					assert false;
					e1.printStackTrace();
				}
			}
			
		});
	}

}
