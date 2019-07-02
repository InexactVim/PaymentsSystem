package me.inexactvim.paymentssystem.controller.admin;

import me.inexactvim.paymentssystem.controller.AbstractController;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.account.AccountNotFoundException;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.util.NumberUtil;
import me.inexactvim.paymentssystem.util.info.BlockedAccountInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@WebServlet("/admin/unblock_account")
public class UnblockAccountController extends AbstractController {
    
    private static Logger logger = LoggerFactory.getLogger(UnblockAccountController.class);

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String accountNumberString = request.getParameter("account_number");

        try {
            long accountNumber = Long.parseLong(accountNumberString);
            Account account = accountService.getAccount(accountNumber);
            accountService.unblockAccount(account);
            Collection<BlockedAccountInfo> blockedAccounts = getSessionAttribute(session, "blockedAccounts");
            blockedAccounts.removeIf(blockedAccountInfo -> blockedAccountInfo.getNumber().equals(accountNumberString));
            if (blockedAccounts.isEmpty()) {
                session.removeAttribute("blockedAccounts");
                alertWarning("There are no cards to unblock", request, response);
            } else {
                session.setAttribute("blockedAccounts", blockedAccounts);
                alertSuccess("Account successfully unblocked", request, response);
                logger.info("Successfully unblocked account " + NumberUtil.accountNumberFormat(accountNumber));
            }
        } catch (DAOException e) {
            logger.error("An error occurred with database when unblocking account (" + accountNumberString + ")", e);
            alertError("An error occurred while loading blocked accounts. Please, try again later", request, response);
        } catch (NumberFormatException e) {
            logger.error("An error occurred with recognizing account number (" + accountNumberString + ")", e);
            alertError("Unable to recognize account number", request, response);
        } catch (AccountNotFoundException e) {
            logger.error("An error occurred with finding account (number: " + accountNumberString + ")", e);
            alertError("Account not found", request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        try {
            Collection<BlockedAccountInfo> blockedAccounts = accountService.getBlockedAccounts();
            if (blockedAccounts.isEmpty()) {
                session.removeAttribute("blockedAccounts");
                alertWarning("There are no cards to unblock", request, response);
            } else {
                session.setAttribute("blockedAccounts", blockedAccounts);
                super.doGet(request, response);
            }
        } catch (DAOException e) {
            logger.error("An error occurred with database while loading blocked accounts", e);
            alertError("An error occurred while loading blocked accounts. Please, try again later", request, response);
        }
    }
}
