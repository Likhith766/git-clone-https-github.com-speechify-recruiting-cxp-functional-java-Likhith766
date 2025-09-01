import java.util.HashMap;

class Node {
   int key;
   int value;
   Node prev;
   Node next;
      Public Node (int key,int value) {
      this key =key;
      this value=value;
   }
}
 public class LRUCache {
 private final int capacity;
 private final HashMap<integer;
 Node>cache;
private final Node head;
private final Node tail;
public LRUCache(int capacity){
this capacity=capacity;
this.cache=new
HashMap<>();
   this.head=new Node(0,0);
   this.tail=new Node(0,0);
this.head.next=this tail;
this.tail.prev=this tail;
}


private void removeNode(Node node) 
{
  node.prev.next=node.next;
  node.next.prev=node.prev;

private void moveToHead(Node node)
{
removeNode(node);
addNode(node);
}

public int get(int key) {
if (cache.containsKey(key)) {
Node node=cache.get(key);
moveToHead(node);
return node.value;
}
return -1;
}

public void set(int key,int value)
{
   if (cache.containsKey(key)) {
Node node=cache.get(key);
     node.value=value;
     moveToHead(node);
}
    else
{
Node(key,value);
cache.put(key,newNode);
addNode(newNode);
if(cache.size()>capacity)
{
Node IruNode=tail.prev;
removeNode(IruNode.key);
                      }
                  }
         }

}

