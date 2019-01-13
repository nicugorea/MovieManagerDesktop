package mmd.common.models;

import java.util.LinkedList;
import java.util.List;

import mmd.common.bases.DMBase;
import mmd.util.MagicValues;

public class MovieDM extends DMBase<MovieDM>
{
	private List<String> Categories;
	private String Description;
	private String IMDbID;
	private String ImgPath;
	private float Score;
	private String Title;
	
	/**
	 * Add a category to categories list
	 * 
	 * @param category Category to add
	 */
	public void addCategory(final String category)
	{
		if (this.Categories == null)
		{
			this.Categories = new LinkedList<String>();
		}
		this.Categories.add(category);
	}
	
	/**
	 * Get Categories List
	 * 
	 * @return List of categories
	 */
	public List<String> getCategories()
	{
		return this.Categories;
	}
	
	/**
	 * Get Categories List to string
	 * 
	 * @return String of Categories list separated by ','
	 */
	public String getCategoriesString()
	{
		if (this.Categories.size() > 0)
		{
			
			StringBuilder categoriesSB = new StringBuilder();
			for (String category : this.Categories)
			{
				categoriesSB.append(category + ", ");
			}
			return categoriesSB.substring(0, categoriesSB.length() - 2).toString();
		}
		
		return "";
	}
	
	/**
	 * Get Description
	 * 
	 * @return String of description
	 */
	public String getDescription()
	{
		return this.Description;
	}
	
	/**
	 * Get IMDb ID
	 * 
	 * @return String of IMDb ID
	 */
	public String getIMDbID()
	{
		return this.IMDbID;
	}
	
	/**
	 * Get Image relative path to default path with extension
	 * 
	 * @return String of Image Path
	 */
	public String getImgPath()
	{
		return this.ImgPath;
	}
	
	/**
	 * Get Name
	 * 
	 * @return String of Name
	 */
	@Override
	public String getName()
	{
		return "Movie";
	}
	
	/**
	 * Get Score
	 * 
	 * @return float of Score
	 */
	public float getScore()
	{
		return this.Score;
	}
	
	/**
	 * Get Title
	 * 
	 * @return String of Title
	 */
	public String getTitle()
	{
		return this.Title;
	}
	
	@Override
	public MovieDM newInstance(final Object object)
	{
		MovieDM dm = (MovieDM) object;
		if (dm.Title == null)
		{
			dm.setTitle("Unknown title");
		}
		if (dm.Description == null)
		{
			dm.setDescription("Unknown description");
		}
		if (dm.IMDbID == null)
		{
			dm.setIMDbID("Unknown id");
		}
		dm.setScore(0.0f);
		if (dm.Categories == null)
		{
			dm.setCategories(new LinkedList<String>());
		}
		if (dm.ImgPath == null)
		{
			dm.setImgPath(MagicValues.MovieDefaultThumbnail);
		}
		return dm;
	}
	
	/**
	 * Set Categories using a list
	 * 
	 * @param categories List to set
	 */
	public void setCategories(final List<String> categories)
	{
		this.Categories = categories;
	}
	
	/**
	 * Set Description
	 * 
	 * @param description Description to set
	 */
	public void setDescription(final String description)
	{
		this.Description = description;
	}
	
	/**
	 * Set IMDb ID
	 * 
	 * @param iMDbID IMDb ID to set
	 */
	public void setIMDbID(final String iMDbID)
	{
		this.IMDbID = iMDbID;
	}
	
	/**
	 * Set Image path
	 * 
	 * @param imgPath Image path to set
	 */
	public void setImgPath(final String imgPath)
	{
		this.ImgPath = imgPath;
	}
	
	/**
	 * Set Score
	 * 
	 * @param score Score to set
	 */
	public void setScore(final float score)
	{
		this.Score = score;
	}
	
	/**
	 * Set Title
	 * 
	 * @param title Title to set
	 */
	public void setTitle(final String title)
	{
		this.Title = title;
	}
	
}
