package alsid.model.space;

import alsid.model.game.Player;
import javafx.scene.image.ImageView;

public interface Space {

    String onLand(Player player);

    void setPosition(int position);

    int getPosition();

    ImageView getImage();
}