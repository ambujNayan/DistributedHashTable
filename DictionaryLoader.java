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

public class DictionaryLoader
{
	static HashMap<Integer, String> dictionary;

	public static void main(String[] args) throws IOException
	{
		System.out.println("DICTIONARY LOADER STARTED !");

		dictionary=new HashMap<Integer, String>();

		String someChordNodeURL=args[0];
		String[] hostPortPair=someChordNodeURL.split(",");
		String host=hostPortPair[0];
		int port=Integer.parseInt(hostPortPair[1]);
		boolean trace=false;

		String dictionaryFilePath=args[1];
		Scanner dictionaryFile = new Scanner(Paths.get(dictionaryFilePath));

		int countOfOverWrittenWords=0;

		Scanner in=new Scanner(System.in);
		System.out.println("Do you want trace to be on for INSERT operation? Enter Y/N: ");
		String response=in.next();
		if(response.equals("Y"))
		{
			trace=true;
		}

		while(dictionaryFile.hasNextLine())
		{
			String wordMeaningPair=dictionaryFile.nextLine();
			String[] wordMeaningPairArr=wordMeaningPair.split(":");
			String word=wordMeaningPairArr[0].trim();
			String meaning=wordMeaningPairArr[1].trim();

			int hashCode=word.hashCode();
			if(hashCode<0)
				hashCode=hashCode>>>1;

			if(dictionary.containsKey(hashCode))
			{
				countOfOverWrittenWords++;
				dictionary.put(hashCode, meaning);
			}
			else
			{
				dictionary.put(hashCode, meaning);
			}
		}

		try 
	  	{
	       TTransport transport; 
	       transport=new TSocket(host, port);
	       transport.open();
	       TProtocol protocol= new TBinaryProtocol(transport);
	       AddService.Client client=new AddService.Client(protocol);
	       findNode(client, host, dictionary, trace);
	       transport.close();
	  	} 
	  	catch (TException x) 
	  	{
		   x.printStackTrace();
	  	}
	}

	private static void findNode(AddService.Client client, String host, HashMap<Integer, String> dictionary, boolean trace) throws TException
	{
		Iterator it=dictionary.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<Integer,String> pair=(Map.Entry) it.next();

			int nodePort=client.find_successor(pair.getKey().intValue());
			
			try 
	  		{
	       		TTransport transport;
	       		transport=new TSocket("localhost", nodePort);
	       		transport.open();
			    TProtocol protocol= new TBinaryProtocol(transport);
			    AddService.Client secondaryClient=new AddService.Client(protocol);
			    secondaryClient.insert(pair.getKey(), pair.getValue(), trace);
			    transport.close();
	  		} 
	  		catch (TException x) 
	  		{
		   		x.printStackTrace();
	  		}
		}
	}
}