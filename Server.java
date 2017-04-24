import java.io.FileWriter;
import java.io.IOException;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;


@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class Server 
{

  public static Node handler;
  public static AddService.Processor processor;
  public static void main(String [] args)
  {
    
	final int port=Integer.parseInt(args[0]);
  final int no=Integer.parseInt(args[1]);
    try 
    {
      handler = new Node("localhost", port);
      processor = new AddService.Processor(handler);

      Runnable simple = new Runnable() 
      {
        public void run() 
        {
          someMethod(processor, port, no);
        }
      };      

      new Thread(simple).start();
    } 
    catch (Exception x) 
    {
      //x.printStackTrace();
    }
  }

  public static void someMethod(AddService.Processor processor, int port, int no) 
  {
    try 
    {
      TServerTransport serverTransport = new TServerSocket(port);
      //TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
      TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
      //TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
      System.out.println("THRIFT NODE SERVER STARTED");
      if(no==0)
      {
        handler.join(null);
      }
      else
      {
        try 
        {
          TTransport transport; 
          transport=new TSocket("localhost", 9000);
          transport.open();
          TProtocol protocol= new TBinaryProtocol(transport);
          AddService.Client clientZ=new AddService.Client(protocol);
          NodeInfo nPrime=clientZ.getNodeInfo();
          handler.join(nPrime);
          transport.close();
        } 
        catch (TException x) 
        {
           x.printStackTrace();
        }
      }
      server.serve();
    } 
    catch (Exception e) 
    {
      //e.printStackTrace();
    }
  }
}
