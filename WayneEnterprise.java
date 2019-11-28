import java.io.*;
import java.util.*;

//parentPointer -> parentPointer
//left
//rightPointer
//color -> nodeColor
//exectime -> execution_time
//bst -> rbTree
//h -> heap

class Assignment{
    String type;
    int buildingNo;
    int globaltime;
    int total_time;
    int x,y;
    Assignment(int gt,String command,int a,int s){//insert
        globaltime=gt;
        if(command.equalsIgnoreCase("Insert")){
        type=command;
        buildingNo=a;
        total_time=s;
    }
    else{
        if(s==-1){
            type=command;
            x=a;
            y=-1;
        }
        else{
            type=command;
            x=a;
            y=s;
        }
    }
    }
}


class Node {
    int buildingNum; // holds the key
    Node parentPointer; // pointer to the parentPointer
    Node leftPointer; // pointer to leftPointer child
    Node rightPointer; // pointer to rightPointer child
    int nodeColor; // 1 . Red, 0 . Black
    int execution_time;
    int total_time;
    
    Node(int key,int tots){
        buildingNum=key;
        total_time=tots;
        execution_time=0;
        nodeColor=1;
    }
    Node(){}
}

class WayneEnterprise{
   
   static int globalTime=0;
	public static void main(String [] args) throws FileNotFoundException{
         Queue<Assignment> construct=new LinkedList<>();
        RBT rbTree = new RBT();
        Heap heap=new Heap();
    
        BufferedReader br = null;
        BufferedWriter bw = null;
        FileWriter fw = null;
        try{    
            File file = new File("Sample_input2.txt");
             if (!file.canRead()) {
            System.err.println(file.getAbsolutePath() + ": cannot be read");
             return;   
}   
           br = new BufferedReader(new FileReader(file));   
          
           
            String contentLine = br.readLine();
           ;
            String gt = " ", command = " ", totalT = " ";
            String builnum =" ", builnum1 = " ", builnum2 = " ";
            while (contentLine != null) {
                int gti = contentLine.indexOf(':');
                int i = contentLine.indexOf('(');
                int comma = contentLine.indexOf(',');
                int close = contentLine.indexOf(')');
                if(i!=-1)
                {
                    gt = contentLine.substring(0,gti);
                  
                    command = contentLine.substring(gti+2,i);
          
                    if(command.equalsIgnoreCase("Insert"))
                    {

                        builnum = contentLine.substring(i+1,comma);
                        
                        totalT = contentLine.substring(comma+1,close);
                        
                         Assignment new_proj=new Assignment(Integer.valueOf(gt),"Insert",Integer.valueOf(builnum),Integer.valueOf(totalT));
                         construct.add(new_proj);
                        
                    }
                    else{//Print
                        if(comma!=-1) // 2 parameters (range)
                        {
                            builnum1 = contentLine.substring(i+1,comma);
                         
                            builnum2 = contentLine.substring(comma+1,close);
                            Assignment new_proj=new Assignment(Integer.valueOf(gt),"Print",Integer.valueOf(builnum1),Integer.valueOf(builnum2));
                            construct.add(new_proj);
                            
                        }
                        else //print one triplet
                        {
                            builnum = contentLine.substring(i+1,close);
                            //bw.write(" BuildingID " + builnum);
                             Assignment new_proj=new Assignment(Integer.valueOf(gt),"Print",Integer.valueOf(builnum),-1);
                            construct.add(new_proj);
                        }
                    }
                  
                }
                contentLine = br.readLine();
            }
       } 
       catch (IOException ioe) 
       {
        System.out.println("ASD");
       ioe.printStackTrace();
       } 
       finally 
       {
           try 
           {
              if (br != null)
                        br.close();
            if (bw != null)
                        bw.close();
            if (fw != null)
                        fw.close();     
           }
           catch (IOException ioe) 
           {
                System.out.println("Error in closing the BufferedReader");
           }
        }
       Assignment in=construct.poll();
        Node x=new Node(in.buildingNo,in.total_time);
        globalTime=0;
        x.execution_time=0;
        heap.insert(x);
        rbTree.insert(x);
        
      startConstruction(construct,rbTree,heap);

}

