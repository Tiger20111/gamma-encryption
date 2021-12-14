package cyber.ui;

import cyber.crypto.Cesar;
import cyber.crypto.Cryptographer;
import cyber.crypto.Shuffle;
import cyber.crypto.Vigenere;
import cyber.decrypto.ProbableWord;

import java.util.Scanner;

public class UIApplication {
    private Scanner scanner = new Scanner(System.in);
    public void getBaseInformation() {
        String filePath = getFilePath();
        boolean isEncrypt = isEncryption();
        int typeEncrypt = getTypeCryptographer();
        Cryptographer cryptographer = getCryptographer(typeEncrypt);
        if (isEncrypt) {
            callEncrypt(cryptographer, filePath);
        } else {
            callDecrypt(cryptographer, filePath);
        }
    }

    private String getFilePath() {
        System.out.println("Enter path to file: ");
        return scanner.nextLine();
    }

    private boolean isEncryption() {
        while (true) {
            System.out.println("Do you want to encrypt? ");
            String answer = scanner.nextLine();
            if (answer.equals("y") || answer.equals("yes")) {
                return true;
            }
            if (answer.equals("n") || answer.equals("no")) {
                return false;
            }
            System.out.println("Invalid value, enter (y/yes) or (n/no).");
        }
    }

    private int getTypeCryptographer() {
        while (true) {
            System.out.println("What type of encryption do you want to use?(Shuffle, Cesar, Vigenere)");
            String answer = scanner.nextLine();
            if (answer.equals("Shuffle")) {
                return 0;
            }
            if (answer.equals("Cesar")) {
                return 1;
            }
            if (answer.equals("Vigenere")) {
                return 2;
            }

            if (answer.equals("No")) {
                return 3;
            }
            System.out.println("Invalid value, enter 'Shuffle', or 'Cesar', or 'Vigenere'.");
        }
    }

    private Cryptographer getCryptographer(int type) {
        switch (type) {
            case 0:
                return new Shuffle();
            case 1:
                return new Cesar();
            case 2:
                return new Vigenere();
            default:
                return null;
        }
    }

    private void callEncrypt(Cryptographer cryptographer, String pathFile) {
        System.out.println("Enter key: ");
        String key = scanner.nextLine();
        if (cryptographer != null) {
            System.out.println(cryptographer.encrypt(pathFile, key));
        }
    }

    private void callDecrypt(Cryptographer cryptographer, String pathFile) {
        if (haveYouKey()) {
            System.out.println("Enter cipher key: ");
            String key = scanner.nextLine();
            if (cryptographer != null) {
                System.out.println(cryptographer.decrypt(pathFile, key));
            }
        } else {
            System.out.println("Enter probable word: ");
            String word = scanner.nextLine();
            System.out.println("Enter key length: ");
            int keySize = scanner.nextInt();
            ProbableWord probableWord = new ProbableWord();
            probableWord.decrypt(cryptographer, pathFile, word, keySize)
                    .entrySet()
                    .forEach(System.out::println);
        }
    }

    private boolean haveYouKey() {
        while (true) {
            System.out.println("Do you know key? ");
            String answer = scanner.nextLine();
            if (answer.equals("y") || answer.equals("yes")) {
                return true;
            }
            if (answer.equals("n") || answer.equals("no")) {
                return true;
            }
            System.out.println("Invalid value, enter (y/yes) or (n/no).");
        }
    }

}
