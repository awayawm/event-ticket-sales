package dao

import groovy.sql.Sql
import util.ConfigUtil

class DAOFactory {

    static private def conn = null

    static def getConnection(){
        def config = ConfigUtil.getConfig()
        if(!conn) {
            conn = Sql.newInstance(config.db.url, config.db.user, config.db.password, config.db.driver)
        }
        return conn
    }

    static def closeConnection(){
        conn.close()
        conn = null
    }

    void createTestData(){
        getRoleDAO().addRole("TestAccountNumber100", "Administrators can configure the application.")
        getRoleDAO().addRole("TestAccountNumber101", "Members are eligible for discounts and promotional outreach.")
    }

    void deleteTestData(){
        getRoleDAO().removeRoleByName("TestAccountNumber100")
        getRoleDAO().removeRoleByName("TestAccountNumber101")
    }

    AccountDAO getAccountDAO(){
        return new AccountDAOImpl()
    }

    RoleDAO getRoleDAO(){
        return new RoleDAOImpl()
    }
}
