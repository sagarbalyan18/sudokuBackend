package com.underscore.sudokuprime.controllers;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.google.gson.Gson;
import com.underscore.sudokuprime.models.UserStats;
import com.underscore.sudokuprime.utils.Constant;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.underscore.sudokuprime.utils.Constant.USER_ONE;
import static com.underscore.sudokuprime.utils.Constant.USER_TWO;

@Component
@Log4j2
public class SocketIOController {

    @Autowired
    private SocketIOServer socketServer;

    SocketIOController(SocketIOServer socketServer){
        this.socketServer=socketServer;

        this.socketServer.addConnectListener(onUserConnectWithSocket);
        this.socketServer.addDisconnectListener(onUserDisconnectWithSocket);

        /**
         * Here we create only one event listener
         * but we can create any number of listener
         * messageSendToUser is socket end point after socket connection user have to send message payload on messageSendToUser event
         */
    }


    public ConnectListener onUserConnectWithSocket = new ConnectListener() {
        @Override
        public void onConnect(SocketIOClient client) {
            socketServer.addEventListener(USER_ONE + "_stats", String.class, onSendMessage);
            socketServer.addEventListener(Constant.USER_TWO + "_stats", String.class, onSendMessage);
//            log.info("Perform operation on user connect in controller");
        }
    };

    public DisconnectListener onUserDisconnectWithSocket = new DisconnectListener() {
        @Override
        public void onDisconnect(SocketIOClient client) {
//            log.info("Perform operation on user disconnect in controller");
        }
    };

    public DataListener<String> onSendMessage = new DataListener<String>() {
        @Override
        public void onData(SocketIOClient client, String stats, AckRequest acknowledge) throws Exception {

            /**
             * Sending message to target user
             * target user should subscribe the socket event with his/her name.
             * Send the same payload to user
             */

            log.info("Stats: " + stats);
            UserStats userStats = new Gson().fromJson(stats, UserStats.class);

            socketServer.getBroadcastOperations().sendEvent(USER_ONE + "_stats",client, stats);
            socketServer.getBroadcastOperations().sendEvent(USER_TWO + "_stats",client, stats);

            /**
             * After sending message to target user we can send acknowledge to sender
             */
            acknowledge.sendAckData("Message send to target user successfully");
        }
    };

}