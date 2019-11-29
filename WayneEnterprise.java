import java.io.*;
import java.util.*;

class Assignment{
    String type;
    int buildingNo;
    int global_time;
    int total_time;
    int x,y;
    Assignment(int gTime,String command,int a,int s){
        global_time=gTime;
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
    int buildingNum; 
    Node parentPointer; 
    Node leftPointer; 
    Node rightPointer; 
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
         Queue<Assignment> build=new LinkedList<>();
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
          
           
            String Line = br.readLine();
           ;
            String gTime = " ", command = " ", totalT = " ";
            String bNumber =" ", bNumber1 = " ", bNumber2 = " ";
            while (Line != null) {
                int gti = Line.indexOf(':');
                int i = Line.indexOf('(');
                int comma = Line.indexOf(',');
                int close = Line.indexOf(')');
                if(i!=-1)
                {
                    gTime = Line.substring(0,gti);
                  
                    command = Line.substring(gti+2,i);
          
                    if(command.equalsIgnoreCase("Insert"))
                    {

                        bNumber = Line.substring(i+1,comma);
                        
                        totalT = Line.substring(comma+1,close);
                        
                         Assignment new_proj=new Assignment(Integer.valueOf(gTime),"Insert",Integer.valueOf(bNumber),Integer.valueOf(totalT));
                         build.add(new_proj);
                        
                    }
                    else{//Print
                        if(comma!=-1) // 2 parameters (range)
                        {
                            bNumber1 = Line.substring(i+1,comma);
                         
                            bNumber2 = Line.substring(comma+1,close);
                            Assignment new_proj=new Assignment(Integer.valueOf(gTime),"Print",Integer.valueOf(bNumber1),Integer.valueOf(bNumber2));
                            build.add(new_proj);
                            
                        }
                        else //print one triplet
                        {
                            bNumber = Line.substring(i+1,close);
                             Assignment new_proj=new Assignment(Integer.valueOf(gTime),"Print",Integer.valueOf(bNumber),-1);
                            build.add(new_proj);
                        }
                    }
                  
                }
                Line = br.readLine();
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
       Assignment in=build.poll();
        Node x=new Node(in.buildingNo,in.total_time);
        globalTime=0;
        x.execution_time=0;
        heap.insert(x);
        rbTree.insert(x);
        
      construct(build,rbTree,heap);

}

    static void construct(Queue<Assignment> build,RBT rbTree,Heap heap){
        Queue<Node> holdQueue=new LinkedList<>();
        int nextProject=-1;
        
   
         while(heap.heap[1]!=null){
          
            Node currentNode=heap.heap[1];
            if(!build.isEmpty())
                nextProject=build.peek().global_time;

            boolean Over=false;
            for(int i=0;i<5;i++){


                if(currentNode.execution_time!=currentNode.total_time)
                {
                  currentNode.execution_time++;
                  globalTime++;
                }

               if(currentNode.execution_time==currentNode.total_time)
                {
                    if(globalTime==nextProject){

                 if(build.peek().type.equalsIgnoreCase("Insert")){
                  Assignment tp=build.poll();
                      Node xx=new Node(tp.buildingNo,tp.total_time);
                      xx.execution_time=0;
                     rbTree.insert(xx);
                     holdQueue.add(xx);

                }
                  else{
                       Assignment p = build.poll();
                       System.out.print(globalTime+" ");
                        if(p.y==-1)
                          rbTree.print(p.x);
                        else
                         rbTree.printRange(p.x,p.y);

                        }
                
                    
                nextProject=build.peek()!=null?build.peek().global_time: -1;
               
            }
                  
                    System.out.println(currentNode.buildingNum+" "+ globalTime);

                 
                  rbTree.nodeDelete(currentNode.buildingNum);
                  heap.remove();
                

                    if(globalTime==nextProject){

                 if(build.peek().type.equalsIgnoreCase("Insert")){
                  Assignment tp=build.poll();
                      Node xx=new Node(tp.buildingNo,tp.total_time);
                    xx.execution_time=1;
                     rbTree.insert(xx);
                     holdQueue.add(xx);

                }
                  else{
                       Assignment p = build.poll();
                       System.out.print(globalTime+" ");
                        if(p.y==-1)
                          rbTree.print(p.x);
                        else
                         rbTree.printRange(p.x,p.y);

                        }
                
                        
                nextProject=build.peek()!=null?build.peek().global_time: -1;
               
            }
                
                 
                   Over=true;
                    
                   break;

                }

               if(globalTime==nextProject){

                 if(build.peek().type.equalsIgnoreCase("Insert")){
                  Assignment tp=build.poll();
                      Node xx=new Node(tp.buildingNo,tp.total_time);
                     xx.execution_time=1;
                     rbTree.insert(xx);
                     holdQueue.add(xx);

                }
                  else{
                       Assignment p = build.poll();
                       System.out.print(globalTime+" ");
                        if(p.y==-1)
                          rbTree.print(p.x);
                        else
                         rbTree.printRange(p.x,p.y);

                        }
                
                nextProject=build.peek()!=null?build.peek().global_time: -1;
               
            }


           
                   

    }
    if(!Over){
       heap.remove();
       heap.insert(currentNode);
    }
    

    while(!holdQueue.isEmpty())
    {
        Node assigned = holdQueue.poll();
       
        heap.insert(assigned);
    }


    
}

      heap.print();
}

}


// // // /// /////// //// //// //// /// //// /// // // / ////////// // // /// /////// //// //// //// /// //// /// // // / ////////

// // // /// /////// //// //// //// // // // ///  HEAP  // // // /// /////// //// //// //// /// //// /// // // / //// /// ////////

// // // /// /////// //// //// //// /// //// /// // // / ////////// // // /// /////// //// //// //// /// //// /// // // / ////////


class Heap  {
     Node heap[]=new Node[2000];
     Node foo;
    
