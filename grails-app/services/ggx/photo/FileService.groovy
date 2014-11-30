package ggx.photo

import org.apache.commons.lang.RandomStringUtils
import org.codehaus.groovy.grails.commons.GrailsApplication

class FileService {

    GrailsApplication grailsApplication

    Photo preprocessFile(File file) {
        String outputPath = grailsApplication.config.ggx.photos.webpath

        String filename = RandomStringUtils.randomAlphanumeric(10)
        def outputFile = "${outputPath}/${new Date().time}_${filename}.png"

        return new Photo(input: file.absolutePath, output: outputFile)
    }

    void deleteFile(Photo photo) {
        log.debug "Deleting file: ${photo.input}"
        new File(photo.input).delete()
    }
}