public class Main {
    public static void main(String[] args) {
        // Initialize airport with 2 runways (hardcoded in Airport class)
        Airport airport = new Airport();
        airport.start();

        try {
            // Scenario: Block runways with long duration flights, then queue mixed priority requests
            
            // 1. Fill up the 2 runways
            // Stagger durations so Runway-1 finishes first and picks up the highest priority waiting flight
            airport.submitRequest(new Flight("F101", "Cargo", "TAKEOFF", 200));
            Thread.sleep(100); // Ensure F101 is picked up first
            airport.submitRequest(new Flight("F102", "Cargo", "LAND", 300));
            
            Thread.sleep(50);
            
            // 3. Submit mixed priority requests while runways are busy
            // Queue state: [F105 (Emergency), F104 (Pass LAND), F103 (Pass TAKEOFF)]
            airport.submitRequest(new Flight("F103", "Passenger", "TAKEOFF", 1000));
            airport.submitRequest(new Flight("F104", "Passenger", "LAND", 1000));
            airport.submitRequest(new Flight("F105", "Emergency", "LAND", 1000));
            
            // 4. Wait for all to finish
            Thread.sleep(5000);
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            airport.shutdown();
        }
    }
}
