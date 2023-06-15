package be.zetta.logisticsdesktop.domain.user.service;

import be.zetta.logisticsdesktop.domain.notification.entity.Notification;
import be.zetta.logisticsdesktop.domain.user.context.UserContext;
import be.zetta.logisticsdesktop.domain.user.entity.ApplicationUser;
import be.zetta.logisticsdesktop.domain.user.entity.UserRole;
import be.zetta.logisticsdesktop.domain.user.entity.dto.ApplicationUserDto;
import be.zetta.logisticsdesktop.domain.user.entity.dto.LoginDto;
import be.zetta.logisticsdesktop.domain.user.entity.dto.RegisterDto;
import be.zetta.logisticsdesktop.domain.user.entity.dto.UserPersonConnectionDto;
import be.zetta.logisticsdesktop.domain.user.mapper.ApplicationUserMapper;
import be.zetta.logisticsdesktop.domain.user.repository.UserRepository;
import be.zetta.logisticsdesktop.domain.util.entity.Person;
import be.zetta.logisticsdesktop.domain.util.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {
    public static final String USER_NOT_FOUND = "User not found";
    public static final String PERSON_NOT_FOUND = "Person not found";
    public static final String ALREADY_REGISTERED_WITH_THIS_EMAIL = "User already registered with this email.";
    private final UserRepository userRepository;
    private final UserContext userContext;
    private final ApplicationUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;

    public boolean login(LoginDto data) {
        log.info("Login for user {} called from Service.", data.getEmail());
        ApplicationUser potentialApplicationUser = userRepository.findByEmail(data.getEmail())
                .orElseThrow(() -> {
                    log.error("user {} not found in database.", data.getEmail());
                    return new IllegalArgumentException(USER_NOT_FOUND);
                });
        if (passwordEncoder.matches(data.getPassword(),potentialApplicationUser.getPassword())) {
            log.info("Setting user with email {} as active application user", potentialApplicationUser.getEmail());
            userContext.setApplicationUser(potentialApplicationUser);
            return true;
        } else {
            return false;
        }
    }

    public ApplicationUserDto register(RegisterDto data) {
        log.info("Register for new user {} called from Service.", data.getEmail());
        if (!userRepository.existsByEmail(data.getEmail())) {
            data.setPassword(passwordEncoder.encode(data.getPassword()));
            ApplicationUser savedApplicationUser = userRepository.save(userMapper.toEntity(data));
            log.info("Setting user with email {} as active application user", data.getEmail());
            userContext.setApplicationUser(savedApplicationUser);
            return userMapper.toDto(savedApplicationUser);
        } else {
            log.error(ALREADY_REGISTERED_WITH_THIS_EMAIL);
            throw new IllegalArgumentException(ALREADY_REGISTERED_WITH_THIS_EMAIL);
        }
    }

    public ApplicationUserDto updateUser(String id, RegisterDto data) {
        log.info("Update for user with id {} called from Service.", id);
        ApplicationUser potentialApplicationUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("user {} not found in database.", data.getEmail());
                    return new IllegalArgumentException(USER_NOT_FOUND);
                });
        if (!passwordEncoder.matches(data.getPassword(), potentialApplicationUser.getPassword())){
            potentialApplicationUser.setPassword(passwordEncoder.encode(data.getPassword()));
        }
        return userMapper.toDto(userRepository.save(userMapper.updateEntity(potentialApplicationUser, data)));
    }

    public ApplicationUserDto deleteUser(String id) {
        log.info("Delete for user with id {} called from Service.", id);
        ApplicationUser deletedApplicationUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("user with id {} not found in database.", id);
                    return new IllegalArgumentException(USER_NOT_FOUND);
                });
        userRepository.deleteById(id);
        log.info("User with id {} deleted.", id);
        return userMapper.toDto(deletedApplicationUser);
    }

    public void AddNotificationsToMagazijniersForNewOrder(String orderId) {
        List<ApplicationUser> magazijniers = userRepository.findAll()
                .stream()
                .filter(user -> user.getRole().equals(UserRole.MAGAZIJNIER))
                .toList();
        magazijniers.forEach(magazijnier -> magazijnier.getNotifications().add(new Notification(orderId)));
        userRepository.saveAll(magazijniers);
    }

    public void AddNotificationsToAankopersForProcessedOrder(String orderId) {
        List<ApplicationUser> aankopers = userRepository.findAll()
                .stream()
                .filter(user -> user.getRole().equals(UserRole.AANKOPER))
                .toList();
        aankopers.forEach(aankoper -> aankoper.getNotifications().add(new Notification(orderId)));
        userRepository.saveAll(aankopers);
    }

    public void connectApplicationUserToPerson(UserPersonConnectionDto data) {
        ApplicationUser potentialUser = userRepository.findById(data.getApplicationUserId())
                .orElseThrow(()-> new IllegalArgumentException(USER_NOT_FOUND));
        Person personToLink = personRepository.findById(data.getApplicationUserId())
                .orElseThrow(()-> new IllegalArgumentException(PERSON_NOT_FOUND));
        potentialUser.setPersoonsgegevens(personToLink);
        userRepository.save(potentialUser);
        if (userContext.getApplicationUser().getUserId().equals(potentialUser.getUserId())){
            this.userContext.setApplicationUser(potentialUser);
        }
    }
}