    int cursize;
    Heap(){
      foo=new Node(Integer.MIN_VALUE,Integer.MIN_VALUE);
      heap[0] = foo;
      heap[0].execution_time= -1;
      cursize=1;

    }
     void insert(Node key)
    
    {

        heap[cursize++]=key;
        int end=cursize-1;
    
        //min_heapify(1);
     while( heap[end].execution_time < heap[parentPointer(end)].execution_time)
        {
            swap(end,parentPointer(end));
            end=parentPointer(end);
        }

        while(heap[end].execution_time==heap[parentPointer(end)].execution_time){
          if(heap[end].buildingNum<heap[parentPointer(end)].buildingNum)
          {
               
            swap(end,parentPointer(end));
                end=parentPointer(end);
              
          }
          else break;
        }
        min_heapify(1); 
      
    }
 
     void min_heapify(int index)
    
    {
        Node element=heap[index];
        Node min;
        int min_i=0;
        if(isLeaf(index))
            return;
        if(checkRC(index)){
            if(element.execution_time>heap[lChild(index)].execution_time||element.execution_time>heap[rChild(index)].execution_time){
            if(heap[lChild(index)].execution_time<heap[rChild(index)].execution_time){
              min=heap[lChild(index)];
                min_i=lChild(index);
                swap(min_i,index);
                min_heapify(min_i);
            }
            else if(heap[lChild(index)].execution_time>heap[rChild(index)].execution_time){
                min=heap[rChild(index)];
                min_i=rChild(index);
                swap(min_i,index);
                min_heapify(min_i); 
            }


            else if(heap[lChild(index)].execution_time==heap[(rChild(index))].execution_time){

              if(heap[lChild(index)].buildingNum<heap[rChild(index)].buildingNum){
                min=heap[lChild(index)];
                     min_i=lChild(index);
                     swap(min_i,index);
            min_heapify(min_i);
                }
              else{
                min=heap[rChild(index)];
                    min_i=rChild(index);
                    swap(min_i,index);
            min_heapify(min_i);
                }
            }
        }
        else if(element.execution_time==heap[lChild(index)].execution_time&&element.execution_time==heap[rChild(index)].execution_time)
        {
                if(heap[lChild(index)].buildingNum<heap[rChild(index)].buildingNum && element.buildingNum>heap[lChild(index)].buildingNum){
                    min=heap[lChild(index)];
                     min_i=lChild(index);
                     swap(min_i,index);
            min_heapify(min_i);
                }
                else if(heap[lChild(index)].buildingNum>heap[rChild(index)].buildingNum && element.buildingNum>heap[rChild(index)].buildingNum){
                    min=heap[rChild(index)];
                    min_i=rChild(index);
                    swap(min_i,index);
            min_heapify(min_i);
                }
        }
        else if(element.execution_time==heap[lChild(index)].execution_time)
        {
                if(element.buildingNum>heap[lChild(index)].buildingNum){
                    min=heap[lChild(index)];
                     min_i=lChild(index);
                     swap(min_i,index);
                     min_heapify(min_i);
                }
        }
        else if(element.execution_time==heap[rChild(index)].execution_time)
        {
                if(element.buildingNum>heap[rChild(index)].buildingNum){
                    min=heap[rChild(index)];
                     min_i=rChild(index);
                     swap(min_i,index);
            min_heapify(min_i);
                }
        }
    }


        else if(element.execution_time>heap[lChild(index)].execution_time){
            min=heap[lChild(index)];
             min_i=lChild(index);
             swap(min_i,index);
            min_heapify(min_i);
        }
        else if(element.execution_time==heap[lChild(index)].execution_time){
            if(element.buildingNum>heap[lChild(index)].buildingNum){
                    min=heap[lChild(index)];
                     min_i=lChild(index);
                     swap(min_i,index);
                     min_heapify(min_i);
                }   
        }
       

    }


