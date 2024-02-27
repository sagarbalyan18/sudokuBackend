package com.underscore.sudokuprime.repository;

import com.underscore.sudokuprime.models.SettlementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SettlementRepository extends JpaRepository<SettlementModel,Integer> {

    @Query(value = "SELECT * FROM settlement_model WHERE ((payer_id=?1 and payee_id=?2) or (payer_id=?2 and payee_id=?1)) and is_settled=false order by date asc",nativeQuery = true)
    List<SettlementModel> getSettlementByPayerId(String payerId, String payeeId);

    @Query(value = "SELECT * FROM settlement_model WHERE (payer_id LIKE %:userId% or payee_id LIKE %:userId%) and is_settled=false", nativeQuery = true)
    List<SettlementModel> getAllSettlements(@Param("userId") String userId);

    @Query(value = "SELECT * FROM settlement_model WHERE group_id=? order by date asc",nativeQuery = true)
    List<SettlementModel> getSettlementsByGroupId(String groupId);

    @Query(value = "DELETE FROM settlement_model WHERE p_key=?",nativeQuery = true)
    List<SettlementModel> deleteSettlement(int pKey);

/*  @Query(value = "SELECT * FROM settlement_model WHERE payer_id=?1 or payee_id=?1",nativeQuery = true)
    List<SettlementModel> getAllSettlements(String userId);*/

    //sagar,saransh

}