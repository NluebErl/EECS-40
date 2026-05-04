import java.util.PriorityQueue;

/**
 * Runway class is the class used for handling runway functionality.
 * Has members for the name of the runway and the queue of the runway.
 * Holds methods and instructors for output, thread run function, and constructor.
 */
public class Runway implements Runnable {
    private final String name;
    private final PriorityQueue<Flight> queue;

    /**
     * Runway constructor which initializes the string name and priorityqueue queue for the flights for that specific runway.
     * @param name Name of the runway
     * @param queue The flights that are waiting for operation
     */
    public Runway(String name, PriorityQueue<Flight> queue)
    {
        this.name = name;
        this.queue = queue;
    }

    /**
     * Code to be executed by the thread once it starts.
     * Runs a loop to get the flight with the highest priority and processes it.
     */
    public void run()
    {
        try{
            // Loop to ensure that the thread runs until it is interrupted
            while(!Thread.currentThread().isInterrupted())
            {
                Flight flight;
                // Only allow one thread to access the queue at a time
                synchronized (queue)
                {
                    // Allow other threads to access if the queue is empty
                    while (queue.isEmpty())
                    {
                        queue.wait();
                    }
                    // Pop the element in queue with the highest priority
                    flight = queue.poll();
                }
                if (flight != null)
                {
                    processRequest(flight);
                }
            }
        } catch (InterruptedException e) {}
    }

    /**
     * processRequest method allows a flight to be added to the queue
     * Simulates the time it would take to process the operation by putting the thread to sleep.
     * @param flight The flight to be processed.
     */
    private void processRequest(Flight flight)
    {
        System.out.println(">>> Started processing: " + flight);
        try{
            Thread.sleep(flight.getDurationMs());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("<<< Finished processing: " + flight);
    }
}