     boolean checkRC(int p)
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
     int lChild(int p)
    {
        
        return 2*p;
    }
     int rChild(int p)
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
        min_heapify(1);
    }
    else{
      heap[1]=null;
      cursize=1;
    }
      

        return r;
    }
}
   

// // // /// /////// //// //// //// /// //// /// // // / ////////// // // /// /////// //// //// //// /// //// /// // // / ////////

// // // /// /////// //// //// //// // // // ///  RED BLACK TREE // // // /// /////// //// //// //// /// //// /// // // / //// ///

// // // /// /////// //// //// //// /// //// /// // // / ////////// // // /// /////// //// //// //// /// //// /// // // / ////////


// class RBT {
//   private Node root_node;
//   private Node ExternalNode;

// // fix the rb tree modified by the delete operation
//   private void Deleter(Node x) {
//     Node s;
//     while (x != root_node && x.nodeColor == 0) {
//       if (x == x.parentPointer.leftPointer) {
//         s = x.parentPointer.rightPointer;
//         if (s.nodeColor == 1) {
//           // case 3.1
//           s.nodeColor = 0;
//           x.parentPointer.nodeColor = 1;
//           rotate_left(x.parentPointer);
//           s = x.parentPointer.rightPointer;
//         }

//         if (s.leftPointer.nodeColor == 0 && s.rightPointer.nodeColor == 0) {
//           // case 3.2
//           s.nodeColor = 1;
//           x = x.parentPointer;
//         } else {
//           if (s.rightPointer.nodeColor == 0) {
//             // case 3.3
//             s.leftPointer.nodeColor = 0;
//             s.nodeColor = 1;
//             rotate_right(s);
//             s = x.parentPointer.rightPointer;
//           } 

//           // case 3.4
//           s.nodeColor = x.parentPointer.nodeColor;
//           x.parentPointer.nodeColor = 0;
//           s.rightPointer.nodeColor = 0;
//           rotate_left(x.parentPointer);
//           x = root_node;
//         }
//       } else {
//         s = x.parentPointer.leftPointer;
//         if (s.nodeColor == 1) {
//           // case 3.1
//           s.nodeColor = 0;
//           x.parentPointer.nodeColor = 1;
//           rotate_right(x.parentPointer);
//           s = x.parentPointer.leftPointer;
//         }

//         if (s.rightPointer.nodeColor == 0 && s.rightPointer.nodeColor == 0) {
//           // case 3.2
//           s.nodeColor = 1;
//           x = x.parentPointer;
//         } else {
//           if (s.leftPointer.nodeColor == 0) {
//             // case 3.3
//             s.rightPointer.nodeColor = 0;
//             s.nodeColor = 1;
//             rotate_left(s);
//             s = x.parentPointer.leftPointer;
//           } 

