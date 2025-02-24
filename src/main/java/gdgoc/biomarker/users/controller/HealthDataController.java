package gdgoc.biomarker.users.controller;

import gdgoc.biomarker.users.dto.FlaskRequestDto;
import gdgoc.biomarker.users.dto.HealthDataRequest;
import gdgoc.biomarker.users.entity.HealthData;
import gdgoc.biomarker.users.repository.HealthDataRepository;
import gdgoc.biomarker.users.repository.UserRepository;
import gdgoc.biomarker.users.service.HealthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/health")
public class HealthDataController {

    @Autowired
    private HealthDataRepository healthDataRepository;

    @Autowired
    private UserRepository userRepository; // 성별을 가져오기 위한 UserRepository

    @Autowired
    private HealthDataService healthDataService; // HealthDataService 추가

    @PostMapping("/add/{userId}")
    public ResponseEntity<Object> addHealthData(@RequestBody HealthDataRequest healthDataRequest, @PathVariable Long userId) {
        // UserRepository 에서 성별 가져오기
        String gender = userRepository.findGenderById(userId);

        // 만약 userId가 없다면 400에러와 메세지 반환
        if(gender == null) {
            return ResponseEntity.status(400).body("찾을 수 없는 userId입니다.");
        }

        // HealthDataRequest로부터 받은 데이터를 바탕으로 FlaskRequestDto 생성
        FlaskRequestDto flaskRequestDto = new FlaskRequestDto(
                healthDataRequest.getHeight(),
                healthDataRequest.getWeight(),
                healthDataRequest.getBody_fat_percentage(),
                healthDataRequest.getSkeletal_muscle_mass(),
                healthDataRequest.getBmr(),
                healthDataRequest.getBody_fat_mass(),
                healthDataRequest.getGoal(),
                gender // 성별 추가
        );

        // Flask 서버로 전송 및 응답 받기
        Map<String, Object> flaskResponse = healthDataService.sendHealthDataToFlaskServer(flaskRequestDto, userId);

        // Flask 서버에서 오류 응답을 받은 경우 처리
        if (flaskResponse == null || !flaskResponse.containsKey("answer") ||
                flaskResponse.get("answer").toString().toLowerCase().contains("error") ||
                flaskResponse.get("answer").toString().contains("실패")) {

            return ResponseEntity.status(400).body(Map.of("message", "Flask 서버에서 오류가 발생했습니다. 데이터 저장이 취소되었습니다."));
        }

        // HealthData 엔티티로 변환
        HealthData healthData = new HealthData(
                healthDataRequest.getHeight(),
                healthDataRequest.getWeight(),
                healthDataRequest.getBody_fat_percentage(),
                healthDataRequest.getSkeletal_muscle_mass(),
                healthDataRequest.getBmr(),
                healthDataRequest.getBody_fat_mass(),
                healthDataRequest.getGoal(),
                gender
        );

        // 데이터베이스에 저장
        HealthData savedHealthData = healthDataRepository.save(healthData);

        return ResponseEntity.ok(flaskResponse);
    }

}
