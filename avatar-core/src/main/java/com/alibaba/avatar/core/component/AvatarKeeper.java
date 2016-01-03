package com.alibaba.avatar.core.component;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * AvatarKeeper是接受远程请求的类
 * 
 * @author beckham
 *
 */
@Component
public class AvatarKeeper {
	  
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private int port = 8099;
	
	@Autowired
	private AvatarKeeperHandler avatarKeeperHandler;
	
	public AvatarKeeper(){
		
		init();
		 
	}
	
	/**
	 *  spring初始化启动init
	 */
	public void init(){
		logger.info("avatar开始启动。");
    	  
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// Configure the server.
		        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		        EventLoopGroup workerGroup = new NioEventLoopGroup();
		        try {
		            ServerBootstrap b = new ServerBootstrap();
		            b.option(ChannelOption.SO_BACKLOG, 1024);
		            b.group(bossGroup, workerGroup)
		             .channel(NioServerSocketChannel.class)
		             .handler(new LoggingHandler(LogLevel.INFO))
		             .childHandler(new ChannelInitializer<SocketChannel>() {
		                 @Override
		                 public void initChannel(SocketChannel ch) {
		                     ChannelPipeline p = ch.pipeline();
		                     
		                     p.addLast(new HttpServerCodec());
		                     p.addLast(avatarKeeperHandler);
		                 }
		             });

		            Channel ch = b.bind(port).sync().channel();
		 
		            ch.closeFuture().sync();
		        } catch(Exception e){
		        	logger.error("server start occured an exception.", e);
		        } finally {
		            bossGroup.shutdownGracefully();
		            workerGroup.shutdownGracefully();
		        }
			}
		});
		
		t.start();
		
		logger.info("avatar启动完成。");
	} 
}
