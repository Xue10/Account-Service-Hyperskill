package account.controller;

import account.business.service.AdminService;
import account.business.response.DeleteSuccess;
import account.business.data.RoleOperation;
import account.business.data.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
@Secured("ROLE_ADMINISTRATOR")
public class AdminController {

    @Autowired
    AdminService adminService;
    
    @PutMapping("/role")
    public UserRoles set(@RequestBody RoleOperation roleOperation) {
        return adminService.changeRoles(roleOperation);
    }
    
    @DeleteMapping
    public DeleteSuccess delete(@PathVariable String email) {
        return adminService.delete(email);
    }
    
    @GetMapping
    public List<UserRoles> get() {
        return adminService.getAll();
    }
}
