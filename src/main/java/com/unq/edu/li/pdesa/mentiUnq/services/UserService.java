package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.models.LoginProvider;
import com.unq.edu.li.pdesa.mentiUnq.models.MentiUser;
import com.unq.edu.li.pdesa.mentiUnq.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void processOAuthPostLogin(String username) {
        MentiUser existUser = userRepository.getUserByUsername(username);

        if (existUser == null) {
            MentiUser newUser = new MentiUser();
            newUser.setUserName(username);
            newUser.setProvider(LoginProvider.GOOGLE);
            newUser.setEnabled(true);

            userRepository.save(newUser);
        }

    }
}
