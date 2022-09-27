package spring3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring3.config.SpringConfig;
import spring3.domain.Account;
import spring3.service.AccountService;

public class App {
    public static void main(String[] args) {
        ApplicationContext app = new AnnotationConfigApplicationContext(SpringConfig.class);

        AccountService accountService = app.getBean(AccountService.class);
        Account account = accountService.findById(1);
        System.out.println(account);
    }
}
