package org.learning.authify.service;

import lombok.RequiredArgsConstructor;
import org.learning.authify.io.ProfileRequest;
import org.learning.authify.io.ProfileResponse;
import org.springframework.stereotype.Service;

public interface ProfileService {

    ProfileResponse createProfile(ProfileRequest request);
}
