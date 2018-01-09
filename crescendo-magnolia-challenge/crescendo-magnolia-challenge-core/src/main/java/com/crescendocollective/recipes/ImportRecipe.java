package com.crescendocollective.recipes;

import info.magnolia.cms.util.AlertUtil;
import info.magnolia.context.Context;
import info.magnolia.commands.impl.BaseRepositoryCommand;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.nodebuilder.NodeBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.List;

public class ImportRecipe extends BaseRepositoryCommand {
    private List<Recipe> getRecipeData(){
        RecipeClient client = new RecipeClient();
        List<Recipe> recipeList = client.getRecipes();
        return recipeList;
    }
    @Override
    public boolean execute(Context ctx) {
        try {

            String parentPath = StringUtils.substringBeforeLast(getPath(), "/");
            String label = StringUtils.substringAfterLast(getPath(), "/");

            Node parentNode = MgnlContext.getJCRSession(this.getRepository()).getNode(parentPath);

            parentNode.addNode("Imported Recipes", "mgnl:page");
            Node recipesNode = parentNode.getNode("Imported Recipes");
            recipesNode.setProperty("Title", "Imported Recipes");
            parentNode.getSession().save();

            for (Recipe recipe:getRecipeData()) {
                recipesNode.addNode(recipe.getTitle(), "mgnl:page");
                Node recipeNode = recipesNode.getNode(recipe.getTitle());
                recipeNode.setProperty("Title", recipe.getTitle());
                recipeNode.setProperty("Description", recipe.getDescription());
                recipeNode.setProperty("PrepTime", recipe.getPrepTime());
                recipeNode.setProperty("CookTime", recipe.getCookTime());
                recipeNode.setProperty("ServingSize", recipe.getServingSize());
                recipesNode.getSession().save();
            }

            parentNode.getSession().save();
        } catch (RepositoryException e) {
            AlertUtil.setException("error: ", e, ctx);
            return false;
        }
        return true;
    }

}