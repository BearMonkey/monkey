package org.monkey.mqtx.account.controller;

import lombok.extern.slf4j.Slf4j;
import org.monkey.common.dto.Result;
import org.monkey.mqtx.account.exception.AccountException;
import org.monkey.mqtx.account.pojo.Account;
import org.monkey.mqtx.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mqtx/account")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/account")
    public Result addNewAccount(@RequestBody Account account) {
        log.info("收到请求: 添加账号, accountNo={}, accountType={}", account.getAccountNo(), account.getAccountType());
        try {
            accountService.addAccount(account);
            log.info("添加账号成功, accountNo={}", account.getAccountNo());
            return Result.ok();
        } catch (AccountException e) {
            log.warn("添加账号失败, accountNo={}", account.getAccountNo());
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/account")
    public Result modifyAccount(@RequestBody Account account) {
        log.info("收到请求: 修改账号, accountNo={}, 修改人={}", account.getAccountNo(), account.getUpdateUser());
        try {
            accountService.updateAccount(account);
        } catch (AccountException e) {
            log.warn("修改账号失败, accountNo={}, errorMsg={}", account.getAccountNo(), e.getMessage());
            return Result.error(e.getMessage());
        }
        return Result.ok();
    }

    @GetMapping("/accounts")
    public Result queryAll() {
        return Result.ok(accountService.queryAllAccount());
    }

    @GetMapping("/account/{id}")
    public Result queryById(@PathVariable("id") Integer id) {
        return Result.ok(accountService.queryById(id));
    }

    @GetMapping("/accountno/{accountNo}")
    public Result queryById(@PathVariable("accountNo") String accountNo) {
        Account account = accountService.queryByAccountNo(accountNo);
        if (null == account) {
            return new Result(Result.ERROR_CODE, "账号不存在");
        }
        return Result.ok(account);
    }

}
