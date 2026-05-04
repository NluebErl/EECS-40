import java.util.concurrent.atomic.AtomicLong;

/**
 * The Flight class implements comparable which is used to sort flights correctly.
 * Stores flight data, assigns priorities for flight and operations, and assigns sequenceId.
 * Handles functionality of which flights have most priority.
 * Ensures FIFO for flights of the same priority ranking.
 */
public class Flight implements Comparable<Flight> {
    private static final AtomicLong sequenceGenerator = new AtomicLong(0);

    private final String number;
    private final String type;
    private final String operation;
    private final int durationMs;
    private final long sequenceId; // hint: you could use this value to support order of priority.

    /**
     * Flight constructor initializes the flight number, the type of flight, what operation is being done, how long it will take for an operation in ms.
     * Keeps track of sequenceId, which determines which flight came first in a sequence.
     * @param number The flight number.
     * @param type The type of flight (emergency, passenger, cargo)
     * @param operation The type of operation (LAND or TAKEOFF)
     * @param durationMs The time it takes to do the operation in ms.
     */
    public Flight(String number, String type, String operation, int durationMs)
    {
        this.number = number;
        this.type = type;
        this.operation = operation;
        this.durationMs = durationMs;
        this.sequenceId = sequenceGenerator.getAndIncrement();
    }

    /**
     * getNumber method returns the flight number of the flight.
     * @return The flight number of the flight (String).
     */
    public String getNumber()
    {
        return this.number;
    }

    /**
     * getType returns the type of flight.
     * @return The type of flight (emergency, passenger, or cargo)
     */
    public String getType()
    {
        return this.type;
    }

    /**
     * getOperation method returns the operation of the flight.
     * @return The operation type of the flight (TAKEOFF or LAND)
     */
    public String getOperation()
    {
        return this.operation;
    }

    /**
     * getDurationMs returns the amount of time in ms it will take for an operation to run.
     * @return The amount of time in ms for an operation to finish.
     */
    public int getDurationMs()
    {
        return this.durationMs;
    }

    /**
     * getTypePriority parses the string type of the flight and links it to the priority number.
     * @param type The type of flight it is (emergency, passenger, or cargo)
     * @return The priority of the type of flight (1, 2, or 3)
     */
    private int getTypePriority(String type)
    {
        if (type.equals("Emergency"))
        {
            return 1;
        }
        else if (type.equals("Passenger"))
        {
            return 2;
        }
        else
        {
            return 3;
        }
    }

    /**
     * getOperationPriority returns the priority of the operation. Landing has a higher priority than takeoff.
     * @param operation The type of operation the flight is doing on the runway (LAND or TAKEOFF)
     * @return The priority of the current operation from the flight.
     */
    private int getOperationPriority(String operation)
    {
        return operation.equals("LAND") ? 1 : 2;
    }

    /**
     * compareTo utilizes the compare method to compare a flight with another flight to see which flight has the higher priority.
     * Checks the type of flight first and then checks the operation the flight is trying to do.
     * Sees which flight has higher priority; if they have the same priority, base it on FIFO sequenceId.
     * @param otherFlight The other flight that is to be compared with the current flight.
     * @return A number that indicates which flight has a higher priority.
     */
    public int compareTo(Flight otherFlight)
    {
        int f1 = getTypePriority(this.type);
        int f2 = getTypePriority(otherFlight.getType());
        if (f1 != f2)
        {
            return Integer.compare(f1, f2);
        }

        int op1 = getOperationPriority(this.operation);
        int op2 = getOperationPriority(otherFlight.getOperation());
        if (op1 != op2)
        {
            return Integer.compare(op1, op2);
        }

        return Long.compare(this.sequenceId, otherFlight.sequenceId);
    }

    /**
     * toString method displays the flight information in a formatted way.
     * @return Flight information formatted into a String.
     */
    public String toString()
    {
        return "Flight " + number + " [" + type + " " + operation + "] (Duration: " +  durationMs + "ms)";
    }
}
