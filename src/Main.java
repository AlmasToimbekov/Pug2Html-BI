import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class Main {

    public static void main(String[] args) {
        try {
            String result = Main.readFile("./file.txt", US_ASCII);
            String[] array = result.split("[\\r?\\n]+");
            for (String str: array) {
                System.out.println(str);
            }
        } catch (IOException error) {
            System.out.println(error.toString());
        }
    }

    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}