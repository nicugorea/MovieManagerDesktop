package mmd.common.models;

import java.util.ArrayList;
import java.util.List;

import mmd.common.interfaces.DMDefinition;

public class MovieDM implements DMDefinition
{
    private List<String> Categories;
    private String Description;
    private String IMDbID;
    private String ImgPath;
    private float Score;
    private String Title;


    public List<String> getCategories()
    {
	return this.Categories;
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
    public ArrayList<Property> getProperties()
    {
	ArrayList<Property> result = new ArrayList<Property>();

	result.add(new Property("Title", this.Title));
	result.add(new Property("Description", this.Description));
	result.add(new Property("Score", Float.toString(this.Score)));
	result.add(new Property("ImgPath", this.ImgPath));
	result.add(new Property("IMDbID", this.IMDbID));
	result.add(new Property("Categories", "Category", this.Categories));
	return result;
    }

    public float getScore()
    {
	return this.Score;
    }

    public String getTitle()
    {
	return this.Title;
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
