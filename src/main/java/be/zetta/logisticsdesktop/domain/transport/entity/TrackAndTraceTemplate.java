package be.zetta.logisticsdesktop.domain.transport.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Random;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class TrackAndTraceTemplate {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String trackAndTraceTemplateId;
    private int numberOfChars;
    private String prefix;
    @Column(columnDefinition = "boolean default false")
    private boolean onlyNumbers;
    @Enumerated(EnumType.STRING)
    private TrackAndTraceExtraVerification extraVerification;

    public String generateTrackAndTraceCode() {
        StringBuilder code = new StringBuilder(this.prefix + "-");
        Random random = new Random();
        if (this.onlyNumbers) {
            for (int i = this.numberOfChars; i > 0; i--) {
                code.append(random.nextInt(10));
            }
        } else {
            String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
            for (int i = this.numberOfChars; i > 0; i--) {
                int index = (int) (random.nextFloat() * SALTCHARS.length());
                code.append(SALTCHARS.charAt(index));
            }
        }
        return code.toString();
    }
}
