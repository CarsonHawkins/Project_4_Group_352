import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import javax.swing.JPanel;

public class Display extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6835355639226626686L;

	public enum ChartType
	{
		PIE_CHART,
		HISTOGRAM
	}

	private ChartType chartType;
	private MediaMaker maker;
	
	public Display(ChartType chartType, MediaMaker maker)
	{
		if (maker == null)
		{
			return;
		}
		
		this.chartType = chartType;
		this.maker = maker; 
	}
	
	
	private void paintPieChart(Graphics2D g)
	{
		//Count each type of credit
		Float[] counts = new Float[]{0f,0f,0f,0f,0f,0f};
		for (Credit credit : maker.getMovieCredits())
		{
			if (credit.getMakerType() == Credit.MakerType.ACTOR)
				counts[0]++;
			if (credit.getMakerType() == Credit.MakerType.DIRECTOR)
				counts[2]++;
			if (credit.getMakerType() == Credit.MakerType.PRODUCER)
				counts[4]++;
		}
		for (Credit credit : maker.getSeriesCredits())
		{
			if (credit.getMakerType() == Credit.MakerType.ACTOR)
				counts[1]++;
			if (credit.getMakerType() == Credit.MakerType.DIRECTOR)
				counts[3]++;
			if (credit.getMakerType() == Credit.MakerType.PRODUCER)
				counts[5]++;
		}
		Color[] colors = new Color[]
				{
						Color.red,
						Color.orange,
						Color.yellow,
						Color.green,
						Color.blue,
						Color.magenta
				};
		
		/* Paint the pie in the left half */
		int strokeWidth = 5;
		g.setStroke(new BasicStroke(strokeWidth));
		g.setColor(Color.lightGray);
		g.fillRect(strokeWidth, strokeWidth, getWidth()/2 - strokeWidth*2, getHeight() - strokeWidth*2);
		g.setColor(Color.black);
		g.drawRect(strokeWidth, strokeWidth, getWidth()/2 - strokeWidth*2, getHeight() - strokeWidth*2);
		
		g.setStroke(new BasicStroke(2));
		float total = 0;
		for (float i : counts)
		{
			total += i;
		}
		
		float angle = 0;
		for (int i = 0; i < 6; i++)
		{
			float angleWidth = counts[i] / total;
			g.setColor(colors[i]);
			g.fillArc(15, 15, getWidth()/2-30, getHeight()-30, (int)angle, (int)(360f*angleWidth)+1);
			angle+=360f*angleWidth;
			
			g.fillRect(getWidth()/2+10, 11+50*i, 25, 25);
			g.setColor(Color.black);
			g.drawRect(getWidth()/2+10, 11+50*i, 25, 25);
		}
		
		g.setColor(Color.black);
		g.drawOval(15, 15, getWidth()/2-30, getHeight()-30);
		g.setFont(g.getFont().deriveFont(18.0f));
		
		/* Paint the key in the right half */
		g.drawString("Movie Acting Credits: " + counts[0].intValue(), getWidth()/2+45, 30);
		g.drawString("Series Acting Credits: " + counts[1].intValue(), getWidth()/2+45, 80);
		g.drawString("Movie Directing Credits: " + counts[2].intValue(), getWidth()/2+45, 130);
		g.drawString("Series Directing Credits: " + counts[3].intValue(), getWidth()/2+45, 180);
		g.drawString("Movie Producing Credits: " + counts[4].intValue(), getWidth()/2+45, 230);
		g.drawString("Series Producing Credits: " + counts[5].intValue(), getWidth()/2+45, 280);
	}
	
	private void paintHistogram(Graphics2D g)
	{
		int startYear = 3000;
		int endYear = 0;
		
		HashMap<Integer, Integer> countMap = new HashMap<>();
		HashMap<Integer, Integer[]> typeCountMap = new HashMap<>();
		
		for (Credit mc : maker.getMovieCredits())
		{
			String year = ((Movie)mc.getMedia()).getYear();
			if (!year.contains("?") && !year.equals(""))
			{
				int iYear = 0;
				try
				{
					iYear = Integer.parseInt(year);
				}
				catch (Exception e)
				{
					System.out.println(mc.getMedia().toString());
					e.printStackTrace();
				}
				
				if (iYear < startYear)
					startYear = iYear;
				if (iYear > endYear)
					endYear = iYear;
				
				if (!countMap.containsKey(iYear))
				{
					countMap.put(iYear, 0);
					typeCountMap.put(iYear, new Integer[]{0,0,0,0,0,0});
				}
				
				countMap.put(iYear, countMap.get(iYear) + 1);
				
				int index = 0;
				if (mc.getMakerType() == Credit.MakerType.ACTOR) index = 0;
				if (mc.getMakerType() == Credit.MakerType.DIRECTOR) index = 2;
				if (mc.getMakerType() == Credit.MakerType.PRODUCER) index = 4;
				
				Integer[] tempIntArr = typeCountMap.get(iYear);
				tempIntArr[index]++;
				typeCountMap.put(iYear, tempIntArr);
			}
		}
		for (Credit sc : maker.getSeriesCredits())
		{
			String year = ((TVEpisode)sc.getMedia()).getEpisodeYear();
			if (!year.contains("?") && !year.equals(""))
			{
				int iYear = Integer.parseInt(year);
				if (iYear < startYear)
					startYear = iYear;
				if (iYear > endYear)
					endYear = iYear;
				
				if (!countMap.containsKey(iYear))
				{
					countMap.put(iYear, 0);
					typeCountMap.put(iYear, new Integer[]{0,0,0,0,0,0});
				}
				
				countMap.put(iYear, countMap.get(iYear) + 1);
				
				int index = 0;
				if (sc.getMakerType() == Credit.MakerType.ACTOR) index = 1;
				if (sc.getMakerType() == Credit.MakerType.DIRECTOR) index = 3;
				if (sc.getMakerType() == Credit.MakerType.PRODUCER) index = 5;
				
				Integer[] tempIntArr = typeCountMap.get(iYear);
				tempIntArr[index]++;
				typeCountMap.put(iYear, tempIntArr);
			}
		}
		
		//Create heights and proportions
		HashMap<Integer, Float> heightMap = new HashMap<Integer, Float>();
		HashMap<Integer, Float[]> proportionMap = new HashMap<Integer, Float[]>();
		int total = 0;
		for (Integer year : countMap.keySet())
		{
			total += countMap.get(year);
		}
		
		float highest = 0;
		for (Integer year: countMap.keySet())
		{
			float height = ((float)countMap.get(year))/(float)(total);
			if (height > highest)
				highest = height;
			
			heightMap.put(year, height);
			Integer[] countList = typeCountMap.get(year);
			
			float typeTotal = countList[0]+countList[1]+countList[2]+countList[3]+countList[4]+countList[5]; 
			Float[] proportionList =
				{
						(float)countList[0] / typeTotal,
						(float)countList[1] / typeTotal,
						(float)countList[2] / typeTotal,
						(float)countList[3] / typeTotal,
						(float)countList[4] / typeTotal,
						(float)countList[5] / typeTotal
				};
			proportionMap.put(year, proportionList);
		}
		
		for (Integer year: countMap.keySet())
		{
			float height = ((float)countMap.get(year))/(float)(total);
			heightMap.put(year, height / highest);
		}
		
		int duration = endYear - startYear;
		
		//Create 0 height bars for any blank years
		for (int year = startYear; year <= endYear; year++)
		{
			if (!heightMap.containsKey(year))
			{
				heightMap.put(year, 0f);
				proportionMap.put(year, new Float[] {.167f, .167f, .167f, .167f, .167f, .167f});
			}
		}
		
		Color[] colors = new Color[]
				{
						Color.red,
						Color.orange,
						Color.yellow,
						Color.green,
						Color.blue,
						Color.magenta
				};
		
		/* Paint the histogram in the left half */
		int strokeWidth = 5;
		g.setStroke(new BasicStroke(strokeWidth));
		g.setColor(Color.white);
		g.fillRect(strokeWidth, strokeWidth, getWidth()/2 - strokeWidth*2, getHeight() - strokeWidth*2);
		g.setColor(Color.black);
		g.drawRect(strokeWidth, strokeWidth, getWidth()/2 - strokeWidth*2, getHeight() - strokeWidth*2);
		
		g.setStroke(new BasicStroke(1));
		
		for (int year = startYear; year <= endYear; year++)
		{
			float totalHeight = heightMap.get(year) * getHeight()-50;
			
			float bottom = 0;
			float barWidth = ((float)getWidth()/2f-30f)/((float)duration+1f);
			for (int segment = 0; segment < 6; segment++)
			{
				float segmentHeight = proportionMap.get(year)[segment]*totalHeight;
				g.setColor(colors[segment]);
				g.fillRect((int)(15+(year-startYear)*barWidth), 
						(int)(getHeight()-35-bottom-segmentHeight), 
						(int)(barWidth), 
						(int)(segmentHeight));
				bottom += segmentHeight;
			}

			g.setColor(Color.black);
			
			g.drawRect((int)(15+(year-startYear)*barWidth), 
					(int)(getHeight()-35-totalHeight), 
					(int)(barWidth), 
					(int)(totalHeight));
		}
		
		/* Paint the key in the right half */
		for (int i = 0; i < 6; i++)
		{
			g.setColor(colors[i]);
			g.fillRect(getWidth()/2+10, 11+50*i, 25, 25);
			g.setColor(Color.black);
			g.drawRect(getWidth()/2+10, 11+50*i, 25, 25);
		}
		
		g.setColor(Color.black);
		g.setFont(g.getFont().deriveFont(18.0f));
		g.drawString(startYear + " to " + endYear, getWidth()/4 - 55, getHeight()-15);
		g.drawString("Movie Acting Credits", getWidth()/2+45, 30);
		g.drawString("Series Acting Credits", getWidth()/2+45, 80);
		g.drawString("Movie Directing Credits", getWidth()/2+45, 130);
		g.drawString("Series Directing Credits", getWidth()/2+45, 180);
		g.drawString("Movie Producing Credits", getWidth()/2+45, 230);
		g.drawString("Series Producing Credits", getWidth()/2+45, 280);
	}

	 
	public void paintComponent(Graphics g)
	{	
		if (chartType == ChartType.PIE_CHART)
		{
			paintPieChart((Graphics2D) g);
		}
		if (chartType == ChartType.HISTOGRAM)
		{
			paintHistogram((Graphics2D) g);
		}
	}

	
	public ChartType getChartType()
	{
		return chartType;
	}

	public void setChartType(ChartType chartType)
	{
		this.chartType = chartType;
	}

	public MediaMaker getMaker()
	{
		return maker;
	}

	public void setMaker(MediaMaker maker)
	{
		this.maker = maker;
	}
}


