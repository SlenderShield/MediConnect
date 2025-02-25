package com.hungrycoder.auth.controllers;

import org.springframework.security.access.prepost.PreAuthorize; // Import PreAuthorize for role-based access control
import org.springframework.web.bind.annotation.CrossOrigin; // Import CrossOrigin for handling CORS
import org.springframework.web.bind.annotation.GetMapping; // Import GetMapping for handling GET requests
import org.springframework.web.bind.annotation.RequestMapping; // Import RequestMapping for defining request paths
import org.springframework.web.bind.annotation.RestController; // Import RestController for creating RESTful controllers

@CrossOrigin(origins = "*", maxAge = 3600) // Allow cross-origin requests from any origin for 1 hour
@RestController // Indicate that this class is a REST controller
@RequestMapping("/api/test") // Base URL for test-related endpoints
public class TestController {

	/**
	 * Public endpoint that can be accessed without any authentication.
	 *
	 * @return A string message indicating public content.
	 */
	@GetMapping("/all") // Map GET requests to "/api/test/all"
	public String allAccess() {
		return "Public Content."; // Return a message accessible by anyone
	}

	/**
	 * Endpoint accessible only to users with USER, MODERATOR, or ADMIN roles.
	 *
	 * @return A string message indicating user content.
	 */
	@GetMapping("/patient") // Map GET requests to "/api/test/user"
	@PreAuthorize("hasRole('PATIENT')")
	public String patientAccess() {
		return "Patient Content."; // Return a message accessible by users with the required roles
	}

	/**
	 * Endpoint accessible only to users with the MODERATOR role.
	 *
	 * @return A string message indicating moderator board content.
	 */
	@GetMapping("/doctor") // Map GET requests to "/api/test/mod"
	@PreAuthorize("hasRole('DOCTOR')") // Require MODERATOR role
	public String doctorAccess() {
		return "Doctor Board."; // Return a message accessible by moderators
	}

	/**
	 * Endpoint accessible only to users with the ADMIN role.
	 *
	 * @return A string message indicating admin board content.
	 */
	@GetMapping("/admin") // Map GET requests to "/api/test/admin"
	@PreAuthorize("hasRole('ADMIN')") // Require ADMIN role
	public String adminAccess() {
		return "Admin Board."; // Return a message accessible by admins
	}
}