    static void startConstruction(Queue<Assignment> construct,RBT rbTree,Heap heap){
        Queue<Node> wait=new LinkedList<>();
        int upcomingProject=-1;
        
   
         while(heap.heap[1]!=null){
          
            Node current=heap.heap[1];
            if(!construct.isEmpty())
                upcomingProject=construct.peek().globaltime;

            boolean Over=false;
            for(int i=0;i<5;i++){


                if(current.execution_time!=current.total_time)
                {
                  current.execution_time++;
                  globalTime++;
                }

               if(current.execution_time==current.total_time)
                {
                    if(globalTime==upcomingProject){

                 if(construct.peek().type.equalsIgnoreCase("Insert")){
                  Assignment tp=construct.poll();
                      Node bb=new Node(tp.buildingNo,tp.total_time);
                      bb.execution_time=0;
                     rbTree.insert(bb);
                     wait.add(bb);

                }
                  else{
                       Assignment p = construct.poll();
                       System.out.print(globalTime+" ");
                        if(p.y==-1)
                          rbTree.print(p.x);
                        else
                         rbTree.printRange(p.x,p.y);

                        }
                
                    
                upcomingProject=construct.peek()!=null?construct.peek().globaltime: -1;
               
            }
                  
                    System.out.println(current.buildingNum+" "+ globalTime);

                 
                  rbTree.deleteNode(current.buildingNum);
                  heap.remove();
                

                    if(globalTime==upcomingProject){

                 if(construct.peek().type.equalsIgnoreCase("Insert")){
                  Assignment tp=construct.poll();
                      Node bb=new Node(tp.buildingNo,tp.total_time);
                    bb.execution_time=1;
                     rbTree.insert(bb);
                     wait.add(bb);

                }
                  else{
                       Assignment p = construct.poll();
                       System.out.print(globalTime+" ");
                        if(p.y==-1)
                          rbTree.print(p.x);
                        else
                         rbTree.printRange(p.x,p.y);

                        }
                
                        
                upcomingProject=construct.peek()!=null?construct.peek().globaltime: -1;
               
            }
                
                 
                   Over=true;
                    
                   break;

                }

               if(globalTime==upcomingProject){

                 if(construct.peek().type.equalsIgnoreCase("Insert")){
                  Assignment tp=construct.poll();
                      Node bb=new Node(tp.buildingNo,tp.total_time);
                     bb.execution_time=1;
                     rbTree.insert(bb);
                     wait.add(bb);

                }
                  else{
                       Assignment p = construct.poll();
                       System.out.print(globalTime+" ");
                        if(p.y==-1)
                          rbTree.print(p.x);
                        else
                         rbTree.printRange(p.x,p.y);

                        }
                
                upcomingProject=construct.peek()!=null?construct.peek().globaltime: -1;
               
            }


           
                   

    }
    if(!Over){
       heap.remove();
       heap.insert(current);
    }
    

    while(!wait.isEmpty())
    {
        Node assigned = wait.poll();
       
        heap.insert(assigned);
    }


    
}

      heap.print();
}

}




    /// HEAP ///
    /// HEAP ///
    /// HEAP ///
    /// HEAP ///
    /// HEAP ///
    /// HEAP ///






class Heap  {
     Node heap[]=new Node[2000];
     Node dummy;
    
    int cursize;
    Heap(){
      dummy=new Node(Integer.MIN_VALUE,Integer.MIN_VALUE);
      heap[0] = dummy;
      heap[0].execution_time= -1;
      cursize=1;

    }
     void insert(Node key)
    
    {

        heap[cursize++]=key;
        int last=cursize-1;
    
        //minheapify(1);
     while( heap[last].execution_time < heap[parentPointer(last)].execution_time)
        {
            swap(last,parentPointer(last));
            last=parentPointer(last);
        }

        while(heap[last].execution_time==heap[parentPointer(last)].execution_time){
          if(heap[last].buildingNum<heap[parentPointer(last)].buildingNum)
          {
               
            swap(last,parentPointer(last));
                last=parentPointer(last);
              
          }
          else break;
        }
        minheapify(1); 
      
    }
 
