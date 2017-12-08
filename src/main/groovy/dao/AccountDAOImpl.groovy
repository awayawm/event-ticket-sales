package dao

import entity.Account

class AccountDAOImpl implements AccountDAO {
    Account findAccountById(int id) { new Account() }
    Account findAccountByUsername(String username) { new Account() }
    String stmt
    List result

    Account addAccount(String username, String password, String email, int roleId) {
        stmt = "insert into Account (username, password, email, roleId) values (?, ?, ?, ?)"
        result = DAOFactory.getConnection().executeInsert stmt, [username, password, email, roleId]
        return new Account(username: username, password: password, email: email, roleId: roleId)
    }

    boolean removeAccountById(int id) {
        stmt = "delete from Account where id = ?"
        result = DAOFactory.getConnection().executeInsert stmt, [id]
        return result.isEmpty()
    }

    boolean removeAccountByUsername(String username) {
        stmt = "delete from Account where username = ?"
        result = DAOFactory.getConnection().executeInsert stmt, [username]
        return result.isEmpty()
    }

}
