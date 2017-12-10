package dao

import entity.Account
import entity.Role

interface AccountDAO {
    enum TokenType{
        confirmationToken(),
        passwordResetToken()

    }
    Account getAccountById(int id)
    Account getAccountByUsername(String username)
    Account addAccount(String username, String password, String email, int roleId)
    boolean removeAccountById(int id)
    boolean removeAccountByUsername(String username)
    Role getRoleForAccountId(int id)
    boolean updateAccountPasswordById(int id, String password)
    Account updateAccountPrimaryInformationById(int id, String fullname, String email, String phoneNumber)
    Account updateAccountLocationInformationById(int id, String streetAddress, String city, String state, String zip)
    Account updateAccountLastLoggedInById(int id)
    List<Account> getAllAccounts()
    Account updateTokenById(int id, TokenType tokenType, String token)
    boolean updateReceivePromotionalEmailsById(int id, boolean enabled)
    boolean updateUserConfirmedById(int id, boolean enabled)
}
