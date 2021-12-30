package io.github.attt.json;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author atpexgo
 */
public class JsonTest {

    @Test
    public void TestDynamicString() {
        DynamicString string = new DynamicString("abcdefg");
        System.out.println(string);
        System.out.println(string.getChar(0));
        System.out.println(string.getString(0, 2));
        string.slice(2, 6);
        System.out.println(string.getString(0, 1));
        System.out.println(string);
        for (char ch : string) {
            System.out.println(ch);
        }

        string = new DynamicString("abcd");
        string.slice(1);
        System.out.println(string);
        System.out.println(string.length());
        string.slice(1);
        System.out.println(string);
        System.out.println(string.length());
        string.slice(1);
        System.out.println(string);
        System.out.println(string.length());
    }

    @Test
    public void TestLexer() {
        JsonLexer lexer = new JsonLexer();
        LexisList lexis = lexer.lex("{\"a\":[{\"b\":'va'},{'c':1.1},{'d':2}]}");
        System.out.println(lexis);
    }


    @Test
    public void TestParser() {
        JsonLexer lexer = new JsonLexer();
        LexisList lexis = lexer.lex("{\"a\":[{\"b\":'va'},{'c':1.1},{'d':2}]}");
        JsonParser parser = new JsonParser();
        Object obj = parser.parse(lexis);
        System.out.println(obj);
    }

    @Test
    public void TestEditionAfterParsing() {
        Object obj = JsonCompiler.compile("{\"a\":[{\"b\":'va'},{'c':1.1},{'d':2}], 'e':[1,2,3,4]}");
        JsonObj jsonObj = ((JsonObj) obj);
        jsonObj.put("newKey111", "hey!!!!");
        System.out.println(obj);
        JsonArray jsonArray = jsonObj.get("a", JsonArray.class);
        JsonObj newObj = new JsonObj();
        newObj.put("newObjKey", null);
        jsonArray.add(newObj);
        System.out.println(obj);
    }

