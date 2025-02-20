package com.salesianostriana.dam.farma_app.util;

import com.resend.Resend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResendConfig {
    @Bean
    public Resend resend(@Value("${resend.api.key}") String resendApiKey) {
        return new Resend(resendApiKey);
    }
}
