package com.underscore.sudokuprime.repository;

import com.underscore.sudokuprime.models.RoomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RoomRepository extends JpaRepository<RoomModel, Integer> {

    @Query(value = "SELECT * FROM room_model WHERE room_id=?",nativeQuery = true)
    RoomModel findByRoomId(String roomId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE room_model SET user_two_id=? where room_id=?",nativeQuery = true)
    void joinRoom(String userTwoId, String roomId);

}
