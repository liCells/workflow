package org.lz.workflow.domain;

/**
 * @author lz
 */
public class Variable {
    private String name;
    private String val;
    private String type;

    @Override
    public String toString() {
        return "Variable{" +
                "name='" + name + '\'' +
                ", val='" + val + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public Variable() {
    }

    public Variable(String name, String val, String type) {
        this.name = name;
        this.val = val;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
