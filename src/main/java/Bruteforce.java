import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Bruteforce {
    private final CaesarCipher caesar = new CaesarCipher();
    private static final int MAX_LENGTH_WORD = 28;

    public void bruteforce() {
        Util.writeMessage("Введите путь для его рассшифровки:");
        String path = Util.readString();

        Path bruteforce = Util.buildFileName(path, "_bruteforce");

        try (BufferedReader reader = Files.newBufferedReader(Path.of(path));
             BufferedWriter writer = Files.newBufferedWriter(bruteforce)) {
            StringBuilder builder = new StringBuilder();
            List<String> list = new ArrayList<>();
            while (reader.ready()) {
                String string = reader.readLine();
                builder.append(string);
                list.add(string);
            }
            for (int i = 0; i < caesar.alphabetLength(); i++) {
                String decrypt = caesar.decrypt(builder.toString(), i);
                if (isValidateText(decrypt)) {
                    for (String string : list) {
                        String encrypt = caesar.decrypt(string, i);
                        writer.write(encrypt);
                        writer.newLine();
                    }
                    Util.writeMessage("Содержимое рассшифровано, ключ равен " + i + System.lineSeparator());
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isValidateText(String text) {
        boolean isValidate = false;
        String[] words = text.split(" ");
        for (String word : words) {
            if (word.length() > MAX_LENGTH_WORD) {
                return false;
            }
        }
        if (text.contains(". ") || text.contains(", ") || text.contains("? ") || text.contains("! ")) {
            isValidate = true;
        }
        while (isValidate) {
            Util.writeMessage(text);
            Util.writeMessage("Понятен ли этот текст? Y/N");
            String answer = Util.readString();
            if (answer.equalsIgnoreCase("Y")) {
                return true;
            } else if (answer.equalsIgnoreCase("N")) {
                isValidate = false;
            } else {
                Util.writeMessage("Выбор только между Y/N");
            }
        }
        return false;
    }
}
