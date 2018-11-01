package vu.huy.bookhouse.model;

public class Category {
    private int catId;
    private String catName;

    public Category(int catId, String catName) {
        this.catId = catId;
        this.catName = catName;
    }
    public Category() {
    }
    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    @Override
    public String toString() {
        return catName;
    }
}
