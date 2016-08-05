import org.apache.avro.Protocol;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by mashilu on 2016/7/19.
 */
public class Utils {
    public static Protocol getProtocol() {
        Protocol protocol = null;
        try {
            URL url = Utils.class.getResource("message.avpr");
            System.out.println(url);
            protocol = Protocol.parse(new File(url.getPath()));
        } catch (IOException e) {
            System.out.println("server:avro.Utils.getProtocol==============");
            e.printStackTrace();
        }

        return protocol;
    }
}
