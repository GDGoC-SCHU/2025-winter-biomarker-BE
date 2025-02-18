package gdgoc.biomarker.auth.service;

import gdgoc.biomarker.auth.dto.request.LoginRequest;
import gdgoc.biomarker.auth.dto.response.LoginResponse;
import gdgoc.biomarker.auth.dto.request.SignUpRequest;
import gdgoc.biomarker.auth.dto.response.SignUpResponse;
import gdgoc.biomarker.users.entity.User;
import gdgoc.biomarker.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    // 회원가입 로직
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        // 새로운 사용자 생성
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setGender(signUpRequest.getGender());

        // 사용자 저장
        user = userRepository.save(user);

        // 응답 반환
        return new SignUpResponse(user.getId());
    }

    // 로그인 로직
    public LoginResponse login(LoginRequest loginRequest) {
        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일을 확인해주세요"));

        // 비밀번호 확인
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        // 로그인 성공, 응답 반환
        return new LoginResponse(user.getId());
    }

}
