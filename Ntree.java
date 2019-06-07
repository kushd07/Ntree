
import java.util.ArrayList;
import java.util.Scanner;

public class Ntree {
    public Ntree parent;
    final int N,D;
    final int I;
    boolean b;
    public final ArrayList<Ntree> children;

    //Initializing the tree
    public Ntree(int n, int depth, int index,Ntree parent) {

        this.parent=parent;
        this.N = n;
        this.D=depth;
        this.I=index;
        this.b=false;
        children = new ArrayList<>(n);
    }

    //Add child function
    private boolean addChild(Ntree node) {
        if (children.size() < N) {
            return children.add(node);
        }

        return false;
    }

    public boolean addChild(int p, int d, int i,Ntree parent) {
        return addChild(new Ntree(p,d,i,parent));
    }

    //Returning all the children of the tree
    public ArrayList<Ntree> getChildren() {
        return new ArrayList<>(children);
    }

    public static void ReadAllChildren(Ntree root, int c,int index){
        int d=1;
        ReadAllChilds(root,c,d,index);
    }
    //Reading all children starting from depth 1
    private static void ReadAllChilds(Ntree node, int c, int d,int index){
        Scanner sc = new Scanner(System.in);
        for(int i=0;i<c;i++){

            System.out.println("How many children boxes do you want inside children box number "+ (i+1) +" of "+c+"?");
            int z=sc.nextInt();
            //Children should not be more than 10
            if(z>=10){
                System.out.println("Too many children boxes!!");
                return;
            }
            //Restricting the lowest child to 1
            if(z==1) {
                node.addChild(z,d,i+1,node);
                continue;
            }
            //if its more than 1 then calling the same function recursively to add children
            else {
                node.addChild(z,d,i+1,node);
                ReadAllChilds(node.getChild(i), z,d+1, index);

            }
        }
    }

    //Return the child according to its index
    public Ntree getChild(int index) {
        if (index < children.size()) {
            return children.get(index);
        }
        return null;
    }

    public static void print(Ntree root) {
        printUtil(root, 0);
    }
    //printing the N-ary tree
    private static void printUtil(Ntree node, int depth) {
        for (int i = 0; i < depth; ++i) {
            System.out.print(" | ");
        }

        System.out.println(node.N);
        //System.out.println(node.D);
        for (Ntree child : node.getChildren()) {

            printUtil(child, depth + 1);
        }
    }

    //Check if the node hs children or not
    public static int hasChildren(Ntree node){
        if (node.getChild(0)==null){
            return 0;
        }
        else
            return 1;
    }
}

