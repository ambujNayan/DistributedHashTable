struct NodeInfo 
{
    1: required i32 id;          
    2: required string host;          
    3: required i32 port;
    4: required string succHost; 
    5: required i32 succPort;
    6: required string predHost; 
    7: required i32 predPort;
}

service AddService 
{
   NodeInfo getNodeInfo(),
   string lookup(i32 key, bool trace),
   bool insert(i32 key, string meaning, bool trace),
   i32 find_successor(i32 id),
   NodeInfo find_predecessor(i32 id),
   NodeInfo closest_preceding_finger(i32 id),
   void join(NodeInfo nPrime),
   void init_finger_table(NodeInfo nPrime),
   void update_others(),
   void update_finger_table(NodeInfo s, i32 i),
   void printFingerTable(),
   void setPredecessor(i32 predPort)
}
