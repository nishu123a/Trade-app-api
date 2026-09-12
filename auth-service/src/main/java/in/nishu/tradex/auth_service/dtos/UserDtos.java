package in.nishu.tradex.auth_service.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDtos() {
    public record UpdateProfileRequest(
          //  @JsonProperty("fullName")
          @NotBlank   String fullName){

    }

    public record ChangePasswordRequest(
            @NotBlank String currentPassword,
            @NotBlank @Size(min=8,max=80) String newPassword) {
    }
}
