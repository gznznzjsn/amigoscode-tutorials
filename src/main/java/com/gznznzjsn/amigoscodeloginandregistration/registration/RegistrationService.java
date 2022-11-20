package com.gznznzjsn.amigoscodeloginandregistration.registration;

import com.gznznzjsn.amigoscodeloginandregistration.appuser.AppUser;
import com.gznznzjsn.amigoscodeloginandregistration.appuser.AppUserRole;
import com.gznznzjsn.amigoscodeloginandregistration.appuser.AppUserService;
import com.gznznzjsn.amigoscodeloginandregistration.registration.token.ConfirmationToken;
import com.gznznzjsn.amigoscodeloginandregistration.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final AppUserService appUserService;

    private final ConfirmationTokenService confirmationTokenService;
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("Email not valid");
        }
        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }


    @Transactional
    public String confirmToken(String token){
        LocalDateTime confirmedAt = LocalDateTime.now();
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);

        if(confirmationToken.getConfirmedAt()!=null){
            throw new IllegalStateException("email already confirmed");
        }
        LocalDateTime expiresAt = confirmationToken.getExpiresAt();
        if(expiresAt.isBefore(confirmedAt)){
            throw new IllegalStateException("token expired");
        }


        confirmationTokenService.setConfirmedAt(token,confirmedAt);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }
}
