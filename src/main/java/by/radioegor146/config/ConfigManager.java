package by.radioegor146.config;

import by.radioegor146.Main;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigManager {
    private Config config;

    public ConfigManager(String configPath) {
        Yaml yaml = new Yaml();
        try {
            this.config = yaml.loadAs(new BufferedInputStream(Files.newInputStream(Paths.get(configPath))), Config.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Config getConfig() {
        return this.config;
    }

    public void saveConfig() {
        Representer representer = new Representer();
        representer.addClassTag(Config.class, Tag.MAP);//会使用User对map的类型进行解析,不会生成!!和包路径
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);// 普通yml格式
        Yaml yaml = new Yaml(representer, options);
        try {
            OutputStreamWriter out = new OutputStreamWriter(Files.newOutputStream(Paths.get(Main.configPath)), StandardCharsets.UTF_8);
            yaml.dump(config, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
