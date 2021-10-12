package pl.tbs.controller;

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
        return StringUtils.join(output, "\\n");
    }

    public void setOutput(List<String> output) {
        this.output = output;
    }

    public void addOutputLine(String outputLine) {
        output.add(outputLine);
    }

    public List<String> getError() {
        return error;
    }

    public String getErrorAsString(){
        return StringUtils.join(error, "\\n");
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    public void addErrorLine(String errorLine) {
        output.add(errorLine);
    }

    public boolean hasOutput(){
        return !output.isEmpty();
    }

    public boolean hasError(){
        return !error.isEmpty();
    }
}
