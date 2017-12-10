package entity

import groovy.transform.Canonical

@Canonical
class Config {
    int id
    String fightLeagueName
    String googleCaptchaV2Key
    String braintreeKey
}
