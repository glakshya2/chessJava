import java.util.*;

public class test {
static Vector<Integer> retuVe(){
    Vector<Integer> list = new Vector<Integer>();
    //list.clear();
    return list;
}
    
    public static void main(String[] args){
        Vector<Integer> list = retuVe();
        int size = list.size();
        if(size==0){
            System.out.println("0");
        }
        else{
            System.out.print("lol");
        }
    }   
}
