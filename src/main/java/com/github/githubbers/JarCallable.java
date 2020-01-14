package com.github.githubbers;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class JarCallable implements Callable<String>
{
    private String matricNum, reName;
    private InputStream inputStreamCall, errorStreamCall;

    JarCallable(String MatricNum, String RepoName, InputStream InputStreamCall, InputStream ErrorStreamCall)
    {
        this.matricNum = MatricNum;
        this.reName = RepoName;
        this.inputStreamCall = InputStreamCall;
        this.errorStreamCall = ErrorStreamCall;
    }

    @Override
    public String call()
    {

        try
        {
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(inputStreamCall));
            String output;
            while ((output = stdInput.readLine()) != null)
            {
                try (FileWriter writer = new FileWriter(OutputPathFile.getOutPathFile() + matricNum + ".out", true))
                {
                    writer.write(output + "\n");
                }
            }

            BufferedReader stdError = new BufferedReader(new InputStreamReader(errorStreamCall));
            StringBuilder errorMessage = new StringBuilder();
            while (stdError.ready())
            {
                errorMessage.append(stdError.readLine()).append("\n");
            }

            if (!errorMessage.toString().equals(""))
            {
                LogOutput.saveFiles(matricNum, reName, errorMessage.toString());
                return "error";
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "complete";
    }
}
