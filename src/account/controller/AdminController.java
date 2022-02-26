package account.controller;

import account.business.data.LockUnlock;
import account.business.response.Status;
import account.business.service.AdminService;
import account.business.response.DeleteSuccess;
import account.business.data.RoleOperation;
import account.business.data.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @PutMapping("/role")
    public UserRoles set(@RequestBody RoleOperation roleOperation) {
        return adminService.changeRoles(roleOperation);
    }

    @PutMapping("/access")
    public Status lockUnlock(@RequestBody LockUnlock msg) {
        return adminService.lockUnlock(msg);
    }
    
    @DeleteMapping("/{email}")
    public DeleteSuccess delete(@PathVariable String email) {
        return adminService.delete(email);
    }

    @GetMapping
    public List<UserRoles> get() {
        return adminService.getAll();
    }
}
