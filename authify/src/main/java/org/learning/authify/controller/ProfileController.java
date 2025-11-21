package org.learning.authify.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.learning.authify.io.ProfileRequest;
import org.learning.authify.io.ProfileResponse;
import org.learning.authify.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse createProfile(@Valid @RequestBody ProfileRequest request){
        System.out.println(request.toString());
        ProfileResponse response = profileService.createProfile(request);
        return response;
    }
}
