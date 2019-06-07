import java.util.Scanner;
//Used N-Ary tree to store the children inside the Box
class TestNaryTree {
    //Two dimensional Array to store the values
    public static int[][] Array = new int[100][100];
    //Static constants during the public functions
    public static int r = 0, t = 0,max_x=0,max_y=0;
    public static int LABEL;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Ntree rd = new Ntree(0, 0,0,null);

        while(LABEL!=4){
            //User can select from three choices
            System.out.println("How would you like to arrange your boxes?\n 1. Horizontal \n 2. Vertical \n 3. Russian Dolls(Boxes)\n 4. Exit");
            LABEL = sc.nextInt();
            //switch statement to run the program more than once if needed
            switch (LABEL) {
                case 1 : case 2:
                    //once you decide it is horizontal or vertical user has to decide the number of boxes inside biggest Box
                    System.out.println("How many children boxes do you want inside out-most Box?");
                    int y = sc.nextInt();
                    //restricting the number of boxes inside to 10 only
                    if(y>10) {
                        System.out.println("Too many boxes to Print!!");
                        break;
                    }
                    //creating the root with depth 0 and index 0 and parent as null
                    Ntree root = new Ntree(y, 0,0,null);
                    int p=0;
                    //read all children and their children and so on
                    root.ReadAllChildren(root, y,p);
                    //once read process the tree in the Array
                    TestNaryTree.Print(root);
                    //Ntree.print(root);

                    //Print the tree which is processed in an Array
                    ArrayTree(Array);
                    //reset the static constants
                    r=t=max_x=max_y=0;
                    break;

                case 3:
                    //Russian dolls example
                    TestNaryTree.R_dolls(rd);
                    ArrayTree(Array);
                    break;

                case 4:
                    break;

                default:
                    System.out.println("Wrong choice!! Try again.");
                    break;
            }
        }
    }

    //printing the Array as the boxes
    public static void ArrayTree(int Array[][]){
        for(int k=0;k<100;k++){
            if(Array[k][0]==0)
                break;

            for(int l=0;l<100;l++){
                if(Array[k][l]==1) {
                    System.out.print("+");
                    Array[k][l]=0;
                    continue;
                }else if(Array[k][l]==2) {
                    System.out.print("-");
                    Array[k][l]=0;
                    continue;
                }else if(Array[k][l]==3) {
                    System.out.print("|");
                    Array[k][l]=0;
                    continue;
                }else if(Array[k][l]==4 || Array[k][l]==0) {
                    System.out.print(" ");
                    Array[k][l]=0;
                    continue;
                }
            }
            System.out.println();
        }

    }

    //Russian dolls (boxes) arranging function
    public static void R_dolls(Ntree root) {
        Scanner sc = new Scanner(System.in);
        System.out.println("How many boxes do you want in total?");
        int r=sc.nextInt();
        int i=r-1,j=(r-1)*2;
        Box(root,i,j);
        int m=i+3;
        int n=j+4;
        for (int k=r-2;k>=0;k--){
            i=k;
            j=k*2;
            DrawBorder(m,n,i,j);
            m=m+1;
            n=n+2;
        }
    }

    //Print a single Box with single space inside it , given starting points i,j
    public static void Box(Ntree node, int i, int j){
        Array[i][j]=1;
        Array[i+1][j]=3;
        Array[i][j+1]=2;
        Array[i+1][j+1]=4;
        Array[i+2][j]=Array[i][j+2]=Array[i+2][j+2]=1;
        Array[i+2][j+1]=2;
        Array[i+1][j+2]=3;
        node.b=true;
    }

    //Drawing the parent function
    public void DrawParent(Ntree node,int i, int j){
        int x =i;
        int y =j;
        int c=0;
        boolean d=false;
        //if node does not have children Print the Box
        if(Ntree.hasChildren(node)==0){
            Box(node,x,y);
        }
        else {
            //else the position will be moved inside the Box
            x=x+1;
            y=y+2;
            //for all children of the parent
            for (int k = 0; k < node.children.size(); k++) {
                Ntree child = node.children.get(k);
                //calling the DrawParent function recursively
                DrawParent(child, x, y);

                //checking if child has siblings
                for (int l = k; l < node.children.size(); l++) {
                    //for each node if it has been visited or not and deciding the next counter position according to the input and the depth of the node
                    if (node.children.get(l).b == false && LABEL == 1) {
                        if (node.children.get(l).D % 2 == 1) {
                            if(node.children.get(l).I>0 && Ntree.hasChildren(node.children.get(l-1))==1) {
                                y = y + 4 > t ? y + 4 : t;
                                y = max_y > y ? max_y : y;

                            }else
                                y=y+4;

                            break;
                        } else {
                            if(Ntree.hasChildren(node.children.get(l-1))==0)
                                x=x+3;
                            else
                                x =  x+3 > r ? x+3 : r;

                            break;
                        }
                    } else if (node.children.get(l).b == false && LABEL == 2) {
                        if (node.children.get(l).D % 2 == 1) {
                            if(node.children.get(l).I>0 && Ntree.hasChildren(node.children.get(l-1))==1) {
                                x = x + 3 > r ? x + 3 : r;
                                x = max_x > x ? max_x : x;
                            }
                            else
                                x=x+3;
                            break;
                        } else {
                            if(l>0 && Ntree.hasChildren(node.children.get(l-1))==0)
                                y=y+4;
                            else
                                y = y+4 > t ? y+4 : t;
                            break;
                        }
                    } else {//if all the nodes are visited of the parent
                        for (int m = 0; m < node.children.size(); m++) {
                            if (node.children.get(m).b == true)
                                d = true;
                            else
                                d = false;

                            if(c==0 && Ntree.hasChildren(node.children.get(m))==1)
                                c=1;
                        }
                        if (d) {//closing the parent Box
                            x = x + 3;
                            y = y + 4;

                            if(x==r-1)
                                r=r-1;
                            if(y==t-2)
                                t=t-2;
                            //incrementing the maximum values of x and y
                            if (max_y < y)
                                max_y = y;
                            if (max_x < x)
                                max_x = x;

                            //marking the parent Box as visited
                            node.b = true;

                            //for the outmost Box
                            if (i == 0 && j == 0) {
                                if(child.N==1  && node.N>=1 && r==0) {
                                }
                                else {
                                    max_y=max_y+2;
                                    max_x=max_x+1;
                                }
                                DrawBorder(max_x, max_y, i, j);
                                return;
                            }

                            if(max_y<t)
                                max_y=t;
                            if(max_x<r)
                                max_x=r;

                            if(x<r && ((r-x)<=3 || c==1) )
                                x=r;
                            if (y < t && (t - y <= 6 || c == 1) )
                                y = t;


                            //with the depth of the node and the how we are arranging the boxes drawing the border of the parent Box
                            //avoiding unnecessary increment of max_y and max_x
                            if (LABEL == 1) {
                                if (node.D % 2 == 1) {
                                    DrawBorder(x, y, i, j);
                                    max_x=max_x-1;
                                }
                                else {
                                    DrawBorder(x, y, i, j);
                                    max_y=max_y-2;
                                }
                            } else {
                                if (node.D % 2 == 1) {
                                    DrawBorder(x, y, i, j);
                                    max_y = max_y - 2;
                                }
                                else {
                                    DrawBorder(x, y, i, j);
                                    max_x = max_x - 1;
                                }
                            }

                            //incrementing the values after drawing the border of parent Box
                            max_y = max_y + 2;
                            max_x = max_x + 1;
                            r=x+1;
                            t=y+2;

                            //depth=node.parent.D > 0 ? node.parent.D : 0;
                            //modifying the values of static constants t and r

                            d=false;
                            //System.out.println(r + " rt" + t);
                        }
                        c=0;
                    }
                }
            }
        }
    }

    //Drawing the border of the parent Box with starting point (i,j) and ending point (x,y)
    public static void DrawBorder(int x,int y, int k,int l){
        for(int j=k+1;j<x;j++)
            Array[j][l] = Array[j][y] = 3;

        for(int j=l+1;j<y+1;j++)
            Array[k][j]=Array[x][j]=2;

        Array[k][l]=Array[x][y]=Array[k][y]=Array[x][l]=1;
    }

    //Print Box function
    public static void Print(Ntree root) {
        int i=0;
        int j=0;
        TestNaryTree t= new TestNaryTree();
        if(Ntree.hasChildren(root)==0){
            Box(root,i,j);
        }
        else {
            t.DrawParent(root,i,j);
        }
    }
}