import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

	public Node(String ipAddress, int port)
	{
		
		int hashCode=(ipAddress+port).hashCode();
		System.out.println("hashCode: "+hashCode);
		//hashCode = (int) Math.abs(hashCode);
		//System.out.println("After applying Math.abs: "+hashCode);
		if(hashCode<0)
			hashCode=hashCode>>>1;
		//hashCode=hashCode%1024;
		//*/

		n=new NodeInfo(hashCode, ipAddress, port, "localhost", -1, "localhost", -1);
		
		fingerTable=new FingerTableObject[32];
		dictionary=new Hashtable<Integer, String>();
	}

	public void printFingerTable()
	{
		System.out.println("FINGER TABLE PRINT: ");
		System.out.println("Local Port: "+n.getPort());
		System.out.println("n.getId(): "+n.getId());
		System.out.println("n.getSuccPort(): "+n.getSuccPort());
		System.out.println("n.getPredPort(): "+n.getPredPort());
		for(int i=0; i<m; i++)
		{
			System.out.println("INDEX: "+(i+1)+" START: "+fingerTable[i].getStart()+" NODE: "+fingerTable[i].getNodeInfo().getPort());
		}
	}

	public  NodeInfo getNodeInfo()
	{
		return n;
	}

	public void setPredecessor(int predPort)
	{
		n.setPredPort(predPort);
	}

	public  String lookup(int key)
	{
		if(dictionary.containsKey(key))
			return dictionary.get(key);
		else
			return "NOT FOUND";
	}

	public  boolean insert(int key, String meaning)
	{
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
		NodeInfo nPrime=find_predecessor(id);
		return nPrime.getSuccPort();
	}

	public  int getSuccId(NodeInfo nPrime)
	{
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
		System.out.println("JOINED THE NETWORK");
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

		System.out.println("JOINED SUCCESSFULLY");

	}

	// initialize finger table of local node
	// nPrime is arbitrary node already in the network
	public  void init_finger_table(NodeInfo nPrime) throws TException
	{
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
				System.out.println("clientE will be connected to port: "+n.getSuccPort());
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
		System.out.println("--------------------------------------------------");
		System.out.println("NODE: update_others()");
		for(int i=0; i<m; i++)
		{
			System.out.println("update_others(): i: "+i);
			long xx=n.getId();
			long y=(long)Math.pow(2, i);
			long z=(xx-y);
			if(z<0)
				z=((long) Math.pow(2, 32))+z;

			System.out.println("n.getId(): "+xx);
			System.out.println("(int)Math.pow(2, i)): "+y);
			System.out.println("Trying to find predecessor of: "+z);
			
			NodeInfo p=null;
			p=find_predecessor((int)z);
			//p=find_predecessor(xx);
			System.out.println("Predecessor of z is: "+p.getPort());
			//System.out.println("Predecessor of xx is: "+p.getPort());

			if(n.getPort()==9100 && i==0)
			{
				System.out.println("Remote call of update_finger_table()");
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
					System.out.println("Remote call of update_finger_table()");
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
		System.out.println("--------------------------------------------------");
	}

	// if s is the ith finger of n, update n's finger table with s
	public void update_finger_table(NodeInfo s, int i)
	{
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Node: update_finger_table");
		System.out.println("s.getPort(): "+s.getPort());
		System.out.println("n.getPort(): "+n.getPort());
		System.out.println("i: "+i);		
		System.out.println("n.getId(): "+n.getId());
		System.out.println("fingerTable[i].getNodeInfo().getId(): "+fingerTable[i].getNodeInfo().getId());
			
		if(n.getPort()!=s.getPort())
		{
			if(n.getId()<fingerTable[i].getNodeInfo().getId())
			{
				System.out.println("IF-PART");
				if(s.getId()>= n.getId() && s.getId()<fingerTable[i].getNodeInfo().getId())
				{
					System.out.println("Finger table got updated");
					fingerTable[i].setNodeInfo(s);
					if(i==0)
					{
						n.setSuccPort(fingerTable[i].getNodeInfo().getPort());
					}	
	
					int predPort=n.getPredPort();
					System.out.println("predPort: "+predPort);
					System.out.println("n.getPort(): "+n.getPort());
					
					
					//if(predPort!=n.getPort() && predPort!=s.getPort())
					//{
					if(predPort!=9100 && n.getPort()!=9000)
					{
						// remote call
						try 
						{
							System.out.println("Calling remote call on port: "+predPort);
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
				System.out.println("ELSE-PART");
				if((s.getId()>= n.getId() || s.getId()<fingerTable[i].getNodeInfo().getId()))
				{
					System.out.println("Finger table got updated");
					fingerTable[i].setNodeInfo(s);
					if(i==0)
					{
						n.setSuccPort(fingerTable[i].getNodeInfo().getPort());
					}	
					
					int predPort=n.getPredPort();
					System.out.println("predPort: "+predPort);
					System.out.println("n.getPort(): "+n.getPort());
					
					//if(predPort!=n.getPort() && predPort!=s.getPort())
					//{
					if(predPort!=9100 && n.getPort()!=9000)
					{
						// remote call
						try 
						{
							System.out.println("Calling remote call on prot: "+predPort);
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
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
	}
}
