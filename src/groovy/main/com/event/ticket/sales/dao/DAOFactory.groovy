package com.event.ticket.sales.dao

class DAOFactory {
    static void createConnection() {}

    AccountDAO getAccountDAO() {
        return new AccountDAOImpl();
    }
}
