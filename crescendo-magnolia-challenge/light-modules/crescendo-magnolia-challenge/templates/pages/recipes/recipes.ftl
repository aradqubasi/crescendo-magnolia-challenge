<!DOCTYPE html>
<html>
<head>
        [@cms.init /]
</head>
<body>
<h1>${content.title!}</h1>
        [@cms.area name="main" /]
<ul>
[#list cmsfn.children(content, "mgnl:page") as child ]
   <li> <a href="${cmsfn.link(child)}">${child.title!}</a> </li>
[/#list]
</ul>
</body>
</html>