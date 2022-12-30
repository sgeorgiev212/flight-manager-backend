package com.example.demo.controller;

import com.example.demo.model.dto.airline.AddAirlineReviewDto;
import com.example.demo.model.dto.passenger.*;
import com.example.demo.model.dto.ticket.PassengerTicketDto;
import com.example.demo.model.dto.flight.BookingRequestDto;
import com.example.demo.model.dto.travelAgency.AddTravelAgencyReviewDto;
import com.example.demo.model.entity.AirlineReview;
import com.example.demo.model.entity.TravelAgencyReview;
import com.example.demo.service.PassengerService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.example.demo.util.ServiceUtil.RESET_PASSWORD_LINK;

@RestController
@RequestMapping("/passenger")
@CrossOrigin(origins = "http://localhost:8080")
public class PassengerController {

    @Autowired
    PassengerService passengerService;

    @Autowired
    JavaMailSender mailSender;

    @PostMapping
    public RegisterPassengerResponseDto registerUser(@RequestBody RegisterPassengerRequestDto registerPassengerDto) {
        return passengerService.registerPassenger(registerPassengerDto);
    }

    @PostMapping("/login")
    public PassengerDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return passengerService.login(loginRequestDto);
    }

    @PutMapping("/{id}")
    public PassengerDto editProfile(@RequestBody EditProfileDto editProfileDto, @PathVariable int id) {
        return passengerService.editProfile(editProfileDto, id);
    }

    @PutMapping("/{id}/password")
    public PassengerDto changePassword(@RequestBody ChangePasswordDto changePasswordDto, @PathVariable int id) {
        return passengerService.changePassword(changePasswordDto, id);
    }

    @GetMapping("/{id}/bookings")
    public List<BookingRequestDto> getAllBookingsForUser(@PathVariable int id) {
        return passengerService.getAllBookingsForUser(id);
    }

    @GetMapping("/{id}/tickets")
    public List<PassengerTicketDto> getAllTicketsForUser(@PathVariable int id) {
        return passengerService.getAllTicketsForUser(id);
    }

    @DeleteMapping("/{id}/bookings/{bookingId}")
    public String cancelABookingRequestForPassenger(@PathVariable int id, @PathVariable int bookingId) {
        return passengerService.cancelABookingRequestForPassenger(id, bookingId);
    }

    @PostMapping("/reviews/agency")
    public TravelAgencyReview addReviewForTravelAgency(@RequestBody AddTravelAgencyReviewDto addTravelAgencyReviewDto) {
        return passengerService.addReviewForTravelAgency(addTravelAgencyReviewDto);
    }

    @PostMapping("/reviews/airline")
    public AirlineReview addReviewForAirline(@RequestBody AddAirlineReviewDto airlineReviewDto) {
        return passengerService.addReviewForAirline(airlineReviewDto);
    }

    @GetMapping("/{id}")
    public PassengerDto findPassengerById(@PathVariable int id) {
        return new PassengerDto(passengerService.findPassengerById(id));
    }

    @PutMapping("/updatePassword")
    public void setNewPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        passengerService.setNewPassword(resetPasswordDto);
    }

    @PostMapping("/resetPassword/{email}")
    public void handleForgotPasswordRequest(@PathVariable String email) {

        String token = RandomString.make(45);
        passengerService.updateResetPasswordToken(token, email);
        String resetPasswordLink = RESET_PASSWORD_LINK + "?token=" + token;
        sendEmail(email, resetPasswordLink);
    }

    private void sendEmail(String email, String resetPasswordLink) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("airlineManager@gmail.com", "Password support");
            helper.setTo(email);

            String subject = "Use this link to reset your password";

            String content = "<p>Hello,</p>"
                    + "<p>You have requested to reset your password.</p>"
                    + "<p>Click the link below to change your password:</p>"
                    + "<p><b><a href=\"" + resetPasswordLink + "\">Change my password</a></b></p>";

            helper.setSubject(subject);
            helper.setText(content, true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        mailSender.send(message);
    }

}
