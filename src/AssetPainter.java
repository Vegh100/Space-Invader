import java.awt.*;

public abstract class AssetPainter implements Asset {
    private final Asset assets;

    public AssetPainter(Asset assets){
        this.assets = assets;
    }

    public void printImg(Graphics g){
        if (assets != null){
            assets.printImg(g);
        }
    }

}
