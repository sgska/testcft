package com.example.testcft;

import org.dom4j.Document;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class TestCft {
    public static void main(String[] args) {
        String findFolder = "src/main/resources/xml/in";
        String outFolder = "src/main/resources/xml/out";
        String sendURL = "http://demo9129248.mockable.io/";


        FileXMLScanner scanner = new FileXMLScanner(findFolder);
        FileXMLWriter writer = new FileXMLWriter(outFolder);

        Thread keyboardListener = new Thread(){
            @Override
            public void run() {
                try {
                    new InputStreamReader(System.in).read();
                } catch (Exception e) {}
            }
        };

        keyboardListener.start();

        System.out.println("Program listen directory: " + new File(findFolder).getAbsolutePath() + "\r\n" +
        "Program out directory: " + new File(outFolder).getAbsolutePath());

        while (keyboardListener.isAlive()) {
            try {
                Thread.sleep(500);

                if (scanner.hasNext()) {
                    File parseFile = scanner.getNext();
                    List<Map<String, String>> fields = FileXMLParser.doParse(parseFile);
                    Document doc = writer.writeFile(parseFile.getName(), fields);
                    DataPostSender.send(doc, sendURL);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        System.out.println(scanner.getStat());
        System.out.println(FileXMLParser.getStat());
        System.out.println(writer.getStat());
        System.out.println(DataPostSender.getStat());

    }
}
