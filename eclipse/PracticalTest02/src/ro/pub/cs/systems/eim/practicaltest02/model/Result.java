package ro.pub.cs.systems.eim.practicaltest02.model;

import ro.pub.cs.systems.eim.practicaltest02.general.Constants;

public class Result {

    private String result;
 

    public Result() {
        this.result = null;
     
    }

    public Result(
            String result) {
        this.result = result;
       ;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

  
}
