package se.systementor.javasecstart.services;

import org.springframework.stereotype.Service;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class HashService {

    public void writeToFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateHash(String dictFilePath, String hashFilePath) throws IOException, NoSuchAlgorithmException {
        List<String> passwords = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(dictFilePath));
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(hashFilePath ))){
            for (String password : passwords){
                String md5 = hashPassword(password, "MD5");
                String sha256 = hashPassword(password, "SHA-256");
                writer.write(password + " MD5: " + md5 + " SHA_256: " + sha256);
                writer.newLine();
            }
        }
    }

    public String hashPassword(String password, String hashAlgorithm) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(hashAlgorithm);
        byte[] digest = messageDigest.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for(byte b : digest){
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public String getPassword(String userInputHash){
        String filePath = "hashes.txt";
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String password = parts[0];
                String md5 = parts[2];
                String sha256 = parts[4];
                if(md5.equals(userInputHash) || sha256.equals(userInputHash)){
                    return password;
                }
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "No password found";
    }

}
