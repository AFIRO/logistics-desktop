package be.zetta.logisticsdesktop.domain.user.controller;

import be.zetta.logisticsdesktop.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;
import java.util.HashSet;

import static be.zetta.logisticsdesktop.domain.user.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private Validator validator;
    @InjectMocks
    private UserController userController;

    @Test
    void login_happyFlow() {
        when(userService.login(getLoginDto())).thenReturn(true);
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());


        assertThat(userController.login(getLoginDto())).isTrue();

    }

    @Test
    void register_happyFlow() {
        when(userService.register(getRegisterDto())).thenReturn(getApplicationUserDto());
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());


        assertThat(userController.register(getRegisterDto())).isEqualTo(getApplicationUserDto());
    }

    @Test
    void updateUser_happyFlow() {
        when(userService.updateUser(TEST_ID, getRegisterDto())).thenReturn(getApplicationUserDto());
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());


        assertThat(userController.updateUser(TEST_ID, getRegisterDto())).isEqualTo(getApplicationUserDto());
    }

    @Test
    void deleteUser_happyFlow() {
        when(userService.deleteUser(TEST_ID)).thenReturn(getApplicationUserDto());

        assertThat(userController.deleteUser(TEST_ID)).isEqualTo(getApplicationUserDto());
    }

    @Test
    void AddNotificationsToMagazijniersForNewOrder_happyFlow(){
        userController.AddNotificationsToMagazijniersForNewOrder(TEST_ID);
        verify(userService,times(1)).AddNotificationsToMagazijniersForNewOrder(TEST_ID);
    }
}