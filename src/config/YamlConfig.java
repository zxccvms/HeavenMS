package config;

import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class YamlConfig {

    public static final YamlConfig config = fromFile("config.yaml");
    
    public List<WorldConfig> worlds;
    public ServerConfig server;

    public static YamlConfig fromFile(String filename) {
        try {
            YamlReader reader = new YamlReader(new FileReader(filename));
            YamlConfig config = reader.read(YamlConfig.class);
            reader.close();
            return config;
        } catch (FileNotFoundException e) {
            String message = "无法读取配置文件 " + filename + ": " + e.getMessage();
            throw new RuntimeException(message);
        } catch (IOException e) {
            String message = "无法成功解析配置文件 " + filename + ": " + e.getMessage();
            throw new RuntimeException(message);
        }
    }
}
