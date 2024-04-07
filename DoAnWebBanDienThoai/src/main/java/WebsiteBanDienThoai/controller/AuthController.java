package WebsiteBanDienThoai.controller;

import WebsiteBanDienThoai.dto.UserDto;
import WebsiteBanDienThoai.entity.OTP;
import WebsiteBanDienThoai.entity.User;
import WebsiteBanDienThoai.repository.OTPRepository;
import WebsiteBanDienThoai.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
public class AuthController {

    private UserService userService;
    @Autowired
    private OTPRepository otpRepository;

    public AuthController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/admin")
    public String dashboard(){
        return "admin/index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @Autowired
    private JavaMailSender javaMailSender;


    @GetMapping("/forgot")
    public String forgotPassword() {


        return "forgot";
    }
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody String email) {
        // Generate random OTP
        String otp = generateOTP();
        String encodedEmail = email;
        try {
            String decodedEmail = URLDecoder.decode(encodedEmail, "UTF-8");
            String kq = decodedEmail.substring(6);
            sendOTPEmail(kq, otp);
            OTP temp = new OTP();
                temp.setEmail(kq);
                temp.setOtp(otp);
            otpRepository.save(temp);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // Save the OTP in your database (you may associate it with the user's email)

        // Send the OTP via email


        return "verify-otp";
    }

    @PostMapping("/verifyOtp")
    public String verifyOtp(@RequestBody String otp) {
        // Lấy mã OTP từ form
        List<OTP> otpList = otpRepository.findAll();

        String kq = otp.substring(4);
        for (OTP otpData : otpList) {
            // Thực hiện kiểm tra logic, ví dụ: so sánh OTP
            if (otpData.getOtp().equals(kq)) {
                // Nếu mã OTP hợp lệ, lấy ID và xoá ID từ cơ sở dữ liệu
                long id = otpData.getId();
                otpRepository.deleteById(id);
                // Thực hiện các hành động tiếp theo hoặc trả về thông báo thành công
                return "index";
            }
        }
        return "forgot";
    }
    private String generateOTP() {
        int otpLength = 6;
        String numbers = "0123456789";
        Random random = new Random();

        return random.ints(otpLength, 0, numbers.length())
                .mapToObj(numbers::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    private void sendOTPEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Forgot Password OTP");
        message.setFrom("nq2017.voducmanh1482002@gmail.com");
        message.setText("Your OTP is: " + otp);

        javaMailSender.send(message);
    }



}
