import java.io.*;
import java.util.*;

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


//parentPointer -> parentPointer
class Node {
    int buildingNum; // holds the key
    Node parentPointer; // pointer to the parentPointer
    Node left; // pointer to left child
    Node right; // pointer to right child
    int color; // 1 . Red, 0 . Black
    int exec_time;
    int total_time;
    
    Node(int key,int tots){
        buildingNum=key;
        total_time=tots;
        exec_time=0;
        color=1;
    }
    Node(){}
}

class WayneEnterprise{
   
   static int globalTime=0;
	public static void main(String [] args) throws FileNotFoundException{
         Queue<Assignment> construct=new LinkedList<>();
        RBT bst = new RBT();
        Heap h=new Heap();
    
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
                int commaindex = contentLine.indexOf(',');
                int closeindex = contentLine.indexOf(')');
                if(i!=-1)
                {
                    gt = contentLine.substring(0,gti);
                  
                    command = contentLine.substring(gti+2,i);
          
                    if(command.equalsIgnoreCase("Insert"))
                    {

                        builnum = contentLine.substring(i+1,commaindex);
                        
                        totalT = contentLine.substring(commaindex+1,closeindex);
                        
                         Assignment new_proj=new Assignment(Integer.valueOf(gt),"Insert",Integer.valueOf(builnum),Integer.valueOf(totalT));
                         construct.add(new_proj);
                        
                    }
                    else{//Print
                        if(commaindex!=-1) // 2 parameters (range)
                        {
                            builnum1 = contentLine.substring(i+1,commaindex);
                         
                            builnum2 = contentLine.substring(commaindex+1,closeindex);
                            Assignment new_proj=new Assignment(Integer.valueOf(gt),"Print",Integer.valueOf(builnum1),Integer.valueOf(builnum2));
                            construct.add(new_proj);
                            
                        }
                        else //print one triplet
                        {
                            builnum = contentLine.substring(i+1,closeindex);
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
        x.exec_time=0;
        h.insert(x);
        bst.insert(x);
        
      startConstruction(construct,bst,h);

}

    static void startConstruction(Queue<Assignment> construct,RBT bst,Heap h){
        Queue<Node> wait=new LinkedList<>();
        int upcomingProject=-1;
        
   
         while(h.h[1]!=null){
          
            Node current=h.h[1];
            if(!construct.isEmpty())
                upcomingProject=construct.peek().globaltime;

            boolean Over=false;
            for(int i=0;i<5;i++){


                if(current.exec_time!=current.total_time)
                {
                  current.exec_time++;
                  globalTime++;
                }

               if(current.exec_time==current.total_time)
                {
                    if(globalTime==upcomingProject){

                 if(construct.peek().type.equalsIgnoreCase("Insert")){
                  Assignment tp=construct.poll();
                      Node bb=new Node(tp.buildingNo,tp.total_time);
                      bb.exec_time=0;
                     bst.insert(bb);
                     wait.add(bb);

                }
                  else{
                       Assignment p = construct.poll();
                       System.out.print(globalTime+" ");
                        if(p.y==-1)
                          bst.print(p.x);
                        else
                         bst.printRange(p.x,p.y);

                        }
                
                    
                upcomingProject=construct.peek()!=null?construct.peek().globaltime: -1;
               
            }
                  
                    System.out.println(current.buildingNum+" "+ globalTime);

                 
                  bst.deleteNode(current.buildingNum);
                  h.remove();
                

                    if(globalTime==upcomingProject){

                 if(construct.peek().type.equalsIgnoreCase("Insert")){
                  Assignment tp=construct.poll();
                      Node bb=new Node(tp.buildingNo,tp.total_time);
                    bb.exec_time=1;
                     bst.insert(bb);
                     wait.add(bb);

                }
                  else{
                       Assignment p = construct.poll();
                       System.out.print(globalTime+" ");
                        if(p.y==-1)
                          bst.print(p.x);
                        else
                         bst.printRange(p.x,p.y);

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
                     bb.exec_time=1;
                     bst.insert(bb);
                     wait.add(bb);

                }
                  else{
                       Assignment p = construct.poll();
                       System.out.print(globalTime+" ");
                        if(p.y==-1)
                          bst.print(p.x);
                        else
                         bst.printRange(p.x,p.y);

                        }
                
                upcomingProject=construct.peek()!=null?construct.peek().globaltime: -1;
               
            }


           
                   

    }
    if(!Over){
       h.remove();
       h.insert(current);
    }
    

    while(!wait.isEmpty())
    {
        Node assigned = wait.poll();
       
        h.insert(assigned);
    }


    
}

      h.print();
}

}




    /// HEAP ///
    /// HEAP ///
    /// HEAP ///
    /// HEAP ///
    /// HEAP ///
    /// HEAP ///






class Heap  {
     Node h[]=new Node[2000];
     Node dummy;
    
    int cursize;
    Heap(){
      dummy=new Node(Integer.MIN_VALUE,Integer.MIN_VALUE);
      h[0] = dummy;
      h[0].exec_time= -1;
      cursize=1;

    }
     void insert(Node key)
    
    {

        h[cursize++]=key;
        int last=cursize-1;
    
        //minheapify(1);
     while( h[last].exec_time < h[parentPointer(last)].exec_time)
        {
            swap(last,parentPointer(last));
            last=parentPointer(last);
        }

        while(h[last].exec_time==h[parentPointer(last)].exec_time){
          if(h[last].buildingNum<h[parentPointer(last)].buildingNum)
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
        Node ele=h[index];
        Node min;
        int min_i=0;
        if(isLeaf(index))
            return;
        if(isRcThere(index)){
            if(ele.exec_time>h[leftChild(index)].exec_time||ele.exec_time>h[rightChild(index)].exec_time){
            if(h[leftChild(index)].exec_time<h[rightChild(index)].exec_time){
              min=h[leftChild(index)];
                min_i=leftChild(index);
                swap(min_i,index);
                minheapify(min_i);
            }
            else if(h[leftChild(index)].exec_time>h[rightChild(index)].exec_time){
                min=h[rightChild(index)];
                min_i=rightChild(index);
                swap(min_i,index);
                minheapify(min_i); 
            }


            else if(h[leftChild(index)].exec_time==h[(rightChild(index))].exec_time){

              if(h[leftChild(index)].buildingNum<h[rightChild(index)].buildingNum){
                min=h[leftChild(index)];
                     min_i=leftChild(index);
                     swap(min_i,index);
            minheapify(min_i);
                }
              else{
                min=h[rightChild(index)];
                    min_i=rightChild(index);
                    swap(min_i,index);
            minheapify(min_i);
                }
            }
        }
        else if(ele.exec_time==h[leftChild(index)].exec_time&&ele.exec_time==h[rightChild(index)].exec_time)
        {
                if(h[leftChild(index)].buildingNum<h[rightChild(index)].buildingNum && ele.buildingNum>h[leftChild(index)].buildingNum){
                    min=h[leftChild(index)];
                     min_i=leftChild(index);
                     swap(min_i,index);
            minheapify(min_i);
                }
                else if(h[leftChild(index)].buildingNum>h[rightChild(index)].buildingNum && ele.buildingNum>h[rightChild(index)].buildingNum){
                    min=h[rightChild(index)];
                    min_i=rightChild(index);
                    swap(min_i,index);
            minheapify(min_i);
                }
        }
        else if(ele.exec_time==h[leftChild(index)].exec_time)
        {
                if(ele.buildingNum>h[leftChild(index)].buildingNum){
                    min=h[leftChild(index)];
                     min_i=leftChild(index);
                     swap(min_i,index);
                     minheapify(min_i);
                }
        }
        else if(ele.exec_time==h[rightChild(index)].exec_time)
        {
                if(ele.buildingNum>h[rightChild(index)].buildingNum){
                    min=h[rightChild(index)];
                     min_i=rightChild(index);
                     swap(min_i,index);
            minheapify(min_i);
                }
        }
    }


        else if(ele.exec_time>h[leftChild(index)].exec_time){
            min=h[leftChild(index)];
             min_i=leftChild(index);
             swap(min_i,index);
            minheapify(min_i);
        }
        else if(ele.exec_time==h[leftChild(index)].exec_time){
            if(ele.buildingNum>h[leftChild(index)].buildingNum){
                    min=h[leftChild(index)];
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
        Node temp=h[child];
        h[child]=h[parentPointer];
        h[parentPointer]=temp;
        
    }
     void print()
    {
        System.out.println("***********************************************");
        for(int i=1;i<cursize;i++){
            Node x=h[i];
            System.out.println(x.buildingNum+" "+ x.exec_time+" "+ x.total_time);
        }
        System.out.println("***********************************");
    }

    Node remove()
    {
        Node r=h[1];

        if(cursize>2){
        h[1]=h[cursize-1];
        cursize--;
        minheapify(1);
    }
    else{
      h[1]=null;
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
    while (x != root && x.color == 0) {
      if (x == x.parentPointer.left) {
        s = x.parentPointer.right;
        if (s.color == 1) {
          // case 3.1
          s.color = 0;
          x.parentPointer.color = 1;
          leftRotate(x.parentPointer);
          s = x.parentPointer.right;
        }

        if (s.left.color == 0 && s.right.color == 0) {
          // case 3.2
          s.color = 1;
          x = x.parentPointer;
        } else {
          if (s.right.color == 0) {
            // case 3.3
            s.left.color = 0;
            s.color = 1;
            rightRotate(s);
            s = x.parentPointer.right;
          } 

          // case 3.4
          s.color = x.parentPointer.color;
          x.parentPointer.color = 0;
          s.right.color = 0;
          leftRotate(x.parentPointer);
          x = root;
        }
      } else {
        s = x.parentPointer.left;
        if (s.color == 1) {
          // case 3.1
          s.color = 0;
          x.parentPointer.color = 1;
          rightRotate(x.parentPointer);
          s = x.parentPointer.left;
        }

        if (s.right.color == 0 && s.right.color == 0) {
          // case 3.2
          s.color = 1;
          x = x.parentPointer;
        } else {
          if (s.left.color == 0) {
            // case 3.3
            s.right.color = 0;
            s.color = 1;
            leftRotate(s);
            s = x.parentPointer.left;
          } 

          // case 3.4
          s.color = x.parentPointer.color;
          x.parentPointer.color = 0;
          s.left.color = 0;
          rightRotate(x.parentPointer);
          x = root;
        }
      } 
    }
    x.color = 0;
  }


  private void rbTransplant(Node u, Node v){
    if (u.parentPointer == null) {
      root = v;
    } else if (u == u.parentPointer.left){
      u.parentPointer.left = v;
    } else {
      u.parentPointer.right = v;
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
        node = node.right;
      } else {
        node = node.left;
      }
    }

    if (z == Ext) {
      System.out.println("Couldn't find key in the tree");
      return;
    } 

    y = z;
    int yOriginalColor = y.color;
    if (z.left == Ext) {
      x = z.right;
      rbTransplant(z, z.right);
    } else if (z.right == Ext) {
      x = z.left;
      rbTransplant(z, z.left);
    } 
    else {
      y = minimum(z.right);
      yOriginalColor = y.color;
      x = y.right;
      if (y.parentPointer == z) {
        x.parentPointer = y;
      } else {
        rbTransplant(y, y.right);
        y.right = z.right;
        y.right.parentPointer = y;
      }

      rbTransplant(z, y);
      y.left = z.left;
      y.left.parentPointer = y;
      y.color = z.color;
    }
    if (yOriginalColor == 0){
      fixDelete(x);
    }
  }


  
  // fix the red-black tree
  private void fixInsert(Node k){
    Node u;
    while (k.parentPointer.color == 1) {
      if (k.parentPointer == k.parentPointer.parentPointer.right) {
        u = k.parentPointer.parentPointer.left; // uncle
        if (u.color == 1) {
          // case 3.1
          u.color = 0;
          k.parentPointer.color = 0;
          k.parentPointer.parentPointer.color = 1;
          k = k.parentPointer.parentPointer;
        } else {
          if (k == k.parentPointer.left) {
            // case 3.2.2
            k = k.parentPointer;
            rightRotate(k);
          }
          // case 3.2.1
          k.parentPointer.color = 0;
          k.parentPointer.parentPointer.color = 1;
          leftRotate(k.parentPointer.parentPointer);
        }
      } else {
        u = k.parentPointer.parentPointer.right; // uncle

        if (u.color == 1) {
          // mirror case 3.1
          u.color = 0;
          k.parentPointer.color = 0;
          k.parentPointer.parentPointer.color = 1;
          k = k.parentPointer.parentPointer;  
        } else {
          if (k == k.parentPointer.right) {
            // mirror case 3.2.2
            k = k.parentPointer;
            leftRotate(k);
          }
          // mirror case 3.2.1
          k.parentPointer.color = 0;
          k.parentPointer.parentPointer.color = 1;
          rightRotate(k.parentPointer.parentPointer);
        }
      }
      if (k == root) {
        break;
      }
    }
    root.color = 0;
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
            
           String sColor = root.color == 1?"RED":"BLACK";
       System.out.println(root.buildingNum+" "+root.total_time+" "+root.exec_time + "(" + sColor + ")");
       printHelper(root.left, indent, false);
       printHelper(root.right, indent, true);
    }
  }


  
  
  // find the node with the minimum key
  public Node minimum(Node node) {
    while (node.left != Ext) {
      node = node.left;
    }
    return node;
  }

  // find the node with the maximum key
  public Node maximum(Node node) {
    while (node.right != Ext) {
      node = node.right;
    }
    return node;
  }

  // find the successor of a given node
  public Node successor(Node x) {
    // if the right subtree is not null,
    // the successor is the leftmost node in the
    // right subtree
    if (x.right != Ext) {
      return minimum(x.right);
    }

    // else it is the lowest ancestor of x whose
    // left child is also an ancestor of x.
    Node y = x.parentPointer;
    while (y != Ext && x == y.right) {
      x = y;
      y = y.parentPointer;
    }
    return y;
  }

  // find the predecessor of a given node
  

  // rotate left at node x
  public void leftRotate(Node x) {
    Node y = x.right;
    x.right = y.left;
    if (y.left != Ext) {
      y.left.parentPointer = x;
    }
    y.parentPointer = x.parentPointer;
    if (x.parentPointer == null) {
      this.root = y;
    } else if (x == x.parentPointer.left) {
      x.parentPointer.left = y;
    } else {
      x.parentPointer.right = y;
    }
    y.left = x;
    x.parentPointer = y;
  }

  // rotate right at node x
  public void rightRotate(Node x) {
    Node y = x.left;
    x.left = y.right;
    if (y.right != Ext) {
      y.right.parentPointer = x;
    }
    y.parentPointer = x.parentPointer;
    if (x.parentPointer == null) {
      this.root = y;
    } else if (x == x.parentPointer.right) {
      x.parentPointer.right = y;
    } else {
      x.parentPointer.left = y;
    }
    y.right = x;
    x.parentPointer = y;
  }

  // insert the key to the tree in its appropriate position
  // and fix the tree
  public void insert(Node node) {
    // Ordinary Binary Search Insertion
    
    
    node.exec_time=0;
    node.left=Ext;
    node.right=Ext;
    node.parentPointer=null;
    node.color=1;
    
     // new node must be red

    Node y = null;
    Node x = this.root;

    while (x != Ext) {
      y = x;
      if (node.buildingNum < x.buildingNum) {
        x = x.left;
      } else {
        x = x.right;
      }
    }

    // y is parentPointer of x
    node.parentPointer = y;
    if (y == null) {
      root = node;
    } else if (node.buildingNum < y.buildingNum) {
      y.left = node;
    } else {
      y.right = node;
    }

    // if new node is a root node, simply return
    if (node.parentPointer == null){
      node.color = 0;
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
       System.out.print("("+cur.buildingNum+" "+ cur.exec_time+" "+ cur.total_time+") ");
        System.out.println();
        return;
      }
      else if(buildingNo>cur.buildingNum)
        cur=cur.right;
      else
        cur=cur.left;
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
        cur=cur.right;
      }
      else{
        cur=cur.left;
      }
    }
  }

  void recurPrint(Node cur,int x,int y){
    if(cur==Ext ) return;

    if(cur.buildingNum==x){
      recurPrint(cur.right,x,y);
      System.out.print("("+cur.buildingNum+" "+ cur.exec_time+" "+ cur.total_time+") ");
    }
    else if(cur.buildingNum==y){
      recurPrint(cur.left,x,y);
      System.out.print("("+cur.buildingNum+" "+ cur.exec_time+" "+ cur.total_time+") ");
    
    }
    else
    {
      if(cur.left!=Ext && inRange( cur.left.buildingNum, x,y)){
      recurPrint(cur.left,x,y);
      } 
      System.out.print("("+cur.buildingNum+" "+ cur.exec_time+" "+ cur.total_time+") ");
      if(cur.right!=Ext && inRange(cur.right.buildingNum,x,y))
        recurPrint(cur.right,x,y);
    }
  }

  boolean inRange(int target,int l,int h){
    if(target>=l && target<=h)
      return true;
    return false;
  }

  public RBT() {
    Ext = new Node();
    Ext.buildingNum=-1;
    Ext.color = 0;
    Ext.left = null;
    Ext.right = null;
    root = Ext;
  }





  
}



