package gdgoc.biomarker.users.service;

import gdgoc.biomarker.users.dto.HealthDataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HealthDataService {

    private final RestTemplate restTemplate;

    @Autowired
    public HealthDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Flask 서버로 데이터를 전송하는 메소드
    public String sendHealthDataToFlaskServer(HealthDataRequest healthDataRequest, Long userId) {
        // Flask 서버 URL
        String flaskServerUrl = "http://localhost:5000/" + userId + "/recommend_meal";

        // HTTP 요청 헤더 설정
        HttpHeaders headers =  new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // HTTP 요청 본문
        HttpEntity<HealthDataRequest> request = new HttpEntity<>(healthDataRequest, headers);

        // Flask 서버로 POST 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                flaskServerUrl,
                HttpMethod.POST,
                request,
                String.class
        );

       // Flask 서버에서 반환된 응답 문자열 반환
        return response.getBody();
    }
}