     void minheapify(int index)
    
    {
        Node ele=heap[index];
        Node min;
        int min_i=0;
        if(isLeaf(index))
            return;
        if(isRcThere(index)){
            if(ele.execution_time>heap[leftChild(index)].execution_time||ele.execution_time>heap[rightChild(index)].execution_time){
            if(heap[leftChild(index)].execution_time<heap[rightChild(index)].execution_time){
              min=heap[leftChild(index)];
                min_i=leftChild(index);
                swap(min_i,index);
                minheapify(min_i);
            }
            else if(heap[leftChild(index)].execution_time>heap[rightChild(index)].execution_time){
                min=heap[rightChild(index)];
                min_i=rightChild(index);
                swap(min_i,index);
                minheapify(min_i); 
            }


            else if(heap[leftChild(index)].execution_time==heap[(rightChild(index))].execution_time){

              if(heap[leftChild(index)].buildingNum<heap[rightChild(index)].buildingNum){
                min=heap[leftChild(index)];
                     min_i=leftChild(index);
                     swap(min_i,index);
            minheapify(min_i);
                }
              else{
                min=heap[rightChild(index)];
                    min_i=rightChild(index);
                    swap(min_i,index);
            minheapify(min_i);
                }
            }
        }
        else if(ele.execution_time==heap[leftChild(index)].execution_time&&ele.execution_time==heap[rightChild(index)].execution_time)
        {
                if(heap[leftChild(index)].buildingNum<heap[rightChild(index)].buildingNum && ele.buildingNum>heap[leftChild(index)].buildingNum){
                    min=heap[leftChild(index)];
                     min_i=leftChild(index);
                     swap(min_i,index);
            minheapify(min_i);
                }
                else if(heap[leftChild(index)].buildingNum>heap[rightChild(index)].buildingNum && ele.buildingNum>heap[rightChild(index)].buildingNum){
                    min=heap[rightChild(index)];
                    min_i=rightChild(index);
                    swap(min_i,index);
            minheapify(min_i);
                }
        }
        else if(ele.execution_time==heap[leftChild(index)].execution_time)
        {
                if(ele.buildingNum>heap[leftChild(index)].buildingNum){
                    min=heap[leftChild(index)];
                     min_i=leftChild(index);
                     swap(min_i,index);
                     minheapify(min_i);
                }
        }
        else if(ele.execution_time==heap[rightChild(index)].execution_time)
        {
                if(ele.buildingNum>heap[rightChild(index)].buildingNum){
                    min=heap[rightChild(index)];
                     min_i=rightChild(index);
                     swap(min_i,index);
            minheapify(min_i);
                }
        }
    }


        else if(ele.execution_time>heap[leftChild(index)].execution_time){
            min=heap[leftChild(index)];
             min_i=leftChild(index);
             swap(min_i,index);
            minheapify(min_i);
        }
        else if(ele.execution_time==heap[leftChild(index)].execution_time){
            if(ele.buildingNum>heap[leftChild(index)].buildingNum){
                    min=heap[leftChild(index)];
                     min_i=leftChild(index);
                     swap(min_i,index);
                     minheapify(min_i);
                }   
        }
       

    }


     boolean isRcThere(int p)
    {
        if((2*p+1)<cursize)
            return true;
        return false;
    }
     boolean isLeaf(int index)
    {
        if((2*index+1)>cursize-1)
            return true;
        return false;
    }
     int leftChild(int p)
    {
        
        return 2*p;
    }
     int rightChild(int p)
    {
        
            return 2*p+1;
        
        
    }        
     int parentPointer(int pos)
    {

        int p=(pos)/2;
        if(p<0)
            return 0;
        return p;
    }
     void swap(int child,int parentPointer)
    {
        Node temp=heap[child];
        heap[child]=heap[parentPointer];
        heap[parentPointer]=temp;
        
    }
     void print()
    {
        System.out.println("***********************************************");
        for(int i=1;i<cursize;i++){
            Node x=heap[i];
            System.out.println(x.buildingNum+" "+ x.execution_time+" "+ x.total_time);
        }
        System.out.println("***********************************");
    }

