import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Date;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

public class Node implements AddService.Iface 
{
	private NodeInfo n;
	private int m=32;
	
	private FingerTableObject[] fingerTable;
	private Hashtable<Integer, String> dictionary;
	private PrintWriter fw;
	private PrintWriter tw;
	private boolean trace;

	public Node(String ipAddress, int port)
	{
		int hashCode=(ipAddress+port).hashCode();
		if(hashCode<0)
			hashCode=hashCode>>>1;

		n=new NodeInfo(hashCode, ipAddress, port, "localhost", -1, "localhost", -1);
		
		fingerTable=new FingerTableObject[32];
		dictionary=new Hashtable<Integer, String>();
		trace=false;

		try 
		{
			fw=new PrintWriter("STRUCTURE_OF_DHT_AT_PORT_"+n.getPort()+".txt");
			tw=new PrintWriter("TRACE_FILE_AT_PORT_"+n.getPort()+".txt");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void printFingerTable()
	{
		fw.append("PRINTING THE STRUCTURE OF DISTRIBUTED HASH TABLE: \n");
		fw.flush();
		fw.append("NODE ID: "+n.getId()+"\n");
		fw.flush();
		fw.append("NODE KEY (same as NODE ID): "+n.getId()+"\n");
		fw.flush();
		fw.append("NODE PORT: "+n.getPort()+"\n");
		fw.flush();
		fw.append("PORT OF NODE's SUCCESSOR: "+n.getSuccPort()+"\n");
		fw.flush();
		fw.append("PORT OF NODE's PREDECESSOR: "+n.getPredPort()+"\n");
		fw.flush();
		fw.append("NUMBER OF ENTRIES STORED AT THIS NODE: "+dictionary.size()+"\n");
		fw.flush();
		fw.append("\nFINGER TABLE: \n");
		fw.flush();

		for(int i=0; i<m; i++)
		{
			fw.append("\n START: "+fingerTable[i].getStart()+" NODE's ID: "+fingerTable[i].getNodeInfo().getId()+" NODE's PORT: "+fingerTable[i].getNodeInfo().getPort()+"\n");
			fw.flush();
		}
	}

	public  NodeInfo getNodeInfo()
	{
		if(this.trace)
		{
			tw.append("\nExecution of getNodeInfo() at "+new Date());
			tw.flush();
		}
		
		return n;
	}

	public void setPredecessor(int predPort)
	{
		if(this.trace)
		{
			tw.append("\nExecution of setPredecessor() at "+new Date());
			tw.flush();
		}
		
		n.setPredPort(predPort);
	}

	public  String lookup(int key, boolean trace)
	{
		this.trace=trace;		
		if(this.trace)
		{
			tw.append("\nExecution of lookup() at "+new Date());
			tw.flush();
		}

		if(dictionary.containsKey(key))
			return dictionary.get(key);
		else
			return "NOT FOUND";
	}

	public  boolean insert(int key, String meaning, boolean trace)
	{
		this.trace=trace;
		if(this.trace)
		{
			tw.append("\nExecution of insert() at "+new Date());
			tw.flush();
		}

		if(dictionary.containsKey(key))
			return false;
		else
		{
			dictionary.put(key, meaning);
			return true;
		}
	}

	// ask node n to find id's successor
	// IP: id (basically the hashcode)
	// OP: NodeInfo (has id, host, port, successor, predecessor)
	public  int find_successor(int id) throws org.apache.thrift.TException
	{
		if(this.trace)
		{
			tw.append("\nExecution of find_successor() at "+new Date());
			tw.flush();
		}

		NodeInfo nPrime=find_predecessor(id);
		return nPrime.getSuccPort();
	}

	public  int getSuccId(NodeInfo nPrime)
	{
		if(this.trace)
		{
			tw.append("\nExecution of getSuccId() at "+new Date());
			tw.flush();
		}

		int succPort=nPrime.getSuccPort();

		if(succPort==n.getPort())
		{
			return n.getId();
		}
		else
		{
			int nPrimeSuccId=-1;
			try 
			{
				TTransport transport; 
				transport=new TSocket("localhost", succPort);
				transport.open();
				TProtocol protocol= new TBinaryProtocol(transport);
				AddService.Client clientA=new AddService.Client(protocol);
				nPrimeSuccId=clientA.getNodeInfo().getId();
				transport.close();
			} 
			catch (TException x) 
			{
			   x.printStackTrace();
			}

			return nPrimeSuccId;
		}	
	}

	// ask node n to find id's predecessor
	// IP: id (basically the hashcode)
	// OP: NodeInfo (has id, host, port, successor, predecessor)
	public  NodeInfo find_predecessor(int id)
	{
		if(this.trace)
		{
			tw.append("\nExecution of find_predecessor() at "+new Date());
			tw.flush();
		}

		NodeInfo nPrime=n;
		boolean loopCheck=false;

		if(nPrime.getId()<getSuccId(nPrime))
			loopCheck=(id>nPrime.getId() && id<=getSuccId(nPrime));
		else
			loopCheck=(id>nPrime.getId() || id<=getSuccId(nPrime));

		while(!loopCheck)
		{
			if(n.getPort()==nPrime.getPort())
			{
				nPrime=closest_preceding_finger(id);
			}
			else
			{
				try 
				{
				    TTransport transport; 
				    transport=new TSocket("localhost", nPrime.getPort());
				    transport.open();
				    TProtocol protocol= new TBinaryProtocol(transport);
				    AddService.Client clientB=new AddService.Client(protocol);
				    nPrime=clientB.closest_preceding_finger(id);
				    transport.close();
				} 
				catch (TException x) 
				{
				   x.printStackTrace();
				}	
			}
			
			if(nPrime.getId()<getSuccId(nPrime))
				loopCheck=(id>nPrime.getId() && id<=getSuccId(nPrime));
			else
				//loopCheck=(id>=getSuccId(nPrime) && id<nPrime.getId());
				loopCheck=(id>nPrime.getId() || id<=getSuccId(nPrime));
		}
		return nPrime;
	}

	// return closest finger id
	// IP: id (basically the hashcode)
	// OP: NodeInfo (has id, host, port, successor, predecessor)
	public  NodeInfo closest_preceding_finger(int id)
	{
		if(this.trace)
		{
			tw.append("\nExecution of closest_preceding_finger() at "+new Date());
			tw.flush();
		}

		for(int i=m-1; i>=0; i--)
		{
			if(n.getId()<id)
			{
				if(fingerTable[i].getNodeInfo().getId()>n.getId() && fingerTable[i].getNodeInfo().getId()<id)
				{
					return fingerTable[i].getNodeInfo();
				}
			}
				
			else
			{
				if((fingerTable[i].getNodeInfo().getId()>n.getId()) || (fingerTable[i].getNodeInfo().getId()<id))
				{
					return fingerTable[i].getNodeInfo();
				}
			}
				
		}
		return n;
	}

	// node n joins tghe network
	// node nPrime is an arbitrary node in the network
	public  void join(NodeInfo nPrime) throws TException
	{
		if(this.trace)
		{
			tw.append("\nExecution of join() at "+new Date());
			tw.flush();
		}

		// if n is the only node in the network
		if(nPrime==null)
		{
			for(int i=0; i<m; i++)
			{
				// populate finger table
				fingerTable[i] = new FingerTableObject((int)(((long)(n.getId()+(long)Math.pow(2, i)))%(long)Math.pow(2, 31)), n);
			}
			n.setSuccHost("localhost");
			n.setSuccPort(n.getPort());
			n.setPredHost("localhost");
			n.setPredPort(n.getPort());
		}
		else
		// nPrime is some arbitrary node in the network
		{
			init_finger_table(nPrime);
			update_others();
			// Check if I have missed anything here
		}
	}

	// initialize finger table of local node
	// nPrime is arbitrary node already in the network
	public  void init_finger_table(NodeInfo nPrime) throws TException
	{
		if(this.trace)
		{
			tw.append("\nExecution of init_finger_table() at "+new Date());
			tw.flush();
		}

		for(int i=0; i<m; i++)
		{
			fingerTable[i] = new FingerTableObject((int)(((long)n.getId()+(long)Math.pow(2, i))%(long)Math.pow(2, 31)), null);
		}

		// remote call of function find_successor
		try 
		{
			TTransport transport; 
			transport=new TSocket("localhost", nPrime.getPort());
			transport.open();
			TProtocol protocol= new TBinaryProtocol(transport);
			AddService.Client clientC=new AddService.Client(protocol);
			int xPort=clientC.find_successor(fingerTable[0].getStart());
			transport.close();
			//
			if(xPort==n.getPort())
			{
				fingerTable[0].setNodeInfo(n);
			}
			else
			{
				transport=new TSocket("localhost", xPort);
				transport.open();
				protocol= new TBinaryProtocol(transport);
				AddService.Client clientD=new AddService.Client(protocol);
				fingerTable[0].setNodeInfo(clientD.getNodeInfo());
				transport.close();	
			}
		} 
		catch (TException x) 
		{
		   x.printStackTrace();
		}

		n.setSuccHost("localhost");
		n.setSuccPort(fingerTable[0].getNodeInfo().getPort());
		
		if(n.getSuccPort()==n.getPort())
		{
			int yPort=n.getPredPort();
			n.setPredHost("localhost");
			n.setPredPort(yPort);
			//
			n.setPredPort(n.getPort());
			n.setPredHost("localhost");
		}
		else
		{
			try 
			{
				TTransport transport; 
				transport=new TSocket("localhost", n.getSuccPort());
				transport.open();
				TProtocol protocol= new TBinaryProtocol(transport);
				AddService.Client clientE=new AddService.Client(protocol);
				int yPort=clientE.getNodeInfo().getPredPort();
				//
				n.setPredHost("localhost");
				n.setPredPort(yPort);
				clientE.setPredecessor(n.getPort());
				transport.close();
			} 
			catch (TException x) 
			{
			   x.printStackTrace();
			}
		}
		
		for(int i=0; i<m-1; i++)
		{
			
			if(n.getId()<fingerTable[i].getNodeInfo().getId())
			{
				if(fingerTable[i+1].getStart()>=n.getId() && fingerTable[i+1].getStart()<fingerTable[i].getNodeInfo().getId())
				{
					fingerTable[i+1].setNodeInfo(fingerTable[i].getNodeInfo());
				}
				else
				{
					if(nPrime.getPort()==n.getPort())
					{
						int yPort=find_successor(fingerTable[i+1].getStart());
						if(yPort==n.getPort())
						{
							fingerTable[i+1].setNodeInfo(n);
						}
						else
						{
							TTransport transport; 
							transport=new TSocket("localhost", yPort);
							transport.open();
							TProtocol protocol= new TBinaryProtocol(transport);
							AddService.Client clientG=new AddService.Client(protocol);
							fingerTable[i+1].setNodeInfo(clientG.getNodeInfo());
							transport.close();
						}

					}
					else
					{
						// remote call
						try 
						{
							TTransport transport; 
							transport=new TSocket("localhost", nPrime.getPort());
							transport.open();
							TProtocol protocol= new TBinaryProtocol(transport);
							AddService.Client clientF=new AddService.Client(protocol);
							int yPort=clientF.find_successor(fingerTable[i+1].getStart());
							transport.close();
							if(yPort==n.getPort())
								fingerTable[i+1].setNodeInfo(n);
							else
							{
								transport=new TSocket("localhost", yPort);
								transport.open();
								protocol= new TBinaryProtocol(transport);
								AddService.Client clientG=new AddService.Client(protocol);
								fingerTable[i+1].setNodeInfo(clientG.getNodeInfo());
								transport.close();	
							}	
						} 
						catch (TException x) 
						{
						   x.printStackTrace();
						}	
					}
					
				}
			}
			else
			{
				if((fingerTable[i+1].getStart()>=n.getId() || fingerTable[i+1].getStart()<fingerTable[i].getNodeInfo().getId()))
				{
					fingerTable[i+1].setNodeInfo(fingerTable[i].getNodeInfo());
				}
				else
				{
					if(nPrime.getPort()==n.getPort())
					{
						int yPort=find_successor(fingerTable[i+1].getStart());
						if(yPort==n.getPort())
						{
							fingerTable[i+1].setNodeInfo(n);
						}
						else
						{
							TTransport transport; 
							transport=new TSocket("localhost", yPort);
							transport.open();
							TProtocol protocol= new TBinaryProtocol(transport);
							AddService.Client clientG=new AddService.Client(protocol);
							fingerTable[i+1].setNodeInfo(clientG.getNodeInfo());
							transport.close();
						}

					}
					else
					{	// remote call
						try 
						{
							TTransport transport; 
							transport=new TSocket("localhost", nPrime.getPort());
							transport.open();
							TProtocol protocol= new TBinaryProtocol(transport);
							AddService.Client clientF=new AddService.Client(protocol);
							int yPort=clientF.find_successor(fingerTable[i+1].getStart());
							transport.close();
							if(yPort==n.getPort())
								fingerTable[i+1].setNodeInfo(n);
							else
							{
								transport=new TSocket("localhost", yPort);
								transport.open();
								protocol= new TBinaryProtocol(transport);
								AddService.Client clientG=new AddService.Client(protocol);
								fingerTable[i+1].setNodeInfo(clientG.getNodeInfo());
								transport.close();	
							}		
						} 
						catch (TException x) 
						{
						   x.printStackTrace();
						}	
					}	
				}
			}
		}
	}

	// update all nodes whose finger tables should refer to n
	public  void update_others()
	{
		if(this.trace)
		{
			tw.append("\nExecution of update_others() at "+new Date());
			tw.flush();
		}

		for(int i=0; i<m; i++)
		{
			long xx=n.getId();
			long y=(long)Math.pow(2, i);
			long z=(xx-y);
			if(z<0)
				z=((long) Math.pow(2, 32))+z;
			
			NodeInfo p=null;
			p=find_predecessor((int)z);
			
			if(n.getPort()==9100 && i==0)
			{
				try 
				{
					TTransport transport; 
					transport=new TSocket("localhost", 9000);
					transport.open();
					TProtocol protocol= new TBinaryProtocol(transport);
					AddService.Client clientH=new AddService.Client(protocol);
					clientH.update_finger_table(n, i);
					transport.close();
				} 
				catch (TException x) 
				{
				   x.printStackTrace();
				}
			}	
			else
			{
				if(p.getPort()!=n.getPort())
				{
					// remote call
					try 
					{
						TTransport transport; 
						transport=new TSocket("localhost", p.getPort());
						transport.open();
						TProtocol protocol= new TBinaryProtocol(transport);
						AddService.Client clientH=new AddService.Client(protocol);
						clientH.update_finger_table(n, i);
						transport.close();
					} 
					catch (TException x) 
					{
					   x.printStackTrace();
					}	
				}	
			}
		}
	}

	// if s is the ith finger of n, update n's finger table with s
	public void update_finger_table(NodeInfo s, int i)
	{
		if(this.trace)
		{
			tw.append("\nExecution of update_finger_table() at "+new Date());
			tw.flush();
		}

		if(n.getPort()!=s.getPort())
		{
			if(n.getId()<fingerTable[i].getNodeInfo().getId())
			{
				if(s.getId()>= n.getId() && s.getId()<fingerTable[i].getNodeInfo().getId())
				{
					fingerTable[i].setNodeInfo(s);
					if(i==0)
					{
						n.setSuccPort(fingerTable[i].getNodeInfo().getPort());
					}	
	
					int predPort=n.getPredPort();
					if(predPort!=9100 && n.getPort()!=9000)
					{
						// remote call
						try 
						{
							TTransport transport; 
							transport=new TSocket("localhost", predPort);
							transport.open();
							TProtocol protocol= new TBinaryProtocol(transport);
							AddService.Client clientI=new AddService.Client(protocol);
							clientI.update_finger_table(s, i);
							transport.close();
						} 
						catch (TException x) 
						{
						   x.printStackTrace();
						}	
					}	
				}
			}
			else
			{
				if((s.getId()>= n.getId() || s.getId()<fingerTable[i].getNodeInfo().getId()))
				{
					fingerTable[i].setNodeInfo(s);
					if(i==0)
					{
						n.setSuccPort(fingerTable[i].getNodeInfo().getPort());
					}	
					
					int predPort=n.getPredPort();
					if(predPort!=9100 && n.getPort()!=9000)
					{
						// remote call
						try 
						{
							TTransport transport; 
							transport=new TSocket("localhost", predPort);
							transport.open();
							TProtocol protocol= new TBinaryProtocol(transport);
							AddService.Client clientI=new AddService.Client(protocol);
							clientI.update_finger_table(s, i);
							transport.close();
						} 
						catch (TException x) 
						{
						   x.printStackTrace();
						}	
					}
				}
			}	
		}
	}
}
