package com.todo.todo.service;

import com.todo.todo.co.UserCo;
import com.todo.todo.dao.repo.UserRepository;
import com.todo.todo.domain.User;
import com.todo.todo.enums.EntityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    HttpSession httpSession;

    @Transactional
    public void addUser(UserCo userCo) {
        Long emailCount = userRepository.countUserByEmail(userCo.getEmail());
        if (emailCount > 0) {
            throw new RuntimeException();
        }
        User user = new User();
        user.setEmail(userCo.getEmail());
        user.setAddress(userCo.getAddress());
        user.setPassword(encryptPassword(userCo.getPassword()));
        userRepository.save(user);
    }

    public void updateUser(Long id, UserCo userCo) {
        User user = userRepository.findById(id).get();
        if (Objects.nonNull(user)) {
            if (Objects.nonNull(userCo.getEmail()) && !userCo.getEmail().equals("")) {
                user.setEmail(userCo.getEmail());
            }
            if (Objects.nonNull(userCo.getAddress()) && !userCo.getAddress().equals("")) {
                user.setAddress(userCo.getAddress());
            }
            if (Objects.nonNull(userCo.getPassword()) && !userCo.getPassword().equals("")) {
                user.setPassword(encryptPassword(userCo.getPassword()));
            }
            userRepository.save(user);
        }
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
    }

    //Validation of login user
    public boolean validateUser(UserCo userCo) {
        User user = userRepository.findByEmailAndEntityStatus(userCo.getEmail(), EntityStatus.ENABLE.name());
        if (Objects.nonNull(user)) {
            if (user.getPassword().equals(encryptPassword(userCo.getPassword()))) {
                httpSession.setAttribute("user", user.getId());
                httpSession.setMaxInactiveInterval(300);
                return true;
            }
        }
        return false;
    }

    //Encryption of password
    private String encryptPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