//           // case 3.4
//           s.nodeColor = x.parentPointer.nodeColor;
//           x.parentPointer.nodeColor = 0;
//           s.leftPointer.nodeColor = 0;
//           rotate_right(x.parentPointer);
//           x = root_node;
//         }
//       } 
//     }
//     x.nodeColor = 0;
//   }


//   private void treeTransplant(Node p, Node q){
//     if (p.parentPointer == null) {
//       root_node = q;
//     } else if (p == p.parentPointer.leftPointer){
//       p.parentPointer.leftPointer = q;
//     } else {
//       p.parentPointer.rightPointer = q;
//     }
//     q.parentPointer = p.parentPointer;
//   }

//   private void deleteKeyNode(Node node, int key) {
//     // find the node containing key
//     Node n = ExternalNode;
//     Node x, y;
//     while (node != ExternalNode){
//       if (node.buildingNum == key) {
//         n = node;
//       }

//       if (node.buildingNum <key) {
//         node = node.rightPointer;
//       } else {
//         node = node.leftPointer;
//       }
//     }

//     if (n == ExternalNode) {
//       System.out.println("Can't find key");
//       return;
//     } 

//     y = n;
//     int yOriginalColor = y.nodeColor;
//     if (n.leftPointer == ExternalNode) {
//       x = n.rightPointer;
//       treeTransplant(n, n.rightPointer);
//     } else if (n.rightPointer == ExternalNode) {
//       x = n.leftPointer;
//       treeTransplant(n, n.leftPointer);
//     } 
//     else {
//       y = minKey(n.rightPointer);
//       yOriginalColor = y.nodeColor;
//       x = y.rightPointer;
//       if (y.parentPointer == n) {
//         x.parentPointer = y;
//       } else {
//         treeTransplant(y, y.rightPointer);
//         y.rightPointer = n.rightPointer;
//         y.rightPointer.parentPointer = y;
//       }

//       treeTransplant(n, y);
//       y.leftPointer = n.leftPointer;
//       y.leftPointer.parentPointer = y;
//       y.nodeColor = n.nodeColor;
//     }
//     if (yOriginalColor == 0){
//       Deleter(x);
//     }
//   }


  
//   // fix the red-black tree
//   private void inserter(Node k){
//     Node p;
//     while (k.parentPointer.nodeColor == 1) {
//       if (k.parentPointer == k.parentPointer.parentPointer.rightPointer) {
//         p = k.parentPointer.parentPointer.leftPointer; // uncle
//         if (p.nodeColor == 1) {
//           // case 3.1
//           p.nodeColor = 0;
//           k.parentPointer.nodeColor = 0;
//           k.parentPointer.parentPointer.nodeColor = 1;
//           k = k.parentPointer.parentPointer;
//         } else {
//           if (k == k.parentPointer.leftPointer) {
//             // case 3.2.2
//             k = k.parentPointer;
//             rotate_right(k);
//           }
//           // case 3.2.1
//           k.parentPointer.nodeColor = 0;
//           k.parentPointer.parentPointer.nodeColor = 1;
//           rotate_left(k.parentPointer.parentPointer);
//         }
//       } else {
//         p = k.parentPointer.parentPointer.rightPointer; // uncle

//         if (p.nodeColor == 1) {
//           // mirror case 3.1
//           p.nodeColor = 0;
//           k.parentPointer.nodeColor = 0;
//           k.parentPointer.parentPointer.nodeColor = 1;
//           k = k.parentPointer.parentPointer;  
//         } else {
//           if (k == k.parentPointer.rightPointer) {
//             // mirror case 3.2.2
//             k = k.parentPointer;
//             rotate_left(k);
//           }
//           // mirror case 3.2.1
//           k.parentPointer.nodeColor = 0;
//           k.parentPointer.parentPointer.nodeColor = 1;
//           rotate_right(k.parentPointer.parentPointer);
//         }
//       }
//       if (k == root_node) {
//         break;
//       }
//     }
//     root_node.nodeColor = 0;
//   }

//   // find the node with the minKey key
//   public Node minKey(Node node) {
//     while (node.leftPointer != ExternalNode) {
//       node = node.leftPointer;
//     }
//     return node;
//   }

