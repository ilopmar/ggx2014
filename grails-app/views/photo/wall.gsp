<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>

        <asset:javascript src="jquery" />
        <asset:javascript src="spring-websocket" />
        <asset:javascript src="handlebars-v2.0.0" />

        <script type="text/javascript">
            $(function() {
                var source = $("#pic-template").html();
                var template = Handlebars.compile(source);

                var socket = new SockJS("${createLink(uri: '/stomp')}");
                var client = Stomp.over(socket);

                var baseUrl = "${createLink(uri:'/', absolute:true)}"

                client.connect({}, function() {
                    client.subscribe("/topic/photos", function(message) {

                        photo = jQuery.parseJSON(message.body);
                        var context = {
                            url: baseUrl + "photos/" + photo.content.url
                        }

                        var html = template(context);
                        $('#timeline').prepend(html);

                        $("#timeline .photo:first-child img").on("load", function() {
                            $(this).parent().css({
                                display: 'none',
                                visibility: 'visible',
                                height: 'auto'
                            });

                            $(this).parent().slideDown();
                        });
                    });
                });
            });
        </script>
    </head>
    <body>

        <%-- Handlebars timeline template --%>
        <script id="pic-template" type="text/x-handlebars-template">
            <div class="photo-cover">
                <div class="photo" style="visibility:hidden; height:0;">
                    <img src="{{url}}" />
                </div>
            </div>
        </script>

        <div id="timeline" class="center">
        </div>

    </body>
</html>