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
		boolean trace=false;
		System.out.println("QUERY CLIENT STARTED !");

		String someChordNodeURL=args[0];
		String[] hostPortPair=someChordNodeURL.split(",");
		String host=hostPortPair[0];
		int port=Integer.parseInt(hostPortPair[1]);

		Scanner in=new Scanner(System.in);
		System.out.println("Do you want trace to be on for LOOKUP operation? Enter Y/N: ");
		String response=in.next();
		if(response.equals("Y"))
		{
			trace=true;
		}

		try 
	  	{
	       TTransport transport; 
	       transport=new TSocket(host, port);
	       transport.open();
	       TProtocol protocol= new TBinaryProtocol(transport);
	       AddService.Client client=new AddService.Client(protocol);
	       findWord(client, host, trace);
	       transport.close();
	  	} 
	  	catch (TException x) 
	  	{
		   x.printStackTrace();
	  	}
	}

	private static void findWord(AddService.Client client, String host, boolean trace) throws TException
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
		
			try 
	  		{
				int nodePort=client.find_successor(hashCode);			
	       		TTransport transport; 
	       		transport=new TSocket(host, nodePort);
	       		transport.open();
			    TProtocol protocol= new TBinaryProtocol(transport);
			    AddService.Client secondaryClient=new AddService.Client(protocol);
			    lookupWord(secondaryClient, hashCode, trace);
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

	private static void lookupWord(AddService.Client secondaryClient, int key, boolean trace) throws TException
	{
		String meaning=secondaryClient.lookup(key, trace);
		System.out.println("Result: "+meaning);
	}
}