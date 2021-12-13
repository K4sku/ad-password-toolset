package pl.tbs.model;

import java.nio.file.Path;

public class SettingsDataModel {
    //settings
    private String domainController;
    private boolean isDomainControllerSet;
    private Path lastOpenedFile;
    private boolean openLastFileOnStart;
    private boolean autosave;
    
    //stats
    private int passwordResetCount;
    private int passwordPrintCount;

    //credentials
    private String username;
    private String password;

    public SettingsDataModel() {
        this.domainController = "";
        this.isDomainControllerSet = false;
        this.lastOpenedFile = null;
        this.openLastFileOnStart = false;
        this.autosave = false;
        this.passwordResetCount = 0;
        this.passwordPrintCount = 0;
        this.username = "";
        this.password = "";
    }

    public void setInstance(SettingsDataModel instance) {
        this.domainController = instance.domainController;
        this.isDomainControllerSet = instance.isDomainControllerSet;
        this.lastOpenedFile = instance.lastOpenedFile;
        this.openLastFileOnStart = instance.openLastFileOnStart;
        this.autosave = instance.autosave;
        this.passwordResetCount = instance.passwordResetCount;
        this.passwordPrintCount = instance.passwordPrintCount;
        this.username = instance.username;
    }

    public String getDomainController() {
        return domainController;
    }
    public void setDomainController(String domainController) {
        this.domainController = domainController;
        this.isDomainControllerSet = true;
    }

    public boolean isDomainControllerSet() {
        return isDomainControllerSet;
    }
    
    public Path getLastOpenedFile() {
        return lastOpenedFile;
    }
    public void setLastOpenedFile(Path lastOpenedFile) {
        this.lastOpenedFile = lastOpenedFile;
    }
    public boolean isOpenLastFileOnStart() {
        return openLastFileOnStart;
    }
    public void setOpenLastFileOnStart(boolean openLastFileOnStart) {
        this.openLastFileOnStart = openLastFileOnStart;
    }
    public boolean isAutosave() {
        return autosave;
    }
    public void setAutosave(boolean autosave) {
        this.autosave = autosave;
    }
    public int getPasswordResetCount() {
        return passwordResetCount;
    }
    public void setPasswordResetCount(int resetedPasswordCount) {
        this.passwordResetCount = resetedPasswordCount;
    }
    public int getPasswordPrintCount() {
        return passwordPrintCount;
    }
    public void setPasswordPrintCount(int printedPasswordCount) {
        this.passwordPrintCount = printedPasswordCount;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    
}
