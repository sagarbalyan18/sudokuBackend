package com.underscore.sudokuprime;

import com.underscore.sudokuprime.firebase.FCMService;
import com.underscore.sudokuprime.models.PushNotificationRequest;
import com.underscore.sudokuprime.models.RoomModel;
import com.underscore.sudokuprime.models.UserModel;
import com.underscore.sudokuprime.repository.RoomRepository;
import com.underscore.sudokuprime.repository.UserRepository;
import com.underscore.sudokuprime.utils.Constant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/* APIs
* Get All users (/users)
* Get a User (/getUser)
* Add a User (/addUser)
* Create a room (/createRoom)
* Join a room (/joinRoom)
* */

@SpringBootApplication
@RestController
public class SudokuPrimeApplication {

	private final UserRepository userRepository;
	private final RoomRepository roomRepository;

	public SudokuPrimeApplication(UserRepository userRepository, RoomRepository roomRepository) {
		this.userRepository = userRepository;
		this.roomRepository = roomRepository;
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
		} return null;
	}

	@PostMapping("/addUser")
	public ApiStatus addUser(@RequestBody UserRequest userRequest){
		UserModel user = new UserModel();
		user.setName(userRequest.name);
		user.setUserId(userRequest.userId);
		user.setAge(userRequest.age);
		user.setBadge(userRequest.badge);
		user.setEmail(userRequest.email);
		user.setTotalBoardPlayed(userRequest.totalBoardPlayed);
		user.setBoardsSolved(userRequest.boardsSolved);
		user.setRank(userRequest.rank);
		user.setIsTop100(userRequest.isTop100);
		user.setFcmToken(userRequest.fcmToken);
		userRepository.save(user);
		return new ApiStatus("success");
	}

	@PostMapping("/createRoom")
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
		//Send notification to the creator that a new user has joined
		//find the fcmtoken of the creator -> by room id
		return roomRepository.findByRoomId(roomRequest.roomId);
	}

	@PostMapping("/joinRoom")
	public RoomModel joinRoom(@RequestBody JoinRoomRequest roomRequest){
		//Send notification to the creator that a new user has joined
		//find the fcmtoken of the creator -> by room id
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
			String fcmToken
			){

	}

    record Friend(String name) {
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

	record CreateRoomResponse(String status,
							 String roomId) {
	}

	record JoinRoomRequest(String roomId, String userId) {
	}

	record RoomIdRequest(String roomId) {
	}

	record ApiStatus(String status) {}

	record Exception(String status, String message) {}

	/* Firebase */

}
