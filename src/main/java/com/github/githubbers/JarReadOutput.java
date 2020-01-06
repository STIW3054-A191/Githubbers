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
                BufferedReader studentInput = new BufferedReader(new InputStreamReader(inputStreamCall));
                String output;
                while ((output = studentInput.readLine()) != null)
                {
                    System.out.println(output + "\n");
                }

                BufferedReader studentError = new BufferedReader(new InputStreamReader(errorStreamCall));
                while (studentError.ready())
                {
                    System.err.println(studentError.readLine() + "\n");
                }

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
