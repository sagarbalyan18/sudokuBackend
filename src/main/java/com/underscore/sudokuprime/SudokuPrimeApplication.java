package com.underscore.sudokuprime;

import com.underscore.sudokuprime.firebase.FCMService;
import com.underscore.sudokuprime.models.*;
import com.underscore.sudokuprime.repository.GroupRepository;
import com.underscore.sudokuprime.repository.RoomRepository;
import com.underscore.sudokuprime.repository.SettlementRepository;
import com.underscore.sudokuprime.repository.UserRepository;
import com.underscore.sudokuprime.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/* APIs
* Get All users (/users)
* Get a User (/getUser)
* Add a User (/addUser)
* Create a room (/createRoom)
* Join a room (/joinRoom)
* */

@Slf4j
@SpringBootApplication
@RestController
public class SudokuPrimeApplication {

	private final UserRepository userRepository;
	private final RoomRepository roomRepository;
	private final SettlementRepository settlementRepository;
	private final GroupRepository groupRepository;

	public SudokuPrimeApplication(UserRepository userRepository,
								  RoomRepository roomRepository,
								  SettlementRepository settlementRepository,
								  GroupRepository groupRepository
								  ) {
		this.userRepository = userRepository;
		this.roomRepository = roomRepository;
		this.settlementRepository = settlementRepository;
		this.groupRepository = groupRepository;
	}

	public static void main(String[] args) {
        SpringApplication.run(SudokuPrimeApplication.class, args);
    }

	/* Apis */

    @GetMapping("/users")
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

	@PostMapping("/getUser")
	public UserModel getUser(@RequestBody UserIdRequest userIdRequest) {
		System.out.println("User Id Request: " + userIdRequest);
		if(userRepository.getUserByUserId(userIdRequest.userId)!=null){
			return userRepository.getUserByUserId(userIdRequest.userId);
		} else {
			UserModel user= new UserModel();
			user.setStatus("failure");
			return user;
		}
	}

	@PostMapping("/addUser")
	public ApiStatus addUser(@RequestBody UserRequest userRequest){
		UserModel user = new UserModel();
		user.setName(userRequest.name);
		user.setUserId(userRequest.userId);
		user.setAge(userRequest.age);
		user.setEmail(userRequest.email);
		user.setFcmToken(userRequest.fcmToken);
		user.setUpi(userRequest.upi);
		userRepository.save(user);
		return new ApiStatus("success");
	}

	@PostMapping("/addSettlement")
	public ApiStatus addSettlement(@RequestBody SettlementRequest settlementRequest){
		SettlementModel settlement = new SettlementModel();
		settlement.setDate(settlementRequest.date);
		settlement.setPayerId(settlementRequest.payerId);
		settlement.setPayerName(settlementRequest.payerName);
		settlement.setPayeeId(settlementRequest.payeeId);
		settlement.setPayeeName(settlementRequest.payeeName);
		settlement.setAmount(settlementRequest.amount);
		settlement.setGroupId(settlementRequest.groupId);
		settlement.setDescription(settlementRequest.description);
		settlement.setSplitRatio(settlementRequest.splitRatio);
		settlementRepository.save(settlement);
		return new ApiStatus("success");
	}

	@PostMapping("/getSettlement")
	public List<SettlementModel> getUserSettlement(@RequestBody SettlementDetailsRequest request){
		List<SettlementModel> settlementModel = settlementRepository.getSettlementByPayerId(request.payerId, request.payeeId);
		return settlementModel;
	}

	@PostMapping("/editSettlement")
	public ApiStatus editSettlement(@RequestBody EditSettlementRequest request){
		SettlementModel settlement = settlementRepository.findById(Integer.valueOf(request.pKey)).orElse(null);
		if(settlement!=null){
			log.info("Settlement found");
			settlement.setDate(request.date);
			settlement.setAmount(request.amount);
			settlement.setDescription(request.description);
			settlement.setSplitRatio(request.splitRatio);
			settlementRepository.save(settlement);
			return new ApiStatus("success");
		} else {
			return new ApiStatus("failure");
		}
	}

