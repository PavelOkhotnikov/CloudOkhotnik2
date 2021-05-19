package okhotnik.gbcloud.server.handlers;


import okhotnik.gbcloud.server.logging.ServerLogger;
import okhotnik.gbcloud.server.events.EClientConnected;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.interceptor.Interceptors;

@ApplicationScoped
@Interceptors(ServerLogger.class)
public class HClientConnected
{
    private void handleClientConnected(@ObservesAsync final EClientConnected event)
    {

    }
}
