package site.sugarnest.backend.service.account;

import jakarta.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.sugarnest.backend.dto.dto.SendEmailDto;
import site.sugarnest.backend.entities.AccountEntity;
import site.sugarnest.backend.entities.TokenEntity;
import site.sugarnest.backend.exception.AppException;
import site.sugarnest.backend.exception.ErrorCode;
import site.sugarnest.backend.reponsitoties.IAccountRepository;
import site.sugarnest.backend.reponsitoties.ITokenRepository;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private IAccountRepository iaccountRepository;

    @Autowired
    private ITokenRepository tokenRepository;

    public String generateResetToken(String email) {
        String token = UUID.randomUUID().toString();
        TokenEntity tokenEntity = new TokenEntity(email, token);
        tokenRepository.save(tokenEntity);
        return token;
    }

    public void sendResetPasswordEmail(String accountEmail, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        InternetAddress fromAddress;
        try {
            fromAddress = new InternetAddress("20130253@st.hcmuaf.edu.vn", "SugarNest");
            message.setFrom(String.valueOf(fromAddress));
            message.setTo(accountEmail);
            message.setSubject("Reset Password");
            String resetLink = "http://localhost:3000/reset-password?token=" + token;
            message.setText("Click the link to reset your password: " + resetLink);
            javaMailSender.send(message);
        } catch (UnsupportedEncodingException e) {
            throw new AppException(ErrorCode.SEND_MAIL_FAILED);
        }
    }

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public void sendMail(String accountEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        InternetAddress fromAddress;
        try {
            fromAddress = new InternetAddress("20130253@st.hcmuaf.edu.vn", "SugarNest");
            message.setFrom(String.valueOf(fromAddress));
            message.setTo(accountEmail);
            message.setSubject(subject);
            message.setText("Mã xác thực: " + body);
            javaMailSender.send(message);
        } catch (UnsupportedEncodingException e) {
            throw new AppException(ErrorCode.SEND_MAIL_FAILED);
        }
    }

    public void verifyMail(SendEmailDto sendEmailDto) {
        AccountEntity entity = iaccountRepository.findByEmail(sendEmailDto.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXITED));
        try {
            if(entity.getVerificationCode().equals(passwordEncoder.encode(sendEmailDto.getVerificationCode())))
                entity.setEnabled("true");
            iaccountRepository.save(entity);
        } catch (Exception e) {
            throw new AppException(ErrorCode.VERIFICATION_ACCOUNT_INCORRECT_CODE);
        }
    }
}









