import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.xml.ws.handler.MessageContext;


public class DisplayView extends View
{
	private Display.ChartType chartType;
	private MediaMaker maker;
	private Display display;
	private MediaModel model;
	
	public DisplayView(Display.ChartType type, MediaMaker maker)
	{
		super();
		this.chartType = type;
		this.maker = maker;
		this.actionPerformed(new ActionEvent(this, 0, EventMessages.DATA_CHANGED));
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals(EventMessages.DATA_CHANGED))
		{
			initComponents();
		}
	}

	@Override
	protected void initComponents()
	{
		if (display != null)
		{
			this.remove(display);
		}
		
		if (maker == null)
		{
			this.dispose();
		}
		
		setResizable(false);
		this.setVisible(true);
		
		//ToolTipManager.sharedInstance().registerComponent(this);
		if (chartType == Display.ChartType.PIE_CHART)
		{
			setTitle(maker.getMdbMediaFirstName() + " " + maker.getMdbMediaLastName() + " - Percentage of Credits"/*maker.getName()*/);
			setSize(new Dimension(640, 340));

		}
		if (chartType == Display.ChartType.HISTOGRAM)
		{
			setTitle(maker.getMdbMediaFirstName() + " " + maker.getMdbMediaLastName() + " - Credits over Time"/*maker.getName()*/);
			setSize(new Dimension(640, 340));
		}
		
		display = new Display(chartType, maker);
		add(display);
		setVisible(true);
	}

	public void ActionPerformed(ActionEvent e)
	{
		initComponents();
	}
}
