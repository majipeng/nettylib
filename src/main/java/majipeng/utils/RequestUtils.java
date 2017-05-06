package majipeng.utils;

import java.net.URISyntaxException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import majipeng.protocol.Protocol;

public class RequestUtils {

	public static void send(ChannelHandlerContext context, String url,String content,String contentType) {
        HttpRequest request= createBaseRequest(content,contentType);
		send(context, request);
	}

	public static void sendPing(ChannelHandlerContext context) throws URISyntaxException {
		send(context,Protocol.PATH_PING,null,"text/plain");
	}

	public static void send(ChannelHandlerContext context,  HttpRequest request) {
		send(context.channel(), request);
	}

	public static void send(Channel ch,  HttpRequest request) {
		ch.writeAndFlush(request);
	}

    private static HttpRequest createBaseRequest(String str,String contentType){
        ByteBuf content= Unpooled.copiedBuffer(str, CharsetUtil.UTF_8);
        HttpRequest request=new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,Protocol.PATH_PING,content);
        request.headers().set(HttpHeaderNames.CONTENT_TYPE,contentType+";charset=UTF-8");
        request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);
        return request;
    }
}
