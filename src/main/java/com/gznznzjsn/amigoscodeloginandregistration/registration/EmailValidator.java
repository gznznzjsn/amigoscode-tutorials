package com.gznznzjsn.amigoscodeloginandregistration.registration;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        //test via regex
        return true;
    }
}
