package com.stardust.sync.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Configuration {

    @Id
    @Size(max = 128)
    String  configKey;

    @Size(max = 512)
    @NotNull
    String  configValue;

    public Configuration() {
    }

    public Configuration(String configKey, String configValue) {
        this.configKey = configKey;
        this.configValue = configValue;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

}