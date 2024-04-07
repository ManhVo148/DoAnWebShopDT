package WebsiteBanDienThoai.Validator;

import WebsiteBanDienThoai.Validator.annotation.ValidUsername;
import WebsiteBanDienThoai.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ValidUsernameValidator implements ConstraintValidator<ValidUsername, String> {
    private final UserService userService;
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return userService.findByEmail(username).getEmail().isEmpty();
    }
}