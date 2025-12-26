package com.luv2code.aopdemo.aspect;

import com.luv2code.aopdemo.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Order(2 )
public class MyDemoLoggingAspect {


    // add new advice for @Around on the findAccounts method

    @Around("execution(* com.luv2code.aopdemo.service.*.getFortune(..))")
    public Object aroundGetFortune(ProceedingJoinPoint theProceedingJoinPoint) throws Throwable {

        // print out method we are advising on

        String method = theProceedingJoinPoint.getSignature().toShortString();
        System.out.println("\n====>>> Executing @Around on method: " + method);

        // get begin timestamp
        long begin = System.nanoTime();

        // now, let's execute the method
        Object result = null;

        try {
            result = theProceedingJoinPoint.proceed();
        } catch (Exception exception) {
            // log the exception
            System.out.println(exception.getMessage());

            // give user a custom message

            result = "Major accident! But no worries, your private AOP helicopter is on the way!";
        }

        // get end timestamp
        long end = System.nanoTime();

        // compute duration and display it
        long duration = end - begin;
        System.out.println("\n=====> Duration: " + duration + " seconds");

        return result;

    }

    // add new advice for @After on the findAccounts method

    @After("execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))")
    public void afterFinallyFindAccountsAdvice(JoinPoint theJoinPoint) {

        // print out which method we are advising on

        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n====>>> Executing @After (finally) on method: " + method);
    }

    // add new advice for @AfterThrowing on the findAccounts method

    @AfterThrowing(
            pointcut = "execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
            throwing = "theException")
    public void afterThrowingFindAccountsAdvice(JoinPoint theJoinPoint, Throwable theException) {

        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n====>>> Executing @AfterThrowing on method: " + method);

        // log the exception

        System.out.println("\n====>>> the exception is: " + theException);

    }

    // add new advice for @AfterReturning on the findAccounts method

    @AfterReturning(
            pointcut = "execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
            returning = "result")
    public void afterReturningFindAccountsAdvice(JoinPoint theJoinPoint, List<Account> result) {

        // print out which method we are advising on

        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n====>>> Executing @AfterReturning on method: " + method);

        // print out the results of the method call

        System.out.println("\n====>>> result is: " + result);

        // let's post-process the data ... let's modify it

        // convert the account names to upper-case

        convertAccountNamesToUpperCase(result);

        System.out.println("\n====>>> result is: " + result);

    }

    private void convertAccountNamesToUpperCase(List<Account> result) {

        for(var el : result) {
            String theUpperName = el.getName().toUpperCase();
            el.setName(theUpperName);
        }
    }

    @Before("com.luv2code.aopdemo.aspect.LuvAopExpressions.forDaoPackageNoGetterSetter()")
    public void beforeAddAccountAdive(JoinPoint theJoinPoint) {
        System.out.println("\n=====>>> Executing @Before adive on addAccount()");

        // display the method signature

        MethodSignature methodSignature = (MethodSignature) theJoinPoint.getSignature();

        System.out.println("Method: " + methodSignature);

        // display method arguments

        // get args
        Object[] args = theJoinPoint.getArgs();

        // loop through the args

        for(Object tempArg : args) {
            System.out.println("temp args:" + tempArg);

            if(tempArg instanceof Account ) {

                Account theAccount = (Account) tempArg;

                System.out.println("account name: " + theAccount.getName());
                System.out.println("account level: " + theAccount.getLevel());
            }
        }

    }

}
