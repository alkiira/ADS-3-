import java.util.Random;

public class Main {
    public static void main(String[] args) {
        BST<MyTestingClass, Student> tree = new BST<>();

        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            String key = generateRandomString(random, 5);
            MyTestingClass myTestingClass = new MyTestingClass(key);
            Student student = new Student(i, "Student " + i);
            tree.put(myTestingClass, student);
        }

        for (BST.Entry<MyTestingClass, Student> entry : tree) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }

        String searchKey = "ABCDE";
        MyTestingClass searchTestClass = new MyTestingClass(searchKey);
        Student searchedStudent = tree.get(searchTestClass);
        if (searchedStudent != null) {
            System.out.println("Student found for key " + searchKey + ": " + searchedStudent);
        } else {
            System.out.println("No student found for key " + searchKey);
        }

        String deleteKey = "FGHIJ";
        MyTestingClass deleteTestClass = new MyTestingClass(deleteKey);
        tree.delete(deleteTestClass);
        System.out.println("Deleted key: " + deleteKey);

        System.out.println("Size of the BST: " + tree.size());
    }

    private static String generateRandomString(Random random, int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}
