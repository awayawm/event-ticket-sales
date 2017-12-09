package dao

import entity.Account
import entity.Role

interface AccountDAO {
    Account getAccountById(int id)
    Account getAccountByUsername(String username)
    Account addAccount(String username, String password, String email, int roleId)
    boolean removeAccountById(int id)
    boolean removeAccountByUsername(String username)
    Role getRoleForAccountId(int id)
    boolean updateAccountPasswordById(int id, String password)
    Account updateAccountPrimaryInformationById(int id, String fullname, String email, String phoneNumber)
    Account updateAccountLocationInformationById(int id, String streetAddress, String city, String state, String zip)
    boolean updateAccountLastLoggedInById(int id)

}
