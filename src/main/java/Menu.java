import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Выберите действие введя его номер:");
            System.out.println("1.Зашифровать текст");
            System.out.println("2.Рассшифровать текст");
            System.out.println("3.Подобрать ключ");
            System.out.println("4.Рассшифровать текст с помощью синтаксического анализа");
            System.out.println("5.Выйти из программы");
            Scanner scanner = new Scanner(System.in);
            switch (scanner.nextInt()) {
                case 1 -> new EncryptedDecrypted().encryptedDecrypted(true);
                case 2 -> new EncryptedDecrypted().encryptedDecrypted(false);
                case 3 -> new Bruteforce().bruteforce();
                case 4 -> new Parsing().parse();
                case 5 -> {
                    return;
                }
            }
        }
    }
}
