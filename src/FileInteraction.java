import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class FileInteraction {
    static String[] getFileTextArr(String path) {
        String fileText;
        String[] textArr;

        try {
            fileText = readFile(path, US_ASCII);
            textArr = fileText.split("[\\r?\\n]+");
            return textArr;
        } catch (IOException error) {
            System.out.println(error.toString());
            System.exit(1);
        }
        return null;
    }

    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    static void writeToFile(String filename, String text) {
        try (PrintWriter out = new PrintWriter(filename)) {
            out.println(text);
        } catch (IOException error) {
            System.out.println(error.toString());
            System.exit(1);
        }
    }
}
