package event.ticket.sales

import grails.gorm.transactions.Transactional

@Transactional
class TicketService {

    def subtractItemMap(def itemMap) {
        itemMap.each(){
            Ticket.findById(it.ticketObject.id).quantity -= Integer.valueOf(it.quantity)
        }
    }
    def addRawRecordTicketQuantiesBack(def rawRecord){

    }
    def createRawRecord(def itemMap){
        StringBuilder sb = new StringBuilder()
        itemMap.each{
            sb.append(it.ticketObject.name.replaceAll(',', '') + ",")
            sb.append(it.ticketObject.description.replaceAll(',', '') + ",")
            sb.append(it.ticketObject.price.toString().replaceAll(',', '') + ",")
            sb.append(it.quantity.toString().replaceAll(',', '') + ",")
        }
        sb.setLength(sb.length() - 1)
        sb.toString()
    }
    def rawRecordToRawRecordItemMap(def rawRecord){
        int itemLength = 4
        def splitRawRecord = rawRecord.split(',')
        def rawRecordItemMap = []
        def rawRecordItem = [:]
        for(int i = 0; i < splitRawRecord.size(); i+=itemLength){
            if(splitRawRecord[i+3] != '0') {
                rawRecordItem.name = splitRawRecord[i]
                rawRecordItem.description = splitRawRecord[i + 1]
                rawRecordItem.price = splitRawRecord[i + 2]
                rawRecordItem.quantity = splitRawRecord[i + 3]
                rawRecordItemMap << rawRecordItem
                rawRecordItem = [:]
            }
        }
        rawRecordItemMap
    }
}
