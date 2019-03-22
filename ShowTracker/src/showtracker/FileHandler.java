package showtracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;

public class FileHandler {
    public static void decompressGzip(File fiInput, File fiOutput) {
        try (GZIPInputStream stInput = new GZIPInputStream(new FileInputStream(fiInput))){
            try (FileOutputStream stOutput = new FileOutputStream(fiOutput)){
                byte[] buffer = new byte[1024];
                int len;
                while((len = stInput.read(buffer)) != -1){
                    stOutput.write(buffer, 0, len);
                }
            }
        } catch (Exception e) {
            System.out.println("DatabaseReader.decompressGzip: " + e);
        }
    }
}
