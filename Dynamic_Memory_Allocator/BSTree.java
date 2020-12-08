// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
    
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }

    private BSTree root(){
        BSTree node = this;
        while (node.parent != null){
            node = node.parent;
        }
        return node.right;
    }
    
    private void deleteLeafOrOnechild(){
        BSTree curr = this;
        BSTree curr1 = curr.parent;
        if (curr.right == null && curr.left == null){
            if (curr1.right == curr){
                curr1.right = null;
                }              
            else{
                curr1.left = null;
            } 
        }
        else if (curr.right == null){
            if (curr1.right == curr){
                curr1.right = curr.left;
                curr.left.parent = curr1;
            }
            else{
                curr1.left = curr.left;
                curr.left.parent = curr1;                        
            }   
        }
        else {
            if (curr1.right == curr){
                curr1.right = curr.right;
                curr.right.parent = curr1;
            }
            else{
                curr1.left = curr.right;
                curr.right.parent = curr1;                        
            }
        }
        return ;
    }
    
    public BSTree Insert(int address, int size, int key) 
    { 
        BSTree newnode = new BSTree(address,size,key);
        BSTree curr = this.root();
        BSTree curr1 ;
        if (curr == null){
            BSTree temp = this;
            temp.right = newnode;
            newnode.parent = temp;
            return newnode;
        }
        else{
            curr1 = curr;
            while (curr != null){
                curr1 = curr;
                if (curr.key < key){
                    curr = curr.right;
                }
                else if (curr.key > key){
                    curr = curr.left;
                }
                else if (curr.address < address){
                    curr = curr.right;
                }
                else{
                    curr = curr.left;
                }
            }
            newnode.parent = curr1;
            if (curr1.key < key){
                curr1.right = newnode;
            }
            else if (curr1.key > key){
                curr1.left = newnode;
            }
            else if (curr1.address < address){
                curr1.right = newnode;
            }
            else{
                curr1.left = newnode;
            }
            return newnode;       
        }
    }

    public boolean Delete(Dictionary e)
    { 
        if (e == null){
            return false;
        } 
        BSTree curr = this.root();
        if (curr == null){
            return false;
        }
        int e_key = e.key;
        int e_add = e.address;
        int e_size = e.size;
        while (curr != null){
            if (curr.key < e_key){
                curr = curr.right;
            }
            else if (curr.key > e_key){
                curr = curr.left;
            }
            else if (curr.address < e_add){
                curr = curr.right;
            }
            else if(curr.address > e_add){
                curr = curr.left;
            }
            else{
                if (curr.size != e_size){
                    curr = curr.left;
                }
                else{
                    break;
                }
            }
        }
        if (curr == null){
            return false;
        }
        else{
            if (curr.right == null || curr.left == null){
                curr.deleteLeafOrOnechild();
                curr = curr.root();
                return true;
            }
            else{
                BSTree next = curr.getNext();
                BSTree newnode = new BSTree(next.address,next.size,next.key);
                newnode.parent = curr.parent;
                if (curr == curr.parent.right){
                    curr.parent.right = newnode;
                }
                else{
                    curr.parent.left = newnode;
                }
                newnode.right = curr.right;
                newnode.right.parent = newnode;
                newnode.left = curr.left;
                newnode.left.parent = newnode;
                next.deleteLeafOrOnechild();
                curr = curr.root();
                return true;
            }
        }    
    }
      
    public BSTree Find(int key, boolean exact)
    { 
        BSTree curr = this.root();
        if (curr == null){
            return null;
        }
        BSTree curr1 = null;
        if (exact == true){
            while (curr != null){
                if (curr.key < key){
                    curr = curr.right;
                }
                else if (curr.key > key){
                    curr = curr.left;
                }
                else{
                    curr1 = curr ;
                    curr = curr.left;
                }
            }
            return curr1;
        }
        else{
            while (curr != null){
                if (curr.key < key){
                    curr = curr.right;
                }
                else if (curr.key >= key){
                    curr1 = curr ;
                    curr = curr.left ;
                }
            }
            return curr1;
        }
    }

    public BSTree getFirst()
    { 
        BSTree curr = this.root();
        if (curr == null){
            return null;
        }
        while(curr.left != null){
            curr = curr.left;
        }
        return curr;
    }

    public BSTree getNext()
    { 
        if (this.parent == null){
            return null;
        }
        
        if (this.parent != null){
            if (this.parent.right != this && this.parent.left != this){
                return null;
            }
        }
        if (this.right != null){
            BSTree curr = this.right;
            while (curr.left != null){
                curr = curr.left;
            }
            return curr;
        }
        
        BSTree curr = this;
        BSTree curr1 = curr.parent;
        while (curr1.parent != null && curr1.right == curr){
            curr = curr1;
            curr1 = curr1.parent;
        }
        if (curr1.parent == null){
            return null;
        }
        else{
            return curr1;
        }
    }
    
    private boolean checkKeyAdd(BSTree root, int k1, int a1, int k2, int a2){
        
        if (root == null){
            return true;
        }
        else{
            if (root.key >= k1 && root.key <= k2){
                if (root.key == k1 && root.address < a1){
                    return false;
                }
                else if (root.key == k2 && root.address > a2){
                    return false;
                }
                return (checkKeyAdd(root.left, k1, a1, root.key, root.address) && checkKeyAdd(root.right, root.key, root.address, k2, a2));
            }
            else{
                return false;
            }
        }
    }

    private boolean checklink(BSTree node){
        if (node == null){
            return true;
        }
        else {
            if (node.left != null){
                if (node.left.parent != node){
                    return false;
                } 
            }
            if (node.right != null){
                if (node.right.parent != node){
                    return false;
                }
            }
            return (checklink(node.left) && checklink(node.right)); 
        }
    }
    
    public boolean sanity()
    { 
        boolean a;
        // Checking for outer loop, reaching the sentinel root if no loop.
        BSTree node = this;
        BSTree node1 = this;
        while (node.parent != null && node1.parent != null && node1.parent.parent != null ){
            node = node.parent;
            node1 = node1.parent.parent;
            if (node == node1){
                return false;
            }
        }
        if (node1.parent == null){
            node = node1;
        }
        else if (node1.parent.parent == null){
            node = node1.parent;
        }

        // Checking sentinel key, address and size
        if(node.key != -1 || node.address != -1 || node.size != -1){
            return false;
        }
        
        // Checking that parent of actual root is head sentinel when root exists. 
        if (node.right != null){
            if (node.right.parent != node){
                return false;
            }
        }

        // Checking that left child of sentinel is null.
        if (node.left != null ){
            return false;
        }
        
        // Checking the tree's links which also checks for remaining cycles.
        BSTree root = node.right;
        if (root!= null){
            a = checklink(root);
            if (a == false){
                return false;
            }
        }

        // Checking the BST property of keys.
        root = node.right;
        if (root != null){
            a = checkKeyAdd(root, Integer.MIN_VALUE,Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE); 
            if (a==false){
                return false;
            }
        }
    
        return true;
    }
    

}


 


