package cyber.crypto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.List.of;

public class Shuffle implements Cryptographer {
    private FileReader inputFile;
    private BufferedReader reader;

    @Override
    public String encrypt(String filename, String key) {
        try {
            List<Integer> cipherKey = of(key.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
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

    private String encryptText(List<Integer> cipherKey) throws IOException {
        String line;
        StringBuilder result = new StringBuilder();
        while (!((line = reader.readLine()) == null)) {
            StringBuilder cipherBody = new StringBuilder(line);
            for (int i = 0; i < line.length() % cipherKey.size(); i++)
                cipherBody.append(line.charAt(i));

            for (int i = 0; i < line.length(); i += cipherKey.size()) {
                char[] transposition = new char[cipherKey.size()];

                for (int j = 0; j < cipherKey.size(); j++)
                    transposition[cipherKey.get(j) - 1] = cipherBody.charAt(i + j);

                for (int j = 0; j < cipherKey.size(); j++)
                    result.append(transposition[j]);
            }
        }
        return result.toString();
    }

    @Override
    public String decrypt(String filename, String key) {
        try {
            inputFile = new FileReader(filename);
            reader = new BufferedReader(inputFile);
            List<Integer> cipherKey = of(key.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
            String result = decryptText(cipherKey);
            inputFile.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String decryptText(List<Integer> cipherKey) throws IOException {
        String line;
        StringBuilder result = new StringBuilder();
        while (!((line = reader.readLine()) == null)) {
            for (int i = 0; i < line.length(); i += cipherKey.size()) {
                char[] transposition = new char[cipherKey.size()];

                for (int j = 0; j < cipherKey.size(); j++)
                    transposition[j] = line.charAt(i + cipherKey.get(j) - 1);

                for (int j = 0; j < cipherKey.size(); j++)
                    result.append(transposition[j]);
            }
        }
        return result.toString();
    }

    @Override
    public String getName() {
        return "Shuffle";
    }
}