    Node remove()
    {
        Node r=heap[1];

        if(cursize>2){
        heap[1]=heap[cursize-1];
        cursize--;
        minheapify(1);
    }
    else{
      heap[1]=null;
      cursize=1;
    }
      

        return r;
    }
}
   








//RBT
//RBT
//RBT
//RBT
//RBT
//RBT
//RBT
//RBT




class RBT {
  private Node root;
  private Node Ext;

  
  

  // fix the rb tree modified by the delete operation
  private void fixDelete(Node x) {
    Node s;
    while (x != root && x.nodeColor == 0) {
      if (x == x.parentPointer.leftPointer) {
        s = x.parentPointer.rightPointer;
        if (s.nodeColor == 1) {
          // case 3.1
          s.nodeColor = 0;
          x.parentPointer.nodeColor = 1;
          leftRotate(x.parentPointer);
          s = x.parentPointer.rightPointer;
        }

        if (s.leftPointer.nodeColor == 0 && s.rightPointer.nodeColor == 0) {
          // case 3.2
          s.nodeColor = 1;
          x = x.parentPointer;
        } else {
          if (s.rightPointer.nodeColor == 0) {
            // case 3.3
            s.leftPointer.nodeColor = 0;
            s.nodeColor = 1;
            rightRotate(s);
            s = x.parentPointer.rightPointer;
          } 

          // case 3.4
          s.nodeColor = x.parentPointer.nodeColor;
          x.parentPointer.nodeColor = 0;
          s.rightPointer.nodeColor = 0;
          leftRotate(x.parentPointer);
          x = root;
        }
      } else {
        s = x.parentPointer.leftPointer;
        if (s.nodeColor == 1) {
          // case 3.1
          s.nodeColor = 0;
          x.parentPointer.nodeColor = 1;
          rightRotate(x.parentPointer);
          s = x.parentPointer.leftPointer;
        }

        if (s.rightPointer.nodeColor == 0 && s.rightPointer.nodeColor == 0) {
          // case 3.2
          s.nodeColor = 1;
          x = x.parentPointer;
        } else {
          if (s.leftPointer.nodeColor == 0) {
            // case 3.3
            s.rightPointer.nodeColor = 0;
            s.nodeColor = 1;
            leftRotate(s);
            s = x.parentPointer.leftPointer;
          } 

          // case 3.4
          s.nodeColor = x.parentPointer.nodeColor;
          x.parentPointer.nodeColor = 0;
          s.leftPointer.nodeColor = 0;
          rightRotate(x.parentPointer);
          x = root;
        }
      } 
    }
    x.nodeColor = 0;
  }


  private void rbTransplant(Node u, Node v){
    if (u.parentPointer == null) {
      root = v;
    } else if (u == u.parentPointer.leftPointer){
      u.parentPointer.leftPointer = v;
    } else {
      u.parentPointer.rightPointer = v;
    }
    v.parentPointer = u.parentPointer;
  }