	@PostMapping("/deleteSettlement")
	public ApiStatus deleteSettlement(@RequestBody SettlementIdRequest request){
		SettlementModel settlement = settlementRepository.findById(Integer.valueOf(request.id)).orElse(null);
		if(settlement!=null){
			log.info("Settlement found");
			settlementRepository.delete(settlement);
			return new ApiStatus("success");
		} else {
			return new ApiStatus("failure");
		}
	}

	@PostMapping("/getGroupSettlements")
	public List<SettlementModel> getGroupSettlements(@RequestBody GroupIdRequest groupIdRequest){
		List<SettlementModel> settlementModel = settlementRepository.getSettlementsByGroupId(groupIdRequest.groupId);
		return settlementModel;
	}

	@PostMapping("/getAllSettlement")
	public List<SettlementModel> getAllSettlement(@RequestBody SettlementDetailsRequest request){
		List<SettlementModel> settlementModel = settlementRepository.getAllSettlements(request.userId);
		return settlementModel;
	}

	@PostMapping("/getSettlementFriends")
	public List<SettlementFriendModel> getSettlementFriends(@RequestBody SettlementDetailsRequest request){
		List<SettlementModel> settlementModelList = settlementRepository.getAllSettlements(request.userId);
		Set<String> set = new HashSet<>();
		List<SettlementFriendModel> result = new ArrayList<>();
		HashMap resultObject = new HashMap();
		for (SettlementModel settlement:  settlementModelList) {
			if(settlement.getPayeeId().contains(",") || settlement.getPayerId().contains(",") ){
				//It's an expense from the group
				if(settlement.getPayeeId().contains(",") && settlement.getPayeeName().contains(",")){
					//There are multiple payees
					List<String> payeesIdList = List.of(settlement.getPayeeId().split(","));
					List<String> payeesNameList = List.of(settlement.getPayeeName().split(","));
					log.info("amount: " + settlement.getAmount()/ payeesIdList.size());
					for(int i=0; i< payeesIdList.size(); i++){
						prepareResultMap(request,
								settlement,
								set,
								resultObject,
								payeesIdList.get(i),
								payeesNameList.get(i),
								settlement.getAmount()/ payeesIdList.size()
								);
					}

				} else if(settlement.getPayerId().contains(",")){
					//There are multiple payers - TODO later as this is less common
				}
			} else {
				prepareResultMap(request, settlement, set, resultObject,
						settlement.getPayeeId(),
						settlement.getPayeeName(),
						settlement.getAmount()
				);
			}
		}
		return getListFromMap(resultObject, result);
	}

	private void prepareResultMap(SettlementDetailsRequest request,
								  SettlementModel settlement,
								  Set<String> set,
								  HashMap resultObject,
								  String payeeId,
								  String payeeName,
								  double amount
								  ){
		if((!request.userId.equals(settlement.getPayeeId()) && set.add(payeeId))){
			resultObject.put(
					payeeId,
					getSettlementFriendObject(settlement, payeeId, payeeName, amount)
			);
		} else if(!request.userId.equals(settlement.getPayerId()) && set.add(settlement.getPayerId())){
			resultObject.put(
					settlement.getPayerId(),
					getSettlementFriendObject(settlement, payeeId, payeeName, amount)
			);
		}else {
			//update the current list item
			System.out.println("update the current list item");
			if(!request.userId.equals(payeeId)){
				SettlementFriendModel settlementFriendModel = (SettlementFriendModel) resultObject.get(payeeId);
				settlementFriendModel.setAmount(settlementFriendModel.getAmount()+amount);
				resultObject.put(payeeId, settlementFriendModel);
			} else if(!request.userId.equals(settlement.getPayerId())){
				SettlementFriendModel settlementFriendModel = (SettlementFriendModel) resultObject.get(settlement.getPayerId());
				settlementFriendModel.setAmount(settlementFriendModel.getAmount()-amount);
				resultObject.put(settlement.getPayerId(), settlementFriendModel);
			}
		}
	}

	private List<SettlementFriendModel> getListFromMap(HashMap resultObject, List<SettlementFriendModel> result) {
		Iterator it = resultObject.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			result.add((SettlementFriendModel) pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}
		return result;
	}

	private SettlementFriendModel getSettlementFriendObject(SettlementModel settlement, String payeeId, String payeeName, double amount) {
		return new SettlementFriendModel(
				payeeName,
				amount,
				payeeId,
				settlement.getPayerId(),
				settlement.getPayerName(),
				settlement.getSplitRatio(),
				settlement.getGroupId()
		);
	}

