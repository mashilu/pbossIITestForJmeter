import org.apache.avro.Protocol;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.ipc.HttpServer;
import org.apache.avro.ipc.generic.GenericResponder;
import org.apache.avro.Protocol.Message;
/**
 * Created by mashilu on 2016/7/19.
 */
public class Server extends GenericResponder {

    private int reqCnt = 0;
    private Protocol protocol = null;
    private int port;

    public Server(Protocol protocol, int port) {
        super(protocol);
        this.protocol = protocol;
        this.port = port;

    }

    @Override
    public Object respond(Message message, Object request) throws Exception {
        reqCnt++;
        GenericRecord req = (GenericRecord)request;
        GenericRecord msg = (GenericRecord)(req.get("message"));
        // process the request;
        System.out.println(reqCnt);
        return msg;
    }

    public void run() {
        try {
            HttpServer server = new HttpServer(this, port);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 18888;  // default port
        if (args.length != 1) {
            System.out.println("Usage: Server port, server will run in default port: 18888");
        } else {
            port = Integer.parseInt(args[0]);
        }
        new Server(Utils.getProtocol(), port).run();
    }
}
