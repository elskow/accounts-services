package learn.microservices.accounts.controller;

import learn.microservices.accounts.constants.AccountConstants;
import learn.microservices.accounts.dto.CustomerDTO;
import learn.microservices.accounts.dto.ResponseDTO;
import learn.microservices.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class AccountController {
    private IAccountService iAccountService;

    @PostMapping("/accounts")
    public ResponseEntity<ResponseDTO> createAccount(@RequestBody CustomerDTO customerDTO){
        iAccountService.createAccount(customerDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountConstants.MESSAGE_201, AccountConstants.STATUS_201));

    }

    @GetMapping("/accounts")
    public ResponseEntity<CustomerDTO> getAccountbyEmail(@RequestParam String email) {
        CustomerDTO customerDTO = iAccountService.getAccountByEmail(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDTO);
    }

    @PutMapping("/accounts")
    public ResponseEntity<ResponseDTO> updateAccount(@RequestBody CustomerDTO customerDTO) {
        boolean isUpdated = iAccountService.updateAccount(customerDTO);

        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .body(new ResponseDTO(AccountConstants.STATUS_304, AccountConstants.MESSAGE_304));
        }
    }
}