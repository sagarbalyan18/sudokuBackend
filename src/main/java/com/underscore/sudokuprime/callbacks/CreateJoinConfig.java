package com.underscore.sudokuprime.callbacks;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateJoinConfig {

    @Bean
    public CreateJoinSender createJoinSender(CreateJoinRoomCallback createJoinRoomCallback){
        return new CreateJoinSender(createJoinRoomCallback);
    }

}
