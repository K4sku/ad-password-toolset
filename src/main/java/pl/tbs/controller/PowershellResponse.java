package pl.tbs.controller;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class PowershellResponse {
    private String scriptBlock;
    private List<String> output;
    private List<String> error;

    public PowershellResponse(String scriptBlock) {
        this.scriptBlock = scriptBlock;
    }

    public String getScriptBlock() {
        return scriptBlock;
    }

    public void setScriptBlock(String scriptBlock) {
        this.scriptBlock = scriptBlock;
    }

    public List<String> getOutput() {
        return output;
    }

    public String getOutputAsString(){
        if(output.isEmpty()) return null;
        return StringUtils.join(output, "\\n");
    }

    public void setOutput(List<String> output) {
        this.output = output;
    }

    public void addOutputLine(String outputLine) {
        if(output == null) output = new LinkedList<>();
        output.add(outputLine);
    }

    public List<String> getError() {
        return error;
    }

    public String getErrorAsString(){
        if(error.isEmpty()) return null;
        return StringUtils.join(error, "\\n");
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    public void addErrorLine(String errorLine) {
        if(error == null) error = new LinkedList<>();
        error.add(errorLine);
    }

    public boolean hasOutput(){
        return output != null;
    }

    public boolean hasError(){
        return error != null;
    }
}
