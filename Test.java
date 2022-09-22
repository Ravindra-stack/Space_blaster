import java.util.*;
class Test
{
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
      int a=sc.nextInt();
      String ss="";
      int k=0;
      String aage="";
      int count=97;
       for(int i=0;i<a;i++)
         {
             if(count>122)
                count = 97;
           if(i%2==0)
           {
             if(i==0)
             {
               char ch = (char)count;
               ss=ss+ch;
               count=count+1;
             }
             else
             {
               char ch = (char)count;
               ss=ch+ss;
               count=count+1;
             }
           }
           else
           {
              char ch = (char)count;
              ss=ss+ch;
              count=count+1;
           }
         }
      String copy=ss;
      System.out.println(copy);

	}
}