package com.github.nahualvisionsback.controller;

import com.github.nahualvisionsback.dto.UpdateAvatarUrl;
import com.github.nahualvisionsback.dto.UpdatePassword;
import com.github.nahualvisionsback.dto.UpdateUsername;
import com.github.nahualvisionsback.dto.UserProfile;
import com.github.nahualvisionsback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping ("/api/user")
@RequiredArgsConstructor
public class UserController {
    @Lazy
    final UserService userService;

    @GetMapping("/")
    public ResponseEntity<UserProfile> getUserByUUID(@RequestHeader("Authorization") String authorizationHeader) {
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            UserProfile user = userService.getUserProfile(authorizationHeader.substring(7))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user was not found"));
            return ResponseEntity.ok(user);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }

    @PutMapping("/username")
    public ResponseEntity<UserProfile> updateUsernameByUUID(@RequestHeader("Authorization") String authorizationHeader,
                                                        @RequestBody UpdateUsername updateUsername) {
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            final UserProfile user = userService.updateUsername(
                    authorizationHeader.substring(7),
                    updateUsername.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad update username"));
            return ResponseEntity.ok(user);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }

    @PutMapping("/password")
    public ResponseEntity<UserProfile> updatePasswordByUUID(@RequestHeader("Authorization") String authorizationHeader,
                                                        @RequestBody UpdatePassword updatePassword){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            final UserProfile user = userService.updatePassword(
                            authorizationHeader.substring(7),
                            updatePassword)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad update password"));
            return ResponseEntity.ok(user);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }

    @PutMapping("/avatarUrl")
    public ResponseEntity<UserProfile> updateAvatarUrlByUUID(@RequestHeader("Authorization") String authorizationHeader,
                                                        @RequestBody UpdateAvatarUrl updateAvatarUrl){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            final UserProfile user = userService.updateAvatarUrl(
                            authorizationHeader.substring(7),
                            updateAvatarUrl.getAvatarUrl())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad update avatar url"));
            return ResponseEntity.ok(user);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserByUUID(@RequestHeader("Authorization") String authorizationHeader) {
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            final String result = userService.deleteUser(authorizationHeader.substring(7));
            if(result != null) return ResponseEntity.ok(result);
            else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error when deleting a user");
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }

    @GetMapping("/anton")
    public String getAnton(){
        return "Anton !!!";
    }
}
