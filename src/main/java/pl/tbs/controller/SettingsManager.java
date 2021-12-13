package pl.tbs.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;

import pl.tbs.model.SettingsDataModel;

public enum SettingsManager {
    INSTANCE;

    private static SettingsDataModel settingsDM;
    private static final String APPDATA_PATH = System.getenv("APPDATA");
    private static final String APP_FOLDER_NAME = "ad-password-toolset";
    private static final String SETTINGS_FILENAME = "settings.json";
    private static final Path PATH_TO_SETTINGS_DIRECTORY = Paths.get(APPDATA_PATH, APP_FOLDER_NAME);
    private static final Path PATH_TO_SETTINGS_FILE = Paths.get(APPDATA_PATH, APP_FOLDER_NAME, SETTINGS_FILENAME);

    public void initDM(SettingsDataModel settingsDM) {
        // ensure model is set once
        if (this.settingsDM != null) {
            throw new IllegalStateException("SettingsDataModel can only be initialized once");
        }

        this.settingsDM = settingsDM;
    }

    public void initSettings() {
        if (Files.exists(PATH_TO_SETTINGS_FILE, LinkOption.NOFOLLOW_LINKS)) {
            loadSettings();
        } else {
            createSettingsFile();
        }
    }

    private void loadSettings() {
        Gson gson = new Gson();
        try {
            String settingsJson = new String(Files.readAllBytes(PATH_TO_SETTINGS_FILE));
            SettingsDataModel settings = gson.fromJson(settingsJson, SettingsDataModel.class);
            if (settings != null) {
                settingsDM.setInstance(settings);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSettingsFile() {
        try {
            Files.createDirectories(PATH_TO_SETTINGS_DIRECTORY);
            Files.createFile(PATH_TO_SETTINGS_FILE);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void saveSettings() {
        Gson gson = new Gson();
        String settingsJson = gson.toJson(settingsDM);
        try {
            Files.write(PATH_TO_SETTINGS_FILE, settingsJson.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void incrementPasswordResetCount() {
        settingsDM.setPasswordResetCount(settingsDM.getPasswordResetCount() + 1);
        saveSettings();
    }

    public void incrementPasswordPrintCount() {
        settingsDM.setPasswordPrintCount(settingsDM.getPasswordPrintCount() + 1);
        saveSettings();
    }


}
