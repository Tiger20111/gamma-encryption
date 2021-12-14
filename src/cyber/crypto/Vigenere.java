package cyber.crypto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Vigenere implements Cryptographer {
    private final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ,.-";
    private FileReader inputFile;
    private BufferedReader reader;

    public String encrypt(String filename, String cipherKey) {
        try {
            inputFile = new FileReader(filename);
            reader = new BufferedReader(inputFile);
            String result = encryptText(cipherKey);
            inputFile.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String encryptText(String cipherKey) throws IOException {
        String line;
        final int alphabetSize = alphabet.length();
        final int keySize = cipherKey.length();
        final StringBuilder result = new StringBuilder();
        while (!((line = reader.readLine()) == null)) {
            for (int i = 0; i < line.length(); i++) {
                final char plainChar = line.charAt(i);
                final char keyChar = cipherKey.charAt(i % keySize);
                final int plainPos = alphabet.indexOf(plainChar);
                if (plainPos == -1) {
                    result.append(plainChar);
                } else {
                    final int keyPos = alphabet.indexOf(keyChar);
                    result.append(alphabet.charAt((plainPos + keyPos) % alphabetSize));

                }
            }
        }
        return result.toString();
    }

    public String decrypt(String filename, String cipherKey) {
        try {
            inputFile = new FileReader(filename);
            reader = new BufferedReader(inputFile);
            String result = decryptText(cipherKey);
            inputFile.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String decryptText(String cipherKey) throws IOException {
        String line;
        final int alphabetSize = alphabet.length();
        final int keySize = cipherKey.length();
        final StringBuilder result = new StringBuilder();
        while (!((line = reader.readLine()) == null)) {
            for (int i = 0; i < line.length(); i++) {
                final char plainChar = line.charAt(i);
                final char keyChar = cipherKey.charAt(i % keySize);
                final int plainPos = alphabet.indexOf(plainChar);
                if (plainPos == -1) {
                    result.append(plainChar);
                } else {
                    final int keyPos = alphabet.indexOf(keyChar);
                    int shiftedPos = plainPos - keyPos;
                    if (shiftedPos < 0) {
                        shiftedPos += alphabetSize;
                    }
                    result.append(alphabet.charAt(shiftedPos));
                }
            }
        }
        return result.toString();
    }

    @Override
    public String getName() {
        return "Vigenere";
    }
}
