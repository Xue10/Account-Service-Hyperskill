package account.business.service;

import account.business.data.*;
import account.business.response.DeleteSuccess;
import account.business.response.Status;
import account.repository.RoleGroupRepository;
import account.repository.SecurityEventRepository;
import account.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
//@Secured({"ROLE_ADMINISTRATOR"})
@Transactional
public class AdminService {

    Logger log = LoggerFactory.getLogger(AdminService.class);

    private final UserRepository users;
    private final RoleGroupRepository groups;
    private final SecurityEventRepository events;

    @Autowired
    public AdminService(UserRepository users, RoleGroupRepository groups, SecurityEventRepository events) {
        this.users = users;
        this.groups = groups;
        this.events = events;
    }

    public UserRoles changeRoles(RoleOperation roleOperation) {
        User user = findUser(roleOperation.getUser());
        String role = roleOperation.getRole().toUpperCase();
        if (!groups.existsByName("ROLE_" + role)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!");
        }
        Set<String> roles = user.getRole();
        String operation = roleOperation.getOperation();
        if ("GRANT".equals(operation)) {
            if (roles.contains("ADMINISTRATOR") || "ADMINISTRATOR".equals(role)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user cannot combine administrative and business roles!");
            }
            roles.add(role);
            events.save(new SecurityEvent("GRANT_ROLE", Util.getEmail(), "Grant role " + role + " to " + user.getEmail(), "/api/admin/user/role"));
        } else if ("REMOVE".equals(operation)) {
            if (!roles.contains(role)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user does not have a role!");
            }
            if ("ADMINISTRATOR".equals(role)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't remove ADMINISTRATOR role!");
            }
            if (roles.size() < 2) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user must have at least one role!");
            }
            roles.remove(role);
            events.save(new SecurityEvent("REMOVE_ROLE", Util.getEmail(), "Remove role " + role + " from " + user.getEmail(), "/api/admin/user/role"));
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
        events.save(new SecurityEvent("DELETE_USER", Util.getEmail(), email, "/api/admin/user"));
        return new DeleteSuccess(email);
    }
    public List<UserRoles> getAll() {
        List<UserRoles> list = new ArrayList<>();
        for (User user : users.findAll()) {
            list.add(new UserRoles(user));
        }
        return list;
    }

    public Status lockUnlock(LockUnlock msg) {
        String email = msg.getUser().toLowerCase();
        User user = findUser(email);
        String operation = msg.getOperation().toUpperCase();
        if ("LOCK".equals(operation)) {
            if (user.getRole().contains("ADMINISTRATOR")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't lock the ADMINISTRATOR!");
            }
            user.setNonLocked(false);
            users.save(user);
            events.save(new SecurityEvent("LOCK_USER", Util.getEmail(), "Lock user " + email, "/api/admin/user/access"));
            return new Status("User " + user.getEmail() + " locked!");
        } else if ("UNLOCK".equals(operation)) {
            user.setNonLocked(true);
            users.save(user);
            events.save(new SecurityEvent("UNLOCK_USER", Util.getEmail(), "Unlock user " + email, "/api/admin/user/access"));
            return new Status("User " + user.getEmail() + " unlocked!");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


    User findUser(String email) {
        log.info(email);
        Optional<User> found = users.findByEmailIgnoreCase(email);
        log.info(email);
        if (found.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
        return found.get();
    }
}
