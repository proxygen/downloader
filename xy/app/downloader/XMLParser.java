package xy.app.downloader;

import com.sun.xml.internal.ws.util.xml.NodeListIterator;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashSet;

import java.util.Iterator;

import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

public class XMLParser {
    public XMLParser() {
        super();
    }

    public static void main(String[] args) {
        XMLParser parser = new XMLParser();
/*        try {
            //parser.parseXmlFile();
            parser.parseXmlFile2();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
*/
      String urlprefix = "https://precisionlms.ptc.com/content/coach_cp_e339983f-ec42-4643-a6b1-288ccdc1ad22//WBT-3147/";
      String id = "WBT-3147";
     // downPageAndResource(urlprefix + "module_03/LoadingaModelfromWindchill/exercise_01/book01.html", id);
     downPageAndResource(urlprefix + "module_03/LoadingaModelusingaStandaloneClient/exercise_01/book01.html", id);
     downPageAndResource(urlprefix + "module_03/Navigating3DModels/exercise_01/book01.html", id);
     downPageAndResource(urlprefix + "module_03/ViewingDrawingsandImages/exercise_01/book01.html", id);
     downPageAndResource(urlprefix + "module_03/WorkingwithModelDisplayOptions/exercise_01/book01.html", id);
    }
    
    private void parseXmlFile2() throws XPathExpressionException {
        //get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File("src/out3.xml");

            //parse using builder to get DOM representation of the XML file
            Document dom = db.parse(file);
            HashSet urlSet = new HashSet();
            getPageUrlRec("", dom.getFirstChild(), urlSet);

            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expUrls = xpath.compile("//page/@url");
            // XPathExpression expRoot = xpath.compile("/ptcuniversity/@url");
            // XPathExpression expId = xpath.compile("/ptcuniversity/course/@id[1]");
            NodeList nl = (NodeList)expUrls.evaluate(dom, XPathConstants.NODESET);
            
            String urlprefix = "https://precisionlms.ptc.com/content/coach_cp_e339983f-ec42-4643-a6b1-288ccdc1ad22//WBT-3147/";
            String id = "WBT-3147";
            for (int i = 0; i < nl.getLength(); i++) {
                String url = urlprefix + nl.item(i).getNodeValue();
                downPageAndResource(url, id);
            }
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void parseXmlFile() throws XPathExpressionException {
        //get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File("src/test.xml");

            //parse using builder to get DOM representation of the XML file
            Document dom = db.parse(file);
            HashSet urlSet = new HashSet();
            getPageUrlRec("", dom.getFirstChild(), urlSet);

            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expRoot = xpath.compile("/ptcuniversity/@url");
            XPathExpression expId = xpath.compile("/ptcuniversity/course/@id[1]");

            //            Node root = (Node) expRoot.evaluate(dom, XPathConstants.NODE);
            //                System.out.println(root.getNodeValue());
            String id = ((Node)expId.evaluate(dom, XPathConstants.NODE)).getNodeValue();
            //                System.out.println(id.getNodeValue());
            //            String url = "https://precisionlms.ptc.com/content/coach_cp_386e0336-a7ec-4ff4-9ccf-8d83797609a1//TRN-2141/abc/def.html";
            //            System.out.println(url.substring(url.indexOf(id)));
            for (Iterator iter = urlSet.iterator(); iter.hasNext(); ) {
                String url = (String)iter.next();
                downPageAndResource(url, id);
            }

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void getPageUrlRec(final String urlPrefix, Node node, HashSet urlSet) {
        String localUrl = urlPrefix;
        NamedNodeMap atts = node.getAttributes();
        if (atts != null) {
            Node nodeURL = atts.getNamedItem("url");
            if (nodeURL != null) {
                localUrl += nodeURL.getNodeValue();
            }
        }
        if (node.getNodeName().equals("page")) {
            //System.out.println(localUrl);
            urlSet.add(localUrl);
        } else {
            NodeList nodes = node.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node child = nodes.item(i);
                getPageUrlRec(localUrl, child, urlSet);
            }
        }
    }

    public static void downPageAndResource(String url, String courseID) {
        try {
            ArrayList<String> regs = new ArrayList<String>();
            regs.add("new SWFObject\\(\"(.*?)\"");
            regs.add("myFlashObject\\.movie=\"(.*)\"");
            regs.add("myFlashObject\\.FlashVars=\"audioClip=(.*)\"");
            regs.add(" [sS][rR][cC]=['\"](.*?)['\"]");
            // regs.add("<link.*?rel='stylesheet'.*?href='(.*?)'.*?type='text/css'.*?>");
            // regs.add("<link.*?href=\"(.*?)\".*?type=\"text/css\".*?>");
            regs.add("<link.*?href=\"(.*?)\".*?>");
            regs.add("id_img\\.src = \"(.*?)\"");
            regs.add("style=\".*?: url\\(\'(.*?)\'\\)");
            regs.add(".*swfobject\\.embedSWF\\( \"(.*?)\"");
            regs.add(".*swfobject\\.embedSWF.*?csPreloader: \"(.*?)\"");
            regs.add(".*swfobject\\.embedSWF.*?csConfigFile: \"(.*?)\"");
            
            String regUrl = "(.*/)";
            Set set = StringProcessor.getContentText(url, regUrl);
            String baseUrl = (String)set.iterator().next();
            byte[] buf = Downloader.download2(url, LOCAL_ROOT_PATH + url.substring(url.indexOf(courseID)));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buf)));
            String line = null;
            while ((line = reader.readLine()) != null) {
                Set s = StringProcessor.getContentText(line, regs);
                for (Iterator iter = s.iterator(); iter.hasNext(); ) {
                    String tmp = baseUrl + iter.next();
                    if (tmp.endsWith(".js")) {
                        downJsAndResource(tmp, courseID);
                    } else if (tmp.endsWith(".xml")) {
                        downXmlAndResource(tmp, courseID);
                    } else {
                        Downloader.download(tmp, LOCAL_ROOT_PATH + tmp.substring(tmp.indexOf(courseID)));
                        System.out.println("--" + tmp);
                        System.out.println("----" + LOCAL_ROOT_PATH + url.substring(url.indexOf(courseID)));
                    }
                }
            }

