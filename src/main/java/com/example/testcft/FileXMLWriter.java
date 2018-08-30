package com.example.testcft;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class FileXMLWriter {
    private String outFolder;
    private static int counter = 0;

    public FileXMLWriter(String outFolder) {
        this.outFolder = outFolder;
    }


    public Document writeFile(String fileName, List<Map<String,String>> fields) {
        Document document = createDocument(fields);

        try {
            String fullFileName = outFolder + "/" + "output_file_" + fileName;
            FileWriter outFile = new FileWriter(fullFileName);
            document.write(outFile);
            outFile.close();
            System.out.println("created file: " + fullFileName);
            counter++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    private Document createDocument(List<Map<String,String>> fields) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("Data").addText("\r\n");

        for (Map<String, String> field : fields) {
            root.addText("\t");

            Element newElement = root.addElement(field.get("type"));

            for (Map.Entry<String, String> entry : field.entrySet()) {
                if (!entry.getKey().equals("type")) {
                    String value = entry.getValue();

                    if (entry.getKey().equals("digitOnly") ||
                            entry.getKey().equals("required") ||
                            entry.getKey().equals("readOnly")) {

                        switch (Integer.parseInt(entry.getValue())) {
                            case 1:
                                value = "true";
                                break;
                            case 0:
                                value = "false";
                                break;
                        }
                    }


                    if (newElement.getName().equals("Sum") && entry.getKey().equals("value")) {
                        value = new DecimalFormat("#0.00").format(Double.parseDouble(value)).replace(',', '.');
                    }

                    if (newElement.getName().equals("Address") && entry.getKey().equals("value")) {
                        String[] address = value.split(",");
                        newElement.addAttribute("street", address[0]);
                        newElement.addAttribute("house", address[1]);
                        newElement.addAttribute("flat", address[2]);
                    } else {
                        newElement.addAttribute(entry.getKey(), value);
                    }
                }
            }

        root.addText("\r\n");
        }

        return document;
    }

    public String getStat() {
        return "Writed files: " + counter;
    }
}
