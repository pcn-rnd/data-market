package co.kr.pcninc.data.marketcore.service;

import co.kr.pcninc.data.marketcore.domain.User;
import co.kr.pcninc.data.marketcore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> getUser(String userId) { return userRepository.findById(userId); }

    public void saveUser(User user) { userRepository.save(user); }

    public List<User> getAll() {return userRepository.findAll();}

    public void deleteUser(String userId) {userRepository.deleteById(userId);}

    public List<String> getAllEmail() {return userRepository.getAllEmails();}

    public List<String> getAllNames() {return userRepository.getAllNames();}

    public List<String> getAllKeys() {return userRepository.getAllKeys();}

}
