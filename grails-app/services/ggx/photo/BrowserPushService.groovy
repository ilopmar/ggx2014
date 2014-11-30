package ggx.photo

import groovy.json.JsonBuilder

class BrowserPushService {
    static transactional = false

    def brokerMessagingTemplate

    Photo pushToBrowser(Photo photo) {
        log.debug "Pushing file to browser: ${photo.output}"

        def fullPathName = photo.output
        def fileName = fullPathName.substring(fullPathName.lastIndexOf("/") + 1, fullPathName.length())

        def builder = new JsonBuilder()
        builder {
            url(fileName)
        }

        brokerMessagingTemplate.convertAndSend "/topic/photos", builder

        return photo
   }
}