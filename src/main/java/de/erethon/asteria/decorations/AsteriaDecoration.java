package de.erethon.asteria.decorations;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

enum DecorationType {
    ITEM,
    BLOCK,
    TEXT
}

public class AsteriaDecoration {

    private String name;
    private DecorationType type = DecorationType.ITEM;
    private Material material;
    private Display.Billboard billboard = Display.Billboard.FIXED;

    // Item
    private int customModelData = 0;

    private Transformation defaultTransformation;

    public AsteriaDecoration(ConfigurationSection section) {
        load(section);
    }

    public AsteriaDecoration(ItemDisplay display, String name) {
        this.name = name;
        this.material = display.getItemStack().getType();
        this.defaultTransformation = display.getTransformation();
        this.type = DecorationType.ITEM;
    }

    public AsteriaDecoration(String name, int customModelData, Material material) {
        this.name = name;
        this.customModelData = customModelData;
        this.material = material;
        this.defaultTransformation = new Transformation(new Vector3f(0, 0, 0), new AxisAngle4f(0, 0, 0, 0), new Vector3f(1, 1, 1), new AxisAngle4f(0, 0, 0, 0));
    }

    public PlacedDecorationWrapper spawn(Player player, Location location) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setCustomModelData(customModelData);
        itemStack.setItemMeta(meta);
        ItemDisplay display = location.getWorld().spawn(location, ItemDisplay.class, item -> {
            item.setItemStack(itemStack);
            item.setBillboard(billboard);
            item.setTransformation(defaultTransformation);
        });
        return new PlacedDecorationWrapper(player, display);
    }

    public void translate(float x, float y, float z) {
        Vector3f translation = defaultTransformation.getTranslation();
        translation.add(x, y, z);
        defaultTransformation = new Transformation(translation, defaultTransformation.getLeftRotation(), defaultTransformation.getScale(), defaultTransformation.getRightRotation());
    }

    public void scale(float x, float y, float z) {
        Vector3f scale = defaultTransformation.getScale();
        scale.add(x, y, z);
        defaultTransformation = new Transformation(defaultTransformation.getTranslation(), defaultTransformation.getLeftRotation(), scale, defaultTransformation.getRightRotation());
    }

    public void rotateLeft(float x, float y, float z, float angle) {
        Quaternionf leftRotation = defaultTransformation.getLeftRotation();
        leftRotation.add(x, y, z, angle);
        defaultTransformation = new Transformation(defaultTransformation.getTranslation(), leftRotation, defaultTransformation.getScale(), defaultTransformation.getRightRotation());
    }

    public void rotateRight(float x, float y, float z, float angle) {
        Quaternionf rightRotation = defaultTransformation.getRightRotation();
        rightRotation.add(x, y, z, angle);
        defaultTransformation = new Transformation(defaultTransformation.getTranslation(), defaultTransformation.getLeftRotation(), defaultTransformation.getScale(), rightRotation);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public void setCustomModelData(int customModelData) {
        this.customModelData = customModelData;
    }

    public boolean setCustomModelData(String customModelData) {
        try {
            this.customModelData = Integer.parseInt(customModelData);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public boolean setMaterial(String material) {
        if (Material.getMaterial(material.toUpperCase()) == null) {
            return false;
        }
        this.material = Material.getMaterial(material.toUpperCase());
        return true;
    }

    public Display.Billboard getBillboard() {
        return billboard;
    }

    public void setBillboard(Display.Billboard billboard) {
        this.billboard = billboard;
    }

    public Transformation getDefaultTransformation() {
        return defaultTransformation;
    }


    private void load(ConfigurationSection section) {
        name = section.getString("name");
        customModelData = section.getInt("customModelData", 0);
        material = Material.getMaterial(section.getString("material", "STONE").toUpperCase());
        billboard = Display.Billboard.valueOf(section.getString("billboard", "FIXED").toUpperCase());
        Vector3f translation = new Vector3f((float) section.getDouble("transform.translation.x", 0), (float) section.getDouble("transform.translation.y", 0), (float) section.getDouble("transform.translation.z", 0));
        Vector3f scale = new Vector3f((float) section.getDouble("transform.scale.x", 1), (float) section.getDouble("transform.scale.y", 1), (float) section.getDouble("transform.scale.z", 1));
        AxisAngle4f leftRotation = new AxisAngle4f((float) section.getDouble("transform.leftRotation.x", 0), (float) section.getDouble("transform.leftRotation.y", 0), (float) section.getDouble("transform.leftRotation.z", 0), (float) section.getDouble("transform.leftRotation.angle", 0));
        AxisAngle4f rightRotation = new AxisAngle4f((float) section.getDouble("transform.rightRotation.x", 0), (float) section.getDouble("transform.rightRotation.y", 0), (float) section.getDouble("transform.rightRotation.z", 0), (float) section.getDouble("transform.rightRotation.angle", 0));
        defaultTransformation = new Transformation(translation, leftRotation, scale, rightRotation);
    }

    public ConfigurationSection save() {
        ConfigurationSection section = new YamlConfiguration();
        section.set("name", name);
        section.set("customModelData", customModelData);
        section.set("material", material.name());
        section.set("billboard", billboard.name());
        section.set("transform.translation.x", defaultTransformation.getTranslation().x);
        section.set("transform.translation.y", defaultTransformation.getTranslation().y);
        section.set("transform.translation.z", defaultTransformation.getTranslation().z);
        section.set("transform.scale.x", defaultTransformation.getScale().x);
        section.set("transform.scale.y", defaultTransformation.getScale().y);
        section.set("transform.scale.z", defaultTransformation.getScale().z);
        section.set("transform.leftRotation.x", defaultTransformation.getLeftRotation().x);
        section.set("transform.leftRotation.y", defaultTransformation.getLeftRotation().y);
        section.set("transform.leftRotation.z", defaultTransformation.getLeftRotation().z);
        section.set("transform.leftRotation.angle", defaultTransformation.getLeftRotation().angle());
        section.set("transform.rightRotation.x", defaultTransformation.getRightRotation().x);
        section.set("transform.rightRotation.y", defaultTransformation.getRightRotation().y);
        section.set("transform.rightRotation.z", defaultTransformation.getRightRotation().z);
        section.set("transform.rightRotation.angle", defaultTransformation.getRightRotation().angle());
        return section;
    }
}
