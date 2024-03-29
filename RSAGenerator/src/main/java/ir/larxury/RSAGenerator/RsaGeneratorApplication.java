package ir.larxury.RSAGenerator;

import ir.larxury.RSAGenerator.service.PemFileWriter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@SpringBootApplication
public class RsaGeneratorApplication implements CommandLineRunner {

    public static final int KEY_SIZE = 2048;
    public static final String RESOURCES_URI = "F:\\LARxury Start-Up\\RSA utils\\RSAGenerator\\src\\main\\resources\\";

    public static void main(String[] args) {
        SpringApplication.run(RsaGeneratorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        KeyPair keyPair = generateRSAKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        writePemFile(privateKey, "RSA PRIVATE KEY", getFileName("sign_key"));
        writePemFile(publicKey, "RSA PUBLIC KEY", getFileName("verify_key.pub"));
    }

    private static String getFileName(String name) {
        return RESOURCES_URI + name;
    }

    private static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(KEY_SIZE);
        return generator.generateKeyPair();
    }

    private static void writePemFile(Key key, String description, String filename)
            throws IOException {
        PemFileWriter pemFileWriter = new PemFileWriter(key, description);
        pemFileWriter.write(filename);

    }
}
