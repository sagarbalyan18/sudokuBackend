package com.underscore.sudokuprime.repository;

import com.underscore.sudokuprime.models.GroupModel;
import com.underscore.sudokuprime.models.RoomModel;
import com.underscore.sudokuprime.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupModel, Integer> {

    @Query(value = "SELECT * FROM group_model WHERE group_id=?",nativeQuery = true)
    GroupModel findByGroupId(String groupId);

    List<GroupModel> findByMembersContaining(String userId);

}