//   // find the node with the maximum key
//   public Node maximum(Node node) {
//     while (node.rightPointer != ExternalNode) {
//       node = node.rightPointer;
//     }
//     return node;
//   }
  

//   // rotate leftPointer at node x
//   public void rotate_left(Node x) {
//     Node y = x.rightPointer;
//     x.rightPointer = y.leftPointer;
//     if (y.leftPointer != ExternalNode) {
//       y.leftPointer.parentPointer = x;
//     }
//     y.parentPointer = x.parentPointer;
//     if (x.parentPointer == null) {
//       this.root_node = y;
//     } else if (x == x.parentPointer.leftPointer) {
//       x.parentPointer.leftPointer = y;
//     } else {
//       x.parentPointer.rightPointer = y;
//     }
//     y.leftPointer = x;
//     x.parentPointer = y;
//   }

//   // rotate rightPointer at node x
//   public void rotate_right(Node x) {
//     Node y = x.leftPointer;
//     x.leftPointer = y.rightPointer;
//     if (y.rightPointer != ExternalNode) {
//       y.rightPointer.parentPointer = x;
//     }
//     y.parentPointer = x.parentPointer;
//     if (x.parentPointer == null) {
//       this.root_node = y;
//     } else if (x == x.parentPointer.rightPointer) {
//       x.parentPointer.rightPointer = y;
//     } else {
//       x.parentPointer.leftPointer = y;
//     }
//     y.rightPointer = x;
//     x.parentPointer = y;
//   }

//   // insert the key to the tree in its appropriate position
//   // and fix the tree
//   public void insert(Node node) {
//     // Ordinary Binary Search Insertion
    
    
//     node.execution_time=0;
//     node.leftPointer=ExternalNode;
//     node.rightPointer=ExternalNode;
//     node.parentPointer=null;
//     node.nodeColor=1;
    
//      // new node must be red

//     Node y = null;
//     Node x = this.root_node;

//     while (x != ExternalNode) {
//       y = x;
//       if (node.buildingNum < x.buildingNum) {
//         x = x.leftPointer;
//       } else {
//         x = x.rightPointer;
//       }
//     }

//     // y is parentPointer of x
//     node.parentPointer = y;
//     if (y == null) {
//       root_node = node;
//     } else if (node.buildingNum < y.buildingNum) {
//       y.leftPointer = node;
//     } else {
//       y.rightPointer = node;
//     }

//     // if new node is a root_node node, simply return
//     if (node.parentPointer == null){
//       node.nodeColor = 0;
//       return;
//     }

//     // if the grandparent is null, simply return
//     if (node.parentPointer.parentPointer == null) {
//       return;
//     }

//     // Fix the tree
//     inserter(node);
//   }

  

//   // delete the node from the tree
//   public void nodeDelete(int buildingNum) {
//     deleteKeyNode(this.root_node, buildingNum);
//   }

//   // print the tree structure on the screen
  
//   void print(int buildingNo){
//     Node cur_ptr=root_node;
//     while(cur_ptr!=null){
//       if(cur_ptr.buildingNum == buildingNo){
//        System.out.print("("+cur_ptr.buildingNum+" "+ cur_ptr.execution_time+" "+ cur_ptr.total_time+") ");
//         System.out.println();
//         return;
//       }
//       else if(buildingNo>cur_ptr.buildingNum)
//         cur_ptr=cur_ptr.rightPointer;
//       else
//         cur_ptr=cur_ptr.leftPointer;
//     }
//   }

//   void printRange(int x,int y)
//   {
//     Node cur_ptr=root_node;
//     while(cur_ptr!=null){
//       if(cur_ptr.buildingNum>=x && cur_ptr.buildingNum<=y){
//         printAll(cur_ptr,x,y);
//         System.out.println();
//         return;
//       }
//       else if(cur_ptr.buildingNum<x){
//         cur_ptr=cur_ptr.rightPointer;
//       }
//       else{
//         cur_ptr=cur_ptr.leftPointer;
//       }
//     }
//   }

//   void printAll(Node cur_ptr,int x,int y){
//     if(cur_ptr==ExternalNode ) return;

