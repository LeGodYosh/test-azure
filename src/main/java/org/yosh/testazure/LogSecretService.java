package org.yosh.testazure;

import com.azure.security.keyvault.secrets.SecretClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogSecretService implements ApplicationRunner {

    @Value("${hidden-secret:default-value}")  // Ajout d'une valeur par défaut
    private String hiddenSecret;

    @Autowired
    private SecretClient secretClient;

    @Override
    public void run(ApplicationArguments args) {
        try {
            // Test direct avec SecretClient d'abord
            log.info("Tentative de récupération directe du secret...");
            var secretFromClient = secretClient.getSecret("hidden-secret");
            log.info("Secret récupéré directement: {}", secretFromClient.getValue());

            // Test via @Value
            log.info("Secret via @Value: {}", hiddenSecret);
        } catch (Exception e) {
            log.error("Erreur lors de l'accès au secret:", e);
            e.printStackTrace();
        }
    }
}