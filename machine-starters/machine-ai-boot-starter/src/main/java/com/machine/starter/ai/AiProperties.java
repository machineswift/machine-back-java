package com.machine.starter.ai;

import com.machine.sdk.base.config.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:machine-ai.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "machine.ai")
public class AiProperties {

    private Ollama ollama;
    private DeepSeek deepseek;
    private DashScope dashScope;

    @Data
    public static class Ollama {
        private String baseUrl;
        private Chat chat = new Chat();

        @Data
        public static class Chat {
            private Options options = new Options();

            @Data
            public static class Options {
                private String model;
            }
        }
    }

    @Data
    public static class DeepSeek {
        private String apiKey;
        private Chat chat = new Chat();

        @Data
        public static class Chat {
            private Options options = new Options();

            @Data
            public static class Options {
                private String model;
            }
        }
    }

    @Data
    public static class DashScope {
        private String baseUrl;
        private String apiKey;
        private Chat chat = new Chat();

        @Data
        public static class Chat {
            private Options options = new Options();

            @Data
            public static class Options {
                private String model;
            }
        }
    }

}