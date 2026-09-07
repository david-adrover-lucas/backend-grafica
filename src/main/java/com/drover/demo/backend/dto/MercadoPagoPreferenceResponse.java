package com.drover.demo.backend.dto;

public class MercadoPagoPreferenceResponse {

    private String preferenceId;
    private String initPoint;
    private String sandboxInitPoint;
    private String externalReference;

    public MercadoPagoPreferenceResponse() {}

    public MercadoPagoPreferenceResponse(String preferenceId, String initPoint, String sandboxInitPoint, String externalReference) {
        this.preferenceId = preferenceId;
        this.initPoint = initPoint;
        this.sandboxInitPoint = sandboxInitPoint;
        this.externalReference = externalReference;
    }

    public String getPreferenceId() { return preferenceId; }
    public void setPreferenceId(String preferenceId) { this.preferenceId = preferenceId; }

    public String getInitPoint() { return initPoint; }
    public void setInitPoint(String initPoint) { this.initPoint = initPoint; }

    public String getSandboxInitPoint() { return sandboxInitPoint; }
    public void setSandboxInitPoint(String sandboxInitPoint) { this.sandboxInitPoint = sandboxInitPoint; }

    public String getExternalReference() { return externalReference; }
    public void setExternalReference(String externalReference) { this.externalReference = externalReference; }
}
