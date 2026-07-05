package com.machine.starter.ai;

import com.machine.starter.redis.RedisAutoConfiguration;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.chat.client.AdvisorParams;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.redis.RedisChatMemoryRepository;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.deepseek.DeepSeekChatOptions;
import org.springframework.ai.deepseek.api.DeepSeekApi;
import org.springframework.ai.deepseek.api.common.DeepSeekConstants;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.RedisClient;

import java.time.Duration;

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class AiAutoConfiguration {

    private final AiProperties aiProperties;

    public AiAutoConfiguration(AiProperties aiProperties) {
        this.aiProperties = aiProperties;
    }

    @Bean(name = "deepSeekFlashChatClient")
    public ChatClient deepSeekFlashChatClient(DeepSeekChatModel chatModel) {
        return ChatClient.create(chatModel)
                .mutate()
                .defaultAdvisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
                .build();
    }

    @Bean(name = "deepSeekProChatClient")
    public ChatClient deepSeekProChatClient() {
        DeepSeekApi deepSeekApi = DeepSeekApi.builder()
                .apiKey(aiProperties.getDeepseek().getApiKey())
                .baseUrl(DeepSeekConstants.DEFAULT_BASE_URL)
                .completionsPath(DeepSeekConstants.DEFAULT_COMPLETIONS_PATH)
                .betaPrefixPath(DeepSeekConstants.DEFAULT_BETA_PATH)
                .build();

        DeepSeekChatModel chatModel = DeepSeekChatModel.builder()
                .deepSeekApi(deepSeekApi)
                .options(DeepSeekChatOptions.builder()
                        .model(DeepSeekApi.ChatModel.DEEPSEEK_V4_PRO)
                        .build())
                .build();

        return ChatClient.create(chatModel)
                .mutate()
                .defaultAdvisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
                .build();
    }

    @Bean(name = "ollamaDefaultChatClient")
    public ChatClient ollamaDefaultChatClient(OllamaChatModel chatModel) {
        return ChatClient.create(chatModel)
                .mutate()
                .defaultAdvisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
                .build();
    }

    @Bean(name = "ollamaDeepseekR1ChatClient")
    public ChatClient ollamaDeepseekR1ChatClient() {
        OllamaApi ollamaApi = OllamaApi.builder()
                .baseUrl(aiProperties.getOllama().getBaseUrl())
                .build();

        OllamaChatModel chatModel = OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .options(OllamaChatOptions.builder()
                        .model("deepseek-r1:8b")
                        .temperature(0.6)
                        .build())
                .observationRegistry(ObservationRegistry.NOOP)
                .modelManagementOptions(ModelManagementOptions.defaults())
                .build();

        return ChatClient.create(chatModel)
                .mutate()
                .defaultAdvisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
                .build();
    }

    @Bean(name = "ollamaQwen3ChatClient")
    public ChatClient ollamaQwen3ChatClient() {
        OllamaApi ollamaApi = OllamaApi.builder()
                .baseUrl(aiProperties.getOllama().getBaseUrl())
                .build();

        OllamaChatModel chatModel = OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .options(OllamaChatOptions.builder()
                        .model("qwen3.5:9b")
                        .temperature(0.8)
                        .build())
                .observationRegistry(ObservationRegistry.NOOP)
                .modelManagementOptions(ModelManagementOptions.defaults())
                .build();

        return ChatClient.create(chatModel)
                .mutate()
                .defaultAdvisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
                .build();
    }

    @Bean(name = "ollamaQwen3VLChatClient")
    public ChatClient ollamaQwen3VLChatClient() {
        OllamaApi ollamaApi = OllamaApi.builder()
                .baseUrl(aiProperties.getOllama().getBaseUrl())
                .build();

        OllamaChatModel chatModel = OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .options(OllamaChatOptions.builder()
                        .model("qwen3-vl")
                        .temperature(0.8)
                        .build())
                .observationRegistry(ObservationRegistry.NOOP)
                .modelManagementOptions(ModelManagementOptions.defaults())
                .build();

        return ChatClient.create(chatModel)
                .mutate()
                .defaultAdvisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
                .build();
    }


    @Bean
    public ChatMemory chatmemory(RedisClient redisClient) {
        ChatMemoryRepository chatMemoryRepository = RedisChatMemoryRepository.builder()
                .jedisClient(redisClient)
                .keyPrefix("chat_memory:")
                .indexName("chat_index")
                .timeToLive(Duration.ofHours(24))
                .build();

        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(20)
                .build();
    }
}


