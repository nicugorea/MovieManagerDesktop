package mmd.common.models;

import mmd.common.bases.DMBase;
import mmd.util.MagicValues;

public class CategoryDM extends DMBase<CategoryDM> {

	private String CategoryName;
	
	
	@Override
	public String getName() {
		return MagicValues.CategoryDMName;
	}

	@Override
	public CategoryDM newInstance(final Object object) {
		CategoryDM category = new CategoryDM();
		category.setCategoryName("Unknown");
		return category;
	}

	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}

}
