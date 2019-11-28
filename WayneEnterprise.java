import java.io.*;
import java.util.*;

class Project{
    String type;
    int bno;
    int globaltime;
    int total_time;
    int b1,b2;
    Project(int gt,String command,int b,int t){//insert
        globaltime=gt;
        if(command.equalsIgnoreCase("Insert")){
        type=command;
        bno=b;
        total_time=t;
    }
    else{
        if(t==-1){
            type=command;
            b1=b;
            b2=-1;
        }
        else{
            type=command;
            b1=b;
            b2=t;
        }
    }
    }
}

class Node {
    int buildingno; // holds the key
    Node parent; // pointer to the parent
    Node left; // pointer to left child
    Node right; // pointer to right child
    int color; // 1 . Red, 0 . Black
    int exec_time;
    int total_time;
    
    Node(int key,int tots){
        buildingno=key;
        total_time=tots;
        exec_time=0;
        color=1;
    }
    Node(){}
}

class WayneEnterprise{
   
   static int globalTime=0;
	public static void main(String [] args) throws FileNotFoundException{
         Queue<Project> construct=new LinkedList<>();
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
                        
                         Project new_proj=new Project(Integer.valueOf(gt),"Insert",Integer.valueOf(builnum),Integer.valueOf(totalT));
                         construct.add(new_proj);
                        
                    }
                    else{//Print
                        if(commaindex!=-1) // 2 parameters (range)
                        {
                            builnum1 = contentLine.substring(i+1,commaindex);
                         
                            builnum2 = contentLine.substring(commaindex+1,closeindex);
                            Project new_proj=new Project(Integer.valueOf(gt),"Print",Integer.valueOf(builnum1),Integer.valueOf(builnum2));
                            construct.add(new_proj);
                            //bw.write("TotalTime " + totalT + "\n");
                           // System.out.println("BuildingID2 " + builnum2 + "\n");
                        }
                        else //print one triplet
                        {
                            builnum = contentLine.substring(i+1,closeindex);
                            //bw.write(" BuildingID " + builnum);
                             Project new_proj=new Project(Integer.valueOf(gt),"Print",Integer.valueOf(builnum),-1);
                            construct.add(new_proj);
                           // System.out.println("BuildingID " + builnum);
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
       Project in=construct.poll();
        //System.out.println(in.bno+" "+ in.total_time);
        Node x=new Node(in.bno,in.total_time);
        globalTime=0;
        x.exec_time=0;
        h.insert(x);
        bst.insert(x);
        
      startConstruction(construct,bst,h);

}

    static void startConstruction(Queue<Project> construct,RBT bst,Heap h){
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
                  Project tp=construct.poll();
                      Node bb=new Node(tp.bno,tp.total_time);
                      bb.exec_time=0;
                     bst.insert(bb);
                     wait.add(bb);

                }
                  else{
                       Project p = construct.poll();
                       System.out.print(globalTime+" ");
                        if(p.b2==-1)
                          bst.print(p.b1);
                        else
                         bst.printRange(p.b1,p.b2);

                        }
                
                    
                upcomingProject=construct.peek()!=null?construct.peek().globaltime: -1;
               
            }
                  
                    System.out.println(current.buildingno+" "+ globalTime);

                 
                  bst.deleteNode(current.buildingno);
                  h.remove();
                

                    if(globalTime==upcomingProject){

                 if(construct.peek().type.equalsIgnoreCase("Insert")){
                  Project tp=construct.poll();
                      Node bb=new Node(tp.bno,tp.total_time);
                    bb.exec_time=1;
                     bst.insert(bb);
                     wait.add(bb);

                }
                  else{
                       Project p = construct.poll();
                       System.out.print(globalTime+" ");
                        if(p.b2==-1)
                          bst.print(p.b1);
                        else
                         bst.printRange(p.b1,p.b2);

                        }
                
                        
                upcomingProject=construct.peek()!=null?construct.peek().globaltime: -1;
               
            }





                  //---------------------------------------------------------

                
                 
                   Over=true;
                    
