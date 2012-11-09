package xy.app.downloader;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileProcessor {
    public FileProcessor() {
        super();
    }

    public static void main(String[] args) {
        FileProcessor fileProcessor = new FileProcessor();
        String path = "c:/aa/bb/cc/tt.txt";
        try {
            touch(path);
        } catch (IOException e) {
        }
    }
    
    public static void touch(String path) throws IOException {
        File file = new File(path);  
        file.getParentFile().mkdirs();  
        file.createNewFile();
    }
    
    public static StringBuffer readTextFile(String path) {
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }

        return sb;
    }
}
