package dao

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException

import entity.Account
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

    private void copyProperties(firstObject, secondObject){
        firstObject.username = secondObject.username
        firstObject.password = secondObject.password
        firstObject.email = secondObject.email
        firstObject.roleId = secondObject.roleId
        firstObject.id = secondObject.id
        firstObject.fullname = secondObject.fullname
        firstObject.phoneNumber = secondObject.phoneNumber
        firstObject.lastLoggedIn = secondObject.lastLoggedIn
        firstObject.streetAddress = secondObject.streetAddress
        firstObject.city = secondObject.city
        firstObject.state = secondObject.state
        firstObject.zip = secondObject.zip
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
            copyProperties(account, it)
        }
        account
    }

    Account getAccountByUsername(String username) {
        stmt = "select * from Account where username=?"
        Account account = null
        DAOFactory.getConnection().eachRow(stmt, [username]){
            account =  new Account()
            copyProperties(account, it)
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
        role
    }

    boolean updateAccountPasswordById(int id, String password) {
        String encodedPassword = PasswordUtil.encryptString(password)
        stmt = "update Account set password=? where id=?"
        result = DAOFactory.getConnection().executeInsert(stmt, [encodedPassword, id])
        result.isEmpty()
    }

    Account updateAccountPrimaryInformationById(int id, String fullname, String email, String phoneNumber) {
        stmt = "update Account set fullname=?, email=?, phoneNumber=? where id=?"
        DAOFactory.getConnection().executeInsert(stmt, [fullname, email, phoneNumber, id])
        getAccountById(id)
    }

    Account updateAccountLocationInformationById(int id, String streetAddress, String city, String state, String zip) {
        stmt = "update Account set streetAddress=?, city=?, state=?, zip=? where id=?"
        DAOFactory.getConnection().executeInsert stmt, [streetAddress, city, state, zip, id]
        getAccountById(id)
    }

    Account updateAccountLastLoggedInById(int id) {
        stmt = "update Account set lastLoggedIn=? where id=?"
        result = DAOFactory.getConnection().executeInsert stmt, [new Date(), id]
        getAccountById(id)
    }

    List<Account> getAllAccounts(){
        List<Account> Accounts = []
        Account account = null
        stmt = "select * from Account"
        DAOFactory.getConnection().eachRow(stmt){
            account = new Account()
            copyProperties(account, it)
            Accounts.add(account)
        }
        return Accounts
    }

    Account updateTokenById(int id, AccountDAO.TokenType tokenType, String token){
//        stmt = "update account"
    }

    boolean updateReceivePromotionalEmailsById(int id, boolean enabled){

    }

    boolean updateUserConfirmedById(int id, boolean enabled){

    }
}
