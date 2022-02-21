package account.controller;

import account.business.data.Salary;
import account.business.data.SalaryOut;
import account.business.response.UpdateSuccess;
import account.business.response.UploadSuccess;
import account.business.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BusinessController {

    @Autowired
    PaymentService paymentService;

    @GetMapping(value = "/empl/payment")
    public List<SalaryOut> get(@AuthenticationPrincipal UserDetails details) {
        String email = details.getUsername();
        return paymentService.get(email);
    }

    @GetMapping(value = "/empl/payment", params = "period")
    public SalaryOut get(@RequestParam String period, @AuthenticationPrincipal UserDetails details) {
        String email = details.getUsername();
        try {
            return paymentService.get(email, period);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/acct/payments")
    public UploadSuccess upload(@RequestBody List<Salary> salaryList) {
        return paymentService.upload(salaryList);
    }

    @PutMapping("/acct/payments")
    public UpdateSuccess update(@RequestBody Salary salary) {
        return paymentService.update(salary);
    }
}
