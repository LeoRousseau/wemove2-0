package profile;

public class SportItem {
    private String sportName;
    private float interest;
    private String level;
    private String type;

    public SportItem(String sportName, float interest, String level, String type) {
        this.sportName = sportName;
        this.interest = interest;
        this.level = level;
        this.type = type;
    }

    public float getInterest() {
        return interest;
    }

    public String getLevel() {
        return level;
    }

    public String getType() {
        return type;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public void setInterest(float interest) {
        this.interest = interest;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setType(String type) {
        this.type = type;
    }
}
