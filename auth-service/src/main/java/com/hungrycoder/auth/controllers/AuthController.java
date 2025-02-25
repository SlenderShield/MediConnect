package com.hungrycoder.auth.controllers;

import com.hungrycoder.auth.payload.request.LoginRequest;
import com.hungrycoder.auth.payload.request.SignupRequest;
import com.hungrycoder.auth.payload.response.JwtResponse;
import com.hungrycoder.auth.payload.response.MessageResponse;
import com.hungrycoder.auth.services.AuthService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
		return ResponseEntity.ok(jwtResponse);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		MessageResponse response = authService.registerUser(signUpRequest);

		if (response.message().startsWith("Error:")) {
			return ResponseEntity.badRequest().body(response);
		}

		return ResponseEntity.ok(response);
	}
}
