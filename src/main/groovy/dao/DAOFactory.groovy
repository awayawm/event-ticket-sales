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

    void createTestData(){
//        getRoleDAO().addRole("Admin", "Administrators can configure the application.")
//        getRoleDAO().addRole("Member", "Members are eligible for discounts and promotional outreach.")
    }

    AccountDAO getAccountDAO(){
        return new AccountDAOImpl()
    }

    RoleDAO getRoleDAO(){
        return new RoleDAOImpl()
    }
}
