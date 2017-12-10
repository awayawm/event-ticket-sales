//TODO when application starts create Admin role and adminUser specified by config if they don't already exist

package dao

import groovy.sql.Sql
import util.ConfigUtil
import util.PasswordUtil

class DAOFactory {

    static private def conn = null

    static def getConnection(){
        def config = ConfigUtil.getFileConfig()
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
        getRoleDAO().addRole("TestRoleNumber100",
                            "Administrators can configure the application.")
        getRoleDAO().addRole("TestRoleNumber101",
                            "Members are eligible for discounts and promotional outreach.")

        getAccountDAO().addAccount("TestUserNumber100",
                PasswordUtil.encryptString("event"),
                "event-email@address.com",
                roleDAO.getRoleByName("TestRoleNumber100").id)

        getAccountDAO().addAccount("TestUserNumber101",
                PasswordUtil.encryptString("ticket"),
                "ticket-email@address.com",
                roleDAO.getRoleByName("TestRoleNumber101").id)

    }

    void deleteTestData(){
        getAccountDAO().removeAccountByUsername("TestUserNumber100")
        getAccountDAO().removeAccountByUsername("TestUserNumber101")
        getRoleDAO().removeRoleByName("TestRoleNumber100")
        getRoleDAO().removeRoleByName("TestRoleNumber101")
    }

    AccountDAO getAccountDAO(){
        return new AccountDAOImpl()
    }

    RoleDAO getRoleDAO(){
        return new RoleDAOImpl()
    }
}
