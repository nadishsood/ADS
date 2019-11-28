import java.util.*;
/*class Building{
	int buildingno;
	int exec_time;
	int total_time;

	public Building(int a,int b,int c){
		buildingno=a;
		exec_time=b;
		total_time=c;
	}
}*/

public class Heap extends WayneEnterprise {
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
               // System.out.println("he");
        		swap(last,parent(last));
           	    last=parent(last);
               // minheapify(last);
        	}
        	else break;
        }
        minheapify(1); 
        //print();
    }
   /* void put(int key,int total_time){
        insert(new Node(key,0,total_time));
    }*/
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
       /* if(ele.exec_time>min.exec_time)
        {
            swap(min_i,index);
            minheapify(min_i);
        }
        else if(ele.exec_time==min.exec_time ){
        	if(ele.buildingno>min.buildingno){
        		swap(min_i,index);
           		 minheapify(min_i);
        	}
        } */

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
   /* public static void main(String args[])
    {
        Heap mh=new Heap();
        Random rand=new Random();
        int count=0;
        while(count<15)
        {
            mh.insert(new Building(count+1,count+1,0));
            count++;
            mh.print();
            System.out.println();
        }
       
        mh.insert(new Building(17,5,2)); 
        mh.print();
        System.out.println();
        Building r=mh.remove();
        System.out.println("\nElement deleted:"+r.buildingno+" "+r.exec_time+" "+ r.total_time);
        mh.insert(900);
        mh.insert(10); 
        mh.insert(84); 
        mh.insert(19); 
        mh.insert(6); 
        mh.insert(22); 
        mh.insert(9);
        mh.insert(100);
        mh.print();
        System.out.println();
        mh.insert(150);
        mh.print();
        System.out.println();
        //System.out.println("\nElement deleted:"+remove());
        
        //System.out.println("\nElement deleted:"+remove());
        
        //System.out.println("\nElement deleted:"+remove());
        
        //System.out.println();
        Building o=mh.remove();
        System.out.println("\nElement deleted:"+o.buildingno+" "+o.exec_time+" "+ o.total_time);
        mh.print();
        System.out.println();
        Building l=mh.remove();
        System.out.println("\nElement deleted:"+l.buildingno+" "+l.exec_time+" "+ l.total_time);
        mh.print();
        System.out.println();
         Building w=mh.remove();
        System.out.println("\nElement deleted:"+w.buildingno+" "+w.exec_time+" "+ w.total_time);
        System.out.println();
        mh.print();
    }*/
                
        
    }