            System.out.println(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void downJsAndResource(String url, String courseID) {
        try {
            ArrayList<String> regs = new ArrayList<String>();
            regs.add("id_img\\.src = \"(.*?)\"");
            
            String regUrl = "(.*/)";
            Set set = StringProcessor.getContentText(url, regUrl);
            String baseUrl = (String)set.iterator().next();
            byte[] buf = Downloader.download2(url, LOCAL_ROOT_PATH + url.substring(url.indexOf(courseID)));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buf)));
            String line = null;
            while ((line = reader.readLine()) != null) {
                Set s = StringProcessor.getContentText(line, regs);
                for (Iterator iter = s.iterator(); iter.hasNext(); ) {
                    String tmp = baseUrl + iter.next();
                    if (tmp.endsWith(".js")) {
                        downJsAndResource(tmp, courseID);
                    } else if (tmp.endsWith(".xml")) {
                        downXmlAndResource(tmp, courseID);
                    } else {
                        Downloader.download(tmp, LOCAL_ROOT_PATH + tmp.substring(tmp.indexOf(courseID)));
                        System.out.println("--" + tmp);
                        System.out.println("----" + LOCAL_ROOT_PATH + url.substring(url.indexOf(courseID)));
                    }
                }
            }

            System.out.println(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void downXmlAndResource(String url, String courseID) {
        try {
            ArrayList<String> regs = new ArrayList<String>();
            regs.add("<uri>(.*?)</uri>");
            
            String regUrl = "(.*/)";
            Set set = StringProcessor.getContentText(url, regUrl);
            String baseUrl = (String)set.iterator().next();
            byte[] buf = Downloader.download2(url, LOCAL_ROOT_PATH + url.substring(url.indexOf(courseID)));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buf)));
            String line = null;
            while ((line = reader.readLine()) != null) {
                Set s = StringProcessor.getContentText(line, regs);
                for (Iterator iter = s.iterator(); iter.hasNext(); ) {
                    String tmp = baseUrl + iter.next();
                    if (tmp.endsWith(".js")) {
                        downJsAndResource(tmp, courseID);
                    } else if (tmp.endsWith(".xml")) {
                        downXmlAndResource(tmp, courseID);
                    } else {
                        Downloader.download(tmp, LOCAL_ROOT_PATH + tmp.substring(tmp.indexOf(courseID)));
                        System.out.println("--" + tmp);
                        System.out.println("----" + LOCAL_ROOT_PATH + url.substring(url.indexOf(courseID)));
                    }
                }
            }

            System.out.println(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String LOCAL_ROOT_PATH = "C:/ptcuniveraity/";
}
