package dao

import entity.Account

interface AccountDAO {
    Account findAccountById(int id)
    Account findAccountByUsername(String username)
    Account addAccount(String username, String password, String email, int roleId)
    boolean removeAccountById(int id)
    boolean removeAccountByUsername(String username)
}