    @Test
    public void TestComplexJsonParsing() {
        Object compiled = JsonCompiler.compile("{\"glossary\":{\"title\":\"example glossary\",\"GlossDiv\":{\"title\":\"S\",\"GlossList\":{\"GlossEntry\":{\"ID\":\"SGML\",\"SortAs\":\"SGML\",\"GlossTerm\":\"Standard Generalized Markup Language\",\"Acronym\":\"SGML\",\"Abbrev\":\"ISO 8879:1986\",\"GlossDef\":{\"para\":\"A meta-markup language, used to create markup languages such as DocBook.\",\"GlossSeeAlso\":[\"GML\",\"XML\"]},\"GlossSee\":\"markup\"}}}}}");
        System.out.println(compiled);

        compiled = JsonCompiler.compile("{\"widget\":{\"debug\":\"on\",\"window\":{\"title\":\"Sample Konfabulator Widget\",\"name\":\"main_window\",\"width\":500,\"height\":500},\"image\":{\"src\":\"Images/Sun.png\",\"name\":\"sun1\",\"hOffset\":250,\"vOffset\":250,\"alignment\":\"center\"},\"text\":{\"data\":\"Click Here\",\"size\":36,\"style\":\"bold\",\"name\":\"text1\",\"hOffset\":250,\"vOffset\":100,\"alignment\":\"center\",\"onMouseUp\":\"sun1.opacity = (sun1.opacity / 100) * 90;\"}}}");
        System.out.println(compiled);

        compiled = JsonCompiler.compile("{\"web-app\":{\"servlet\":[{\"servlet-name\":\"cofaxCDS\",\"servlet-class\":\"org.cofax.cds.CDSServlet\",\"init-param\":{\"configGlossary:installationAt\":\"Philadelphia, PA\",\"configGlossary:adminEmail\":\"ksm@pobox.com\",\"configGlossary:poweredBy\":\"Cofax\",\"configGlossary:poweredByIcon\":\"/images/cofax.gif\",\"configGlossary:staticPath\":\"/content/static\",\"templateProcessorClass\":\"org.cofax.WysiwygTemplate\",\"templateLoaderClass\":\"org.cofax.FilesTemplateLoader\",\"templatePath\":\"templates\",\"templateOverridePath\":\"\",\"defaultListTemplate\":\"listTemplate.htm\",\"defaultFileTemplate\":\"articleTemplate.htm\",\"useJSP\":false,\"jspListTemplate\":\"listTemplate.jsp\",\"jspFileTemplate\":\"articleTemplate.jsp\",\"cachePackageTagsTrack\":200,\"cachePackageTagsStore\":200,\"cachePackageTagsRefresh\":60,\"cacheTemplatesTrack\":100,\"cacheTemplatesStore\":50,\"cacheTemplatesRefresh\":15,\"cachePagesTrack\":200,\"cachePagesStore\":100,\"cachePagesRefresh\":10,\"cachePagesDirtyRead\":10,\"searchEngineListTemplate\":\"forSearchEnginesList.htm\",\"searchEngineFileTemplate\":\"forSearchEngines.htm\",\"searchEngineRobotsDb\":\"WEB-INF/robots.db\",\"useDataStore\":true,\"dataStoreClass\":\"org.cofax.SqlDataStore\",\"redirectionClass\":\"org.cofax.SqlRedirection\",\"dataStoreName\":\"cofax\",\"dataStoreDriver\":\"com.microsoft.jdbc.sqlserver.SQLServerDriver\",\"dataStoreUrl\":\"jdbc:microsoft:sqlserver://LOCALHOST:1433;DatabaseName=goon\",\"dataStoreUser\":\"sa\",\"dataStorePassword\":\"dataStoreTestQuery\",\"dataStoreTestQuery\":\"SET NOCOUNT ON;select test='test';\",\"dataStoreLogFile\":\"/usr/local/tomcat/logs/datastore.log\",\"dataStoreInitConns\":10,\"dataStoreMaxConns\":100,\"dataStoreConnUsageLimit\":100,\"dataStoreLogLevel\":\"debug\",\"maxUrlLength\":500}},{\"servlet-name\":\"cofaxEmail\",\"servlet-class\":\"org.cofax.cds.EmailServlet\",\"init-param\":{\"mailHost\":\"mail1\",\"mailHostOverride\":\"mail2\"}},{\"servlet-name\":\"cofaxAdmin\",\"servlet-class\":\"org.cofax.cds.AdminServlet\"},{\"servlet-name\":\"fileServlet\",\"servlet-class\":\"org.cofax.cds.FileServlet\"},{\"servlet-name\":\"cofaxTools\",\"servlet-class\":\"org.cofax.cms.CofaxToolsServlet\",\"init-param\":{\"templatePath\":\"toolstemplates/\",\"log\":1,\"logLocation\":\"/usr/local/tomcat/logs/CofaxTools.log\",\"logMaxSize\":\"\",\"dataLog\":1,\"dataLogLocation\":\"/usr/local/tomcat/logs/dataLog.log\",\"dataLogMaxSize\":\"\",\"removePageCache\":\"/content/admin/remove?cache=pages&id=\",\"removeTemplateCache\":\"/content/admin/remove?cache=templates&id=\",\"fileTransferFolder\":\"/usr/local/tomcat/webapps/content/fileTransferFolder\",\"lookInContext\":1,\"adminGroupID\":4,\"betaServer\":true}}],\"servlet-mapping\":{\"cofaxCDS\":\"/\",\"cofaxEmail\":\"/cofaxutil/aemail/*\",\"cofaxAdmin\":\"/admin/*\",\"fileServlet\":\"/static/*\",\"cofaxTools\":\"/tools/*\"},\"taglib\":{\"taglib-uri\":\"cofax.tld\",\"taglib-location\":\"/WEB-INF/tlds/cofax.tld\"}}}");
        System.out.println(compiled);
    }

    @Test
    public void TestTypedCompiling() {
        JsonObj jsonObj = JsonCompiler.compileObject("{\"glossary\":{\"title\":\"example glossary\",\"GlossDiv\":{\"title\":\"S\",\"GlossList\":{\"GlossEntry\":{\"ID\":\"SGML\",\"SortAs\":\"SGML\",\"GlossTerm\":\"Standard Generalized Markup Language\",\"Acronym\":\"SGML\",\"Abbrev\":\"ISO 8879:1986\",\"GlossDef\":{\"para\":\"A meta-markup language, used to create markup languages such as DocBook.\",\"GlossSeeAlso\":[\"GML\",\"XML\"]},\"GlossSee\":\"markup\"}}}}}");
        System.out.println(jsonObj);

        JsonArray jsonArray = JsonCompiler.compileArray("[1,2,'a',10E-4]");
        System.out.println(jsonArray);
    }

    @Test
    public void TestScan() {
        Json json = (Json) JsonCompiler.compile("{\"testKey\":[{\"testKey\":2.233},\"testKey\"],\"testKey1\":123,\"testKey2\":{\"testKey\":\"hello\"}}");
        List<Object> objects = json.scan("testKey", false);
        System.out.println(objects);
        objects = json.scan("testKey", true);
        System.out.println(objects);
    }
}
