import java.util.*;

public class MinHeap {
	private ArrayList<HuffmanNode> nodes;
	private int root = 1;
	
   public MinHeap() { 
      nodes = new ArrayList<HuffmanNode>();
      nodes.add(null); 
   }

   public void insert(HuffmanNode newNode) {
      nodes.add(null);
      int i = nodes.size() - 1;
      while (i > 1 && gP(i).compareTo(newNode) > 0) {
         nodes.set(i, gP(i));
         i = gPI(i);
      }
      nodes.set(i, newNode);
      updateHeap();
   }

   public HuffmanNode remove() {
      HuffmanNode next = nodes.get(root);      
      int tail = nodes.size() - 1;
      HuffmanNode endNode = nodes.remove(tail);
      if (tail > root) {
         nodes.set(root, endNode);
         updateHeap();     
      }
      return next;
   }

   private void updateHeap() {
      HuffmanNode root = nodes.get(1);
      int tail = nodes.size() - 1;
      int i = 1;
      boolean done = false;
      while(!done) {
         int childIndex = gLCI(i);
         if (childIndex <= tail) {
            HuffmanNode child = gLC(i);
            if (gRCI(i) <= tail && gRC(i).compareTo(child) < 0) {
               childIndex = gRCI(i);
               child = gRC(i);
            }
            if (child.compareTo(root) < 0) {
               nodes.set(i, child);
               i = childIndex;
            }
            else {
               done = true;
            }
         }
         else {
            done = true; 
         }
      }
      nodes.set(i, root);
   }

   public int size() {
      return nodes.size() - 1;
   }

   private static int gLCI(int i) {
      return 2 * i;
   }

   private static int gRCI(int i) {
      return 2 * i + 1;
   }

   private static int gPI(int i) {
      return i / 2;
   }

   private HuffmanNode gLC(int i) {
      return nodes.get(2 * i);
   }

   private HuffmanNode gRC(int i) {
      return nodes.get(2 * i + 1);
   }

   private HuffmanNode gP(int i) {
      return nodes.get(i / 2);
   }
}