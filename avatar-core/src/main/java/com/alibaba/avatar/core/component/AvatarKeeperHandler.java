package com.alibaba.avatar.core.component;

import java.util.HashMap;
import java.util.Map;
 
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.avatar.core.entity.ListData;
import com.alibaba.avatar.core.entity.MetaData;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter; 
import io.netty.handler.codec.http.HttpRequest;

@Sharable
@Component 
public class AvatarKeeperHandler extends ChannelInboundHandlerAdapter {
 
	@Autowired
	private ConfigCore configCore;
	
//    private static final AsciiString CONTENT_TYPE = new AsciiString("Content-Type");
//    private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");
//    private static final AsciiString CONNECTION = new AsciiString("Connection");
//    private static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;

            System.out.println("--------fuck---------" + msg);
             
            String uri = req.getUri();
            if(uri.startsWith("?")){
            	uri = uri.substring(1, uri.length());
            }
            
            // HTTP参数分析，组装
            String[] paramArr = uri.split("&");
            Map<String, String> paramMap = new HashMap<>();
            for(String tmp : paramArr){
            	String[] tmpArr = tmp.split("=");
            	
            	paramMap.put(tmpArr[0], tmpArr[1]);
            }
            
            
            // 参数不能为空
            String group = paramMap.get("group");
            String dataId = paramMap.get("dataId");
            String subDataId = paramMap.get("subDataId");
            String content = paramMap.get("content");
            String type = paramMap.get("type");
             
            if(StringUtils.isBlank(group)){
            	ctx.writeAndFlush("failure");
            	return;
            }
            
            if(StringUtils.isBlank(dataId)){
            	ctx.writeAndFlush("failure");
            	return;
            }
            
            if(StringUtils.isBlank(subDataId)){
            	ctx.writeAndFlush("failure");
            	return;
            }
            
            if(StringUtils.isBlank(content)){
            	ctx.writeAndFlush("failure");
            	return;
            }
            
            if(StringUtils.isBlank(type)){
            	ctx.writeAndFlush("failure");
            	return;
            }
             
            
            MetaData meta = new MetaData();
            meta.setDataId(dataId);
            meta.setGroup(group);
            
            ListData data = new ListData();
            data.setContent(content);
            data.setSubDataId(subDataId);
            
            if(type.equals("subscriber")){
            	configCore.addRegister(meta, data);
            }else{
            	configCore.addConsumer(meta, data);
            }
            
            System.out.println(" ---> dataId=" + dataId);
            System.out.println(" ---> group=" + group);
            System.out.println(" ---> content=" + content);
            System.out.println(" ---> subDataId=" + subDataId);
            
//            if (Httpmessage.is100ContinueExpected(req)) {
//                ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
//            }
//            boolean keepAlive = HttpUtil.isKeepAlive(req);
//            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(CONTENT));
//            response.headers().set(CONTENT_TYPE, "text/plain");
//            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());

//            if (!keepAlive) {
//                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
//            } else {
//                response.headers().set(CONNECTION, KEEP_ALIVE);
//                ctx.write(response);
//            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}