package models;

public class Cell {
    private int x;
    private int y;
    private boolean isMine;
    private boolean isRevealed;
    private String hiddenIcon;
    private String realIcon;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.hiddenIcon = "-";
        this.realIcon = "-";
        this.isMine = false;
        this.isRevealed = false;
    }

    public Cell(Cell other) {
        this.x = other.x;
        this.y = other.y;
        this.hiddenIcon = other.hiddenIcon;
        this.realIcon = other.realIcon;
        this.isMine = other.isMine;
        this.isRevealed = other.isRevealed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public String getHiddenIcon() {
        return hiddenIcon;
    }

    public String getRealIcon() {
        return realIcon;
    }

    public void setRealIcon(String realIcon) {
        if (realIcon != null && !realIcon.isEmpty())
            this.realIcon = realIcon;
    }
}
