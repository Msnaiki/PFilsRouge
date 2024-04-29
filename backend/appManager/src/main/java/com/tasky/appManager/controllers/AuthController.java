    package com.tasky.appManager.controllers;


    import com.auth0.jwt.JWT;
    import com.auth0.jwt.interfaces.DecodedJWT;
    import com.tasky.appManager.domaine.User;
    import com.tasky.appManager.dtos.EmployeeDto;
    import com.tasky.appManager.dtos.LoginRequest;
    import com.tasky.appManager.dtos.ManagerDto;
    import com.tasky.appManager.security.JwtAuthenticationResponse;
    import com.tasky.appManager.security.JwtTokenProvider;
    import com.tasky.appManager.services.AuthenticationService;
    import com.tasky.appManager.services.EmployeeService;
    import com.tasky.appManager.services.ManagerService;
    import com.tasky.appManager.services.TokenStore;
    import lombok.Data;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.HashMap;
    import java.util.Map;
    import java.util.Optional;

    @RestController
    public class AuthController {

        @Autowired
        private JwtTokenProvider tokenProvider;
        @Autowired
        private AuthenticationService authService;
        @Autowired
        private TokenStore tokenStore;
        @Autowired
        private EmployeeService employeeService;
        @Autowired
        private ManagerService managerService;

        @PostMapping("/auth/login")
        public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
            User user = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            String token = tokenProvider.generateToken(user);
            return ResponseEntity.ok(new JwtAuthenticationResponse("Bearer " + token));
        }

        @PostMapping("/auth/logout")
       /* public void logout(@RequestHeader(value="Authorization") String authHeader) {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                System.out.println ("Logged out successfully - Debug mode");
            }
            System.out.println ("No Authorization header present");
        }*/

        public ResponseEntity<?> logout(@RequestHeader(value="Authorization") String authHeader) {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                long remainingTime = calculateRemainingTime(token);
                System.out.println(authHeader);
                if (remainingTime > 0) {
                    tokenStore.invalidateToken(token, remainingTime);
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "logout success");
                    return ResponseEntity.ok(response);

                }
            }
            return ResponseEntity.badRequest().body("Invalid token or token already expired");
        }

        @PutMapping ("/auth/em/update-password")
        public ResponseEntity<?> updateEmployeePassword(@RequestHeader(value="Authorization") String authHeader, @RequestParam String newPassword) {
            String username = extractUsernameFromToken(authHeader);
            if (username != null) {
                Optional<EmployeeDto> updatedEmployee = employeeService.updateEmployeePassword(username, newPassword);
                if (updatedEmployee.isPresent()) {
                    return ResponseEntity.ok().body("Password updated successfully");
                } else {
                    return ResponseEntity.badRequest().body("Failed to update password");
                }
            } else {
                return ResponseEntity.badRequest().body("Invalid token or token already expired");
            }
        }

        private String extractUsernameFromToken(String authHeader) {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                DecodedJWT jwt = JWT.decode(token);
                // Assuming the token contains the username in some claim
                return jwt.getClaim("sub").asString();
            }
            return null;
        }
        private long calculateRemainingTime(String token) {
            // Decode the JWT to find the expiration time
            DecodedJWT jwt = JWT.decode(token);
            long expirationTime = jwt.getExpiresAt().getTime();
            long currentTime = System.currentTimeMillis();
            return Math.max(0, expirationTime - currentTime);
        }
        @PutMapping ("/auth/manager/update-password")
        public ResponseEntity<?> updateManagerPassword(@RequestHeader(value="Authorization") String authHeader, @RequestParam String newPassword) {
            String username = extractUsernameFromToken(authHeader);
            if (username != null) {
                Optional<ManagerDto> updatedManager = managerService.updateManagerPassword(username, newPassword);
                if (updatedManager.isPresent()) {
                    return ResponseEntity.ok().body("Password updated successfully");
                } else {
                    return ResponseEntity.badRequest().body("Failed to update password");
                }
            } else {
                return ResponseEntity.badRequest().body("Invalid token or token already expired");
            }
        }
    }