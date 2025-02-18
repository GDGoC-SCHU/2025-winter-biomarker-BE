package gdgoc.biomarker.auth.controller;

import gdgoc.biomarker.auth.dto.request.LoginRequest;
import gdgoc.biomarker.auth.dto.request.SignUpRequest;
import gdgoc.biomarker.auth.dto.response.ErrorResponse;
import gdgoc.biomarker.auth.dto.response.LoginResponse;
import gdgoc.biomarker.auth.dto.response.SignUpResponse;
import gdgoc.biomarker.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // 회원가입 로직
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Validated @RequestBody SignUpRequest signUpRequest) {
        try{
            // 회원가입 서비스 호출
            SignUpResponse response = authService.signUp(signUpRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 Created
        } catch (IllegalArgumentException e){
            // 예외 발생 시 400
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    //로그인 로직
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest loginRequest) {
        try {
            // 로그인 서비스 호출
            LoginResponse response = authService.login(loginRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e){
            // 예외 발생 시 400
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
