package com.genomic.Fibersmart.dto;

public class PasswordDTO {

    private String currentPassword;

    private String newPassword;

    private String confirmNewPassword;

    public PasswordDTO(final String currentPassword, final String newPassword, final String confirmNewPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(final String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(final String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
