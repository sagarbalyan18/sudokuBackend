package com.underscore.sudokuprime.callbacks;

public class CreateJoinSender {
    private final CreateJoinRoomCallback createJoinRoomCallback;

    public CreateJoinSender(CreateJoinRoomCallback createJoinRoomCallback) {
        this.createJoinRoomCallback = createJoinRoomCallback;
    }

    public void sendNotification(String userId) {
        createJoinRoomCallback.onCreateRoom(userId);
    }
}
