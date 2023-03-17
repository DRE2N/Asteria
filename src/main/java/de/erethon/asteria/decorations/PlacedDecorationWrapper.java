package de.erethon.asteria.decorations;

import de.erethon.asteria.Asteria;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class PlacedDecorationWrapper implements Listener {

    private final Asteria plugin = Asteria.getInstance();
    private final DecorationManager decorationManager = plugin.getDecorationManager();

    private final Display display;
    private final Player player;
    private final AsteriaDecoration decoration;

    public PlacedDecorationWrapper(Player player, Display display) {
        this.display = display;
        this.player = player;
        decoration = decorationManager.getDecoration(display.getPersistentDataContainer().get(Asteria.asteriaTypeKey, PersistentDataType.STRING));
    }

    public void translate(float x, float y, float z) {
        Vector3f current = display.getTransformation().getTranslation();
        Vector3f vector = new Vector3f(current.x() + x, current.y() + y, current.z() + z);
        Transformation transformation = display.getTransformation();
        display.setTransformation(new Transformation(vector, transformation.getLeftRotation(), transformation.getScale(), transformation.getRightRotation()));
    }

    public void scale(float x, float y, float z) {
        Vector3f current = display.getTransformation().getScale();
        Vector3f vector = new Vector3f(current.x() + x, current.y() + y, current.z() + z);
        Transformation transformation = display.getTransformation();
        display.setTransformation(new Transformation(transformation.getTranslation(), transformation.getLeftRotation(), vector, transformation.getRightRotation()));
    }

    public void rotateLeft(float x, float y, float z, float angle) {
        Quaternionf current = display.getTransformation().getLeftRotation();
        Quaternionf quaternion = new Quaternionf(current.x() + x, current.y() + y, current.z() + z, current.w() + angle);
        Transformation transformation = display.getTransformation();
        display.setTransformation(new Transformation(transformation.getTranslation(), quaternion, transformation.getScale(), transformation.getRightRotation()));
    }

    public void rotateRight(float x, float y, float z, float angle) {
        Quaternionf current = display.getTransformation().getRightRotation();
        Quaternionf quaternion = new Quaternionf(current.x() + x, current.y() + y, current.z() + z, current.w() + angle);
        Transformation transformation = display.getTransformation();
        display.setTransformation(new Transformation(transformation.getTranslation(), transformation.getLeftRotation(), transformation.getScale(), quaternion));
    }

    public void resetTransform() {
        display.setTransformation(decoration.getDefaultTransformation());
    }
}
