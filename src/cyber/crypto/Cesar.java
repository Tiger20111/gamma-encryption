package cyber.crypto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Cesar implements Cryptographer {
    private FileReader inputFile;
    private BufferedReader reader;


    public String encrypt(String filename, String key) {
        try {
            int cipherKey = Integer.parseInt(key);
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

    private String encryptText(int cipherKey) throws IOException {
        String line;
        StringBuilder result = new StringBuilder();
        while (!((line = reader.readLine()) == null)) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) != ' ') {
                    int letterCode = line.charAt(i) + cipherKey;
                    int encryptedLetterCode = (letterCode - 1040) % 64 + 1040;
                    result.append((char) encryptedLetterCode);
                } else {
                    result.append(line.charAt(i));
                }
            }
        }
        return result.toString();
    }

    @Override
    public String decrypt(String filename, String key) {
        try {
            int cipherKey = Integer.parseInt(key);
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

    private String decryptText(int cipherKey) throws IOException {
        String line;
        StringBuilder result = new StringBuilder();
        while (!((line = reader.readLine()) == null)) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) != ' ') {
                    int letterCode = line.charAt(i) - cipherKey;
                    int encryptedLetterCode = 1103 - (1104 - letterCode) % 64;
                    result.append((char) encryptedLetterCode);
                } else {
                    result.append(line.charAt(i));
                }
            }
        }
        return result.toString();
    }

    @Override
    public String getName() {
        return "Cesar";
    }
}
