package dao

import entity.Account
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
import entity.Role
import util.PasswordUtil

class AccountDAOImpl implements AccountDAO {

    String stmt
    List result

    Account addAccount(String username, String password, String email, int roleId) {
        stmt = "insert into Account (username, password, email, roleId) values (?, ?, ?, ?)"
        try {
            result = DAOFactory.getConnection().executeInsert stmt, [username, password, email, roleId]
            return new Account(id: result[0][0], username: username, password: password, email: email, roleId: roleId)
        } catch(MySQLIntegrityConstraintViolationException ex) {
            println ex.getStackTrace()
        }
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

    Role getRoleForAccountId(int id) {
        stmt = "select Role.id, Role.name, Role.description from Role inner join Account on Account.roleId = Role.id where Account.id=?"
        Role role = null
        DAOFactory.getConnection().eachRow(stmt, [id]) {
            role = new Role()
            role.id = it.id
            role.name = it.name
            role.description = it.description
        }
        return role
    }

    boolean updatePasswordById(int id, String password) {
        String encodedPassword = PasswordUtil.encryptString(password)
        stmt = "update Account set password=? where id=?"
        result = DAOFactory.getConnection().executeInsert(stmt, [encodedPassword, id])
        result.isEmpty()
    }

    boolean updatePrimaryInformationById(int id, String fullname, String email, String phoneNumber) {

    }

    boolean updateLocationInformationById(int id, String streetAddress, String city, String state, String zip) {

    }

}
