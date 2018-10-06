package first_hometask;

public class Recursion {
    static void showNumChain(int n){
        if (n>1) showNumChain(n-1);
        System.out.print(n+" ");
    }
    public static void main(String[] args){
        showNumChain(1234);
    }
}

