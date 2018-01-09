<!DOCTYPE html>
<html>
    <head>
        [@cms.init /]
    </head>
    <body>
        <h1>${content.title!}</h1>
        [#assign currentNode = cmsfn.asJCRNode(content)]
        [#assign rootPageNode = cmsfn.root(currentNode, "mgnl:page")!]
        [#if rootPageNode??]
            [#assign recipePages=cmsfn.contentListByTemplateId(rootPageNode, "crescendo-magnolia-challenge:pages/recipe")]
            [#if recipePages??]
                [#list recipePages as child ]
                    ${cmsfn.asContentMap(child).title!},
                [/#list]
            [/#if]
        [/#if]
    </body>
</html>