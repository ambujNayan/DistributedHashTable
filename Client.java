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

public class Client
{
	public static void main(String[] args) throws IOException
	{
		System.out.println("CLIENT STARTED !");

		String someChordNodeURL=args[0];
		String[] hostPortPair=someChordNodeURL.split(",");
		String host=hostPortPair[0];
		int port=Integer.parseInt(hostPortPair[1]);

		// Displays - to be removed
		System.out.println("Host: "+host);
		System.out.println("Port: "+port);

		try 
	  	{
	       TTransport transport; 
	       transport=new TSocket(host, port);
	       transport.open();
	       TProtocol protocol= new TBinaryProtocol(transport);
	       AddService.Client client=new AddService.Client(protocol);
	       findWord(client, host);
	       transport.close();
	  	} 
	  	catch (TException x) 
	  	{
		   x.printStackTrace();
	  	}

	  	System.out.println("ISSUING PRINT COMMAND \n");

	  	try 
		{
		    TTransport transport; 
		    transport=new TSocket(host, 9000);
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

		try 
		{
		    TTransport transport; 
		    transport=new TSocket(host, 9100);
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

		try 
		{
		    TTransport transport; 
		    transport=new TSocket(host, 9200);
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

		try 
		{
		    TTransport transport; 
		    transport=new TSocket(host, 9300);
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

		try 
		{
		    TTransport transport; 
		    transport=new TSocket(host, 9400);
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

		try 
		{
		    TTransport transport; 
		    transport=new TSocket(host, 9500);
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

		try 
		{
		    TTransport transport; 
		    transport=new TSocket(host, 9600);
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

		try 
		{
		    TTransport transport; 
		    transport=new TSocket(host, 9700);
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

	private static void findWord(AddService.Client client, String host) throws TException
	{
		Scanner in=new Scanner(System.in);
		System.out.println("Enter 1 to lookup, 2 to exit: ");
		System.out.println("Enter your choice: ");
		int choice=in.nextInt();

		while(choice!=2)
		{
			System.out.println("Enter a word: ");
			String word=in.next();
			int hashCode=word.hashCode();
			if(hashCode<0)
				hashCode=hashCode>>>1;	
			hashCode=hashCode%1024;		
			try 
	  		{
				int nodePort=client.find_successor(hashCode);			
	       		TTransport transport; 
	       		transport=new TSocket(host, nodePort);
	       		transport.open();
			    TProtocol protocol= new TBinaryProtocol(transport);
			    AddService.Client secondaryClient=new AddService.Client(protocol);
			    lookupWord(secondaryClient, hashCode);
			    transport.close();
	  		} 
	  		catch (TException x) 
	  		{
		   		x.printStackTrace();
	  		}

	  		System.out.println("Enter 1 to lookup, 2 to exit: ");
			System.out.println("Enter your choice: ");
			choice=in.nextInt();
		}
	}

	private static void lookupWord(AddService.Client secondaryClient, int key) throws TException
	{
		String meaning=secondaryClient.lookup(key);
		// Displays - to be removed
		System.out.println("Result: "+meaning);
	}
}