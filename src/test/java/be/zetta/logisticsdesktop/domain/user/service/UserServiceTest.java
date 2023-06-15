package be.zetta.logisticsdesktop.domain.user.service;

import be.zetta.logisticsdesktop.domain.user.context.UserContext;
import be.zetta.logisticsdesktop.domain.user.entity.ApplicationUser;
import be.zetta.logisticsdesktop.domain.user.entity.UserRole;
import be.zetta.logisticsdesktop.domain.user.entity.dto.ApplicationUserDto;
import be.zetta.logisticsdesktop.domain.user.entity.dto.LoginDto;
import be.zetta.logisticsdesktop.domain.user.entity.dto.RegisterDto;
import be.zetta.logisticsdesktop.domain.user.mapper.ApplicationUserMapper;
import be.zetta.logisticsdesktop.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static be.zetta.logisticsdesktop.domain.user.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserContext userContext;
    @Mock
    private ApplicationUserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Captor
    ArgumentCaptor<List<ApplicationUser>> userCaptor;
    @InjectMocks
    private UserService userService;


    @Test
    void login_happyFlow_returnsTrue() {
        LoginDto data = getLoginDto();
        ApplicationUser expected = getApplicationUser();

        when(userRepository.findByEmail(data.getEmail())).thenReturn(Optional.ofNullable(expected));
        when(passwordEncoder.matches(data.getPassword(),expected.getPassword())).thenReturn(true);
        Boolean actual = userService.login(data);

        assertThat(actual).isEqualTo(true);
        verify(userRepository, times(1)).findByEmail(data.getEmail());
        verify(userContext, times(1)).setApplicationUser(expected);
    }

    @Test
    void login_wrongPassword_returnsFalse() {
        LoginDto data = getLoginDto();
        ApplicationUser expected = getInvalidApplicationUser();

        when(userRepository.findByEmail(data.getEmail())).thenReturn(Optional.ofNullable(expected));
        Boolean actual = userService.login(data);

        assertThat(actual).isEqualTo(false);
        verify(userRepository, times(1)).findByEmail(data.getEmail());
        verify(userContext, times(0)).setApplicationUser(expected);
    }

    @Test
    void login_notFound_Throws() {
        LoginDto data = getLoginDto();
        ApplicationUser expected = getApplicationUser();

        when(userRepository.findByEmail(data.getEmail())).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.login(data));
        assertThat(exception.getMessage()).isEqualTo(NOT_FOUND);
        verify(userRepository, times(1)).findByEmail(data.getEmail());
        verify(userContext, times(0)).setApplicationUser(expected);
    }

    @Test
    void register_happyFlow_returnsDto() {
        RegisterDto data = getRegisterDto();
        ApplicationUserDto expected = getApplicationUserDto();
        ApplicationUser noId = getApplicationUserNoId();
        ApplicationUser savedUser = getApplicationUser();

        when(userRepository.existsByEmail(data.getEmail())).thenReturn(false);
        when(userMapper.toEntity(data)).thenReturn(noId);
        when(userRepository.save(noId)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(expected);

        ApplicationUserDto actual = userService.register(data);

        assertThat(actual).isEqualTo(expected);
        verify(userRepository, times(1)).existsByEmail(data.getEmail());
        verify(userContext, times(1)).setApplicationUser(savedUser);
    }

    @Test
    void register_alreadyExists_throwsException() {
        RegisterDto data = getRegisterDto();
        ApplicationUser savedUser = getApplicationUser();

        when(userRepository.existsByEmail(data.getEmail())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.register(data));
        assertThat(exception.getMessage()).isEqualTo(ALREADY_REGISTERED_WITH_THIS_EMAIL);

        verify(userRepository, times(1)).existsByEmail(data.getEmail());
        verify(userContext, times(0)).setApplicationUser(savedUser);
    }

    @Test
    void updateUser_HappyFlow() {
        RegisterDto data = getRegisterDto();
        ApplicationUser foundUser = getApplicationUser();
        ApplicationUser updatedUser = getUpdatedApplicationUser();
        ApplicationUserDto expected = getApplicationUserDto();

        when(userRepository.findById(TEST_ID)).thenReturn(Optional.ofNullable(foundUser));
        when(userMapper.updateEntity(foundUser, data)).thenReturn(updatedUser);
        when(passwordEncoder.matches(foundUser.getPassword(),data.getPassword())).thenReturn(true);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        when(userMapper.toDto(updatedUser)).thenReturn(expected);

        ApplicationUserDto actual = userService.updateUser(TEST_ID, data);
        assertThat(actual).isEqualTo(expected);
        verify(userRepository, times(1)).findById(TEST_ID);
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    void updateUser_notFound_throws() {
        RegisterDto data = getRegisterDto();
        ApplicationUser updatedUser = getUpdatedApplicationUser();

        when(userRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.updateUser(TEST_ID, data));
        assertThat(exception.getMessage()).isEqualTo(NOT_FOUND);
        verify(userRepository, times(1)).findById(TEST_ID);
        verify(userRepository, times(0)).save(updatedUser);
    }


    @Test
    void deleteUser_happyFlow() {
        ApplicationUser deletedUser = getApplicationUser();
        ApplicationUserDto expected = getApplicationUserDto();

        when(userRepository.findById(TEST_ID)).thenReturn(Optional.ofNullable(deletedUser));
        when(userMapper.toDto(deletedUser)).thenReturn(expected);

        ApplicationUserDto actual = userService.deleteUser(TEST_ID);
        assertThat(actual).isEqualTo(expected);
        verify(userRepository, times(1)).findById(TEST_ID);
        verify(userRepository, times(1)).deleteById(TEST_ID);
    }

    @Test
    void deleteUser_notFound_throws() {
        when(userRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(TEST_ID));
        assertThat(exception.getMessage()).isEqualTo(NOT_FOUND);
        verify(userRepository, times(1)).findById(TEST_ID);
        verify(userRepository, times(0)).deleteById(TEST_ID);
    }

    @Test
    void AddNotificationsToMagazijniersForNewOrder_happyFlow(){
        ApplicationUser listContents = getApplicationUser();
        listContents.setRole(UserRole.MAGAZIJNIER);
        List<ApplicationUser> startData = Arrays.asList(listContents);

        when(userRepository.findAll()).thenReturn(startData);
        userService.AddNotificationsToMagazijniersForNewOrder(TEST_ID);
        verify(userRepository,times(1)).saveAll(userCaptor.capture());
        ApplicationUser actual = userCaptor.getValue().get(0);

        verify(userRepository, times(1)).findAll();
        Assertions.assertThat(actual.getNotifications()).isNotEmpty();
        Assertions.assertThat(actual.getNotifications().get(0).getIdOfOrderToBeChecked()).isEqualTo(TEST_ID);
        Assertions.assertThat(actual.getNotifications().get(0).isActive()).isEqualTo(true);
    }

    @Test
    void AddNotificationsToMagazijniersForNewOrder_WrongRole_Unchanged(){
        ApplicationUser listContents = getApplicationUser();
        List<ApplicationUser> startData = Arrays.asList(listContents);

        when(userRepository.findAll()).thenReturn(startData);
        userService.AddNotificationsToMagazijniersForNewOrder(TEST_ID);
        verify(userRepository,times(1)).saveAll(userCaptor.capture());
        List<ApplicationUser> actual = userCaptor.getValue();

        verify(userRepository, times(1)).findAll();
        assertThat(actual).isEmpty();
    }

    @Test
    void AddNotificationsToAankopersForPrcoessedOrder_happyFlow(){
        ApplicationUser listContents = getApplicationUser();
        listContents.setRole(UserRole.AANKOPER);
        List<ApplicationUser> startData = Arrays.asList(listContents);

        when(userRepository.findAll()).thenReturn(startData);
        userService.AddNotificationsToAankopersForProcessedOrder(TEST_ID);
        verify(userRepository,times(1)).saveAll(userCaptor.capture());
        ApplicationUser actual = userCaptor.getValue().get(0);

        verify(userRepository, times(1)).findAll();
        Assertions.assertThat(actual.getNotifications()).isNotEmpty();
        Assertions.assertThat(actual.getNotifications().get(0).getIdOfOrderToBeChecked()).isEqualTo(TEST_ID);
        Assertions.assertThat(actual.getNotifications().get(0).isActive()).isEqualTo(true);
    }

    @Test
    void AddNotificationsToAankopersForPrcoessedOrder_WrongRole_Unchanged(){
        ApplicationUser listContents = getApplicationUser();
        List<ApplicationUser> startData = Arrays.asList(listContents);

        when(userRepository.findAll()).thenReturn(startData);
        userService.AddNotificationsToAankopersForProcessedOrder(TEST_ID);
        verify(userRepository,times(1)).saveAll(userCaptor.capture());
        List<ApplicationUser> actual = userCaptor.getValue();

        verify(userRepository, times(1)).findAll();
        assertThat(actual).isEmpty();
    }

}