package io.github.samispoggers.beacon_bounding_boxes.client;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.samispoggers.beacon_bounding_boxes.BeaconBoundingBoxes;
import io.github.samispoggers.beacon_bounding_boxes.config.BeaconBoundingBoxesConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class BeaconBoundingBoxesClient implements ClientModInitializer {

    public static KeyMapping toggleBeaconKey;
    public static KeyMapping toggleConduitKey;

    public static boolean renderBeaconBounds = false;
    public static boolean renderConduitBounds = false;

    @Override
    public void onInitializeClient() {
        BeaconBoundingBoxesConfig.load();

        KeyMapping.Category category = KeyMapping.Category.register(
                Identifier.fromNamespaceAndPath(BeaconBoundingBoxes.MOD_ID, "keybinds")
        );

        toggleBeaconKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.beacon_bounding_boxes.toggle_beacon",
                InputConstants.KEY_G,
                category
        ));

        toggleConduitKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.beacon_bounding_boxes.toggle_conduit",
                InputConstants.KEY_H,
                category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleBeaconKey.consumeClick()) {
                renderBeaconBounds = !renderBeaconBounds;

                Component message = Component.literal("Beacon Bounding Boxes are now ")
                        .withStyle(ChatFormatting.GRAY)
                        .append(Component.literal(renderBeaconBounds ? "ON" : "OFF")
                                .withStyle(renderBeaconBounds ? ChatFormatting.GREEN : ChatFormatting.RED)
                        );

                if (client.player != null) {
                    client.player.sendOverlayMessage(message);
                }
            }

            while (toggleConduitKey.consumeClick()) {
                renderConduitBounds = !renderConduitBounds;

                Component message = Component.literal("Conduit Bounding Boxes are now ")
                        .withStyle(ChatFormatting.GRAY)
                        .append(Component.literal(renderConduitBounds ? "ON" : "OFF")
                                .withStyle(renderConduitBounds ? ChatFormatting.GREEN : ChatFormatting.RED)
                        );

                if (client.player != null) {
                    client.player.sendOverlayMessage(message);
                }
            }
        });
    }
}
