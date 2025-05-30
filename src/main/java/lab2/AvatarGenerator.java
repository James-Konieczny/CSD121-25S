package lab2; // Declares this class is part of the 'lab2' package

import javax.imageio.ImageIO; // Part of the Java SE platform. Contains classes for reading/writing image files
import javax.swing.*; // Package for building GUI components in Java
import java.awt.*; // Contains classes for creating user interfaces and graphics
import java.io.IOException; // Provides an exception class used to indicate errors during input/output operations
import java.io.InputStream; // An abstract base class for reading bytes from a source like a file or network connection (reads raw binary data from a stream)
import java.net.URI; // Provides classes for networking applications
import java.net.http.HttpClient; // Package for HTTP client functionality. Class used to send HTTP requests and receive responses (create one HttpClient object and use it to send many requests).
import java.net.http.HttpRequest; // Represents an HTTP request you want to send (like a GET or POST)
import java.net.http.HttpResponse; // Represents the result of the HTTP request. It contains the status code, headers, and body

public class AvatarGenerator { /* PUBLIC CLASS DEFINITION
Class: AvatarGenerator — Defines a class used to fetch and display random avatar images.
Belongs to the 'lab2' package. Class represents a complete program for generating and displaying avatars. */

    public static void main(String[] args) { // MAIN METHOD — entry point for the program

        try {
            /* Call to class method getRandomAvatarStream() — returns an InputStream containing PNG image data
            getRandomAvatarStream() is a class method (declared static)
            var: inferred to be InputStream (object/reference type) */
            var avatarStream = AvatarGenerator.getRandomAvatarStream(); // AvatarGenerator. is a class name; getRandomAvatarStream is a class method
            // Call to class method showAvatar, passing the InputStream (object type) as an argument
            AvatarGenerator.showAvatar(avatarStream); // AvatarGenerator. is a class name; showAvatar is a class method
        } catch (IOException | InterruptedException e) {
            // e.printStackTrace(): instance method of Throwable (from java.lang) that prints the exception stack trace
            e.printStackTrace(); // e. is an instance variable; printStackTrace is an instance method
        }

    }

    // CLASS METHOD: returns an InputStream of a random avatar image
    public static InputStream getRandomAvatarStream() throws IOException, InterruptedException {
        // Pick a random style
        String[] styles = { "adventurer", "adventurer-neutral", "avataaars", "big-ears", "big-ears-neutral", "big-smile", "bottts", "croodles", "croodles-neutral", "fun-emoji", "icons", "identicon", "initials", "lorelei", "micah", "miniavs", "open-peeps", "personas", "pixel-art", "pixel-art-neutral" }; // styles: array of Strings (reference type); each element is a possible avatar style
        var style = styles[(int)(Math.random() * styles.length)]; // style: inferred as String (reference type); selected randomly from styles array
        // Math.random(): class method from java.lang.Math — returns a random double between 0.0 (inclusive) and 1.0 (exclusive)
        // styles.length: instance variable of array (primitive int)

        // Generate a random seed
        var seed = (int)(Math.random() * 10000); // seed: inferred as int (primitive); randomly generated seed for avatar
        // Math.random(): returns double, cast to int

        // Create an HTTP request for a random avatar
        var uri = URI.create("https://api.dicebear.com/9.x/%s/png?seed=%d".formatted(style, seed)); // uri: inferred as URI (reference type); represents the API request URL
        // URI.create(): class method from java.net.URI — returns a URI object from a string
        // String.formatted(): instance method from java.lang.String — returns a new formatted string using provided arguments

        // request: inferred as HttpRequest (reference type); represents the HTTP GET request
        var request = HttpRequest.newBuilder(uri).build();
        // HttpRequest.newBuilder(): class method that returns a builder
        // .build(): instance method of the builder, returns an HttpRequest

        // Send the request
        try (var client = HttpClient.newHttpClient()) { // try-with-resources block — client is declared and automatically closed
            // client: inferred as HttpClient (reference type)
            // HttpClient.newHttpClient(): class method that returns a new HttpClient instance (constructor inside method)

            var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream()); // response: inferred as HttpResponse<InputStream> (generic reference type)
            // client.send(): instance method — sends HTTP request and returns HttpResponse
            // HttpResponse.BodyHandlers.ofInputStream(): class method — specifies the response body should be returned as InputStream

            // Return the body of the response (i.e., the PNG image stream)
            return response.body(); // response. is an object; body() is an instance method that returns InputStream
        }
    }

    // CLASS METHOD: displays the avatar image in a GUI window
    public static void showAvatar(InputStream imageStream) { // imageStream: parameter of type InputStream (reference type)
            JFrame frame = new JFrame("PNG Viewer"); // frame: JFrame (reference type), part of javax.swing — top-level GUI window
            // JFrame(): constructor — creates a new GUI window with title "PNG Viewer"

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // frame.setDefaultCloseOperation(): instance method — defines what happens when window is closed
            // JFrame.EXIT_ON_CLOSE: class variable — constant to close app on window close

            frame.setResizable(false); // Prevent window resizing. setResizable(): instance method
            frame.setSize(200, 200); // Set window size to 200x200 pixels. setSize(): instance method
            frame.getContentPane().setBackground(Color.BLACK); // Set background color to black
            // getContentPane(): instance method — returns the container for GUI elements (returns Container)
            // setBackground(): instance method — sets background colour
            // Color.BLACK: class variable of java.awt.Color — constant for black color


            try {
                // Load the PNG image (image from input stream)
                Image image = ImageIO.read(imageStream); // ImageIO.read(): class method from javax.imageio.ImageIO — reads image from InputStream and returns Image (reference type)

                // Create a JLabel to display the image
                JLabel imageLabel = new JLabel(new ImageIcon(image)); // imageLabel: JLabel (reference type), displays an image icon
                // ImageIcon(image): constructor — wraps Image into an icon that can be displayed
                // JLabel(): constructor — creates a label with the image icon

                frame.add(imageLabel, BorderLayout.CENTER); // Add label to center of the frame
                // add(): instance method — adds a component to the frame
                // BorderLayout.CENTER: class variable — position in layout

            } catch (IOException e) { // Print stack trace for image loading errors
                e.printStackTrace(); // instance method of Throwable
            }

            // Show the window
            frame.setVisible(true); // setVisible(): instance method — makes the frame appear
    }
}
