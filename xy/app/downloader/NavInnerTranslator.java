package xy.app.downloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.InputStreamReader;

import java.util.HashSet;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import org.xml.sax.SAXException;

public class NavInnerTranslator {
    public NavInnerTranslator() {
        super();
    }

    public static void main(String[] args) {
        NavInnerTranslator navInnerTranslator = new NavInnerTranslator();
        String path = "src/designerNavInner.xml";
        System.out.println(FileProcessor.readTextFile(path));
    }

    public static void translate() {
        FileInputStream in;
        try {
            String path = "src/designerNavInner.xml";
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            
        } catch (FileNotFoundException e) {
        }
    }
}
