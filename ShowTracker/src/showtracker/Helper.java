package showtracker;

import java.io.*;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class Helper {
	public static final DecimalFormat df = new DecimalFormat("0.#");
	
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

    public static void writeToFile(Object o, String file) {
        File fiWrite = new File(file);
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fiWrite)))) {
            oos.writeObject(o);
        } catch (Exception e) {
            System.out.println("Helper.writeToFile: " + e);
        }
    }

    public static Object readFromFile(String file) {
        File fiRead= new File(file);
        Object o = null;
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fiRead)))) {
            o = ois.readObject();
        } catch (Exception e) {
            System.out.println("Helper.readFromFile: " + e);
        }
        return o;
    }

    public static String decodeUnicode(String input) {
        if (input != null) {
            Pattern pattern = Pattern.compile("\\\\u.{4}");
            Matcher matcher = pattern.matcher(input);
            while (matcher.find()) {
                String s = Character.toString((char) matcher.group().getBytes()[0]);
                input.replaceAll(matcher.group(), s);
            }
        }
        return input;
    }
}
