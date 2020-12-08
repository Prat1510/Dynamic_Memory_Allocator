// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    
    private int Height(AVLTree node){
        if (node == null){
            return 0;
        }
        else{
            return node.height;
        }
    }

    private AVLTree root(){
        AVLTree node = this;
        while (node.parent != null){
            node = node.parent;
        }
        return node.right;
    }

    public AVLTree Insert(int address, int size, int key) 
    { 
        AVLTree newnode = new AVLTree(address,size,key);
        newnode.height = 1;
        AVLTree curr = this.root();
        AVLTree curr1 ;
        if (curr == null){
            AVLTree temp = this;
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
            
            curr1.height = 2;
            int temp;
            int l;
            int r;
            curr1 = curr1.parent;
            while (curr1.parent != null){
                temp = Height(curr1);
                l = Height(curr1.left);
                r = Height(curr1.right);
                if (l - r < 2 && r - l < 2){
                    curr1.height = Math.max(l,r) + 1;
                    if (curr1.height == temp){
                        break;
                    }
                }
                else{
                    curr1.Rebalance();
                    break;
                }
                curr1 = curr1.parent;
            }
        return newnode;
        }
    }

    public boolean Delete(Dictionary e)
    { 
        if (e == null){
            return false;
        } 
        AVLTree curr = this.root();
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
                curr = curr.parent;
            }
            else{
                AVLTree next = curr.getNext();
                AVLTree newnode = new AVLTree(next.address,next.size,next.key);
                newnode.height = curr.height;
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
                curr = next.parent;
            }
            int temp;
            int l;
            int r;
            while (curr.parent != null){
                temp = Height(curr);
                l = Height(curr.left);
                r = Height(curr.right);
                if (l - r < 2 && r - l < 2){
                    curr.height = Math.max(l,r) + 1;
                    if (curr.height == temp){
                        break;
                    }
                    curr = curr.parent;
                }
                else{
                    curr.Rebalance();
                    curr = curr.parent.parent;
                }
            }
            return true;
        }    
    }
        
    public AVLTree Find(int key, boolean exact)
    { 
        AVLTree curr = this.root();
        if (curr == null){
            return null;
        }
        AVLTree curr1 = null;
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

    public AVLTree getFirst()
    { 
        AVLTree curr = this.root();
        if (curr == null){
            return null;
        }
        while(curr.left != null){
            curr = curr.left;
        }
        return curr;
    }

    public AVLTree getNext()
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
            AVLTree curr = this.right;
            while (curr.left != null){
                curr = curr.left;
            }
            return curr;
        }
        
        AVLTree curr = this;
        AVLTree curr1 = curr.parent;
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

    private boolean checkheight(AVLTree node){
        if (node == null){
            return true;
        }
        else {
            int l = Height(node.left);
            int r = Height(node.right);
            if (node.height != 1 + Math.max(l,r)){
                return false ;
            }
            else if (l-r >= 2 || r-l >= 2 ){
                return false;
            }
            return checkheight(node.left) && checkheight(node.right); 
        }
    }

    private boolean checkKeyAdd(AVLTree root, int k1, int a1, int k2, int a2){
        
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

    private boolean checklink(AVLTree node){
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
        AVLTree node = this;
        AVLTree node1 = this;
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
        AVLTree root = node.right;
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
        
        // Checking height balance property.
        root = node.right;
        if (root != null){
            a = checkheight(root);
            if (a == false){
                return false;
            }
        }
        return true;
    }

    private void Rebalance(){
        AVLTree node = this;
        int h1 = Height(node.left);
        int h2 = Height(node.right);
        int flag = 0;
        if (node.parent.left == node){
                flag = 0;
        }
        else {
            flag = 1;
        }
        if (h1 > h2){
            if (Height(node.left.left) >= Height( node.left.right)){
                node = node.RightRotate();
                node.right.height = Math.max(Height(node.right.right), Height(node.right.left)) + 1;
                node.height = Math.max(Height(node.left),Height(node.right)) + 1;
            }
            else{
                node = node.LeftRightRotate();
                node.right.height = Math.max(Height(node.right.right), Height(node.right.left)) + 1;
                node.left.height = Math.max(Height(node.left.left), Height(node.left.right)) + 1;
                node.height = Math.max(Height(node.left),Height(node.right)) + 1;
            }
        }
        else{
            if (Height(node.right.right) >= Height(node.right.left)){
                node = node.LeftRotate();
                node.left.height = Math.max(Height(node.left.left), Height(node.left.right)) + 1;
                node.height = Math.max(Height(node.left),Height(node.right)) + 1;
            }
            else{
                node = node.RightLeftRotate();
                node.right.height = Math.max(Height(node.right.right), Height(node.right.left)) + 1;
                node.left.height = Math.max(Height(node.left.left), Height(node.left.right)) + 1;
                node.height = Math.max(Height(node.left),Height(node.right)) + 1;
            }
        }
        if (flag == 0){
            node.parent.left = node;
        }
        else{
            node.parent.right = node;
        }
        return;
    }

    private AVLTree RightRotate(){
        AVLTree temp = this.left;
        this.left = temp.right;
        if (this.left != null){
            this.left.parent = this;
        }
        temp.right = this;
        temp.parent = this.parent;
        this.parent = temp;
        return temp;
    }

    private AVLTree LeftRotate(){
        AVLTree temp = this.right;
        this.right = temp.left;
        if (this.right != null){
            this.right.parent = this;
        }
        temp.left = this;
        temp.parent = this.parent;
        this.parent = temp;
        return temp;
    }
    
    private AVLTree LeftRightRotate(){
        this.left = this.left.LeftRotate();
        return this.RightRotate();
    }

    private AVLTree RightLeftRotate(){
        this.right = this.right.RightRotate();
        return this.LeftRotate();
    }

    private void deleteLeafOrOnechild(){
        AVLTree curr = this;
        AVLTree curr1 = curr.parent;
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
}


