import java.util.*;

/*
 * Name: Alex Cohenander
 * Block: Extra Cool Block
 * Title: Modifying disarrays8
 */

public class ModifyingArrays {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner input = new Scanner(System.in);

        int size = 0, choice = 0, element = 0, num = 0, result = 0, searchChoice, rotateChoice, rotate = 0;

        boolean valid = false;
        while(!valid) {
            try {
                System.out.print("How long is your desired array(must be at least 1 element long): ");
                size = input.nextInt();
                if (size > 0) valid = true;
            } catch(InputMismatchException ime) {
                valid = false;
            }
        }
        System.out.print("\n");
        int[] numbers = new int[size];
        int[] zeronumbers = new int[size];
        for (int x = 0; x < size; x++) {
            if ((x == 1) || ((x % 10 == 1) && (x != 11)))
                System.out.print("Enter your " + x + "st element: ");
            else if ((x == 2) || ((x % 10 == 2) && (x != 12)))
                System.out.print("Enter your " + x + "nd element: ");
            else if ((x == 3) || ((x % 10 == 3) && (x != 13)))
                System.out.print("Enter your " + x + "rd element: ");
            else
                System.out.print("Enter your " + x + "th element: ");
            element = input.nextInt();
            numbers[x] = element;
        }
        do {
            System.out.println("\n\nChoose an array modification");
            System.out.println("1. Display Array");
            System.out.println("2. Find Number");
            System.out.println("3. Switch Smallest Number With First Elemtent");
            System.out.println("4. Rotate Array");
            System.out.println("5. Remove Zeros");
            System.out.println("6. Quit");
            choice = input.nextInt();
            if (choice == 1)
                displayNums(numbers, size);
            else if (choice == 2) {
                System.out.print("\n");
                do {
                    System.out.print("What entry: ");
                    num = input.nextInt();
                    result = findNums(numbers, size, num);
                    if (result < size)
                        System.out.print("Status: Found at possition " + result);
                    else if (result == size + 1)
                        System.out.print("Satus: Not found");
                    System.out.print("\nAnother search (yes(1)/no(0))? ");
                    searchChoice = input.nextInt();
                    if (searchChoice > 1 || searchChoice < 0) {
                        System.out.println("Option not valid");
                        System.out.print("\nAnother search (yes(1)/no(0))? ");
                        searchChoice = input.nextInt();
                    }
                } while (searchChoice != 0);
            } else if (choice == 3) {
                System.out.print("Smallest element first:\n");
                switchSmallest(numbers, size);
            } else if (choice == 4) {
                do {
                    System.out.print("How many steps? ");
                    rotate = input.nextInt();
                    rotateNums(numbers, size, rotate);
                    System.out.print("\nRotate again (yes(1)/no(0))? ");
                    rotateChoice = input.nextInt();
                    if (rotateChoice > 1 || rotateChoice < 0) {
                        System.out.print("Option not valid");
                        System.out.print("\nRotate again (yes(1)/no(0))? ");
                        rotateChoice = input.nextInt();
                    }
                } while (rotateChoice != 0);
            } else if (choice == 5) {
                zeronumbers = removeZeros(numbers, size);
                System.out.println("Size of Array: " + zeronumbers.length);
                System.out.print("Array Positions: ");
                for (int x = 0; x < zeronumbers.length; x++) {
                    System.out.printf("%-5d", x);
                }
                System.out.print("\nArray Elements:  ");
                for (int x = 0; x < zeronumbers.length; x++)
                    System.out.printf("%-5d", zeronumbers[x]);
                size = zeronumbers.length;
                for (int x = 0; x < size; x++)
                    numbers[x] = zeronumbers[x];
            } else if (choice == 6)
                System.out.print("Thank you for using my array modifier");
        } while (choice != 6);
        input.close();
    }

    private static void displayNums(int[] numbers, int size) {

        System.out.println("\nSize of Array: " + size);
        System.out.print("Array Positions: ");
        for (int x = 0; x < size; x++) {
            System.out.printf("%-5d", x);
        }
        System.out.print("\n");
        System.out.print("Array Elements:  ");
        for (int x = 0; x < size; x++) {
            System.out.printf("%-5d", numbers[x]);
        }
    }

    private static int findNums(int[] numbers, int size, int num) {
        for (int x = 0; x < size; x++) {
            if (numbers[x] == num)
                return x;
        }
        return size + 1;
    }

    private static void switchSmallest(int[] numbers, int size) {
        int min, minx = 0;
        System.out.print("Array Positions: ");
        for (int x = 0; x < size; x++) {
            System.out.printf("%-5d", x);
        }
        min = numbers[0];
        for (int x = 0; x < size; x++) {
            if (numbers[x] < min) {
                min = numbers[x];
                minx = x;
            }
        }
        numbers[minx] = numbers[0];
        numbers[0] = min;
        System.out.print("\nArray Elements:  ");
        for (int x = 0; x < size; x++)
            System.out.printf("%-5d", numbers[x]);
    }

    private static void rotateNums(int[] numbers, int size, int rotate) {
        System.out.print("Rotated Array\nArray Positions: ");
        for (int x = 0; x < size; x++) {
            System.out.printf("%-5d", x);
        }
        System.out.print("\nArray Elements:  ");
        if (rotate >= 0)
            for (int k = 0; k < rotate; k++) {
                int hold = numbers[size - 1];
                for (int x = size - 1; x > 0; x--) {
                    numbers[x] = numbers[x - 1];

                }
                numbers[0] = hold;
            }
        else if (rotate < 0) {
            rotate = size - (rotate * -1);
            for (int k = 0; k < rotate; k++) {
                int hold = numbers[size - 1];
                for (int x = size - 1; x > 0; x--) {
                    numbers[x] = numbers[x - 1];
                }
                numbers[0] = hold;
            }
        }
        for (int x = 0; x < size; x++) {
            System.out.printf("%-5d", numbers[x]);
        }
    }

    private static int[] removeZeros(int[] numbers, int size) {
        int count = 0;
        for (int x = 0; x < size; x++)
            if (numbers[x] != 0)
                count++;
        int[] zeronumbers = new int[count];
        int i = 0;
        for (int k = 0; k < size; k++)
            if (numbers[k] != 0) {
                zeronumbers[i] = numbers[k];
                i++;
            }
        return zeronumbers;
    }
}