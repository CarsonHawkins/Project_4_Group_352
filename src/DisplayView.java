import java.awt.Dimension;
import java.awt.event.ActionEvent;


/**
 * A view that holds a pie chart or histogram for a maker
 * @author Daniel Schon
 *
 */
public class DisplayView extends View
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** type of chart */
	private Display.ChartType chartType;
	/** mediamaker this graph describes */
	private MediaMaker maker;
	/** jpanel that shows the data */
	private Display display;

	/**
	 * Constructor for displayview
	 * @param type
	 * @param maker
	 */
	public DisplayView(Display.ChartType type, MediaMaker maker)
	{
		super();
		this.chartType = type;
		this.maker = maker;
		initComponents();
	}

	/**
	 * method is called when an action is performed
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals(EventMessages.DATA_CHANGED))
		{
			initComponents();
		}
	}

	/**
	 * initialize the components
	 */
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
}
