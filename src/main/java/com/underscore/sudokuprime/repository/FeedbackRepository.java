package com.underscore.sudokuprime.repository;

import com.underscore.sudokuprime.models.FeedbackModel;
import com.underscore.sudokuprime.models.GroupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<FeedbackModel, Integer> {

/*
    @Query(value = "SELECT * FROM group_model WHERE group_id=?",nativeQuery = true)
    GroupModel findByGroupId(String groupId);

    List<GroupModel> findByMembersContaining(String userId);
*/

}

