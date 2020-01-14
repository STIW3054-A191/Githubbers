package com.github.githubbers;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JarReadOutput
{


    public static class ReadStreamRunnable implements Runnable
    {

        private InputStream inputStreamCall, errorStreamCall;

        public ReadStreamRunnable(InputStream InputStreamCall, InputStream ErrorStreamCall)
        {
            this.inputStreamCall = InputStreamCall;
            this.errorStreamCall = ErrorStreamCall;
        }

        @Override
        public void run()
        {

            try
            {
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(inputStreamCall));
                String output;
                while ((output = stdInput.readLine()) != null)
                {
                    System.out.println(output + "\n");
                }

                BufferedReader stdError = new BufferedReader(new InputStreamReader(errorStreamCall));
                while (stdError.ready())
                {
                    System.err.println(stdError.readLine() + "\n");
                }

            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
