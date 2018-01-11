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
import org.apache.jackrabbit.commons.JcrUtils;

import javax.imageio.ImageIO;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//        package my.commands
//        import info.magnolia.commands.*
//
//        cm = info.magnolia.commands.CommandsManager.getInstance()
//        command = cm.getCommand('my','import-image')
//        ctx.setAttribute('name', 'amazing-muffin-cups-3.png', 1)
//        ctx.setAttribute('url', 'http://www.johnsonville.com/recipe/amazing-muffin-cups/image/1885.jpg', 1)
//        command.execute(ctx)

public class ImportImage extends BaseRepositoryCommand {

    @Override
    public boolean execute(Context ctx) {

        Map<String, Object> m = ctx.getAttributes();
        String imageUrl = (String)m.get("url");
        String imageName = (String)m.get("name");
        String extension = imageName.substring(0, imageName.indexOf(".") + 1);
        String mimeType = URLConnection.guessContentTypeFromName(imageName);

        try {
            URL url = new URL(imageUrl);
            long length = url.openConnection().getContentLength();
            BufferedImage image = ImageIO.read(url);
            int height = image.getHeight();
            int width = image.getWidth();
//            InputStream image = url.openStream();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, extension, os);
            InputStream imageStream = new ByteArrayInputStream(os.toByteArray());

            // "Navigate" to the assets folder node
            AssetProviderRegistry assetProviderRegistry = Components.getComponent(AssetProviderRegistry.class);
            JcrAssetProvider jcrAssetProvider = (JcrAssetProvider) assetProviderRegistry.getProviderById(DamConstants.DEFAULT_JCR_PROVIDER_ID);
//            JcrFolder assetFolder = (JcrFolder) jcrAssetProvider.getFolder("/");
            JcrFolder assetFolder = (JcrFolder) jcrAssetProvider.getRootFolder();
            Node assetFolderNode = assetFolder.getNode();

            // Create asset node
            Node assetNode = JcrUtils.getOrAddNode(assetFolderNode, imageName, AssetNodeTypes.Asset.NAME);
            assetNode.setProperty(AssetNodeTypes.Asset.ASSET_NAME, imageName);

            Session session = MgnlContext.getJCRSession(DamConstants.WORKSPACE);

            // Create asset resource node
            Node assetResourceNode = JcrUtils.getOrAddNode(assetNode, AssetNodeTypes.AssetResource.RESOURCE_NAME, AssetNodeTypes.AssetResource.NAME);
            assetResourceNode.setProperty(AssetNodeTypes.AssetResource.DATA, session.getValueFactory().createBinary(imageStream));
            assetResourceNode.setProperty(AssetNodeTypes.AssetResource.FILENAME, imageName);
            assetResourceNode.setProperty(AssetNodeTypes.AssetResource.EXTENSION, imageName);
            assetResourceNode.setProperty(AssetNodeTypes.AssetResource.SIZE, Long.toString(length));
            assetResourceNode.setProperty(AssetNodeTypes.AssetResource.MIMETYPE, mimeType);
            assetResourceNode.setProperty(AssetNodeTypes.AssetResource.WIDTH, Long.toString(width));
            assetResourceNode.setProperty(AssetNodeTypes.AssetResource.HEIGHT, Long.toString(height));

//            String publisher = (String)m.get("publisher");
//            assetResourceNode.setProperty("publisher", (String)m.get("publisher"));
//            assetResourceNode.setProperty("caption", (String)m.get("caption"));
//            assetResourceNode.setProperty("languages", (String)m.get("languages"));
//            assetResourceNode.setProperty("name", (String)m.get("name"));
//            assetResourceNode.setProperty("contributor", (String)m.get("contributor"));
//            assetResourceNode.setProperty("description", (String)m.get("description"));
//            assetResourceNode.setProperty("relation", (String)m.get("relation"));
//            assetResourceNode.setProperty("source", (String)m.get("source"));
//            assetResourceNode.setProperty("title", (String)m.get("title"));
//            assetResourceNode.setProperty("copyright", (String)m.get("copyright"));
//            assetResourceNode.setProperty("subject", (String)m.get("subject"));

            session.save();
            return true;
        } catch (RepositoryException e) {
            e.printStackTrace();
            return false;
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }


    }

}