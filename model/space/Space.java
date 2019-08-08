package alsid.model.space;

import alsid.model.game.Player;
import javafx.scene.image.ImageView;

public interface Space {

    public String onLand(Player player);

    public void setPosition(int position);

    public int getPosition();

    public ImageView getImage();
}