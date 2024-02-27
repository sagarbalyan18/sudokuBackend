package com.underscore.sudokuprime;

import com.underscore.sudokuprime.firebase.FCMService;
import com.underscore.sudokuprime.models.*;
import com.underscore.sudokuprime.repository.*;
import com.underscore.sudokuprime.utils.Constant;
import lombok.extern.java.Log;
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
	private final FeedbackRepository feedbackRepository;

	public SudokuPrimeApplication(UserRepository userRepository,
								  RoomRepository roomRepository,
								  SettlementRepository settlementRepository,
								  GroupRepository groupRepository,
								  FeedbackRepository feedbackRepository
								  ) {
		this.userRepository = userRepository;
		this.roomRepository = roomRepository;
		this.settlementRepository = settlementRepository;
		this.groupRepository = groupRepository;
		this.feedbackRepository = feedbackRepository;
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
		user.setUserPic(userRequest.userPic);
		user.setUserPic(userRequest.userPic);
		user.setUserCover(userRequest.userCover);
		userRepository.save(user);
		return new ApiStatus("success", "Successfully processed request.");
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
		settlement.setCategory(settlementRequest.category);
		settlement.setSplitRatio(settlementRequest.splitRatio);
		settlement.setSettled(false);
		settlement.setBill(settlementRequest.bill);
		settlementRepository.save(settlement);
		return new ApiStatus("success", "Successfully processed request.");
	}

	@PostMapping("/getSettlement")
	public List<SettlementModel> getUserSettlement(@RequestBody SettlementDetailsRequest request){
		List<SettlementModel> settlementModel = settlementRepository.getSettlementByPayerId(request.payerId, request.payeeId);
		return settlementModel;
	}

	@PostMapping("/editUser")
	public ApiStatus editUser(@RequestBody EditUserRequest userRequest){
		UserModel user = userRepository.getUserByUserId(userRequest.userId);
		if(user!=null){
			user.setName(userRequest.name);
			user.setEmail(userRequest.email);
			user.setUpi(userRequest.upi);
			user.setUserPic(userRequest.userPic);
			user.setUserCover(userRequest.userCover);
			userRepository.save(user);
			return new ApiStatus("success", "Successfully processed request.");
		} else {
			return new ApiStatus("failure", "User doesn't exist");
		}
	}

	@PostMapping("/editGroup")
	public ApiStatus editGroup(@RequestBody EditGroupRequest editGroupRequest){
		GroupModel group = groupRepository.findByGroupId(editGroupRequest.groupId);
		if(group!=null){
			group.setGroupName(editGroupRequest.groupName);
			group.setGroupPic(editGroupRequest.groupPic);
			group.setGroupCover(editGroupRequest.groupCover);
			group.setMembers(editGroupRequest.groupMembers);
			groupRepository.save(group);
			return new ApiStatus("success", "Successfully processed request.");
		} else {
			return new ApiStatus("failure", "User doesn't exist");
		}
	}

	@PostMapping("/editSettlement")
	public ApiStatus editSettlement(@RequestBody EditSettlementRequest request){
		SettlementModel settlement = settlementRepository.findById(Integer.valueOf(request.pKey)).orElse(null);
		if(settlement!=null){
			System.out.println("Settlement found");
			settlement.setDate(request.date);
			settlement.setPayerId(request.payerId);
			settlement.setPayeeId(request.payeeId);
			settlement.setAmount(request.amount);
			settlement.setDescription(request.description);
			settlement.setSplitRatio(request.splitRatio);
			settlement.setSettled(request.isSettled);
			settlement.setCategory(request.category);
			settlement.setBill(request.bill);
			settlementRepository.save(settlement);
			return new ApiStatus("success", "Successfully processed request.");
		} else {
			return new ApiStatus("failure", "User doesn't exist");
		}
	}

	@PostMapping("/deleteSettlement")
	public ApiStatus deleteSettlement(@RequestBody DeleteSettlementRequest request){
		SettlementModel settlement = settlementRepository.findById(Integer.valueOf(request.pKey)).orElse(null);
		if(settlement!=null){
			settlementRepository.deleteSettlement(request.pKey);
			return new ApiStatus("success", "Successfully processed request.");
		} else {
			return new ApiStatus("failure", "User doesn't exist");
		}
	}

	@PostMapping("/settleExpenses")
	public ApiStatus settleExpenses(@RequestBody SettlementDetailsRequest request){
		List<SettlementModel> settlementList = settlementRepository.getSettlementByPayerId(request.payerId, request.payeeId);
		for(SettlementModel settlement: settlementList){
			settlement.setSettled(true);
			settlementRepository.save(settlement);
		}
		return new ApiStatus("success", "Successfully processed request.");
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
		//Get all settlements
		List<SettlementModel> settlementModelList = settlementRepository.getAllSettlements(request.userId);
		//Set to maintain unique user id, so that a user is not added in the map twice
		Set<String> set = new HashSet<>();
		List<SettlementFriendModel> result = new ArrayList<>();

		//Map - of Userid, and the user's expense
		HashMap<String, SettlementFriendModel> resultObject = new HashMap();

		//Loop through all the expenses
		for (SettlementModel settlement:  settlementModelList) {
			if(settlement.getPayeeId().contains(",") || settlement.getPayerId().contains(",") ){
				//It's an expense from the group because either payers are more than 1
				// or payees are more than one
				handleGroupExpense(settlement, request, set, resultObject);
			} else {
				//It's an individual expense (i.e.e Not a group expense)
				System.out.println("New expense: " + settlement.getAmount()*settlement.getSplitRatio());
				prepareResultMap(request,
						settlement,
						set,
						resultObject,
						settlement.getPayeeId(),
						settlement.getPayeeName(),
						settlement.getAmount()*settlement.getSplitRatio()
				);
			}
		}
		return getListFromMap(resultObject, result);
	}

	private void handleGroupExpense(
			SettlementModel settlement,
			SettlementDetailsRequest request,
			Set<String> set,
			HashMap<String, SettlementFriendModel> resultObject){
		if(settlement.getPayeeId().contains(",") && settlement.getPayeeName().contains(",")){
			//There are multiple payees
			List<String> payeesIdList = List.of(settlement.getPayeeId().split(","));
			List<String> payeesNameList = List.of(settlement.getPayeeName().split(","));
			System.out.println("amount: " + settlement.getAmount()/ payeesIdList.size());
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
			// 1. User not added in set
			// 2. User is not the payee
			// 3.  +amount because user is not the payee, he is the payer
			resultObject.put(
					payeeId,
					getSettlementFriendObject(settlement, payeeId, payeeName, amount)
			);
			System.out.println("resultObject.put:" + payeeId + ": " + amount);
		} else if(!request.userId.equals(settlement.getPayerId()) && set.add(settlement.getPayerId())){
			// 1. User not added in set
			// 2. User is not the payer
			// 3. -amount because user is not the payer, he is the payee
			resultObject.put(
					settlement.getPayerId(),
					getSettlementFriendObject(settlement, payeeId, payeeName, -amount)
			);
			System.out.println("resultObject.put:" + payeeId + ": " + amount);
		}else {
			// User is already added in the set
			//update the current user details in the map with the new expense
			System.out.println("update the current list item");
			//Find out if user is the payer or the payee
			if (!request.userId.equals(payeeId)) {
				//User is not the payee, he is the payer
				SettlementFriendModel settlementFriendModel = (SettlementFriendModel) resultObject.get(payeeId);
				settlementFriendModel.setAmount(settlementFriendModel.getAmount()+amount);
				System.out.println("More expense: " + settlement.getAmount()*settlement.getSplitRatio() + "+" + amount +  "=" + (settlementFriendModel.getAmount()*settlementFriendModel.getSplitRatio()+amount));
				resultObject.put(payeeId, settlementFriendModel);
			} else if(!request.userId.equals(settlement.getPayerId())){
				//User is not the payer, he is the payee
				SettlementFriendModel settlementFriendModel = (SettlementFriendModel) resultObject.get(settlement.getPayerId());
				settlementFriendModel.setAmount(settlementFriendModel.getAmount()-amount);
				System.out.println("More expense: " + settlement.getAmount()*settlement.getSplitRatio() + "-" + amount +  "=" + (settlementFriendModel.getAmount()*settlementFriendModel.getSplitRatio()-amount));
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
		group.setGroupPic(groupRequest.groupPic);
		groupRepository.save(group);
		return new ApiStatus("success", "Successfully processed request.");
	}

	@PostMapping("/submitFeedback")
	public ApiStatus submitFeedback(@RequestBody FeedbackRequest feedbackRequest){
		FeedbackModel feedback = new FeedbackModel();
		feedback.setName(feedbackRequest.name);
		feedback.setUserId(feedbackRequest.userId);
		feedback.setFeedback(feedbackRequest.feedback);
		feedbackRepository.save(feedback);
		return new ApiStatus("success", "Successfully processed request.");
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

	record SettleExpenseRequest(String date){}

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
			String upi,
			String userPic,
			String userCover
			){

	}

	record EditUserRequest(
			String userId,
			String name,
			String email,
			String upi,
			String userPic,
			String userCover
	){

	}

	record EditGroupRequest(
			String groupId,
			String groupName,
			String groupPic,
			String groupCover,
			String groupMembers
	){

	}

    record Friend(String name) {
    }

	record SettlementIdRequest(String id) {
	}

	record GroupRequest(
			String groupId,
			String groupName,
			String groupPic,
			String members) {
	}

	record FeedbackRequest(
			String userId,
			String name,
			String feedback) {
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
			String category,
			String date,
			String payerId,
			String payeeId,
			double splitRatio,
			boolean isSettled,
			String pKey,
			String bill
	){}

	record CreateRoomResponse(String status,
							 String roomId) {
	}

	record DeleteSettlementRequest(String pKey) {
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
							 String category,
							 double splitRatio,
							 String bill
							 ) {
	}

	record ApiStatus(String status, String message) {}

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
