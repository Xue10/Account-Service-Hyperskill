package account.business.service;

import account.business.data.RoleOperation;
import account.business.data.User;
import account.business.data.UserRoles;
import account.business.response.DeleteSuccess;
import account.repository.RoleGroupRepository;
import account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AdminService {

    private final UserRepository users;
    private final RoleGroupRepository groups;

    @Autowired
    public AdminService(UserRepository users, RoleGroupRepository groups) {
        this.users = users;
        this.groups = groups;
    }

    public UserRoles changeRoles(RoleOperation roleOperation) {
        User user = findUser(roleOperation.getUser());
        String role = roleOperation.getRole().toUpperCase();
        if (!groups.existsByName("RULE_" + role)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!");
        }
        Set<String> roles = user.getRole();
        String operation = roleOperation.getOperation();
        if ("GRANT".equals(operation)) {
            if (roles.contains("ADMINISTRATOR")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user cannot combine administrative and business roles!");
            }
            roles.add(role);
        } else if ("REMOVE".equals(operation)) {
            if (!roles.contains(role)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user does not have a role!");
            }
            if (roles.size() < 2) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user must have at least one role!");
            }
            if ("ADMINISTRATOR".equals(role)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't remove ADMINISTRATOR role!");
            }
            roles.remove(role);
        }

        return new UserRoles(user);
    }

    public DeleteSuccess delete(String email) {
        User user = findUser(email);
        Set<String> roles = user.getRole();
        if (roles.contains("ADMINISTRATOR")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't remove ADMINISTRATOR role!");
        }
        users.delete(user);
        return new DeleteSuccess(email);
    }

    public List<UserRoles> getAll() {
        List<UserRoles> list = new ArrayList<>();
        for (User user : users.findAll()) {
            list.add(new UserRoles(user));
        }
        return list;
    }


    User findUser(String email) {
        Optional<User> found = users.findByEmailIgnoreCase(email);
        if (found.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
        return found.get();
    }
}