/** For the item data with three attributes: itemCode, itemName, and unitPrice */

public class Item throws java.io.IOException{

    //Create file instance
    File file = new File('/Users/sarah/Desktop/Items.txt');

    //Create scanner for the file
    Scanner input = new Scanner(file);

    //Read data from the file
        /while(input.hasNext()){

            int code = nextInt();

            String name = next();

            int unitPrice = nextInt();
        }
        //close the file

        input.close();
}
