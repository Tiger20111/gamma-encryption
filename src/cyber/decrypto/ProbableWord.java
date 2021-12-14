package cyber.decrypto;

import cyber.crypto.Cryptographer;

import java.util.HashMap;

import static java.lang.String.valueOf;

public class ProbableWord {
    public HashMap<String, String> decrypt(Cryptographer cryptographer, String fileName, String probableWord, int keySize) {
        HashMap<String, String> probableKeys = new HashMap<>();
        if (cryptographer.getName().equals("Cesar")) {
            decryptCesar(fileName, probableWord, cryptographer, probableKeys);
            return probableKeys;
        } else if (cryptographer.getName().equals("Shuffle")) {
            decryptSuffle(fileName, probableWord, keySize, cryptographer, probableKeys);
            return probableKeys;
        } else if (cryptographer.getName().equals("vigenere")) {
            decryptVigenere(new char[keySize], 0, fileName, probableWord, cryptographer, probableKeys);
        }
        return probableKeys;
    }

    private void decryptCesar(String fileName, String probableWord, Cryptographer cryptographer, HashMap<String, String> probableKeys) {
        for (int i = 0; i < 64; i++) {
            String probableText;
            assert cryptographer != null;
            probableText = cryptographer.decrypt(fileName, valueOf(i));
            if (probableText.contains(probableWord)) {
                probableKeys.put(valueOf(i), probableText);
            }
        }
    }

    private void decryptSuffle(String fileName, String probableWord, int keySize, Cryptographer cryptographer, HashMap<String, String> probableKeys) {
        StringBuilder probableKey = new StringBuilder();
        for (int i = 1; i < keySize + 1; i++) {
            probableKey.append(i);
        }
        int[] factorials = new int[keySize + 1];
        factorials[0] = 1;
        for (int i = 1; i <= keySize; i++) {
            factorials[i] = factorials[i - 1] * i;
        }

        for (int i = 0; i < factorials[probableKey.length()]; i++) {
            StringBuilder onePermutation = new StringBuilder();
            String temp = probableKey.toString();
            int positionCode = i;
            for (int position = probableKey.length(); position > 0; position--) {
                int selected = positionCode / factorials[position - 1];
                onePermutation.append(temp.charAt(selected));
                positionCode = positionCode % factorials[position - 1];
                temp = temp.substring(0, selected) + temp.substring(selected + 1);
            }
            String key = String.join(",", onePermutation.toString().split(""));
            assert cryptographer != null;
            String probableText = cryptographer.decrypt(fileName, key);
            if (probableText.contains(probableWord)) {
                probableKeys.put(key, probableText);
            }
        }
    }

    private static void decryptVigenere(char[] perm, int position, String fileName, String probableWord, Cryptographer cryptographer, HashMap<String, String> probableKeys) {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ,.-";
        if (position == perm.length) {
            String probableLine = cryptographer.decrypt(fileName, new String(perm));
            if (probableLine.contains(probableWord)) {
                probableKeys.put(new String(perm), probableLine);
            }
        } else {
            for (int i = 0; i < alphabet.length(); i++) {
                perm[position] = alphabet.charAt(i);
                decryptVigenere(perm, position + 1, fileName, probableWord, cryptographer, probableKeys);
            }
        }
    }
}
