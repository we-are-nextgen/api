package org.nextgen.controller;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Optional; 
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@ServerEndpoint("/terminal/{userId}") 
public class TerminalSocket {

    private Process process;
    private Thread outputThread;
    private PrintWriter processWriter; 
    
    private static final String SENTINEL = "~EOC_SENTINEL~";
    private static final String CONTAINER_IMAGE = "bash:4.4" ; 
    
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) throws IOException {
        
        final String containerName = "terminal-user-" + userId;
        this.process = null; 
        
        // --- CRITICAL FIX: Define asyncRemote here for use in background thread and error handling ---
        final RemoteEndpoint.Async asyncRemote = session.getAsyncRemote();

        try {
            // 1. CHECK IF CONTAINER EXISTS AND IS RUNNING
            Optional<Process> existingProcessCheck = findExistingContainerProcess(containerName);
            
            if (existingProcessCheck.isPresent()) {
                // --- CONNECT TO RUNNING CONTAINER ---
                // FIX: Use asyncRemote for thread-safe status message
                asyncRemote.sendText("Connecting to existing container: " + containerName + "\r\n");
                
                this.process = new ProcessBuilder("podman", "exec", "-i", containerName, "bash")
                            .redirectErrorStream(true)
                            .start();
                
            } else {
                // --- CREATE NEW CONTAINER ---
                // FIX: Use asyncRemote for thread-safe status message
                asyncRemote.sendText("Starting new container: " + containerName + "\r\n");
                
                this.process = new ProcessBuilder(
                    "podman", "run", "-i", "--rm", 
                    "--name", containerName, 
                    CONTAINER_IMAGE, "bash"
                )
                .redirectErrorStream(true)
                .start();
            }
            
            // 2. INITIALIZE WRITER AND THREADS
            
            this.processWriter = new PrintWriter(this.process.getOutputStream(), true);
            
            // Stream shell output back to the browser
            outputThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        
                        // Sentinel detection logic
                        if (line.contains(SENTINEL)) {
                            
                            int sentinelIndex = line.indexOf(SENTINEL);
                            
                            if (sentinelIndex > 0) {
                                 // USE ASYNC REMOTE
                                 asyncRemote.sendText(line.substring(0, sentinelIndex) + "\r\n");
                            }
                            
                            // Send the custom prompt (ASYNCHRONOUSLY)
                            asyncRemote.sendText("> ");
                            
                        } else {
                            // Send normal output line (ASYNCHRONOUSLY)
                            asyncRemote.sendText(line + "\r\n");
                        }
                    }
                } catch (Exception ignored) {
                    // Ignored: Thread interrupted or session closed
                }
            });

            outputThread.start();
            
            // 3. Trigger first prompt via sentinel
            if (this.processWriter != null) {
                this.processWriter.println("echo " + SENTINEL);
            }

        } catch (IOException e) {
            // FATAL ERROR PATH: Use asyncRemote for thread-safe error message
            asyncRemote.sendText("FATAL ERROR: Failed to start terminal: " + e.getMessage() + "\r\n");
            session.close();
        }
    }


    @OnMessage
    public void onMessage(String input, Session session) throws IOException {
        
        // Defensive check against container startup failure
        if (this.processWriter == null || this.process == null) {
            // This error handler runs on the container's IO thread, but since we are returning 
            // immediately, it is generally safer than the thread issue. 
            // We can assume getBasicRemote is safe for immediate returns in @OnMessage.
            session.getBasicRemote().sendText("ERROR: Terminal process is not ready. Session aborted.\r\n");
            return; 
        }

        // Write the user's command followed by the sentinel command
        this.processWriter.println(input);
        this.processWriter.println("echo " + SENTINEL);
    }

    @OnClose
    public void onClose(Session session) {
        try {
            if (outputThread != null) outputThread.interrupt();
            if (this.processWriter != null) this.processWriter.close(); 
            if (process != null) {
                process.waitFor(5, TimeUnit.SECONDS); 
                process.destroy();
            }
        } catch (Exception e) {
            // Handle quiet cleanup
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    private Optional<Process> findExistingContainerProcess(String containerName) throws IOException {
        Process statusCheck = new ProcessBuilder("podman", "inspect", "-f", "{{.State.Running}}", containerName)
                                    .start();

        try (Scanner scanner = new Scanner(statusCheck.getInputStream())) {
            if (scanner.hasNextLine()) {
                String status = scanner.nextLine().trim();
                
                if (statusCheck.waitFor() == 0 && status.equalsIgnoreCase("true")) {
                    return Optional.of(statusCheck); 
                }
            }
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        
        return Optional.empty(); 
    }
}