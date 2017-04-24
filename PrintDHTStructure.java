import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.*;	
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

public class PrintDHTStructure
{
	public static void main(String[] args) throws IOException
	{
		System.out.println("Print DHT Structure command issued \n");

	  	for(int i=0; i<800; i+=100)
	  	{
	  		try 
			{
				System.out.println("Commmand issued for server at port: "+(9000+i));
			    TTransport transport; 
			    transport=new TSocket("localhost", (9000+i));
			    transport.open();
			    TProtocol protocol= new TBinaryProtocol(transport);
			    AddService.Client client=new AddService.Client(protocol);
			    client.printFingerTable();
			    transport.close();
			} 
			catch (TException x) 
			{
			   x.printStackTrace();
			}
	  	}
	}
}
