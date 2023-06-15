package be.zetta.logisticsdesktop.domain.user;

import be.zetta.logisticsdesktop.domain.user.entity.ApplicationUser;
import be.zetta.logisticsdesktop.domain.user.entity.UserRole;
import be.zetta.logisticsdesktop.domain.user.entity.dto.ApplicationUserDto;
import be.zetta.logisticsdesktop.domain.user.entity.dto.LoginDto;
import be.zetta.logisticsdesktop.domain.user.entity.dto.RegisterDto;

import java.util.ArrayList;

public class UserTestData {
    public static final String TEST_MAIL = "TEST_MAIL";
    public static final String TEST_PASSWORD = "TEST_PASSWORD";
    public static final String TEST_PASSWORD_INVALID = "TEST_PASSWORD_INVALID";
    public static final UserRole TEST_ROLE = UserRole.ADMIN;
    public static final String TEST_ID = "TEST_ID";
    public static final String TEST_MAIL_UPDATED = "TEST_MAIL_UPDATED";
    public static final String NOT_FOUND = "User not found";
    public static final String ALREADY_REGISTERED_WITH_THIS_EMAIL = "User already registered with this email.";

    public static RegisterDto getRegisterDto() {
        return RegisterDto.builder()
                .email(TEST_MAIL)
                .password(TEST_PASSWORD)
                .role(TEST_ROLE)
                .build();
    }

    public static RegisterDto getUpdatedRegisterDto() {
        return RegisterDto.builder()
                .email(TEST_MAIL_UPDATED)
                .password(TEST_PASSWORD)
                .role(TEST_ROLE)
                .build();
    }

    public static ApplicationUser getApplicationUser() {
        return ApplicationUser.builder()
                .userId(TEST_ID)
                .email(TEST_MAIL)
                .password(TEST_PASSWORD)
                .role(TEST_ROLE)
                .notifications(new ArrayList<>())
                .build();
    }

    public static ApplicationUser getApplicationUserNoId() {
        return ApplicationUser.builder()
                .userId(null)
                .email(TEST_MAIL)
                .password(TEST_PASSWORD)
                .role(TEST_ROLE)
                .build();
    }

    public static ApplicationUser getUpdatedApplicationUser() {
        return ApplicationUser.builder()
                .userId(TEST_ID)
                .email(TEST_MAIL_UPDATED)
                .password(TEST_PASSWORD)
                .role(TEST_ROLE)
                .build();
    }

    public static ApplicationUserDto getApplicationUserDto() {
        return ApplicationUserDto.builder()
                .userId(TEST_ID)
                .email(TEST_MAIL)
                .role(TEST_ROLE)
                .notifications(new ArrayList<>())
                .build();
    }

    public static LoginDto getLoginDto() {
        return LoginDto.builder()
                .email(TEST_MAIL)
                .password(TEST_PASSWORD)
                .build();
    }

    public static ApplicationUser getInvalidApplicationUser() {
        return ApplicationUser.builder()
                .userId(TEST_ID)
                .email(TEST_MAIL)
                .password(TEST_PASSWORD_INVALID)
                .role(TEST_ROLE)
                .build();
    }

}
