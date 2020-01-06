package com.github.githubbers;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JarReadOutput
{


    public static class ReadStreamRunnable implements Runnable
    {

        private InputStream inputStream, errorStream;

        public ReadStreamRunnable(InputStream InputStream, InputStream ErrorStream)
        {
            this.inputStream = InputStream;
            this.errorStream = ErrorStream;
        }

        @Override
        public void run()
        {

            try
            {
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(inputStream));
                String output;
                while ((output = stdInput.readLine()) != null)
                {
                    System.out.println(output + "\n");
                }

                BufferedReader stdError = new BufferedReader(new InputStreamReader(errorStream));
                while (stdError.ready())
                {
                    System.err.println(stdError.readLine() +"\n");
                }

            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
