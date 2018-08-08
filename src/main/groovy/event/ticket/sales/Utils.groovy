package event.ticket.sales

class Utils {
    ConfigService configService = new ConfigService()
    public def addConfigToModel(def model){
        model.title = configService.getConfig().admin.title
        model.dateFormat = configService.getConfig().admin.dateFormat
        model
    }
}
