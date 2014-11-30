package ggx.xmpp

class XmppController {

    def xmppService

    def chat(String msg) {
        msg = msg ?: "Hello GGX from Spring Integration XMPP!"

        xmppService.sendMessage(msg)

        render "Done!"
    }
}
