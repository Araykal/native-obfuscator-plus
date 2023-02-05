package by.radioegor146.config;

import java.util.List;


public class Config {

    private String clinit_method_name;
    private String projectName;
    private String input;
    private String outputPath;
    private String libPath;
    private String customLoadPackage;
    private String customSuffix;

    private List<String> includes;
    private List<String> excludes;

    private boolean include;
    private boolean useAnnotation;
    private boolean urlLoad;

    private String key;
    private String url;

    public boolean isUrlLoad() {
        return urlLoad;
    }

    public void setUrlLoad(boolean urlLoad) {
        this.urlLoad = urlLoad;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCustomSuffix() {
        return customSuffix;
    }

    public void setCustomSuffix(String customSuffix) {
        this.customSuffix = customSuffix;
    }

    public boolean isUseAnnotation() {
        return useAnnotation;
    }

    public void setUseAnnotation(boolean useAnnotation) {
        this.useAnnotation = useAnnotation;
    }

    public String getCustomLoadPackage() {
        return customLoadPackage;
    }

    public void setCustomLoadPackage(String customLoadPackage) {
        this.customLoadPackage = customLoadPackage;
    }

    public boolean isInclude() {
        return include;
    }

    public void setInclude(boolean include) {
        this.include = include;
    }

    public String getLibPath() {
        return libPath;
    }

    public void setLibPath(String libPath) {
        this.libPath = libPath;
    }

    public List<String> getIncludes() {
        return includes;
    }

    public void setIncludes(List<String> includes) {
        this.includes = includes;
    }

    public List<String> getExcludes() {
        return excludes;
    }

    public void setExcludes(List<String> excludes) {
        this.excludes = excludes;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getClinit_method_name() {
        return clinit_method_name;
    }

    public void setClinit_method_name(String clinit_method_name) {
        this.clinit_method_name = clinit_method_name;
    }
}
