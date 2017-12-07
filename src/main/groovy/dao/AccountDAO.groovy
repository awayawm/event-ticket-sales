package dao

import entity.Account

interface AccountDAO {
    Account findById(int id)
    Account findByUsername(String username)
}
