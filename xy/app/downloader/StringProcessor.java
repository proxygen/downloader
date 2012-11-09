package xy.app.downloader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringProcessor {
    public StringProcessor() {
        super();
    }

    public static void main(String[] args) {
        StringProcessor class1 = new StringProcessor();
        String str1 = "myFlashObject.movie=\"images/audio.swf\"";
        String str2 = "myFlashObject.FlashVars=\"audioClip=audio/slide-10.mp3\"";
        String str3 = "<script src='activate_flash.js' language='JAVASCRIPT'></script>";
        String str4 = "<script src='content.js'></script>";
        String str5 =
            "</script></div><div style='margin-left: 0pt; margin-right: 0pt; text-indent: 0pt; margin-top: 0pt; font-family: Arial; font-size: 12pt; color: #800000; '><img alt='Presentation' src=\"graphics/Slide10.GIF\"><table style='width: 800; border-collapse: collapse; border: medium none' cellspacing='0' cellpadding='0'><tr><td height='3' width='798' style='background-color: #FFFFFF' class='comment'></td><tr><td height='18' valign='top' width='788' style=\"background-image: url('images/bg.gif')\" class='comment' align='right'><b><font color='#FFFFFF' style='font-family: verdena; font-size: 10pt;'>if you prefer to read Lecture Notes, <a href='#' onclick=\"return hideShow('search_options','search_arrow','Less','More');\"><font color='#FFFFFF' style='font-family: verdena; font-size: 10pt;'>Click here </font><img border='0' alt='Expanded Text Area' src='images/arrow_down.gif' id='search_arrow' align='middle'><font color='#FFFFFF'></font></a></font></b></td></tr></tr><tr><td bgcolor='#FFFFFF' width='778' id='search_options'><div style='margin-left: 0pt; margin-right: 0pt; text-indent: 0pt; font-family: sans-serif; color: #0000FF; text-align: left; text-align-last: relative; hyphenate: false; keep-with-next.within-column: 7; keep-together.within-column: 7; line-height: 1.1; margin-top: 1.5em; margin-bottom: 1em; font-weight: bold; font-family: Verdana; font-weight: normal; font-size: 0.1pt; color: #FFFFFF; margin-top: 0px; margin-bottom: 5pt; ' ch:class='title' id='styler-id1.4.9.2.1.2.1.1'>Windchill System Components: The Client Tier</div><div style='margin-left: 0pt; margin-right: 0pt; text-indent: 0pt; margin-top: 8pt; font-family: Verdana; font-size: 11.3pt; color: #666666; text-align: left; margin-top: 2px; margin-bottom: 5.00px; '>This diagram gives a detailed view of the three-tiered Windchill architecture. This diagram also includes an expanded view of several third-party components needed for Windchill environment to function properly in the test or production implementations.</div><div style='margin-left: 0pt; margin-right: 0pt; text-indent: 0pt; margin-top: 8pt; font-family: Verdana; font-size: 11.3pt; color: #666666; text-align: left; margin-top: 2px; margin-bottom: 5.00px; '>Instead of using a standalone Web browser to connect to the server, users may use a CAD authoring tool, such as Pro/ENGINEER. Pro/ENGINEER has an embedded Web browser, which can be used to navigate through different areas of the software environment. Other CAD tools, such as AutoCAD, CATIA, Unigraphics, and SolidWorks, require the Unified Work Group Manager to interact with Windchill. The Unified Work Group Manager acts as the middle layer between third-party CAD tools and Windchill.</div></td></tr></table></div><div class=' x-title-5-0' ch:class='title' id='styler-id1.4.9.3'>Windchill System Components: The Client Tier</div></div>";
        
        ArrayList<String> strs = new ArrayList<String>();
        strs.add(str1);
        strs.add(str2);
        strs.add(str3);
        strs.add(str4);
        strs.add(str5);
        strs.add("https://precisionlms.ptc.com/content/coach_cp_386e0336-a7ec-4ff4-9ccf-8d83797609a1//TRN-2141/module_01/lecture/images/bottom_bar.gif");
        strs.add("<link rel='stylesheet' href='viewer.css' type='text/css'>");

        ArrayList<String> regs = new ArrayList<String>();
        regs.add("myFlashObject\\.movie=\"(.*)\"");
        regs.add("myFlashObject\\.FlashVars=\"audioClip=(.*)\"");
        regs.add(" [sS][rR][cC]=['\"](.*?)['\"]");
        regs.add("(.*/)");
        //regs.add("<link.*?rel='stylesheet'.*?href='(.*?)'.*?type='text/css'.*?>");
        regs.add("<link.*?href=\"(.*?)\".*?type=\"text/css\".*?>");
        //regs.add (" [sS][rR][cC]=['\"](.*?(mp3|MP3|js|JS|gif|GIF|swf|SWF))['\"]");

        for (Iterator<String> iter = strs.iterator(); iter.hasNext(); ) {
            Set r = getContentText(iter.next(), regs);
            System.out.println(r);
        }
    }

    public static HashSet<String> getContentText(String str, ArrayList<String> regs) {
        HashSet<String> result = new HashSet<String>();
        for (Iterator<String> iter = regs.iterator(); iter.hasNext(); ) {
            String regex = iter.next();

            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
            while (m.find()) {
                result.add(m.group(1));
            }
        }
        return result;
    }

    public static HashSet<String> getContentText(String str, String regex) {
        HashSet<String> result = new HashSet<String>();

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        while (m.find()) {
            result.add(m.group(1));
        }

        return result;
    }
}
