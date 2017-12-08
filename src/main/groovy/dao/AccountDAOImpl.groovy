package dao

import entity.Account

class AccountDAOImpl implements AccountDAO {

    String stmt
    List result

    Account addAccount(String username, String password, String email, int roleId) {
        stmt = "insert into Account (username, password, email, roleId) values (?, ?, ?, ?)"
        result = DAOFactory.getConnection().executeInsert stmt, [username, password, email, roleId]
        new Account(username: username, password: password, email: email, roleId: roleId)
    }

    boolean removeAccountById(int id) {
        stmt = "delete from Account where id = ?"
        result = DAOFactory.getConnection().executeInsert stmt, [id]
        result.isEmpty()
    }

    boolean removeAccountByUsername(String username) {
        stmt = "delete from Account where username = ?"
        result = DAOFactory.getConnection().executeInsert stmt, [username]
        result.isEmpty()
    }

    Account getAccountById(int id) {
        stmt = "select * from Account where id=?"
        Account account = null
        DAOFactory.getConnection().eachRow(stmt, [id]){
            account =  new Account()
            account.username = it.username
            account.password = it.password
            account.email = it.email
            account.roleId = it.roleId
            account.id = it.id
        }
        account
    }

    Account getAccountByUsername(String username) {
        stmt = "select * from Account where username=?"
        Account account = null
        DAOFactory.getConnection().eachRow(stmt, [username]){
            account =  new Account()
            account.username = it.username
            account.password = it.password
            account.email = it.email
            account.roleId = it.roleId
            account.id = it.id
        }
        account
    }

}
