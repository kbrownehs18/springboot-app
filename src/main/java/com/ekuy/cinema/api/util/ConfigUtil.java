package com.ekuy.cinema.api.util;

import com.ekuy.cinema.api.config.AppConfig;
import org.springframework.core.env.Environment;

public class ConfigUtil {
    private static Environment environment = AppConfig.getApplicationContext().getEnvironment();

    /**
     * 根据key获取配置信息
     * @param key
     * @return
     */
    public static String get(String key){
        return environment.getProperty(key);
    }

}
