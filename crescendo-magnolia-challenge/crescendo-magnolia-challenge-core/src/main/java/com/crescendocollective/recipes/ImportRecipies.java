package com.crescendocollective.recipes;

import info.magnolia.cms.core.Content;
import info.magnolia.cms.core.HierarchyManager;
import info.magnolia.commands.impl.BaseRepositoryCommand;
import info.magnolia.context.Context;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Connection;


public class ImportRecipies extends BaseRepositoryCommand {

    @Override
    public boolean execute(Context context) throws Exception {
//
//        HierarchyManager hm = context.getHierarchyManager("website");
//        Content root = hm.getRoot();
//
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
//        HttpPost request = new HttpPost("http://www.johnsonville.com/recipes.top-rated.json'");
//        StringEntity params =new StringEntity("details={\"name\":\"myname\",\"age\":\"20\"} ");
//        request.addHeader("content-type", "application/json");
//        request.setEntity(params);
//        HttpResponse response = httpClient.execute(request);
//
//        response.getEntity().getContent();
        /*

        HTTPBuilder http = new HTTPBuilder("http://www.johnsonville.com/recipes.top-rated.json", ContentType.JSON);
        http.headers.Accept = ContentType.JSON
        http.parser[ContentType.JSON] = http.parser.'application/json'
        http.request(Connection.Method.GET) {
            response.success = { resp, json ->
                    slurped = new JsonSlurper().parseText(JsonOutput.toJson(json))
                    slurped.size()
            }
        }

        root = hm.getRoot()
        slurped.each { piece ->
                content = root.createContent(piece.title, "mgnl:page")
            content.metaData.setTemplate("crescendo-magnolia-challenge:pages/recipes/recipe")
            content.createNodeData("title", piece.title)
            content.createNodeData("description", piece.description)
            content.createNodeData("prepTime", piece.prepTime)
            content.createNodeData("cookTime", piece.cookTime)
            content.createNodeData("servingSize", piece.servingSize)
            content.getProperties()
        }
        root.save()
        */
        return false;
    }
}
