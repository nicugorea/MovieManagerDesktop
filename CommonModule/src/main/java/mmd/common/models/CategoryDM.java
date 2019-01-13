package mmd.common.models;

import mmd.common.bases.DMBase;
import mmd.util.MagicValues;

/**
 * 
 * Category data model used for specifying a Category
 */
public class CategoryDM extends DMBase<CategoryDM>
{
	
	private String CategoryName;
	
	@Override
	public String getName()
	{
		return MagicValues.CategoryDMName;
	}
	
	@Override
	public CategoryDM newInstance(final Object object)
	{
		CategoryDM category = (CategoryDM) object;
		if (category.getCategoryName() == null) category.setCategoryName("Unknown");
		return category;
	}
	
	/**
	 * Method to get category name
	 * 
	 * @return {@link CategoryName}
	 */
	public String getCategoryName()
	{
		return CategoryName;
	}
	
	/**
	 * Method to set category name
	 * 
	 * @param categoryName Name to set
	 */
	public void setCategoryName(String categoryName)
	{
		CategoryName = categoryName;
	}
	
}
