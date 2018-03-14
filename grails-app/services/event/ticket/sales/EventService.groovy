package event.ticket.sales

import grails.gorm.transactions.Transactional

@Transactional
class EventService {
    def getEvents(){
        [events:Event.findAll()]
    }
}
