package showtracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;

public class FileHandler {
    public static void decompressGzip(String input) {
        File fiInput = new File(input);
        String stOutput = fiInput.getName().substring(0, fiInput.getName().lastIndexOf("."));
        File fiOutput = new File(stOutput);
        try (GZIPInputStream stInput = new GZIPInputStream(new FileInputStream(fiInput))){
            try (FileOutputStream fos = new FileOutputStream(fiOutput)){
                byte[] buffer = new byte[1024];
                int len;
                while((len = stInput.read(buffer)) != -1){
                    fos.write(buffer, 0, len);
                }
            }
        } catch (Exception e) {
            System.out.println("DatabaseReader.decompressGzip: " + e);
        }
    }
}
