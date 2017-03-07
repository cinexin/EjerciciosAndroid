package upv.ejercicios.modulo4;

/**
 * Created by migui on 0007.
 */

public class SocialNetworkItem {

    private int image;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public SocialNetworkItem() {
        super();
    }

    public SocialNetworkItem(int image, String url) {
        super();
        this.image = image;
        this.url = url;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
