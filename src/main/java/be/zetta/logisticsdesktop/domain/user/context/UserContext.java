package be.zetta.logisticsdesktop.domain.user.context;

import be.zetta.logisticsdesktop.domain.user.entity.ApplicationUser;
import lombok.Data;
import org.springframework.stereotype.Component;


@Component()
@Data
public class UserContext {
    private ApplicationUser applicationUser;
}

