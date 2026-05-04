import java.util.PriorityQueue;

/**
 * Airport class stores the runway queue and two threads for two runways. Handles methods to handle threads, add flights to the queue, and stop threads.
 */
public class Airport {
    private final PriorityQueue<Flight> runwayQueue;
    private Thread runwayThread1;
    private Thread runwayThread2;

    /**
     * Airport constructor which initializes a new airport to hold queue information.
     * Handles inputs based on their priority rank provided from their type.
     */
    public Airport()
    {
        runwayQueue = new PriorityQueue<>();
    }

    /**
     * The start method initiates thread execution.
     * Initialize runway threads 1 and 2.
     * Start the runway threads to run the run() method in Runway class.
     */
    public void start()
    {
        Runway rw1 = new Runway("Runway 1", runwayQueue);
        Runway rw2 = new Runway("Runway 2", runwayQueue);

        runwayThread1 = new Thread(rw1);
        runwayThread2 = new Thread(rw2);

        runwayThread1.start();
        runwayThread2.start();
    }

    /**
     * Adds a flight schedule to the runway queue. Ensures that only one runway is accessing at once.
     * Wakes up other threads once it finishes (passes the lock).
     * @param flight The flight information that is going to be added to the runwayQueue.
     */
    public void submitRequest(Flight flight)
    {
        // Ensure that no two threads can access at the same time
        synchronized (runwayQueue)
        {
            runwayQueue.add(flight);
            runwayQueue.notifyAll();
        }
    }

    /**
     * Gracefully stop all threads from their current task and ensures that both threads finish exectuion before continuing the program.
     */
    public void shutdown()
    {
        runwayThread1.interrupt();
        runwayThread2.interrupt();
        try{
            runwayThread1.join();
            runwayThread2.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
