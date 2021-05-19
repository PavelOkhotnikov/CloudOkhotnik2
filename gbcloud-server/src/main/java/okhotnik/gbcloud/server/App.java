package okhotnik.gbcloud.server;

import okhotnik.gbcloud.server.bootstrap.ServerBootstrap;

import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.inject.spi.CDI;

public class App
{
    public static void main(String[] args)
    {
        SeContainerInitializer.newInstance().addPackages(App.class).initialize();
        final ServerBootstrap bootstrap = CDI.current().select(ServerBootstrap.class).get();
        bootstrap.run(args);
    }
}
