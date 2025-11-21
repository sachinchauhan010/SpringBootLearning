package org.learning.authify.service;

import lombok.RequiredArgsConstructor;
import org.learning.authify.entity.UserEntity;
import org.learning.authify.io.ProfileRequest;
import org.learning.authify.io.ProfileResponse;
import org.learning.authify.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final UserRepository userRepository;
    @Override
    public ProfileResponse createProfile(ProfileRequest request){
        UserEntity newProfile= convertToUserEntity(request);
        if(!userRepository.existsByEmail(newProfile.getEmail())){
            newProfile=userRepository.save(newProfile);
            return convertToProfileResponse(newProfile);

        }

        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");

    }

    private UserEntity convertToUserEntity(ProfileRequest request) {
        return UserEntity.builder()
                .email(request.getEmail())
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .password(request.getPassword())
                .isAccountVerified(false)
                .resetOtpExpireAt(0L)
                .verifyOtp(null)
                .verifyOtpExpireAt(0L)
                .resetOtp(null)
                .build();
    }

    private ProfileResponse convertToProfileResponse(UserEntity newProfile) {
        return ProfileResponse.builder()
                .name(newProfile.getName())
                .email(newProfile.getEmail())
                .userId(newProfile.getUserId())
                .isAccountVerified(newProfile.getIsAccountVerified())
                .build();
    }
}
