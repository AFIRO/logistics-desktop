package be.zetta.logisticsdesktop.domain.packaging.entity.dto;

import be.zetta.logisticsdesktop.domain.packaging.entity.PackagingType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PackagingDto {
    private String packagingId;
    @NotBlank(message = "A packaging requires a name.")
    @NotNull(message = "A packaging requires a name.")
    private String packagingName;
    @NotNull(message = "A packaging requires a type.")
    private PackagingType type;
    @NotNull(message = "A packaging requires a height.")
    @Positive(message = "You can not input a negative height.")
    private double height;
    @NotNull(message = "A packaging requires a width.")
    @Positive(message = "You can not input a negative width.")
    private double width;
    @NotNull(message = "A packaging requires a length.")
    @Positive(message = "You can not input a negative length.")
    private double length;
    @NotNull(message = "A packaging requires a price.")
    @Positive(message = "You can not input a negative price.")
    private double price;
    @NotNull(message = "Packaging must be active or disabled.")
    private boolean active;
}
