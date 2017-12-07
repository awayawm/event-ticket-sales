package util

class ConfigUtil {
    // TODO slurp from root directory if config is not present in project
    static String path = 'src/main/resources/event-ticket-sales.config'

    static getConfig() {
        return new ConfigSlurper().parse(new File(path).toURI().toURL())
    }
}
