import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class EncryptedDecrypted {
    public void encryptedDecrypted(boolean flag) {
        Util.writeMessage("Введите путь к файлу для его " + (flag ? "зашифровки:" : "рассшифровки:"));

        String path = Util.readString();

        Util.writeMessage("Введите ключ:");
        int key = Util.readInt();

        Path pathResult = Util.buildFileName(path, flag? "_encrypted" : "_decrypted");

        CaesarCipher caesar = new CaesarCipher();
        try (BufferedReader reader = Files.newBufferedReader(Path.of(path));
             BufferedWriter writer = Files.newBufferedWriter(pathResult)) {
            while (reader.ready()) {
                String string = reader.readLine();
                String encryptDecrypt = "";
                encryptDecrypt = flag ? caesar.encrypt(string, key) : caesar.decrypt(string, key);
                writer.write(encryptDecrypt + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Util.writeMessage("Содержимое файла " + (flag ? "зашифрованно" : "расшифрованно"));

        System.out.println();
    }
}
