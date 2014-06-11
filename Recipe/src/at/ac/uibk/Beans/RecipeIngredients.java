package at.ac.uibk.Beans;

import java.util.ArrayList;
import java.util.List;

public class RecipeIngredients {
	private List<String> quantities;
	private List<IngredientType> ingredients;

	public RecipeIngredients() {
		ingredients = new ArrayList<IngredientType>();
		quantities = new ArrayList<String>();
	}

	public boolean addIngredient(String quantity, IngredientType ingredient) {
		if (quantities.add(quantity)) {
			if (ingredients.add(ingredient)) {
				return true;
			} else {
				quantities.remove(quantities.size() - 1);
				return false;
			}
		}
		return false;

	}

	public boolean removeIngredient(IngredientType ingredient) {
		if (ingredients.contains(ingredient)) {
			int index = ingredients.indexOf(ingredient);
			quantities.remove(index);
			ingredients.remove(index);
			return true;
		}
		return false;
	}

	public List<IngredientType> getIngredients() {
		return ingredients;
	}

	public List<String> getQuantities() {
		return quantities;
	}
}