                   break;

                }

               if(globalTime==upcomingProject){

                 if(construct.peek().type.equalsIgnoreCase("Insert")){
                  Project tp=construct.poll();
                      Node bb=new Node(tp.bno,tp.total_time);
                     bb.exec_time=1;
                     bst.insert(bb);
                     wait.add(bb);

                }
                  else{
                       Project p = construct.poll();
                       System.out.print(globalTime+" ");
                        if(p.b2==-1)
                          bst.print(p.b1);
                        else
                         bst.printRange(p.b1,p.b2);

                        }
                
                   // h.print();    
                upcomingProject=construct.peek()!=null?construct.peek().globaltime: -1;
               
            }


           // if(!Over){
             // current.exec_time++;
             // globalTime++;
              
          //}


          //else{

            //break;
          //}
          

       // System.out.print(globalTime+" ");
                   //System.out.println(current.buildingno+" "+current.exec_time+" "+current.total_time);
                   

    }
    if(!Over){
       h.remove();
       h.insert(current);
    }
    

    while(!wait.isEmpty())
    {
        Node assigned = wait.poll();
       
       // Node x=new Node(assigned.bno,assigned.total_time);
        h.insert(assigned);
       // bst.insert(x);
    }


    //globalTime++;
    
}
//globalTime++;
//System.out.println("------------------------");
      h.print();
   // System.out.println("------------------------");
}



        /*while(!construct.isEmpty()){
           if(globalTime==construct.peek().globaltime){
                    Project cur=construct.poll();

                    if(cur.type.equalsIgnoreCase("Insert")){
                         Node current=new Node(cur.bno,cur.total_time);
                         h.insert(cur);
                         bst.insert(cur);
                     
              



                        

                    }
                    else{//print command
                        if(cur.b2==-1){
                            bst.print(cur.b1);
                        }
                        else{
                            bst.printRange(cur.b1,cur.b2);
                        }
                    }
                }

            
            //System.out.println("-----------");
           //h.print();
            //bst.prettyPrint();
            globalTime++;
        }*/
    }




    /// HEAP ///
    /// HEAP ///
    /// HEAP ///
    /// HEAP ///
    /// HEAP ///
    /// HEAP ///






class Heap  {
     Node h[]=new Node[2000];
    // h[0]=new Building(-1,Integer.MIN_VALUE,0);
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
        /*if(h.size()==10)
        {
            if(h.get(0)<key)
            {
            remove(key);
            return;
            }
            else
                return;
        }*/
        h[cursize++]=key;
        int last=cursize-1;
    
        //minheapify(1);
     while( h[last].exec_time < h[parent(last)].exec_time)
        {
          //  System.out.println("he");
            swap(last,parent(last));
            last=parent(last);
           // minheapify(last);
        }

        while(h[last].exec_time==h[parent(last)].exec_time){
          if(h[last].buildingno<h[parent(last)].buildingno)
          {
               
            swap(last,parent(last));
                last=parent(last);
              
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

              if(h[leftChild(index)].buildingno<h[rightChild(index)].buildingno){
                    //System.out.println("Dsffd");
                min=h[leftChild(index)];
                     min_i=leftChild(index);
                     swap(min_i,index);
            minheapify(min_i);
                }
              else{
                    //System.out.println("Dsfdsfdsffd");
                min=h[rightChild(index)];
                    min_i=rightChild(index);
                    swap(min_i,index);
            minheapify(min_i);
                }
            }
        }
        else if(ele.exec_time==h[leftChild(index)].exec_time&&ele.exec_time==h[rightChild(index)].exec_time)
        {
           // System.out.println("DSFSDFsdfdsdfdsfds");
                if(h[leftChild(index)].buildingno<h[rightChild(index)].buildingno && ele.buildingno>h[leftChild(index)].buildingno){
                    min=h[leftChild(index)];
                     min_i=leftChild(index);
                     swap(min_i,index);
            minheapify(min_i);
                }
                else if(h[leftChild(index)].buildingno>h[rightChild(index)].buildingno && ele.buildingno>h[rightChild(index)].buildingno){
                    min=h[rightChild(index)];
                    min_i=rightChild(index);
                    swap(min_i,index);
            minheapify(min_i);
                }
        }
        else if(ele.exec_time==h[leftChild(index)].exec_time)
        {
                if(ele.buildingno>h[leftChild(index)].buildingno){
                    min=h[leftChild(index)];
                     min_i=leftChild(index);
                     swap(min_i,index);
                     minheapify(min_i);
                }
        }
        else if(ele.exec_time==h[rightChild(index)].exec_time)
        {
                if(ele.buildingno>h[rightChild(index)].buildingno){
                    min=h[rightChild(index)];
                     min_i=rightChild(index);
                     swap(min_i,index);
            minheapify(min_i);
                }
        }
    }

