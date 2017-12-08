package dao

import entity.Account

interface AccountDAO {
    Account getAccountById(int id)
    Account getAccountByUsername(String username)
    Account addAccount(String username, String password, String email, int roleId)
    boolean removeAccountById(int id)
    boolean removeAccountByUsername(String username)
}
