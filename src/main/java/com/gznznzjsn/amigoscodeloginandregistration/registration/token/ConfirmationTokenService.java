package com.gznznzjsn.amigoscodeloginandregistration.registration.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken getToken(String token) {
        return confirmationTokenRepository.findByToken(token).orElseThrow(() -> new IllegalStateException("token not found"));
    }

    public void setConfirmedAt(String token, LocalDateTime confirmedAt) {
        ConfirmationToken confirmationToken = getToken(token);
        confirmationToken.setConfirmedAt(confirmedAt);
        confirmationTokenRepository.save(confirmationToken);
    }
}