/*            else
            {
               min=h[rightChild(index)];
                 min_i=rightChild(index);
            } */
        else if(ele.exec_time>h[leftChild(index)].exec_time){
            min=h[leftChild(index)];
             min_i=leftChild(index);
             swap(min_i,index);
            minheapify(min_i);
        }
        else if(ele.exec_time==h[leftChild(index)].exec_time){
            if(ele.buildingno>h[leftChild(index)].buildingno){
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
     int parent(int pos)
    {

        int p=(pos)/2;
        if(p<0)
            return 0;
        return p;
    }
     void swap(int child,int parent)
    {
        Node temp=h[child];
        h[child]=h[parent];
        h[parent]=temp;
        
    }
     void print()
    {
        System.out.println("***********************************************");
        for(int i=1;i<cursize;i++){
            Node x=h[i];
            System.out.println(x.buildingno+" "+ x.exec_time+" "+ x.total_time);
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
      if (x == x.parent.left) {
        s = x.parent.right;
        if (s.color == 1) {
          // case 3.1
          s.color = 0;
          x.parent.color = 1;
          leftRotate(x.parent);
          s = x.parent.right;
        }

        if (s.left.color == 0 && s.right.color == 0) {
          // case 3.2
          s.color = 1;
          x = x.parent;
        } else {
          if (s.right.color == 0) {
            // case 3.3
            s.left.color = 0;
            s.color = 1;
            rightRotate(s);
            s = x.parent.right;
          } 

          // case 3.4
          s.color = x.parent.color;
          x.parent.color = 0;
          s.right.color = 0;
          leftRotate(x.parent);
          x = root;
        }
      } else {
        s = x.parent.left;
        if (s.color == 1) {
          // case 3.1
          s.color = 0;
          x.parent.color = 1;
          rightRotate(x.parent);
          s = x.parent.left;
        }

        if (s.right.color == 0 && s.right.color == 0) {
          // case 3.2
          s.color = 1;
          x = x.parent;
        } else {
          if (s.left.color == 0) {
            // case 3.3
            s.right.color = 0;
            s.color = 1;
            leftRotate(s);
            s = x.parent.left;
          } 

          // case 3.4
          s.color = x.parent.color;
          x.parent.color = 0;
          s.left.color = 0;
          rightRotate(x.parent);
          x = root;
        }
      } 
    }
    x.color = 0;
  }


  private void rbTransplant(Node u, Node v){
    if (u.parent == null) {
      root = v;
    } else if (u == u.parent.left){
      u.parent.left = v;
    } else {
      u.parent.right = v;
    }
    v.parent = u.parent;
  }

  private void deleteNodeUtil(Node node, int key) {
    // find the node containing key
    Node z = Ext;
    Node x, y;
    while (node != Ext){
      //System.out.println("hey");
      if (node.buildingno == key) {
        z = node;
      }

      if (node.buildingno <key) {
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
      if (y.parent == z) {
        x.parent = y;
      } else {
        rbTransplant(y, y.right);
        y.right = z.right;
        y.right.parent = y;
      }

      rbTransplant(z, y);
      y.left = z.left;
      y.left.parent = y;
      y.color = z.color;
    }
    if (yOriginalColor == 0){
      fixDelete(x);
    }
  }


  
  // fix the red-black tree
  private void fixInsert(Node k){
    Node u;
    while (k.parent.color == 1) {
      if (k.parent == k.parent.parent.right) {
        u = k.parent.parent.left; // uncle
        if (u.color == 1) {
          // case 3.1
          u.color = 0;
          k.parent.color = 0;
          k.parent.parent.color = 1;
          k = k.parent.parent;
        } else {
          if (k == k.parent.left) {
            // case 3.2.2
            k = k.parent;
            rightRotate(k);
          }
          // case 3.2.1
          k.parent.color = 0;
          k.parent.parent.color = 1;
          leftRotate(k.parent.parent);
        }
      } else {
        u = k.parent.parent.right; // uncle

        if (u.color == 1) {
          // mirror case 3.1
          u.color = 0;
          k.parent.color = 0;
          k.parent.parent.color = 1;
          k = k.parent.parent;  
        } else {
          if (k == k.parent.right) {
            // mirror case 3.2.2
            k = k.parent;
            leftRotate(k);
          }
          // mirror case 3.2.1
          k.parent.color = 0;
          k.parent.parent.color = 1;
          rightRotate(k.parent.parent);
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
       System.out.println(root.buildingno+" "+root.total_time+" "+root.exec_time + "(" + sColor + ")");
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
    Node y = x.parent;
    while (y != Ext && x == y.right) {
      x = y;
      y = y.parent;
    }
    return y;
  }

  // find the predecessor of a given node
  

  // rotate left at node x
  public void leftRotate(Node x) {
    Node y = x.right;
    x.right = y.left;
    if (y.left != Ext) {
      y.left.parent = x;
    }
    y.parent = x.parent;
    if (x.parent == null) {
      this.root = y;
    } else if (x == x.parent.left) {
      x.parent.left = y;
    } else {
      x.parent.right = y;
    }
    y.left = x;
    x.parent = y;
  }

  // rotate right at node x
  public void rightRotate(Node x) {
    Node y = x.left;
    x.left = y.right;
    if (y.right != Ext) {
      y.right.parent = x;
    }
    y.parent = x.parent;
    if (x.parent == null) {
      this.root = y;
    } else if (x == x.parent.right) {
      x.parent.right = y;
    } else {
      x.parent.left = y;
    }
    y.right = x;
    x.parent = y;
  }

  // insert the key to the tree in its appropriate position
  // and fix the tree
  public void insert(Node node) {
    // Ordinary Binary Search Insertion
    
    
    node.exec_time=0;
    node.left=Ext;
    node.right=Ext;
    node.parent=null;
    node.color=1;
    
     // new node must be red

    Node y = null;
    Node x = this.root;

    while (x != Ext) {
      y = x;
      if (node.buildingno < x.buildingno) {
        x = x.left;
      } else {
        x = x.right;
      }
    }

    // y is parent of x
    node.parent = y;
    if (y == null) {
      root = node;
    } else if (node.buildingno < y.buildingno) {
      y.left = node;
    } else {
      y.right = node;
    }

    // if new node is a root node, simply return
    if (node.parent == null){
      node.color = 0;
      return;
    }

    // if the grandparent is null, simply return
    if (node.parent.parent == null) {
      return;
    }

    // Fix the tree
    fixInsert(node);
  }

  

  // delete the node from the tree
  public void deleteNode(int buildingno) {
    deleteNodeUtil(this.root, buildingno);
  }

  // print the tree structure on the screen
  
  void print(int bno){
    Node cur=root;
    while(cur!=null){
      if(cur.buildingno == bno){
       System.out.print("("+cur.buildingno+" "+ cur.exec_time+" "+ cur.total_time+") ");
        System.out.println();
        return;
      }
      else if(bno>cur.buildingno)
        cur=cur.right;
      else
        cur=cur.left;
    }
  }

  void printRange(int b1,int b2)
  {
    Node cur=root;
    while(cur!=null){
      if(cur.buildingno>=b1 && cur.buildingno<=b2){
        recurPrint(cur,b1,b2);
        System.out.println();
        return;
      }
      else if(cur.buildingno<b1){
        cur=cur.right;
      }
      else{
        cur=cur.left;
      }
    }
  }

  void recurPrint(Node cur,int b1,int b2){
    if(cur==Ext ) return;

    if(cur.buildingno==b1){
      recurPrint(cur.right,b1,b2);
      System.out.print("("+cur.buildingno+" "+ cur.exec_time+" "+ cur.total_time+") ");
    }
    else if(cur.buildingno==b2){
      recurPrint(cur.left,b1,b2);
      System.out.print("("+cur.buildingno+" "+ cur.exec_time+" "+ cur.total_time+") ");
    
    }
    else
    {
      if(cur.left!=Ext && inRange( cur.left.buildingno, b1,b2)){
      recurPrint(cur.left,b1,b2);
      } 
      System.out.print("("+cur.buildingno+" "+ cur.exec_time+" "+ cur.total_time+") ");
      if(cur.right!=Ext && inRange(cur.right.buildingno,b1,b2))
        recurPrint(cur.right,b1,b2);
    }
  }

  boolean inRange(int target,int l,int h){
    if(target>=l && target<=h)
      return true;
    return false;
  }

  public RBT() {
    Ext = new Node();
    Ext.buildingno=-1;
    Ext.color = 0;
    Ext.left = null;
    Ext.right = null;
    root = Ext;
  }





  
}