//     if(cur_ptr.buildingNum==x){
//       printAll(cur_ptr.rightPointer,x,y);
//       System.out.print("("+cur_ptr.buildingNum+" "+ cur_ptr.execution_time+" "+ cur_ptr.total_time+") ");
//     }
//     else if(cur_ptr.buildingNum==y){
//       printAll(cur_ptr.leftPointer,x,y);
//       System.out.print("("+cur_ptr.buildingNum+" "+ cur_ptr.execution_time+" "+ cur_ptr.total_time+") ");
    
//     }
//     else
//     {
//       if(cur_ptr.leftPointer!=ExternalNode && checkRange( cur_ptr.leftPointer.buildingNum, x,y)){
//       printAll(cur_ptr.leftPointer,x,y);
//       } 
//       System.out.print("("+cur_ptr.buildingNum+" "+ cur_ptr.execution_time+" "+ cur_ptr.total_time+") ");
//       if(cur_ptr.rightPointer!=ExternalNode && checkRange(cur_ptr.rightPointer.buildingNum,x,y))
//         printAll(cur_ptr.rightPointer,x,y);
//     }
//   }

//   boolean checkRange(int target,int l,int heap){
//     if(target>=l && target<=heap)
//       return true;
//     return false;
//   }

//   public RBT() {
//     ExternalNode = new Node();
//     ExternalNode.buildingNum=-1;
//     ExternalNode.nodeColor = 0;
//     ExternalNode.leftPointer = null;
//     ExternalNode.rightPointer = null;
//     root_node = ExternalNode;
//   }
// }




class RBT {
  private Node root_node;
  private Node ExternalNode;

// fix the rb tree modified by the delete operation
  private void Deleter(Node f) {
    Node s;
    while (f != root_node && f.nodeColor == 0) {
      if (f == f.parentPointer.leftPointer) {
        s = f.parentPointer.rightPointer;
        if (s.nodeColor == 1) {
          // case 3.1
          s.nodeColor = 0;
          f.parentPointer.nodeColor = 1;
          rotate_left(f.parentPointer);
          s = f.parentPointer.rightPointer;
        }

        if (s.leftPointer.nodeColor == 0 && s.rightPointer.nodeColor == 0) {
          // case 3.2
          s.nodeColor = 1;
          f = f.parentPointer;
        } else {
          if (s.rightPointer.nodeColor == 0) {
            // case 3.3
            s.leftPointer.nodeColor = 0;
            s.nodeColor = 1;
            rotate_right(s);
            s = f.parentPointer.rightPointer;
          } 

          // case 3.4
          s.nodeColor = f.parentPointer.nodeColor;
          f.parentPointer.nodeColor = 0;
          s.rightPointer.nodeColor = 0;
          rotate_left(f.parentPointer);
          f = root_node;
        }
      } else {
        s = f.parentPointer.leftPointer;
        if (s.nodeColor == 1) {
          // case 3.1
          s.nodeColor = 0;
          f.parentPointer.nodeColor = 1;
          rotate_right(f.parentPointer);
          s = f.parentPointer.leftPointer;
        }

        if (s.rightPointer.nodeColor == 0 && s.rightPointer.nodeColor == 0) {
          // case 3.2
          s.nodeColor = 1;
          f = f.parentPointer;
        } else {
          if (s.leftPointer.nodeColor == 0) {
            // case 3.3
            s.rightPointer.nodeColor = 0;
            s.nodeColor = 1;
            rotate_left(s);
            s = f.parentPointer.leftPointer;
          } 

          // case 3.4
          s.nodeColor = f.parentPointer.nodeColor;
          f.parentPointer.nodeColor = 0;
          s.leftPointer.nodeColor = 0;
          rotate_right(f.parentPointer);
          f = root_node;
        }
      } 
    }
    f.nodeColor = 0;
  }


  private void treeTransplant(Node p, Node q){
    if (p.parentPointer == null) {
      root_node = q;
    } else if (p == p.parentPointer.leftPointer){
      p.parentPointer.leftPointer = q;
    } else {
      p.parentPointer.rightPointer = q;
    }
    q.parentPointer = p.parentPointer;
  }

