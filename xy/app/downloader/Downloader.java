package xy.app.downloader;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.URL;
import java.net.URLConnection;

public class Downloader {
    public Downloader() {
        super();
    }

    public static void main(String[] args) throws Exception {
        byte[] buf = download2("http://www.luoqiu.com/html/58/58718/8112353.html", "C:/8112353.html");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buf)));
        String line = null;
        while((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

      /**
       * Download file to local machine
       *
       * @param urlString
       *          URL of file to download
       * @param filename
       *          local path
       * @throws Exception
       *           Exception
       */
      public static void download(String urlString, String filename) throws Exception {
        // URL
        URL url = new URL(urlString);
        // Open connection
        URLConnection con = url.openConnection();
        // inputstream
        InputStream is = con.getInputStream();

        // 1K buffer
        byte[] bs = new byte[1024];
        // length of data receved
        int len;
        // outputstream
        FileProcessor.touch(filename);
        OutputStream os = new FileOutputStream(filename);
        // start read
        while ((len = is.read(bs)) != -1) {
          os.write(bs, 0, len);
        }
        // done, close the connection
        os.close();
        is.close();
      }
      
      public static byte[] download2(String urlString, String filename) throws Exception {
          StringBuffer sb = new StringBuffer();
        // URL
        URL url = new URL(urlString);
        // Open connection
        URLConnection con = url.openConnection();
        // inputstream
        InputStream is = con.getInputStream();

        // 1K buffer
        byte[] bs = new byte[1024];
        // length of data receved
        int len;
        // outputstream
        FileProcessor.touch(filename);
        OutputStream os = new FileOutputStream(filename);
        ByteArrayOutputStream os2 = new ByteArrayOutputStream();
        // start read
        while ((len = is.read(bs)) != -1) {
          os.write(bs, 0, len);
          os2.write(bs, 0, len);
        }
        byte[] result = os2.toByteArray();
        
        // done, close the connection
        os.close();
        os2.close();
        is.close();
        
        return result;
      }
}
