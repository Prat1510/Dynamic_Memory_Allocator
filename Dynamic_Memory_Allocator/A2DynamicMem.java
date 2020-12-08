// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 

    public void Defragment() {
        Dictionary newTree;
        if (type == 1){
            return;
        }
        else if (type == 2){
            newTree = new BSTree();
        }
        else{
            newTree = new AVLTree();
        }
        Dictionary curr = freeBlk.getFirst() ;
        while (curr != null){
            newTree.Insert(curr.address,curr.size,curr.address);
            curr = curr.getNext();
        }
        curr = newTree.getFirst();
        if (curr == null){
            return;
        }
        Dictionary curr1 = curr.getNext();
        if (curr1 == null){
            return;
        }
        Dictionary d1;
        Dictionary d2;
        boolean b;
        while (curr1 != null){
            if(curr.address + curr.size == curr1.address){
                newTree.Delete(curr);
                newTree.Delete(curr1);
                if (type == 2){
                    d1 = new BSTree(curr.address, curr.size, curr.size);                   
                }
                else{
                    d1 = new AVLTree(curr.address, curr.size, curr.size);     
                }
                if (type == 2){
                    d2 = new BSTree(curr1.address, curr1.size, curr1.size);
                }
                else{
                    d2 = new AVLTree(curr1.address, curr1.size, curr1.size);
                }
                freeBlk.Delete(d1);
                freeBlk.Delete(d2);
                freeBlk.Insert(curr.address, curr.size + curr1.size, curr.size + curr1.size);
                curr = newTree.Insert(curr.address, curr.size + curr1.size, curr.address);
                curr1 = curr.getNext();
            }
            else{
                curr = curr1;
                curr1 = curr1.getNext();
            }
        }
        return ;
    }
    
}