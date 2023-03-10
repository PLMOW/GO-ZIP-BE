package com.gozip.webchat.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        log.info("configureMessageBroker");
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
    }


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        log.info("registerStompEndpoints");
        registry.addEndpoint("/stomp").setAllowedOriginPatterns("*")
                .withSockJS();
    }

}
