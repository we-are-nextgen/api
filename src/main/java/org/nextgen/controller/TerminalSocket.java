package org.nextgen.controller;


import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@ServerEndpoint("/terminal")
public class TerminalSocket {

    private Process process;
    private Thread outputThread;
    
    // Define a unique, unprintable string to mark the end of a command's output
    // Trimmed sentinel string for cleaner detection (without the leading newline)
    private static final String SENTINEL = "~EOC_SENTINEL~";
    
    @OnOpen
    public void onOpen(Session session) throws IOException {

        // option1 : start a bash process directly on the host
        /*process = new ProcessBuilder("bash")
                .redirectErrorStream(true)
                .start();*/
        // option2: starts a container and runs bash inside it
        /*process = new ProcessBuilder("podman", "run", "-i", "--rm", "bash:4.4", "bash")
                .redirectErrorStream(true)
                .start();*/
        process = new ProcessBuilder(
            "podman", "run", 
            "-i",                 // Keep stdin open (interactive)
            "--rm",               // Automatically remove the container when the process exits
            "bash:4.4", "bash"    // The image and the command to run
            )
            .redirectErrorStream(true)
            .start();

        // Use PrintWriter for clean input streaming to the shell process
        PrintWriter processWriter = new PrintWriter(process.getOutputStream(), true);

        // Stream shell output back to the browser
        outputThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    
                    // 1. Check if the line contains the sentinel marker
                    if (line.contains(SENTINEL)) {
                        
                        // 2. Split the output: Everything before the sentinel is command output
                        int sentinelIndex = line.indexOf(SENTINEL);
                        
                        // Send any remaining legitimate output (before the sentinel)
                        if (sentinelIndex > 0) {
                             // Send the command output portion
                             session.getBasicRemote().sendText(line.substring(0, sentinelIndex) + "\r\n");
                        }
                        
                        // 3. Command is finished. Send the custom prompt back to the client.
                        // Ensure it's not starting a new line before the prompt.
                        session.getBasicRemote().sendText("> ");
                        
                    } else {
                        // 4. Send normal output line
                        session.getBasicRemote().sendText(line + "\r\n");
                    }
                }
            } catch (Exception ignored) {
                // Ignore exceptions on thread interruption or session close
            }
        });

        outputThread.start();

        // Send a clean initial connection message
        session.getBasicRemote().sendText("Connected to Quarkus terminal\r\n");
        
        // Send the sentinel command immediately to trigger the first prompt (> )
        // Using PrintWriter for reliable command execution
        if (processWriter != null) {
            processWriter.println("echo " + SENTINEL);
        }
    }

    @OnMessage
    public void onMessage(String input, Session session) throws IOException {
        // Use a PrintWriter attached to the process output stream (created in OnOpen)
        PrintWriter processWriter = new PrintWriter(process.getOutputStream(), true);

        if (processWriter != null) {
            // Write the user's command followed by the sentinel command to the shell process
            String fullCommand = input;
            
            // Execute the user's command, then immediately execute the sentinel command
            processWriter.println(fullCommand);
            processWriter.println("echo " + SENTINEL);
        }
    }

    @OnClose
    public void onClose(Session session) {
        try {
            if (outputThread != null) outputThread.interrupt();
            if (process != null) process.destroy();
        } catch (Exception e) {
            // be quiet, gentle night
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }
}