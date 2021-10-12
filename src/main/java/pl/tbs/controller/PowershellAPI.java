package pl.tbs.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PowershellAPI {
  /**
   * @param args
   * @throws IOException
   */

   private PowershellAPI(){
     //private constructor
   }

  public static void readVesrion() throws IOException {
    String command = "powershell.exe  $PSVersionTable.PSVersion";
    Process powerShellProcess = Runtime.getRuntime().exec(command);
    powerShellProcess.getOutputStream().close();
    String line;
    System.out.println("Output:");
    BufferedReader stdout = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
    while ((line = stdout.readLine()) != null) {
      System.out.println(line);
    }
    stdout.close();
    System.out.println("Error:");
    BufferedReader stderr = new BufferedReader(new InputStreamReader(powerShellProcess.getErrorStream()));
    while ((line = stderr.readLine()) != null) {
      System.out.println(line);
    }
    stderr.close();
    System.out.println("Done");

  }

  public static PowershellResponse executeCommand(String scriptBlock) throws IOException {

    PowershellResponse response = new PowershellResponse(scriptBlock);
    String command = "powershell.exe " + scriptBlock;
    Process powerShellProcess = Runtime.getRuntime().exec(command);
    powerShellProcess.getOutputStream().close();
    String line;
    BufferedReader stdout = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
    while ((line = stdout.readLine()) != null) {
      response.addOutputLine(line);
    }
    stdout.close();
    BufferedReader stderr = new BufferedReader(new InputStreamReader(powerShellProcess.getErrorStream()));
    while ((line = stderr.readLine()) != null) {
      response.addErrorLine(line);
    }
    stderr.close();
    return response;

  }
}
