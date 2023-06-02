package com.weride.service;

import com.weride.dto.ResetPasswordRequest;
import com.weride.dto.Result;
import com.weride.dto.UserActivationRequest;
import com.weride.dto.UserAuthRequest;


public interface UserService {

    Result register(UserAuthRequest request);

    Result login(UserAuthRequest request);

    Result sendVerificationEmail(String email);

    Result resetPassword(ResetPasswordRequest request);

    Result activateAccount(UserActivationRequest user);

    Result refreshTokens(String oldRefreshToken);
}
