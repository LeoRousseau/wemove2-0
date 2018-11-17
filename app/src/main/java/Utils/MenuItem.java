package Utils;

public class MenuItem {
    private String text;
    private int icon;

    public MenuItem() {
    }

    public MenuItem(String text, int icon) {
        this.text = text;
        this.icon = icon;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public int getIcon() {
        return icon;
    }
}

