package account.business;

import account.business.data.RoleGroup;
import account.repository.RoleGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private final RoleGroupRepository roleGroupRepository;

    @Autowired
    public DataLoader(RoleGroupRepository roleGroupRepository) {
        this.roleGroupRepository = roleGroupRepository;
        createRoles();
    }

    private void createRoles() {
        try {
            roleGroupRepository.save(new RoleGroup("ROLE_ADMINISTRATOR"));
            roleGroupRepository.save(new RoleGroup("ROLE_USER"));
            roleGroupRepository.save(new RoleGroup("ROLE_ACCOUNTANT"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
