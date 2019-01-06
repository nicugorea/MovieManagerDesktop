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

    public void addCategory(final String category) {
	if(this.Categories==null)
	{
	    this.Categories=new LinkedList<String>();
	}
	this.Categories.add(category);
    }

    public List<String> getCategories()
    {
	return this.Categories;
    }

    public String getCategoriesString()
    {
	if(this.Categories.size()>0) {

	    StringBuilder categoriesSB = new StringBuilder();
	    for (String category : this.Categories)
	    {
		categoriesSB.append(category+", ");
	    }
	    return categoriesSB.substring(0,categoriesSB.length()-3).toString();
	}

	return "";
    }

    public String getDescription()
    {
	return this.Description;
    }

    public String getIMDbID()
    {
	return this.IMDbID;
    }

    public String getImgPath()
    {
	return this.ImgPath;
    }

    @Override
    public String getName()
    {
	return "Movie";
    }

    public float getScore()
    {
	return this.Score;
    }

    public String getTitle()
    {
	return this.Title;
    }

    @Override
    public MovieDM newInstance(final Object object) {
	MovieDM obj = (MovieDM)object;
	MovieDM dm = obj;
	if(obj.Title.isEmpty())
	{
	    dm.setTitle("Unknown title");
	}
	if(obj.Description.isEmpty())
	{
	    dm.setDescription("Unknown description");
	}
	if(obj.IMDbID.isEmpty())
	{
	    dm.setIMDbID("Unknown id");
	}
	dm.setScore(0.0f);
	if(obj.Categories==null)
	{
	    dm.setCategories(new LinkedList<String>());
	}
	if(obj.ImgPath.isEmpty())
	{
	    dm.setImgPath(MagicValues.MovieDefaultThumbnail);
	}
	return dm;
    }

    public void setCategories(final List<String> categories)
    {
	this.Categories = categories;
    }

    public void setDescription(final String description)
    {
	this.Description = description;
    }

    public void setIMDbID(final String iMDbID)
    {
	this.IMDbID = iMDbID;
    }

    public void setImgPath(final String imgPath)
    {
	this.ImgPath = imgPath;
    }

    public void setScore(final float score)
    {
	this.Score = score;
    }

    public void setTitle(final String title)
    {
	this.Title = title;
    }

}
