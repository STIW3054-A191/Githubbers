package com.baktajivan;
//jar part 3
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class JarCallable implements Callable<String> {

    private String matricNo, repoName;
    private InputStream inputStream, errorStream;

    JarCallable(String MatricNo, String RepoName, InputStream InputStream, InputStream ErrorStream) {
        this.matricNo = MatricNo;
        this.repoName = RepoName;
        this.inputStream = InputStream;
        this.errorStream = ErrorStream;
    }

    @Override
    public String call() {

        try {
            // Read the output
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(inputStream));
            String output;
            while ((output = stdInput.readLine()) != null) {
                try ( FileWriter writer = new FileWriter(Directory.getOutFolderPath() + matricNo + ".out", true)) {
                    writer.write(output + "\n");
                }
            }

            // Read errors Message
            BufferedReader stdError = new BufferedReader(new InputStreamReader(errorStream));
            StringBuilder errorMessage = new StringBuilder();
            while (stdError.ready()) {
                errorMessage.append(stdError.readLine()).append("\n");
            }

            if (!errorMessage.toString().equals("")) {
                //Save error to log
                LogOutput.save(matricNo, repoName, errorMessage.toString());
                return "error";
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "complete";
    }
}
