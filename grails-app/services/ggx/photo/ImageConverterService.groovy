package ggx.photo

import org.im4java.core.ConvertCmd
import org.im4java.core.IMOperation

class ImageConverterService {

    def rnd = new Random()

    Photo applyEffect(Photo photo) {
        log.debug "Applying effect to file: ${photo.input}..."

        def inputFile = photo.input
        def outputFile = photo.output

        def op = new IMOperation()
        op.addImage(inputFile)
        op.thumbnail(260, 260)
        op.set("caption", "Groovy & Grails eXchange 2014")
        op.gravity("center")
        op.pointsize(16)
        op.background("black")
        int polaroidRotation = rnd.nextInt(6)
        rnd.nextBoolean() ? op.polaroid(polaroidRotation) : op.polaroid(-polaroidRotation)
        op.addImage(outputFile)

        def command = new ConvertCmd()
        command.run(op)

        photo
    }
}