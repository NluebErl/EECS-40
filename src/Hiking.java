/**
 * Hiking class holds the main function along with the hiking class itself which has methods that can find and print the hikers with the fastest hiking times (along with their times)
 */
public class Hiking {
    /**
     * Main function runs the methods created. Holds values for student strings and their times.
     * @param args Command line arguments that are inputted.
     */
    public static void main(String[] args){
        // Array of student names participating in the hiking
        String[] students = {"Mason", "Olivia", "Sophia", "Liam", "Noah", "Lucas", "Harper", "Aria", "Ethan", "Ava", "Jackson", "Charlotte", "Benjamin", "Amelia", "Oliver", "Mia"};
        // Array of corresponding completion times in minutes
        int[] times = {278, 310, 259, 289, 332, 297, 305, 264, 290, 283, 315, 275, 301, 288, 319, 276};
        
        // Create an instance of Hiking class
        Hiking h = new Hiking();
        // Find and display the fastest runner
        h.findBestRunner(students, times);
        // Find and display the second fastest runner
        h.findSecondBestRunner(students, times);
    }

    /**
     * This method loops through the list of students along with their corresponding times to complete a hike.
     * Loop to find the index of the fastest time, which correlates with the student's position on the list.
     * @param students list of students in an array.
     * @param times list of the students' time it took them to finish the hike.
     */
    public void findBestRunner(String[] students, int[] times) {
        // TODO: Implement the method
        int fastestIndex = 0;
        for (int i = 1; i < students.length; i++)
        {
            if (times[i] < times[fastestIndex])
            {
                fastestIndex = i;
            }
        }
        System.out.println("Fastest runner: " + students[fastestIndex] + " (" +  times[fastestIndex] + " minutes)");
    }

    /**
     * Finds the second-best runner on the hike by first finding the fastest runner and comparing their times.
     * After finding the fastest runner, the method iterates through the list again and finds the runner that has the shortest time but longer than the first place runner.
     * The second-best runner and their time gets printed out.
     * @param students list of students in an array.
     * @param times list of the students' time it took them to finish the hike.
     */
    public void findSecondBestRunner(String[] students, int[] times) {
        // TODO: Implement the method
        // Initialize variables for fastest index and the second fastest index
        int fastestIndex = 0;
        int secondBestIndex = -1;
        // Loop through the list to find the fastest index
        for (int i = 1; i < students.length; i++)
        {
            if (times[i] < times[fastestIndex])
            {
                fastestIndex = i;
            }
        }
        // Loop through again to find the second fastest index
        for (int i = 0; i < students.length; i++)
        {
            // Skip if it's the fastest index
            if (i == fastestIndex)
                continue;
            if (secondBestIndex == -1 || times[i] < times[secondBestIndex])
            {
                secondBestIndex = i;
            }
        }
        System.out.println("Second fastest runner: " + students[secondBestIndex] + " (" + times[secondBestIndex] + " minutes)");
    }
}