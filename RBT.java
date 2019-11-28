


public class RBT {
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