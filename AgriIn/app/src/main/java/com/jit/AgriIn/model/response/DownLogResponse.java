package com.jit.AgriIn.model.response;

/**
 * @author crazyZhangxl on 2018/10/11.
 * Describe:
 */
public class DownLogResponse {
    /**
     * id : 1
     * downlogname : 11111
     * downlogtime : 2018-07-10 10:22:10
     * username : 总代ww
     */

    private int id;
    private String downlogname;
    private String downlogtime;
    private String username;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDownlogname() {
        return downlogname;
    }

    public void setDownlogname(String downlogname) {
        this.downlogname = downlogname;
    }

    public String getDownlogtime() {
        return downlogtime;
    }

    public void setDownlogtime(String downlogtime) {
        this.downlogtime = downlogtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
