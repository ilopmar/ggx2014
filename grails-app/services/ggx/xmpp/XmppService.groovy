package ggx.xmpp

import org.springframework.integration.support.MessageBuilder
import org.springframework.integration.xmpp.XmppHeaders
import org.springframework.messaging.Message

class XmppService {
    static transactional = false

    def grailsApplication
    def sendXmppMessagesChannel

    void sendMessage(String msg) {
        log.debug "Sending response by XMPP: ${msg}"

        Message<String> message = MessageBuilder.withPayload(msg)
            .setHeader(XmppHeaders.TO, grailsApplication.config.ggx.xmpp.friend)
            .build()

        sendXmppMessagesChannel.send(message)
    }

    void chatMessageReceived(String msg) {
        log.debug "<< Received: ${msg}"
    }

    XmppCommand parseCommand(String command) {
        def cmds = command.tokenize(' ')

        if (cmds[0] == "command") {
            if (cmds.size() <= 2) {
                return new HelpCommand()
            }

            def params = cmds[2..-1]
            if (cmds[1] == "weather") {
                return new WeatherCommand(params.join(" "))
            } else if (cmds[1] == "stock") {
                return new StockCommand(params)
            }
        }

        return new HelpCommand()
    }

    String executeCommand(XmppCommand command) {
        return command.execute()
    }
}