  private void deleteKeyNode(Node node, int key) {
    // find the node containing key
    Node n = ExternalNode;
    Node f, g;
    while (node != ExternalNode){
      if (node.buildingNum == key) {
        n = node;
      }

      if (node.buildingNum <key) {
        node = node.rightPointer;
      } else {
        node = node.leftPointer;
      }
    }

    if (n == ExternalNode) {
      System.out.println("Can't find key");
      return;
    } 

    g = n;
    int yOriginalColor = g.nodeColor;
    if (n.leftPointer == ExternalNode) {
      f = n.rightPointer;
      treeTransplant(n, n.rightPointer);
    } else if (n.rightPointer == ExternalNode) {
      f = n.leftPointer;
      treeTransplant(n, n.leftPointer);
    } 
    else {
      g = minKey(n.rightPointer);
      yOriginalColor = g.nodeColor;
      f = g.rightPointer;
      if (g.parentPointer == n) {
        f.parentPointer = g;
      } else {
        treeTransplant(g, g.rightPointer);
        g.rightPointer = n.rightPointer;
        g.rightPointer.parentPointer = g;
      }

      treeTransplant(n, g);
      g.leftPointer = n.leftPointer;
      g.leftPointer.parentPointer = g;
      g.nodeColor = n.nodeColor;
    }
    if (yOriginalColor == 0){
      Deleter(f);
    }
  }


  
  // fix the red-black tree
  private void inserter(Node k){
    Node p;
    while (k.parentPointer.nodeColor == 1) {
      if (k.parentPointer == k.parentPointer.parentPointer.rightPointer) {
        p = k.parentPointer.parentPointer.leftPointer; // uncle
        if (p.nodeColor == 1) {
          // case 3.1
          p.nodeColor = 0;
          k.parentPointer.nodeColor = 0;
          k.parentPointer.parentPointer.nodeColor = 1;
          k = k.parentPointer.parentPointer;
        } else {
          if (k == k.parentPointer.leftPointer) {
            // case 3.2.2
            k = k.parentPointer;
            rotate_right(k);
          }
          // case 3.2.1
          k.parentPointer.nodeColor = 0;
          k.parentPointer.parentPointer.nodeColor = 1;
          rotate_left(k.parentPointer.parentPointer);
        }
      } else {
        p = k.parentPointer.parentPointer.rightPointer; // uncle

        if (p.nodeColor == 1) {
          // mirror case 3.1
          p.nodeColor = 0;
          k.parentPointer.nodeColor = 0;
          k.parentPointer.parentPointer.nodeColor = 1;
          k = k.parentPointer.parentPointer;  
        } else {
          if (k == k.parentPointer.rightPointer) {
            // mirror case 3.2.2
            k = k.parentPointer;
            rotate_left(k);
          }
          // mirror case 3.2.1
          k.parentPointer.nodeColor = 0;
          k.parentPointer.parentPointer.nodeColor = 1;
          rotate_right(k.parentPointer.parentPointer);
        }
      }
      if (k == root_node) {
        break;
      }
    }
    root_node.nodeColor = 0;
  }

  // find the node with the minKey key
  public Node minKey(Node node) {
    while (node.leftPointer != ExternalNode) {
      node = node.leftPointer;
    }
    return node;
  }

  // find the node with the maximum key
  public Node maximum(Node node) {
    while (node.rightPointer != ExternalNode) {
      node = node.rightPointer;
    }
    return node;
  }
  

  // rotate leftPointer at node f
  public void rotate_left(Node f) {
    Node g = f.rightPointer;
    f.rightPointer = g.leftPointer;
    if (g.leftPointer != ExternalNode) {
      g.leftPointer.parentPointer = f;
    }
    g.parentPointer = f.parentPointer;
    if (f.parentPointer == null) {
      this.root_node = g;
    } else if (f == f.parentPointer.leftPointer) {
      f.parentPointer.leftPointer = g;
    } else {
      f.parentPointer.rightPointer = g;
    }
    g.leftPointer = f;
    f.parentPointer = g;
  }

  // rotate rightPointer at node f
  public void rotate_right(Node f) {
    Node g = f.leftPointer;
    f.leftPointer = g.rightPointer;
    if (g.rightPointer != ExternalNode) {
      g.rightPointer.parentPointer = f;
    }
    g.parentPointer = f.parentPointer;
    if (f.parentPointer == null) {
      this.root_node = g;
    } else if (f == f.parentPointer.rightPointer) {
      f.parentPointer.rightPointer = g;
    } else {
      f.parentPointer.leftPointer = g;
    }
    g.rightPointer = f;
    f.parentPointer = g;
  }