	@PostMapping("/createGroup")
	public ApiStatus createGroup(@RequestBody GroupRequest groupRequest){
		GroupModel group = new GroupModel();
		group.setGroupId(groupRequest.groupId);
		group.setGroupName(groupRequest.groupName);
		group.setMembers(groupRequest.members);
		groupRepository.save(group);
		return new ApiStatus("success");
	}

	@PostMapping("/getGroupDetails")
	public GroupModel getGroupDetails(@RequestBody GroupIdRequest groupIdRequest){
		return groupRepository.findByGroupId(groupIdRequest.groupId);
	}

	@PostMapping("/getUserGroups")
	public List<GroupModel> getUserGroups(@RequestBody UserIdRequest userIdRequest){
		return groupRepository.findByMembersContaining(userIdRequest.userId);
	}

	@PostMapping("/joinRoom")
	public RoomModel joinRoom(@RequestBody JoinRoomRequest roomRequest){
		roomRepository.joinRoom(roomRequest.userId, roomRequest.roomId);
		RoomModel room = roomRepository.findByRoomId(roomRequest.roomId);
		FCMService fcmService = new FCMService();
		PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
		pushNotificationRequest.setTitle("Join Room");
		pushNotificationRequest.setMessage(roomRequest.userId);
		pushNotificationRequest.setNotificationType(0);
		pushNotificationRequest.setToken(room.getCreatorFcmToken());
		Constant.USER_TWO = roomRequest.userId();
		try{
			fcmService.sendMessageToToken(pushNotificationRequest);
		} catch (java.lang.Exception e){
			e.printStackTrace();
		}
		return room;
	}

	/* Records */

	record UserRequest(
			String name,
			String userId,
			String age,
			String email,
			String badge,
			String rank,
			String isTop100,
			String boardsSolved,
			String totalBoardPlayed,
			String fcmToken,
			String upi
			){

	}

    record Friend(String name) {
    }

	record SettlementIdRequest(String id) {
	}

	record GroupRequest(
			String groupId,
			String groupName,
			String members) {
	}

    record UserResult(
			String name,
			Friend friends,
			String userId,
			String age,
			String email,
			String badge,
			String rank,
			String isTop100,
			String boardsSolved,
			String totalBoardPlayed,
			String fcmToken
			) {
    }

    record UserResponse(String status, UserResult result) {
    }

	record UserIdRequest(String userId, String creatorFcmToken) {
	}

	record GroupIdRequest(String groupId) {
	}

	record SettlementDetailsRequest(
			String userId,
			String payerId,
			String payeeId) {
	}

	record EditSettlementRequest(
			double amount,
			String description,
			String date,
			double splitRatio,
			String pKey
	){}


	record CreateRoomResponse(String status,
							 String roomId) {
	}

	record JoinRoomRequest(String roomId, String userId) {
	}

	record RoomIdRequest(String roomId) {
	}

	record SettlementRequest(String date,
							 String payerId,
							 String payeeId,
							 String payerName,
							 String payeeName,
							 double amount,
							 String groupId,
							 String description,
							 double splitRatio
							 ) {
	}

	record ApiStatus(String status) {}

	record Exception(String status, String message) {}

	/* Firebase */

	/*	@PostMapping("/createRoom")
	public CreateRoomResponse createRoom(@RequestBody UserIdRequest roomRequest){
		RoomModel room = new RoomModel();
		Random rand = new Random();
		int boardNumber = rand.nextInt(0,15);
		room.setRoomId(UUID.randomUUID().toString().substring(0,6));
		room.setUserOne(roomRequest.userId);
		room.setBoardNo(String.valueOf(boardNumber));
		room.setCreatorFcmToken(roomRequest.creatorFcmToken);
		roomRepository.save(room);
		Constant.USER_ONE = roomRequest.userId();
		return new CreateRoomResponse("success",room.getRoomId());
	}

	@PostMapping("/getRoom")
	public RoomModel getRoom(@RequestBody RoomIdRequest roomRequest){
		return roomRepository.findByRoomId(roomRequest.roomId);
	}*/

}
