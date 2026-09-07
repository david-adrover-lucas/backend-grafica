package com.drover.demo.backend.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mercadopago")
public class MercadoPagoProperties {

    private String webhookUrl;
    private String successUrl;
    private String failureUrl;
    private String pendingUrl;
    private String webhookSecret;
    private List<CuentaMercadoPago> cuentas = new ArrayList<>();

    public Optional<CuentaMercadoPago> buscarPorCuentaId(Long cuentaId) {
        if (cuentaId == null) {
            return Optional.empty();
        }
        return cuentas.stream()
            .filter(cuenta -> cuenta.getCuentaId() != null && cuenta.getCuentaId().equals(cuentaId))
            .findFirst();
    }

    public String getWebhookUrl() { return webhookUrl; }
    public void setWebhookUrl(String webhookUrl) { this.webhookUrl = webhookUrl; }

    public String getSuccessUrl() { return successUrl; }
    public void setSuccessUrl(String successUrl) { this.successUrl = successUrl; }

    public String getFailureUrl() { return failureUrl; }
    public void setFailureUrl(String failureUrl) { this.failureUrl = failureUrl; }

    public String getPendingUrl() { return pendingUrl; }
    public void setPendingUrl(String pendingUrl) { this.pendingUrl = pendingUrl; }

    public String getWebhookSecret() { return webhookSecret; }
    public void setWebhookSecret(String webhookSecret) { this.webhookSecret = webhookSecret; }

    public List<CuentaMercadoPago> getCuentas() { return cuentas; }
    public void setCuentas(List<CuentaMercadoPago> cuentas) { this.cuentas = cuentas; }

    public static class CuentaMercadoPago {
        private String alias;
        private Long cuentaId;
        private String accessToken;
        private String publicKey;

        public String getAlias() { return alias; }
        public void setAlias(String alias) { this.alias = alias; }

        public Long getCuentaId() { return cuentaId; }
        public void setCuentaId(Long cuentaId) { this.cuentaId = cuentaId; }

        public String getAccessToken() { return accessToken; }
        public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

        public String getPublicKey() { return publicKey; }
        public void setPublicKey(String publicKey) { this.publicKey = publicKey; }
    }
}
