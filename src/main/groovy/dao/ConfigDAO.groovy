package dao

import entity.Config

interface ConfigDAO {
    enum Keys {
        braintreeKey(),
        googleCaptchaV2Key()
    }

    Config getConfigById()
}
