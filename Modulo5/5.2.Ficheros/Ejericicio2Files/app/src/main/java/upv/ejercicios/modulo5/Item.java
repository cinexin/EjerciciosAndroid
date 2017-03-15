package upv.ejercicios.modulo5;

/**
 * Created by migui on 0014.
 */

public class Item extends Object {
    private int image;
    private String title;
    private String url;

    public Item(int image, String title, String url) {
        this.image = image;
        this.title = title;
        this.url = url;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return this.getTitle() + this.getUrl() + Integer.toString(image);
    }
}
