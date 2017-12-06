package com.event.ticket.sales.dao

import com.event.ticket.sales.entity.Account

interface AccountDAO {
    Account findById(int id)
}
