package by.radioegor146;

import by.radioegor146.config.ConfigManager;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static String configPath = "config.yml";
    public static ConfigManager configManager;
    private static final String VERSION = "20220928";

    public static void main(String[] args) throws IOException {
        configManager = new ConfigManager(configPath);
            System.out.println("native-obfuscator " + VERSION);
            List<Path> libs = new ArrayList<>();
            Files.walk(Paths.get(configManager.getConfig().getLibPath()), FileVisitOption.FOLLOW_LINKS)
                    .filter(f -> f.toString().endsWith(".jar") || f.toString().endsWith(".zip"))
                    .forEach(libs::add);
            new NativeObfuscator().process(Paths.get(configManager.getConfig().getInput()), Paths.get(configManager.getConfig().getOutputPath()), libs, configManager.getConfig().isInclude(), configManager.getConfig().getIncludes(), configManager.getConfig().getExcludes());
        }
    }
