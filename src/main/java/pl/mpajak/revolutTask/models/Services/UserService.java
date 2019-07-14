package pl.mpajak.revolutTask.models.Services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mpajak.revolutTask.models.entitis.UserEntity;
import pl.mpajak.revolutTask.models.repositoris.UserRepository;

import java.util.Optional;

@Service
@Data
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransferSession userSession;

    public Iterable<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(int userId) {
        return userRepository.findUserById(userId);
    }

    @Transactional
    public boolean deleteUserById(int userId){

        Optional<UserEntity> optionalUserEntity = getUserById(userId);
        if (optionalUserEntity.isPresent()) {
            userRepository.softDeleteUserById(userId);
            return getUserById(userId).get().isDelete();
        }

        return false;
    }
}
