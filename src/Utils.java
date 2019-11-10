import java.io.*;
import java.util.Scanner;

public class Utils {

    /*
    public static String loadResource(String fileName) throws Exception {
        String result;
        try (InputStream in = Class.forName(Utils.class.getName()).getResourceAsStream(fileName);
             Scanner scanner = new Scanner(in, "UTF-8")) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }
    */

    public static String loadShader (String path) {
        var builder = new StringBuilder();
        try (InputStream in = new FileInputStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load a shader file!"
                    + System.lineSeparator() + ex.getMessage());
        }
        //CharSequence source = builder.toString();
        var source = builder.toString();
        return source;
    }

}
