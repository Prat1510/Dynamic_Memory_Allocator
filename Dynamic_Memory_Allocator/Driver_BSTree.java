import java.util.Scanner;

public class Driver_BSTree {
    public static void main(String args[]) {
        int numTestCases;
        Scanner sc = new Scanner(System.in);
        numTestCases = sc.nextInt();
        while (numTestCases-- > 0) {
            Dictionary obj = new BSTree();
            int numCommands = sc.nextInt();
            while (numCommands-- > 0) {
                String command;
                command = sc.next();
                int argument1, argument2, argument3;
                Dictionary temp;
                int result = -5;
                switch (command) {
                    case "Insert":
                        argument1 = sc.nextInt();
                        argument2 = sc.nextInt();
                        argument3 = sc.nextInt();
                        temp = obj.Insert(argument1, argument2, argument3);
                        result = temp.key;
                        break;
                    case "Insert_Move":
                        argument1 = sc.nextInt();
                        argument2 = sc.nextInt();
                        argument3 = sc.nextInt();
                        obj = obj.Insert(argument1, argument2, argument3);
                        result = obj.key;
                        break;
                    case "Delete":
                        argument1 = sc.nextInt();
                        temp = obj.Find(argument1, true);
                        result = obj.Delete(temp) ? 1 : 0;
                        break;
                    case "Find":
                        argument1 = sc.nextInt();
                        argument2 = sc.nextInt();
                        temp = obj.Find(argument1, (argument2 == 1));
                        result = (temp == null) ? -100 : temp.address;
                        break;
                    case "First":
                        obj = obj.getFirst();
                        result = (obj == null) ? -101 : obj.address;
                    default:
                        break;
                }
                System.out.println(result);
                boolean sane = obj.sanity();

                if (!sane) {
                    System.out.println("Sanity Broken....Closing the testcase");
                    break;
                }
            }

        }
    }
}