  private void deleteNodeUtil(Node node, int key) {
    // find the node containing key
    Node z = Ext;
    Node x, y;
    while (node != Ext){
      //System.out.println("hey");
      if (node.buildingNum == key) {
        z = node;
      }

      if (node.buildingNum <key) {
        node = node.rightPointer;
      } else {
        node = node.leftPointer;
      }
    }

    if (z == Ext) {
      System.out.println("Couldn't find key in the tree");
      return;
    } 

    y = z;
    int yOriginalColor = y.nodeColor;
    if (z.leftPointer == Ext) {
      x = z.rightPointer;
      rbTransplant(z, z.rightPointer);
    } else if (z.rightPointer == Ext) {
      x = z.leftPointer;
      rbTransplant(z, z.leftPointer);
    } 
    else {
      y = minimum(z.rightPointer);
      yOriginalColor = y.nodeColor;
      x = y.rightPointer;
      if (y.parentPointer == z) {
        x.parentPointer = y;
      } else {
        rbTransplant(y, y.rightPointer);
        y.rightPointer = z.rightPointer;
        y.rightPointer.parentPointer = y;
      }

      rbTransplant(z, y);
      y.leftPointer = z.leftPointer;
      y.leftPointer.parentPointer = y;
      y.nodeColor = z.nodeColor;
    }
    if (yOriginalColor == 0){
      fixDelete(x);
    }
  }


  
  // fix the red-black tree
  private void fixInsert(Node k){
    Node u;
    while (k.parentPointer.nodeColor == 1) {
      if (k.parentPointer == k.parentPointer.parentPointer.rightPointer) {
        u = k.parentPointer.parentPointer.leftPointer; // uncle
        if (u.nodeColor == 1) {
          // case 3.1
          u.nodeColor = 0;
          k.parentPointer.nodeColor = 0;
          k.parentPointer.parentPointer.nodeColor = 1;
          k = k.parentPointer.parentPointer;
        } else {
          if (k == k.parentPointer.leftPointer) {
            // case 3.2.2
            k = k.parentPointer;
            rightRotate(k);
          }
          // case 3.2.1
          k.parentPointer.nodeColor = 0;
          k.parentPointer.parentPointer.nodeColor = 1;
          leftRotate(k.parentPointer.parentPointer);
        }
      } else {
        u = k.parentPointer.parentPointer.rightPointer; // uncle

        if (u.nodeColor == 1) {
          // mirror case 3.1
          u.nodeColor = 0;
          k.parentPointer.nodeColor = 0;
          k.parentPointer.parentPointer.nodeColor = 1;
          k = k.parentPointer.parentPointer;  
        } else {
          if (k == k.parentPointer.rightPointer) {
            // mirror case 3.2.2
            k = k.parentPointer;
            leftRotate(k);
          }
          // mirror case 3.2.1
          k.parentPointer.nodeColor = 0;
          k.parentPointer.parentPointer.nodeColor = 1;
          rightRotate(k.parentPointer.parentPointer);
        }
      }
      if (k == root) {
        break;
      }
    }
    root.nodeColor = 0;
  }

  private void printHelper(Node root, String indent, boolean last) {
    // print the tree structure on the screen
      if (root != Ext) {
       System.out.print(indent);
       if (last) {
          System.out.print("R----");
          indent += "     ";
       } else {
          System.out.print("L----");
          indent += "|    ";
       }
            
           String sColor = root.nodeColor == 1?"RED":"BLACK";
       System.out.println(root.buildingNum+" "+root.total_time+" "+root.execution_time + "(" + sColor + ")");
       printHelper(root.leftPointer, indent, false);
       printHelper(root.rightPointer, indent, true);
    }
  }


  
  
  // find the node with the minimum key
  public Node minimum(Node node) {
    while (node.leftPointer != Ext) {
      node = node.leftPointer;
    }
    return node;
  }

  // find the node with the maximum key
  public Node maximum(Node node) {
    while (node.rightPointer != Ext) {
      node = node.rightPointer;
    }
    return node;
  }

  // find the successor of a given node
  public Node successor(Node x) {
    // if the rightPointer subtree is not null,
    // the successor is the leftmost node in the
    // rightPointer subtree
    if (x.rightPointer != Ext) {
      return minimum(x.rightPointer);
    }

    // else it is the lowest ancestor of x whose
    // leftPointer child is also an ancestor of x.
    Node y = x.parentPointer;
    while (y != Ext && x == y.rightPointer) {
      x = y;
      y = y.parentPointer;
    }
    return y;
  }

  // find the predecessor of a given node
  

  // rotate leftPointer at node x
  public void leftRotate(Node x) {
    Node y = x.rightPointer;
    x.rightPointer = y.leftPointer;
    if (y.leftPointer != Ext) {
      y.leftPointer.parentPointer = x;
    }
    y.parentPointer = x.parentPointer;
    if (x.parentPointer == null) {
      this.root = y;
    } else if (x == x.parentPointer.leftPointer) {
      x.parentPointer.leftPointer = y;
    } else {
      x.parentPointer.rightPointer = y;
    }
    y.leftPointer = x;
    x.parentPointer = y;
  }

