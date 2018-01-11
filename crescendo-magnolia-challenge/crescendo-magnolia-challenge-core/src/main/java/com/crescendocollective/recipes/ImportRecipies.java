package com.crescendocollective.recipes;

import info.magnolia.cms.core.Content;
import info.magnolia.cms.core.HierarchyManager;
import info.magnolia.commands.impl.BaseRepositoryCommand;
import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;
import info.magnolia.dam.api.AssetProviderRegistry;
import info.magnolia.dam.jcr.AssetNodeTypes;
import info.magnolia.dam.jcr.DamConstants;
import info.magnolia.dam.jcr.JcrAssetProvider;
import info.magnolia.dam.jcr.JcrFolder;
import info.magnolia.objectfactory.Components;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.jackrabbit.commons.JcrUtils;
import org.jsoup.Connection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.jcr.Node;
import javax.jcr.Session;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/*
        package my.commands
        import info.magnolia.commands.*
        cm = info.magnolia.commands.CommandsManager.getInstance()
        command = cm.getCommand('my','import-recipies')
        command.execute(ctx)
*/

public class ImportRecipies extends BaseRepositoryCommand {

    @Override
    public boolean execute(Context context) throws Exception {

        int randomNum = ThreadLocalRandom.current().nextInt();

        HierarchyManager hm = context.getHierarchyManager("website");
        Content root = hm.getRoot();

        String recipiesCaption = Integer.toString(randomNum) + "_recipies";
        Content recipies = root.createContent(recipiesCaption, "mgnl:page");
        recipies.getMetaData().setTemplate("crescendo-magnolia-challenge:pages/recipes/recipes");
        recipies.createNodeData("title", "recipies");
        root.save();

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://www.johnsonville.com/recipes.top-rated.json");
        request.addHeader("accept", "application/json");
        HttpResponse response = client.execute(request);
        String json = IOUtils.toString(response.getEntity().getContent());
        JSONArray array = new JSONArray(json);

        // "Navigate" to the assets folder node
        AssetProviderRegistry assetProviderRegistry = Components.getComponent(AssetProviderRegistry.class);
        JcrAssetProvider jcrAssetProvider = (JcrAssetProvider) assetProviderRegistry.getProviderById(DamConstants.DEFAULT_JCR_PROVIDER_ID);
        JcrFolder assetFolder = (JcrFolder) jcrAssetProvider.getRootFolder();
        Node assetFolderNode = assetFolder.getNode();
        Session session = MgnlContext.getJCRSession(DamConstants.WORKSPACE);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            //Create recipe
            {

                String title = object.getString("title");
                String description = object.getString("description");
                String prepTime = object.getString("prepTime");
                String cookTime = object.getString("cookTime");
                String servingSize = object.getString("servingSize");
                String recipyCaption = Integer.toString(randomNum) + "_" + title;
                Content recipy = recipies.createContent(recipyCaption, "mgnl:page");
                recipy.getMetaData().setTemplate("crescendo-magnolia-challenge:pages/recipes/recipe");
                recipy.createNodeData("title", title);
                recipy.createNodeData("description", description);
                recipy.createNodeData("prepTime", prepTime);
                recipy.createNodeData("cookTime", cookTime);
                recipy.createNodeData("servingSize", servingSize);
            }
            //Load image to dam
            {
                String imageUrl = "http:" + object.getString("largeImageUrl");
//                String imageName = Integer.toString(randomNum) + "_" + imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                String imageName = Integer.toString(randomNum) + "_" + Integer.toString(ThreadLocalRandom.current().nextInt()) + imageUrl.substring(imageUrl.lastIndexOf("."));;
                String extension = imageUrl.substring(imageUrl.lastIndexOf(".") + 1);
                String mimeType = URLConnection.guessContentTypeFromName(imageName);
                URL url = new URL(imageUrl);
                long length = url.openConnection().getContentLength();
                BufferedImage image = ImageIO.read(url);
                int height = image.getHeight();
                int width = image.getWidth();
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(image, extension, os);
                InputStream imageStream = new ByteArrayInputStream(os.toByteArray());

                // Create asset node
                Node assetNode = JcrUtils.getOrAddNode(assetFolderNode, imageName, AssetNodeTypes.Asset.NAME);
                assetNode.setProperty(AssetNodeTypes.Asset.ASSET_NAME, imageName);

                // Create asset resource node
                Node assetResourceNode = JcrUtils.getOrAddNode(assetNode, AssetNodeTypes.AssetResource.RESOURCE_NAME, AssetNodeTypes.AssetResource.NAME);
                assetResourceNode.setProperty(AssetNodeTypes.AssetResource.DATA, session.getValueFactory().createBinary(imageStream));
                assetResourceNode.setProperty(AssetNodeTypes.AssetResource.FILENAME, imageName);
                assetResourceNode.setProperty(AssetNodeTypes.AssetResource.EXTENSION, imageName);
                assetResourceNode.setProperty(AssetNodeTypes.AssetResource.SIZE, Long.toString(length));
                assetResourceNode.setProperty(AssetNodeTypes.AssetResource.MIMETYPE, mimeType);
                assetResourceNode.setProperty(AssetNodeTypes.AssetResource.WIDTH, Long.toString(width));
                assetResourceNode.setProperty(AssetNodeTypes.AssetResource.HEIGHT, Long.toString(height));


            }
        }

        session.save();
        root.save();
        return false;
    }
}
