package com.crescendocollective.recipes;

import info.magnolia.cms.util.AlertUtil;
import info.magnolia.commands.impl.BaseRepositoryCommand;
import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;
import info.magnolia.dam.api.Asset;
import info.magnolia.dam.api.AssetProviderRegistry;
import info.magnolia.dam.api.AssetQuery;
import info.magnolia.dam.api.Item;
import info.magnolia.dam.core.config.DamCoreConfiguration;
import info.magnolia.dam.jcr.AssetNodeTypes;
import info.magnolia.dam.jcr.DamConstants;
import info.magnolia.dam.jcr.JcrAssetProvider;
import info.magnolia.dam.jcr.JcrFolder;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.objectfactory.Components;
import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.*;
import java.util.Iterator;
import java.util.List;

// Groovy command
//import info.magnolia.cms.core.*;
//import info.magnolia.cms.util.*;
//import info.magnolia.context.*;
//import static info.magnolia.nodebuilder.Ops.*
//@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7' )
//import javax.jcr.Node;
//        import javax.jcr.RepositoryException;
//        import javax.jcr.Session;
//        import info.magnolia.jcr.util.NodeUtil
//        import groovy.json.JsonSlurper
//        import groovy.json.JsonBuilder
//        import groovy.json.JsonOutput
//        import groovyx.net.http.HTTPBuilder
//        import groovyx.net.http.ContentType
//        import groovyx.net.http.Method
//
//        hm = ctx.getHierarchyManager('website')
//        root = hm.getRoot()
//
//        HTTPBuilder http = new HTTPBuilder('http://www.johnsonville.com/recipes.top-rated.json', ContentType.JSON);
//        http.headers.Accept = ContentType.JSON
//        http.parser[ContentType.JSON] = http.parser.'application/json'
//        http.request(Method.GET) {
//        response.success = { resp, json ->
//        slurped = new JsonSlurper().parseText(JsonOutput.toJson(json))
//        slurped.size()
//        }
//        }
//
//        root = hm.getRoot()
//        slurped.each { piece ->
//        content = root.createContent(piece.title, "mgnl:page")
//        content.metaData.setTemplate("crescendo-magnolia-challenge:pages/recipes/recipe")
//        content.createNodeData("title", piece.title)
//        content.createNodeData("description", piece.description)
//        content.createNodeData("prepTime", piece.prepTime)
//        content.createNodeData("cookTime", piece.cookTime)
//        content.createNodeData("servingSize", piece.servingSize)
//        content.getProperties()
//        }
//        root.save()

public class ImportRecipe extends BaseRepositoryCommand {
    private List<Recipe> getRecipeData(){
        RecipeClient client = new RecipeClient();
        List<Recipe> recipeList = client.getRecipes();
        return recipeList;
    }

    private void checkNode(Node node) throws RepositoryException {
        System.out.print(NodeUtil.getName(node));
        System.out.print(NodeUtil.getNodePathIfPossible(node));
        System.out.print(node.getIdentifier());

//        for (Iterator<Property> properties = node.getReferences(); properties.hasNext(); ) {
//            Property property = properties.next();
//            String name = property.getName();
//            int type = property.getType();
//            Value value = property.getValue();
//            //value.
//            //property.get
//            System.out.print(name);
//        }

        String nname = node.getName();
        if (node.getName().equals("carrot-3x.png")) {
            Property data = node.getProperty("jcr:data");
            String dname = data.getName();
        }

        for (Iterator<Property> properties = node.getProperties(); properties.hasNext(); ) {
            Property property = properties.next();
            String name = property.getName();
            int type = property.getType();
            Value value = property.getValue();
            //value.
            //property.get
            System.out.print(name);
        }
    }

    @Override
    public boolean execute(Context ctx) {

//        Node assetResourceNode = JcrUtils.getOrAddNode(assetNode, AssetNodeTypes.AssetResource.RESOURCE_NAME, AssetNodeTypes.AssetResource.NAME);
//        assetResourceNode.setProperty(AssetNodeTypes.AssetResource.DATA, session.getValueFactory().createBinary(multipartFile.getInputStream()));
//        assetResourceNode.setProperty(AssetNodeTypes.AssetResource.FILENAME, newFileName);
//        assetResourceNode.setProperty(AssetNodeTypes.AssetResource.EXTENSION, fileExtension);
//        assetResourceNode.setProperty(AssetNodeTypes.AssetResource.SIZE, Long.toString(multipartFile.getSize()));
//        assetResourceNode.setProperty(AssetNodeTypes.AssetResource.MIMETYPE, imageInfo.getMimeType());
//        assetResourceNode.setProperty(AssetNodeTypes.AssetResource.WIDTH, Long.toString(imageInfo.getWidth()));
//        assetResourceNode.setProperty(AssetNodeTypes.AssetResource.HEIGHT, Long.toString(imageInfo.getHeight()));
//
//        try {
//
//            {
//                DamCoreConfiguration cfg = new DamCoreConfiguration();
//                JcrAssetProvider provider = new JcrAssetProvider(cfg);
//                AssetQuery showAll = new AssetQuery.Builder().fromPath("/").build();
//                for (Iterator<Item> assets = provider.list(showAll); assets.hasNext(); ) {
//                    Item item = assets.next();
//                    if (item.isAsset()) {
//                        Asset asset = provider.getAsset(item.getItemKey());
//                    }
//                }
//            }
//
////            Session session = MgnlContext.getJCRSession(DamConstants.WORKSPACE);
//            Session session = MgnlContext.getJCRSession("dam");
//
//            Node root = session.getRootNode();
//
//            for (Node node:NodeUtil.getNodes(root)) {
//                checkNode(node);
//                if (node.hasNodes()) {
//                    for (Node subNode:NodeUtil.getNodes(node)) {
//                        checkNode(subNode);
//                    }
//                }
//            }
//
//        } catch (RepositoryException e) {
//            AlertUtil.setException("error: ", e, ctx);
//            return false;
//        }
        return true;
    }

}