  // rotate rightPointer at node x
  public void rightRotate(Node x) {
    Node y = x.leftPointer;
    x.leftPointer = y.rightPointer;
    if (y.rightPointer != Ext) {
      y.rightPointer.parentPointer = x;
    }
    y.parentPointer = x.parentPointer;
    if (x.parentPointer == null) {
      this.root = y;
    } else if (x == x.parentPointer.rightPointer) {
      x.parentPointer.rightPointer = y;
    } else {
      x.parentPointer.leftPointer = y;
    }
    y.rightPointer = x;
    x.parentPointer = y;
  }

  // insert the key to the tree in its appropriate position
  // and fix the tree
  public void insert(Node node) {
    // Ordinary Binary Search Insertion
    
    
    node.execution_time=0;
    node.leftPointer=Ext;
    node.rightPointer=Ext;
    node.parentPointer=null;
    node.nodeColor=1;
    
     // new node must be red

    Node y = null;
    Node x = this.root;

    while (x != Ext) {
      y = x;
      if (node.buildingNum < x.buildingNum) {
        x = x.leftPointer;
      } else {
        x = x.rightPointer;
      }
    }

    // y is parentPointer of x
    node.parentPointer = y;
    if (y == null) {
      root = node;
    } else if (node.buildingNum < y.buildingNum) {
      y.leftPointer = node;
    } else {
      y.rightPointer = node;
    }

    // if new node is a root node, simply return
    if (node.parentPointer == null){
      node.nodeColor = 0;
      return;
    }

    // if the grandparent is null, simply return
    if (node.parentPointer.parentPointer == null) {
      return;
    }

    // Fix the tree
    fixInsert(node);
  }

  

  // delete the node from the tree
  public void deleteNode(int buildingNum) {
    deleteNodeUtil(this.root, buildingNum);
  }

  // print the tree structure on the screen
  
  void print(int buildingNo){
    Node cur=root;
    while(cur!=null){
      if(cur.buildingNum == buildingNo){
       System.out.print("("+cur.buildingNum+" "+ cur.execution_time+" "+ cur.total_time+") ");
        System.out.println();
        return;
      }
      else if(buildingNo>cur.buildingNum)
        cur=cur.rightPointer;
      else
        cur=cur.leftPointer;
    }
  }

  void printRange(int x,int y)
  {
    Node cur=root;
    while(cur!=null){
      if(cur.buildingNum>=x && cur.buildingNum<=y){
        recurPrint(cur,x,y);
        System.out.println();
        return;
      }
      else if(cur.buildingNum<x){
        cur=cur.rightPointer;
      }
      else{
        cur=cur.leftPointer;
      }
    }
  }

  void recurPrint(Node cur,int x,int y){
    if(cur==Ext ) return;

    if(cur.buildingNum==x){
      recurPrint(cur.rightPointer,x,y);
      System.out.print("("+cur.buildingNum+" "+ cur.execution_time+" "+ cur.total_time+") ");
    }
    else if(cur.buildingNum==y){
      recurPrint(cur.leftPointer,x,y);
      System.out.print("("+cur.buildingNum+" "+ cur.execution_time+" "+ cur.total_time+") ");
    
    }
    else
    {
      if(cur.leftPointer!=Ext && inRange( cur.leftPointer.buildingNum, x,y)){
      recurPrint(cur.leftPointer,x,y);
      } 
      System.out.print("("+cur.buildingNum+" "+ cur.execution_time+" "+ cur.total_time+") ");
      if(cur.rightPointer!=Ext && inRange(cur.rightPointer.buildingNum,x,y))
        recurPrint(cur.rightPointer,x,y);
    }
  }

  boolean inRange(int target,int l,int heap){
    if(target>=l && target<=heap)
      return true;
    return false;
  }

  public RBT() {
    Ext = new Node();
    Ext.buildingNum=-1;
    Ext.nodeColor = 0;
    Ext.leftPointer = null;
    Ext.rightPointer = null;
    root = Ext;
  }





  
}



