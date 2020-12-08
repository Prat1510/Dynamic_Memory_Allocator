// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {
        if (this.next == null){
            return null;
        }
        A1List newnode = new A1List(address, size, key);
        A1List temp = this.next;
        newnode.next = temp;
        temp.prev = newnode;
        this.next = newnode;
        newnode.prev = this;
        return newnode;
    }

    public boolean Delete(Dictionary d) 
    {
        if (d==null){
            return false;
        }
        int add = d.address;
        int k = d.key;
        int siz = d.size;
        if (this.next != null && this.prev != null){    
            if (this.key == k && this.address == add && this.size == size){
                this.key = this.prev.key;
                this.address = this.prev.address;
                this.size = this.prev.size;
                if (this.prev.prev == null){
                    this.prev = null;
                    return true;
                }
                else{
                    this.prev = this.prev.prev;
                    this.prev.next = this;
                    return true;
                }
            }
        }

        A1List curr = this; 
        
        if (curr.prev == null){
            curr = curr.next;
        }
        while (curr.next != null){
            if (curr.key == k && curr.address == add && curr.size == siz){
                A1List temp = curr.next;
                temp.prev = curr.prev;
                curr.prev.next = temp;
                return true;
            }    
            else {
                curr = curr.next;
            }   
        }
        
        curr = this; 
        while (curr.prev != null){
            if (curr.key == k && curr.address == add && curr.size == siz){
                A1List temp = curr.next;
                temp.prev = curr.prev;
                curr.prev.next = temp;
                return true;
            }
            else {
                curr = curr.prev;
            }  
        }
        return false;
    }

    public A1List Find(int k, boolean exact) 
    { 
        A1List curr = this.getFirst();
        if (curr == null){
            return null;
        }
        while (curr.next != null){
            if (curr.key >= k){
                if (exact == false){
                    return curr;
                }
                else if (curr.key == k){
                    return curr;
                }
                else{
                    curr = curr.next; 
                }
            }
            else {
                curr = curr.next;
            }   
        }
        return null;
    }

    public A1List getFirst()
    {
        A1List curr = this; 
        while (curr.prev != null){
            curr = curr.prev;
        }
        if (curr.next.next != null){
            return curr.next;
        }
        else{
            return null;
        }        
    }
    
    public A1List getNext() 
    {
        if (this.next == null || this.next.next == null){
            return null;
        }
        else{
            return this.next;
        }
    }

    public boolean sanity()
    {

        // Checking for 1) For non-sentinel node - (node.next.prev == node) should always be true.
        //              2) For non-sentinel node - (node.prev.next == node) should always be true.
        //              3) Tail node has null as its previous element. Similarly head node has null as next element.

        A1List curr = this;
        while (curr.next != null){
            if (curr.next.prev != curr){
                return false;
            }
            if(curr.prev != null){
                if (curr.prev.next != curr){
                return false;
                }
            }
            curr = curr.next;
        }
        if (curr.key != -1 || curr.size != -1 || curr.address != -1){
            return false;
        }

        curr = this;
        while (curr.prev != null){
            if (curr.next.prev != curr){
                return false;
            }
            if (curr.prev.next != curr){
                return false;
            }
            curr = curr.prev;
        }
        if (curr.key != -1 || curr.size != -1 || curr.address != -1){
            return false;
        }

        // Checking for 4) Linked list is circular.
        
        curr = this;
        A1List curr2 = this;
        while (curr.next != null && curr2.next != null && curr2.next.next != null){
            curr = curr.next;
            curr2 = curr2.next.next;
            if (curr == curr2){ 
                return false;
            }
        }
        curr = this;
        curr2 = this;
        while (curr.prev != null && curr2.prev != null && curr2.prev.prev != null){
            curr = curr.prev;
            curr2 = curr2.prev.prev;
            if (curr == curr2){
                return false;
            }
        }
        return true;
    }
}


