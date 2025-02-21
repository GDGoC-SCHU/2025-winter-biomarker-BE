package gdgoc.biomarker.users.controller;

import gdgoc.biomarker.users.dto.HealthDataRequest;
import gdgoc.biomarker.users.entity.HealthData;
import gdgoc.biomarker.users.repository.HealthDataRepository;
import gdgoc.biomarker.users.repository.UserRepository;
import gdgoc.biomarker.users.service.HealthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health")
public class HealthDataController {

    @Autowired
    private HealthDataRepository healthDataRepository;

    @Autowired
    private UserRepository userRepository; // 성별을 가져오기 위한 UserRepository

    @Autowired
    private HealthDataService healthDataService; // HealthDataService 추가

    @PostMapping("/add/{userId}")
    public ResponseEntity<String> addHealthData(@RequestBody HealthDataRequest healthDataRequest, @PathVariable Long userId) {
        // UserRepository 에서 성별 가져오기
        String gender = userRepository.findGenderById(userId);

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

        // Flask 서버로 전송 및 응답 받기
        String flaskResponse = healthDataService.sendHealthDataToFlaskServer(healthDataRequest,userId);
        return new ResponseEntity<>(flaskResponse, HttpStatus.CREATED);
    }

}
