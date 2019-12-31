import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class Main {

    public static void main(String[] args) {
        String[] textArr = getFileTextArr("./file.txt");
        TagTree tagTree = new TagTree(textArr);
    }

    static String[] getFileTextArr(String path) {
        String fileText;
        String[] textArr;

        try {
            fileText = Main.readFile(path, US_ASCII);
            textArr = fileText.split("[\\r?\\n]+");
            return textArr;
        } catch (IOException error) {
            System.out.println(error.toString());
        }
        return null;
    }

    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}