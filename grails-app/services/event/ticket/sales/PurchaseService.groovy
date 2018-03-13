package event.ticket.sales

import grails.gorm.transactions.Transactional

@Transactional
class PurchaseService {
    def getEvents(){
        [events:Event.findAll()]
    }
}
