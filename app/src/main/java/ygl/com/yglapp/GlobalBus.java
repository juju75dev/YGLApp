package ygl.com.yglapp;

/**
 * Created by juju on 19/05/2017.
 */
import com.squareup.otto.Bus;

public class GlobalBus {
    private static Bus sBus;
    public static Bus getBus() {
        if (sBus == null)
            sBus = new Bus();
        return sBus;
    }
}

