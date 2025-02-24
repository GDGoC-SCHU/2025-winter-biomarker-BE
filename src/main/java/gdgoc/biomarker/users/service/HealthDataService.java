package gdgoc.biomarker.users.service;

import gdgoc.biomarker.users.dto.FlaskRequestDto;
import gdgoc.biomarker.users.dto.HealthDataRequest;
import gdgoc.biomarker.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class HealthDataService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    @Autowired
    public HealthDataService(RestTemplate restTemplate,UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
    }

    // Flask 서버로 데이터를 전송하는 메소드
    public Map<String, Object> sendHealthDataToFlaskServer(FlaskRequestDto flaskRequestDto, Long userId) {
        String gender = userRepository.findGenderById(userId);
        String flaskServerUrl = "http://localhost:5000/" + userId + "/recommend_meal";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<FlaskRequestDto> request = new HttpEntity<>(flaskRequestDto, headers);

        // JSON을 Map으로 변환
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                flaskServerUrl,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        return response.getBody();
    }
}
