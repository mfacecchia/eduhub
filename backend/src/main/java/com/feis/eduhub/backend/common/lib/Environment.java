package com.feis.eduhub.backend.common.lib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Utility class used to load load environmental variables
 * from a {@code .env} file.
 */
public class Environment {

    private Environment() {
    }

    /**
     * Obtains environmental variables values from the list provided.
     * 
     * @param variablesName the list of all environmental variables key names to
     *                      retrieve
     * @return a {@link java.util.Map Map} representing the env variables key-value
     *         pair
     */
    public static Map<String, String> getEnvironmentVariables(List<String> variablesName) {
        Dotenv dotenv = Dotenv.load();
        Map<String, String> env = new HashMap<>();
        for (String name : variablesName) {
            env.put(name, dotenv.get(name));
        }
        return env;
    }
}
