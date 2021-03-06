package okhotnik.client.transport;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import okhotnik.client.conversations.ClientConversationManager;
import okhotnik.client.config.ClientConfig;
import okhotnik.client.events.EConnectionClosed;
import okhotnik.client.events.EMessageReceived;
import okhotnik.gbcloud.common.messages.IMessage;
import okhotnik.gbcloud.common.transport.INetworkEndpoint;
import okhotnik.gbcloud.common.transport.ITransportChannel;
import okhotnik.gbcloud.common.transport.Nio;
import okhotnik.gbcloud.common.transport.NioTransportChannel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

@Nio
@ApplicationScoped
public class NioClient implements INetworkEndpoint
{
    @Inject
    @Getter
    @Setter
    private ClientConversationManager conversationManager;

    @Nio
    @Inject
    @Getter
    private ITransportChannel transportChannel;

    @Inject
    private Event<EMessageReceived> messageReceivedBus;

    @Inject
    private Event<EConnectionClosed> connectionClosedBus;

    @Inject
    private ClientConfig config;

    private Selector selector;

    private SocketChannel socketChannel;

    private final Runnable worker = new Runnable()
    {
        @Override
        public void run()
        {
            try
            {
                ((NioTransportChannel) transportChannel).setSocketChannel(socketChannel);
                conversationManager.setTransportChannel(transportChannel);
                conversationManager.authenticate();

                while (transportChannel.isConnected())
                {
                    selector.select();
                    if (!selector.isOpen()) break;
                    for (final SelectionKey selectionKey : selector.selectedKeys())
                    {
                        if (selectionKey.isReadable())
                        {
                            try
                            {
                                final IMessage message = transportChannel.readMessage();
                                messageReceivedBus.fireAsync(new EMessageReceived(message));
                            }
                            catch (final ITransportChannel.CorruptedDataReceived e)
                            { //ignore

                            }
                            catch (Exception e)
                            {
                                transportChannel.closeSilently();
                                break;
                            }
                        }
                    }
                    selector.selectedKeys().clear();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                try
                {
                    if (selector.isOpen()) selector.close();
                }
                catch (Exception ex) {}
                transportChannel.closeSilently();
            }
            finally
            {
                connectionClosedBus.fireAsync(new EConnectionClosed());
            }
        }
    };

    private Thread workerThread;

    @Override
    @SneakyThrows
    public void start()
    {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(config.getServerAddress(), config.getServerPort()));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        workerThread = new Thread(worker);
        workerThread.start();
    }

    @Override
    @SneakyThrows
    public void stop()
    {
        if (selector.isOpen()) selector.close();
        transportChannel.closeSilently();
        if (workerThread != null) workerThread.join();
    }
}