  // insert the key to the tree in its appropriate position
  // and fix the tree
  public void insert(Node node) {
    // Ordinary Binary Search Insertion
    
    
    node.execution_time=0;
    node.leftPointer=ExternalNode;
    node.rightPointer=ExternalNode;
    node.parentPointer=null;
    node.nodeColor=1;
    
     // new node must be red

    Node g = null;
    Node f = this.root_node;

    while (f != ExternalNode) {
      g = f;
      if (node.buildingNum < f.buildingNum) {
        f = f.leftPointer;
      } else {
        f = f.rightPointer;
      }
    }

    // g is parentPointer of f
    node.parentPointer = g;
    if (g == null) {
      root_node = node;
    } else if (node.buildingNum < g.buildingNum) {
      g.leftPointer = node;
    } else {
      g.rightPointer = node;
    }

    // if new node is a root_node node, simply return
    if (node.parentPointer == null){
      node.nodeColor = 0;
      return;
    }

    // if the grandparent is null, simply return
    if (node.parentPointer.parentPointer == null) {
      return;
    }

    // Fix the tree
    inserter(node);
  }

  

  // delete the node from the tree
  public void nodeDelete(int buildingNum) {
    deleteKeyNode(this.root_node, buildingNum);
  }

  // print the tree structure on the screen
  
  void print(int buildingNo){
    Node cur_ptr=root_node;
    while(cur_ptr!=null){
      if(cur_ptr.buildingNum == buildingNo){
       System.out.print("("+cur_ptr.buildingNum+" "+ cur_ptr.execution_time+" "+ cur_ptr.total_time+") ");
        System.out.println();
        return;
      }
      else if(buildingNo>cur_ptr.buildingNum)
        cur_ptr=cur_ptr.rightPointer;
      else
        cur_ptr=cur_ptr.leftPointer;
    }
  }

  void printRange(int f,int g)
  {
    Node cur_ptr=root_node;
    while(cur_ptr!=null){
      if(cur_ptr.buildingNum>=f && cur_ptr.buildingNum<=g){
        printAll(cur_ptr,f,g);
        System.out.println();
        return;
      }
      else if(cur_ptr.buildingNum<f){
        cur_ptr=cur_ptr.rightPointer;
      }
      else{
        cur_ptr=cur_ptr.leftPointer;
      }
    }
  }

  void printAll(Node cur_ptr,int f,int g){
    if(cur_ptr==ExternalNode ) return;

    if(cur_ptr.buildingNum==f){
      printAll(cur_ptr.rightPointer,f,g);
      System.out.print("("+cur_ptr.buildingNum+" "+ cur_ptr.execution_time+" "+ cur_ptr.total_time+") ");
    }
    else if(cur_ptr.buildingNum==g){
      printAll(cur_ptr.leftPointer,f,g);
      System.out.print("("+cur_ptr.buildingNum+" "+ cur_ptr.execution_time+" "+ cur_ptr.total_time+") ");
    
    }
    else
    {
      if(cur_ptr.leftPointer!=ExternalNode && checkRange( cur_ptr.leftPointer.buildingNum, f,g)){
      printAll(cur_ptr.leftPointer,f,g);
      } 
      System.out.print("("+cur_ptr.buildingNum+" "+ cur_ptr.execution_time+" "+ cur_ptr.total_time+") ");
      if(cur_ptr.rightPointer!=ExternalNode && checkRange(cur_ptr.rightPointer.buildingNum,f,g))
        printAll(cur_ptr.rightPointer,f,g);
    }
  }

  boolean checkRange(int target,int l,int heap){
    if(target>=l && target<=heap)
      return true;
    return false;
  }

  public RBT() {
    ExternalNode = new Node();
    ExternalNode.buildingNum=-1;
    ExternalNode.nodeColor = 0;
    ExternalNode.leftPointer = null;
    ExternalNode.rightPointer = null;
    root_node = ExternalNode;
  }
}