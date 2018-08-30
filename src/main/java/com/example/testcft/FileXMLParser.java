package com.example.testcft;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

public class FileXMLParser {
    private static List<Map<String, String>> fields;
    private static int counter = 0;

    public static List<Map<String, String>> doParse(File inputFile) throws DocumentException {
        SAXReader reader = new SAXReader();
        fields = new ArrayList<Map<String, String>>();


        System.out.println("try parse: " + inputFile.getName());

        Document document = reader.read(inputFile);

        getNodes(document, "/Form/Groups/Group/Fields/Field");
        getNodes(document, "/Form/Groups/Group/Groups/Group/Fields/Field");

        counter++;

         return fields;
    }

    private static void getNodes(Document document, String xpath) {
        List<Node> nodes = document.selectNodes(xpath);

        for (Node node : nodes) {
            List<Node> innerNodes = node.selectNodes("*");
            Map<String, String> params = new HashMap<String, String>();
            for (Node innNode : innerNodes) {
                params.put(innNode.getName(), innNode.getStringValue());
            }
            fields.add(params);
        }
    }

    public static String getStat(){
        return "Parsed files: " + counter;
    }
}
