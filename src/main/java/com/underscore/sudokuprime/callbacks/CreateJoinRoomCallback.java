package com.underscore.sudokuprime.callbacks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@NoRepositoryBean
@Configuration
public interface CreateJoinRoomCallback {

    @Autowired
    @Bean
    void onCreateRoom(String userId);

    @Autowired
    @Bean
    void onJoinRoom(String userId);
}
