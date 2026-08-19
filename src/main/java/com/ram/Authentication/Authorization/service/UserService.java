package com.ram.Authentication.Authorization.service;

import com.ram.Authentication.Authorization.entity.User;
import com.ram.Authentication.Authorization.entity.VerificationToken;
import com.ram.Authentication.Authorization.exceptions.UserAlreadyExistException;
import com.ram.Authentication.Authorization.exceptions.UserNotVerifiedException;
import com.ram.Authentication.Authorization.repository.UserRepository;
import com.ram.Authentication.Authorization.repository.VerificationRepository;
import com.ram.Authentication.Authorization.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class  UserService implements UserDetailsService {
    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private VerificationRepository _verificationRespository;

    @Autowired
    private BCryptPasswordEncoder _passwordEncoder;

    public User registerUser(User user) throws UserAlreadyExistException {
        Optional<User> optionalUser = _userRepository.findByUserEmail(user.getUserEmail());
        if(optionalUser.isPresent()){
            throw new UserAlreadyExistException("user with this email "+ user.getUserEmail()+" already exist");
        }

        // we can't directly save password as plain text to DB, need to hashed it first
        String hashedPassword = _passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(hashedPassword);

        return _userRepository.save(user);
    }

    @Override // Login get connected to Registered User.....login details came here and then go to filters
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = _userRepository.findByUserName(username);

        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("user with this username "+username+" not exist" );
        }
        User existedUser = optionalUser.get();

        return org.springframework.security.core.userdetails.User.builder()
                .username(existedUser.getUserName())
                .password(existedUser.getUserPassword())
                .roles(existedUser.getUserRole())
                .disabled(!existedUser.getIsEnabled())
                .build();
    }

    public void saveVerificationToken(User persistedUser, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(persistedUser);
        verificationToken.setExpireAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));

        _verificationRespository.save(verificationToken);
    }

    public String verifyToken(String token) {
        Optional<VerificationToken> optionalVerificationToken = _verificationRespository.findByToken(token);

        if(optionalVerificationToken.isEmpty()){
            return "invalid token, please try again";
        }
        VerificationToken verificationToken = optionalVerificationToken.get();
        if(verificationToken.getExpireAt().before(new Date())){
            _userRepository.delete(verificationToken.getUser());
            _verificationRespository.delete(verificationToken);
            return "token expired, please register agin";
        }
        User persistedUser = verificationToken.getUser();
        persistedUser.setIsEnabled(true);
        _verificationRespository.delete(verificationToken);
        _userRepository.save(persistedUser);

        return "verification successful";
    }

    public String signInUser(String username, String password)throws UserNotVerifiedException {
        Optional<User> optionalUser = _userRepository.findByUserName(username);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("user with this username "+ username +" not exist");
        }
        User existedUser = optionalUser.get();

        if(existedUser.getIsEnabled().equals(false)){
            throw new UserNotVerifiedException("username "+ username + " not verified, please verify your account");
        }

        Boolean isPass = _passwordEncoder.matches(password, existedUser.getUserPassword());
        if(isPass.equals(false)){
            return "invalid password, provide correct credentials";
        }

        return JwtUtils.generateJwtToken(existedUser);
    }
}
