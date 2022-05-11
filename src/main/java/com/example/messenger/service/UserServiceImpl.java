package com.example.messenger.service;

import com.example.messenger.model.Conversation;
import com.example.messenger.model.User;
import com.example.messenger.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(String phone, String email, String password, String nickname, String firstName,
                            String lastName, Date creatAt) {
        try {
            userRepository.registerUser(phone, email, password, nickname, firstName, lastName, creatAt);
        } catch (DataIntegrityViolationException exception) {
            return false;
        }
        return true;
    }

    public boolean verify(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public boolean verifyUser(String nickname, String password) {
        Optional<User> user = userRepository.verifyUser(nickname, password);
        return user.isPresent();
    }

    public User findByNickName(String nickname) {
        Optional<User> user = userRepository.findByNickName(nickname);
        if (user.isEmpty()) {
            //throw new Exception();
        }
        return user.get();
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> pullContacts(String nickname) {
        User user = userRepository.findByNickName(nickname).orElse(null);
        if (user == null) {
            //throw new Exception();
        }
        return userRepository.pullContacts(user.getId());
    }

    public List<User> findAllParticipants(Long conversationId) {
        return userRepository.findAllParticipants(conversationId);
    }
}
