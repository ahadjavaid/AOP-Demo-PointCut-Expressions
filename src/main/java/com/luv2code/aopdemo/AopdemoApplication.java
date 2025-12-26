package com.luv2code.aopdemo;

import com.luv2code.aopdemo.dao.AccountDAO;
import com.luv2code.aopdemo.dao.MembershipDAO;
import com.luv2code.aopdemo.service.TrafficFortuneService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AopdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AopdemoApplication.class, args);
	}


    @Bean
    public CommandLineRunner commandLineRunner(AccountDAO theAccountDAO, MembershipDAO theMembershipDAO,
                                               TrafficFortuneService theTrafficFortuneService) {
        return runner -> {
//            demoTheBeforeAdvice(theAccountDAO, theMembershipDAO);
//            demoTheAfterReturningAdvice(theAccountDAO);
//            demoTheAfterThrowingAdivce(theAccountDAO);
//            demoTheAfterAdvice(theAccountDAO);
            demoTheAroundAdvice(theTrafficFortuneService);
        };
    }

    private void demoTheAroundAdvice(TrafficFortuneService theTrafficFortuneService) {

        System.out.println("\nMain program: demoTheAroundAdive");

        System.out.println("Calling getFortune()");

        String data = theTrafficFortuneService.getFortune();

        System.out.println("\nMy fortune is: " + data);

        System.out.println("Finished");
    }

    private void demoTheAfterAdvice(AccountDAO theAccountDAO) {

        // call method to find the accounts
        List<Account> theAccounts = null;

        try {
            boolean tripWire = true;
            theAccounts = theAccountDAO.findAccounts(tripWire);
        } catch (Exception exception) {
            System.out.println("\n\nMain Program ... caught exception: " + exception);
        }

        // display the accounts
        System.out.println("\n\nMain Program: demoTheAfterThrowingAdvice");
        System.out.println("----");

        System.out.println(theAccounts);

        System.out.println("\n");

    }

    private void demoTheAfterThrowingAdivce(AccountDAO theAccountDAO) {

        // call method to find the accounts
        List<Account> theAccounts = null;

        try {
            boolean tripWire = true;
            theAccounts = theAccountDAO.findAccounts(tripWire);
        } catch (Exception exception) {
            System.out.println("\n\nMain Program ... caught exception: " + exception);
        }

        // display the accounts
        System.out.println("\n\nMain Program: demoTheAfterThrowingAdvice");
        System.out.println("----");

        System.out.println(theAccounts);

        System.out.println("\n");

    }

    private void demoTheAfterReturningAdvice(AccountDAO theAccountDAO) {

        // call method to find the accounts
        List<Account> theAccounts = theAccountDAO.findAccounts();

        // display the accounts
        System.out.println("\n\nMain Program: demoTheAfterReturningAdvice");
        System.out.println("----");

        System.out.println(theAccounts);

        System.out.println("\n");
    }

    private void demoTheBeforeAdvice(AccountDAO theAccountDAO, MembershipDAO theMembershipDAO) {

        // call the business method
        Account tempAccount = new Account("Ahad","Platinum");
        theAccountDAO.addAccount(tempAccount,true);
        theAccountDAO.doWork();

        // call the accountdao getter/setter methods

        theAccountDAO.setName("foobar");
        theAccountDAO.setServiceCode("silver");

        String name = theAccountDAO.getName();
        String code = theAccountDAO.getServiceCode();

        // call the membership business method
        theMembershipDAO.addSillyMember();
        theMembershipDAO.goToSleep();

    }
}
