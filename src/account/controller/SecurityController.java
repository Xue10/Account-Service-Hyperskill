package account.controller;

import account.business.data.SecurityEvent;
import account.repository.SecurityEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Secured({"ROLE_AUDITOR"})
public class SecurityController {

    @Autowired
    private SecurityEventRepository events;

    @GetMapping("api/security/events")
    public List<SecurityEvent> get() {
        List<SecurityEvent> list = new ArrayList<>();
        events.findAll().forEach(list::add);
        return list;
    }
}
