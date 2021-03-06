package okhotnik.gbcloud.server.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.SneakyThrows;
import okhotnik.gbcloud.common.transport.INetworkEndpoint;
import okhotnik.gbcloud.common.transport.Netty;
import okhotnik.gbcloud.server.config.ServerConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@Netty
@Default
@ApplicationScoped
public class NettyServer implements INetworkEndpoint
{
    private static final int MAXIMUM_OBJECT_SIZE = 1024 * 1024 * 10; //10M

    private ChannelFuture channelFuture;

    @Inject
    private ServerConfig config;

    @Override
    @SneakyThrows
    public void start()
    {
        final EventLoopGroup bossGroup = new NioEventLoopGroup();
        final EventLoopGroup workerGroup = new NioEventLoopGroup();
        try
        {
            final ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception
                        {
                            socketChannel.pipeline().addLast(new ObjectEncoder(),
                                    new ObjectDecoder(MAXIMUM_OBJECT_SIZE, ClassResolvers.cacheDisabled(null)),
                                    CDI.current().select(NettyServerHandler.class).get());
                        }
                    }).childOption(ChannelOption.SO_KEEPALIVE, true);
            channelFuture = serverBootstrap.bind(config.getServerPort()).sync();
            channelFuture.channel().closeFuture().sync();
        }
        finally
        {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop()
    {
        channelFuture.channel().close();
    }
}
