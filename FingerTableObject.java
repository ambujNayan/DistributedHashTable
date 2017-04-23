class FingerTableObject
{
	private int start;
	private NodeInfo node;

	public FingerTableObject(int start, NodeInfo node)
	{
		this.start=start;
		this.node=node;
	}

	public int getStart()
	{
		return start;
	}

	public NodeInfo getNodeInfo()
	{
		return node;
	}

	public void setNodeInfo(NodeInfo node)
	{
		this.node=node;
	}

	public void setStart(int start)
	{
		this.start=start;
	}
}